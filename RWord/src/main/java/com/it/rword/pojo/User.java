package com.it.rword.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class User extends BasePojo{

    private Integer uid;

    private  String headPhoto;

    private String username;

    private String password;

    private String salt;

}
