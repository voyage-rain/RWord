package com.it.rword.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class User extends BasePojo{

    private Integer uid;

    private String headPhoto;

    private String username;

    private String password;

    private String salt;

    /**
     * status:
     *      为0 代表该用户存在
     *      为1 代表该用户被删除了
     */
    private Integer status;

}
