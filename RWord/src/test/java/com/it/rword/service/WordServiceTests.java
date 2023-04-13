package com.it.rword.service;

import com.it.rword.pojo.Word;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class WordServiceTests {

    @Resource
    private WordService wordService;

    @Test
    public void wordList() {
        List<Word> wordList = wordService.wordList(3, 1);
        for (Word word : wordList) {
            System.out.println(word);
        }
    }

    @Test
    public void updateRecord() {
        wordService.updateRecord(8, 1, 3, "管理员");
    }
}
