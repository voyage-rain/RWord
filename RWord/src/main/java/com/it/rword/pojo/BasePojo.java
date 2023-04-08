package com.it.rword.pojo;


import lombok.Data;

import java.util.Date;

@Data
public class BasePojo {

    private String creator;

    private Date createTime;

    private String modifyPeople;

    private Date modifyTime;

}
