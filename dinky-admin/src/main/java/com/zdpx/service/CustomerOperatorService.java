package com.zdpx.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.zdpx.mapper.CustomerOperatorMapper;
import com.zdpx.model.CustomerOperator;
import com.zdpx.model.FlowGraph;
import org.dinky.mybatis.service.ISuperService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

public interface CustomerOperatorService extends ISuperService<CustomerOperator> {
    String saveByName(CustomerOperator customerOperator);

    String selectByName(String name);

    String updateName(String oldName, String newName);

    int delete(String name);
}
