package com.it.rword.service;

import com.it.rword.pojo.Word;

import java.util.List;

public interface WordService {

    /**
     * 从数据库表中查询一定数量的单词，放入后端的缓存中
     * @param uid 用户id
     * @param bid 书籍id
     * @return 返回值为单词列表
     */
    List<Word> wordList(Integer uid, Integer bid);

    void updateRecord(Integer wid, Integer bid, Integer uid, String username);
}
