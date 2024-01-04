package com.m2t.hachikodictionary.service;

import com.m2t.hachikodictionary.dto.Response;
import com.m2t.hachikodictionary.dto.UpdateLearnedWordRequest;
import com.m2t.hachikodictionary.exception.AccountNotFoundException;
import com.m2t.hachikodictionary.exception.WordNotFoundException;
import com.m2t.hachikodictionary.model.Account;
import com.m2t.hachikodictionary.model.LearnedWord;
import com.m2t.hachikodictionary.model.Word;
import com.m2t.hachikodictionary.repository.LearnedWordRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class LearnedWordService {
    private static final Logger logger = LoggerFactory.getLogger(LearnedWordService.class);
    private final LearnedWordRepository learnedWordRepository;
    private final AccountService accountService;
    private final WordService wordService;

    public LearnedWordService(LearnedWordRepository learnedWordRepository, AccountService accountService, WordService wordService){
        this.learnedWordRepository = learnedWordRepository;
        this.accountService = accountService;
        this.wordService = wordService;
    }


    public Response updateLearnedWord(UpdateLearnedWordRequest updateLearnedWordRequest) {
        Account account = accountService.findAccountById(updateLearnedWordRequest.getAccountId());
        if (account == null) {
            throw new AccountNotFoundException("Account not found.");
        }

        Word word = wordService.findWordById(updateLearnedWordRequest.getWordId());

        if (word == null) {
            throw new WordNotFoundException("Word not found.");
        }

        LearnedWord learnedWord = learnedWordRepository.findByAccountAndWord(account, word);

        if (learnedWord == null) {
            LearnedWord newLearnedWord = new LearnedWord(account, word, 0);
            if(updateLearnedWordRequest.getResult()){
                newLearnedWord.setLevel(1);
            }else {
                newLearnedWord.setLevel(-1);
            }
            learnedWordRepository.save(newLearnedWord);
            logger.info("{} learned new word {}", account.getUsername(), word.getTitle());
            return new Response(true, "Learned word created", newLearnedWord, false);
        }

        if(updateLearnedWordRequest.getResult()){
            if(learnedWord.getLevel() < 2){
                learnedWord.setLevel(learnedWord.getLevel() + 1);
            }
        }else {
            if(learnedWord.getLevel() > -2){
                learnedWord.setLevel(learnedWord.getLevel() - 1);
            }
        }

        learnedWordRepository.save(learnedWord);
        return new Response(true, "Learned word updated.", learnedWord, false);
    }
}
