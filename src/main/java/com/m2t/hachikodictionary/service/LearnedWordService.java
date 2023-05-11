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
        try {
            logger.info("Updating learned word");
            Account account = accountService.findAccountById(updateLearnedWordRequest.getAccountId());
            if (account == null) {
                logger.error("Account not found");
                throw new AccountNotFoundException("Account not found.");
            }

            logger.info("Account found.");
            Word word = wordService.findWordById(updateLearnedWordRequest.getWordId());

            if (word == null) {
                logger.error("Word not found");
                throw new WordNotFoundException("Word not found.");
            }

            logger.info("Word found.");
            LearnedWord learnedWord = learnedWordRepository.findByAccountAndWord(account, word);

            logger.info("Learned word found.");


            if (learnedWord == null) {
                LearnedWord newLearnedWord = new LearnedWord(account, word, 0);
                if(updateLearnedWordRequest.getResult()){
                    newLearnedWord.setLevel(1);
                }else {
                    newLearnedWord.setLevel(-1);
                }
                learnedWordRepository.save(newLearnedWord);
                logger.info("Learned word created");
                return new Response(true, "Learned word created", newLearnedWord);
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

            logger.info("Learned word updated");
            return new Response(true, "Learned word updated.", learnedWord);
        } catch (Exception e) {
            logger.error("Error updating learned word", e);
            return new Response(false, "Error updating learned word");
        }
    }
}
