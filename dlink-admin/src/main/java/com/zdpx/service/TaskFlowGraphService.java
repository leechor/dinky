package com.zdpx.service;

import com.dlink.db.service.ISuperService;
import com.dlink.model.Task;

import com.zdpx.model.FlowGraph;

/**
 *
 */
public interface TaskFlowGraphService extends ISuperService<FlowGraph> {

    boolean insert(FlowGraph statement);

    boolean saveOrUpdateTask(Task task);

}
