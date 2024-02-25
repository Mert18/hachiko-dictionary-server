package com.m2t.hachikodictionary.dto.quiz;

import com.m2t.hachikodictionary.util.Constants;
import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuizResponse {
    private String accountId;
    private int gameCount;
    private int correctCount;
    private int incorrectCount;
    private int notAnsweredCount;
    private int totalQuestions;
    private double correctRate;

    // Custom constructor to include the correctRate calculation
    public QuizResponse(String accountId, int gameCount, int correctCount, int incorrectCount, int notAnsweredCount, int totalQuestions) {
        this.accountId = accountId;
        this.gameCount = gameCount;
        this.correctCount = correctCount;
        this.incorrectCount = incorrectCount;
        this.notAnsweredCount = notAnsweredCount;
        this.totalQuestions = totalQuestions;
        this.correctRate = calculateCorrectRate();
    }

    private double calculateCorrectRate() {
        if (totalQuestions > 0) {
            double score = correctCount - Constants.QUIZ_UNANSWERED_PENALTY * incorrectCount - Constants.QUIZ_UNANSWERED_PENALTY * notAnsweredCount;
            double rate = score / totalQuestions;
            return Math.max(0.0, Math.min(rate, 1.0));
        } else {
            return 0.0;
        }
    }
}