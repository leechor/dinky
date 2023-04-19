package org.dinky.service;


import org.dinky.db.service.ISuperService;
import org.dinky.model.FlowGraph;
import org.dinky.model.Task;

/**
 *
 */
public interface TaskFlowGraphService extends ISuperService<FlowGraph> {

    boolean insert(FlowGraph statement);

    boolean saveOrUpdateTask(Task task);

}
