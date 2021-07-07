package com.glacier.common.core.entity.dto;


import java.io.Serializable;

/**
 * ID请求模型
 * @author glacier
 * @version 1.0
 * date 2020-05-23 21:53
 */
public class IdDto implements Serializable {
    private static final long serialVersionUID = -5618778600490092506L;
    /**
     * 主键
     */
    protected String id;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "IdDto{" +
                "id='" + this.id + '\'' +
                '}';
    }
}
