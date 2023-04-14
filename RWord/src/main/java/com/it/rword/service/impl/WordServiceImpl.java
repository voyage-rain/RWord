package com.it.rword.service.impl;

import com.it.rword.mapper.WordMapper;
import com.it.rword.pojo.Word;
import com.it.rword.service.WordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class WordServiceImpl implements WordService {

    @Resource
    private WordMapper wordMapper;

    private final static Integer num = 2;
    /**
     * 从数据库表中查询一定数量的单词，放入后端的缓存中
     *
     * @param uid 用户id
     * @param bid 书籍id
     * @return 返回值为单词列表
     */
    @Override
    public List<Word> wordList(Integer uid, Integer bid) {
        // 根据用户id查询该用户背单词的起始位置
        Integer start = wordMapper.findLastEnd(uid, bid);

        System.out.println(bid);
        System.out.println(uid);
        System.out.println(start);
        if (start == null) {
            System.out.println("用户没有将该书添加到书架");
            return null;
        }

        // 根据bid查询该书单词的末位置
        Integer end = wordMapper.findEnd(bid);

        System.out.println("end"+end);

        // 默认一次性从数据库查询100个单词作为缓存的单词列表
        // 判断从当前位置加上100个单词后是否超出该书的单词区间
        List<Word> wordList = null;
        if (start+num < end) {
            // 没有超过，则查询100个单词
            wordList = wordMapper.findOnce(start, num);
        } else {
            // 如果超过，则查询剩余单词
            wordList = wordMapper.findOnce(start, end - start);
        }

        if (wordList == null) {
            System.out.println("没有单词数据");
        }
        return wordList;
    }

    @Override
    public void updateRecord(Integer wid, Integer bid, Integer uid, String username) {
        // 获取上一次背单词的时间
        Date date = wordMapper.currentDate(uid, bid);
        // 获取日期(yyyy-MM-dd形式)
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String rememberDate = df.format(date);
        // 获取当前日期
        Date time = new Date();
        String currentTime = df.format(time);

        // 找到上一次背书的start和end
        Integer lastStart = wordMapper.findLastStart(uid, bid);
        Integer lastEnd = wordMapper.findLastEnd(uid, bid);

        // 如果上一次背单词的日期和当前日期相同（同日），则不更新start，反之，更新，每次更新需更新end
        int rows = 0;
        Integer start;
        if (rememberDate.equals(currentTime)) {
            // 存在问题：当超过100个单词时，存在更新问题（暂未解决）
            start = lastStart;
        } else {
            start = lastEnd;
            // 更新记录用户总背单词数
            Integer result = wordMapper.wordTotal(uid, lastEnd - lastStart);
            if (result != 1) {
                System.out.println("计数失败");
            }
        }
        rows = wordMapper.record(start, wid, bid, uid, username, new Date());

        if (rows == 1) {
            System.out.println("更新成功");
        } else {
            System.out.println("更新失败");
        }
    }
}
