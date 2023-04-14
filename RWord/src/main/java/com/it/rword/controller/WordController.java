package com.it.rword.controller;

import com.it.rword.pojo.User;
import com.it.rword.util.JsonResult;

import com.it.rword.pojo.Word;
import com.it.rword.service.UserService;
import com.it.rword.service.WordService;
import com.it.rword.util.JsonResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/words")
public class WordController {

    private static final int OK = 200;

    @Resource
    private WordService wordService;

    @RequestMapping("/start")
    public JsonResult<List<Word>> wordList(Integer bid, HttpSession session) {
        System.out.println("bid"+bid);
        bid = 1;
        Integer uid = Integer.valueOf(session.getAttribute("uid").toString());
        List<Word> wordList = wordService.wordList(uid, bid);
        if(wordList == null){
            System.out.println("该书没添加到书架，不可背词");
            return new JsonResult<>(100);
        }
        for (Word word : wordList) {
            System.out.println(word);
        }
        return new JsonResult<>(OK, wordList);
    }

    @RequestMapping("/end")
    public JsonResult<Void> updateRecord(Integer wid, Integer bid, HttpSession session){
        Integer uid = Integer.valueOf(session.getAttribute("uid").toString());
        String username = session.getAttribute("username").toString();
        wordService.updateRecord(wid, bid, uid, username);
        return new JsonResult<>(OK);
    }
}
