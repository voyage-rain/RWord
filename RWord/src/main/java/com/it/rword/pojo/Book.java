package com.it.rword.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class Book extends BasePojo{

    private Integer bid;

    private String bookName;

    // 被用户加入书架的数量，用于反映该书的热度
    private Integer amount;

    private String variety;

    private Integer startWid;

    private Integer endWid;

}
