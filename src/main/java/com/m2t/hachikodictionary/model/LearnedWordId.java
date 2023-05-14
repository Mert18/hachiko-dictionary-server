package com.m2t.hachikodictionary.model;

import java.io.Serializable;
import java.util.Objects;

public class LearnedWordId implements Serializable {

    private Account account;
    private Word word;


    @Override
    public int hashCode() {
        return Objects.hash(account, word);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof LearnedWordId)) return false;
        LearnedWordId that = (LearnedWordId) obj;
        return Objects.equals(account, that.account) &&
                Objects.equals(word, that.word);
    }
}
