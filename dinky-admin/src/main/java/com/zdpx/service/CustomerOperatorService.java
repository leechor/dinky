package com.zdpx.service;

import org.dinky.mybatis.service.ISuperService;

import com.zdpx.model.CustomerOperator;

public interface CustomerOperatorService extends ISuperService<CustomerOperator> {
    String saveByName(CustomerOperator customerOperator);

    String selectByName(String name);

    String updateName(String oldName, String newName);

    int delete(String name);
}
