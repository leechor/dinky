package com.zdpx.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("zdpx_customer_operator")
public class CustomerOperator implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String name;

    private String code;

    private String feature;

    private String groups;

    private String type;

    private String ports;

    private String script;

    private Date deleteTime;

    @TableField(exist = false)
    private String specification;//从文件中动态读取，不许要存入数据库

    @Override
    public String toString() {
        return "{\"name\": \""+name+"\","
                + "\"code\": \""+code+"\","
                + "\"feature\": \""+feature+"\","
                + "\"group\":\""+groups+"\","
                + "\"type\":\""+type+"\","
                + "\"ports\":"+ports+","
                + "\"specification\": "+specification+"}";
    }
}
