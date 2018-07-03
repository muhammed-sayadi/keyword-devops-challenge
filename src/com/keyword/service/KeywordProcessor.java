package com.keyword.service;

/**
 * Processes given keyword and generates score
 */
public class KeywordProcessor {

    private String keyword;

    public KeywordProcessor(String keyword) {
        this.keyword = keyword;
    }

    public int getScore() throws Exception {
        if (keyword == null || keyword.length() == 0) {
            throw new Exception("Empty keyword");
        }

        /* dummy implementation */
        return Math.abs(keyword.hashCode() % 100);
    }

}
