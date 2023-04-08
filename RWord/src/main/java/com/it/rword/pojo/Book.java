package com.it.rword.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class Book extends BasePojo{

    private Integer bid;

    private String bookName;

    private Integer amount;

    private String variety;

}
