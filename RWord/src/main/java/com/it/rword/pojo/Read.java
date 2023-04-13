package com.it.rword.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class Read extends BasePojo{

    private Integer rid;

    private Integer bid;

    private Integer uid;

    private Integer start;

    private Integer end;

    private Date date;
}
