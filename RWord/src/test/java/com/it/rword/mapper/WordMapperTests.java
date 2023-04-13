package com.it.rword.mapper;

import com.it.rword.pojo.Word;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class WordMapperTests {

    @Resource
    private WordMapper wordMapper;

    @Test
    public void findEnd() {
        System.out.println(wordMapper.findEnd(1));
    }

    @Test
    public void findOnce() {
        List<Word> wordList = wordMapper.findOnce(3, 5);
        for (Word word : wordList) {
            System.out.println(word);
        }
    }

    @Test
    public void findLastEnd() {
        System.out.println(wordMapper.findLastEnd(3, 1));
    }

    @Test
    public void currentDate() {
//        System.out.println(wordMapper.currentDate(3, 1).toString());
        Date date = wordMapper.currentDate(3, 1);
        // 截取日期
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String rememberDate = df.format(date);
        System.out.println(rememberDate);
        Date time = new Date();
        String currentTime = df.format(time);
        if (currentTime.equals(rememberDate)) {
            System.out.println("请继续背诵");
        } else {
            System.out.println("背诵完成");
        }
    }

    @Test
    public void record() {
        wordMapper.record(0, 1, 1, 3, "管理员", new Date());
    }

    @Test
    public void wordTotal() {
        wordMapper.wordTotal(1, 30);
    }
}
