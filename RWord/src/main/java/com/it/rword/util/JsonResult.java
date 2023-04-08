package com.it.rword.util;

import lombok.Data;

import java.io.Serializable;

/**
 * 创建响应：状态码、状态描述信息、数据。这部分功能封装再一个类中，将这类作为方法的返回值，返回给前端浏览器。
 * Json格式的数据进行响应
 */
@Data
public class JsonResult<E> implements Serializable {
    /**
     * 状态码
     */
    private Integer state;
    /**
     * 描述信息
     */
    private String message;
    /**
     * 数据
     */
    private E data;

    public JsonResult() {
    }

    public JsonResult(Integer state) {
        this.state = state;
    }

    public JsonResult(Throwable e) {
        this.message = e.getMessage();
    }

    public JsonResult(Integer state, E data) {
        this.state = state;
        this.data = data;
    }
}