package com.it.rword.mapper;

import com.it.rword.pojo.Word;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

@Mapper
public interface WordMapper {
    /**
     * 根据wid和count从数据库一次性读出单词数据
     * @param wid 单词id
     * @param count 一次查询的单词数量
     * @return 返回值为单词列表
     */
    List<Word> findOnce(Integer wid, Integer count);

    /**
     * 开始背单词的时候，找到上一次背单词的起始位置
     * @param uid 用户id
     * @param bid 书籍id
     * @return 返回值为起始位置
     */
    Integer findLastEnd(Integer uid, Integer bid);

    /**
     * 开始背单词的时候，找到上一次背单词的末位置
     * @param uid 用户id
     * @param bid 书籍id
     * @return 返回值为起始位置
     */
    Integer findLastStart(Integer uid, Integer bid);

    /**
     * 查找该书单词的末位置
     * @param bid 书籍id
     */
    Integer findEnd(Integer bid);

    /**
     * 查询用户背诵该书的时间
     * @param uid 用户id
     * @param bid 书籍id
     * @return 返回值为日期
     */
    Date currentDate(Integer uid, Integer bid);

    /**
     /**
     * 更新read表中的start和end字段
     * @param start 今日背书的起始位置
     * @param end 今日背书的末位置
     * @param bid 书籍id
     * @param uid 用户id
     * @return 返回值为受影响的行数
     */
    Integer record(Integer start, Integer end, Integer bid, Integer uid, String modifyUser, Date modifyTime);

    /**
     * 更新用户总背单词数
     * @param uid 用户id
     * @param count 今日背单词数量
     * @return 返回值为收影响的行数
     */
    Integer wordTotal(Integer uid, Integer count);
}
