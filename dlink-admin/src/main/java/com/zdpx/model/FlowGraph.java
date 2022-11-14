package com.zdpx.model;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("zdpx_task_flow_graph")
public class FlowGraph implements Serializable {
    private Integer id;
    /**
     *
     */
    private Integer taskId;

    /**
     *
     */
    private String script;

    /**
     *
     */
    private Date createTime;

    /**
     *
     */
    private Date updateTime;

}
