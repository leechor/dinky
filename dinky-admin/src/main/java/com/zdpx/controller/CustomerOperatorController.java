package com.zdpx.controller;

import org.dinky.data.enums.Status;
import org.dinky.data.result.Result;

import com.zdpx.model.CustomerOperator;
import com.zdpx.service.CustomerOperatorService;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Api("自定义组节点相关接口")
@RequestMapping("/api/zdpx/customer")
public class CustomerOperatorController {

    private final CustomerOperatorService customerOperatorService;

    public CustomerOperatorController(CustomerOperatorService customerOperatorService) {
        this.customerOperatorService = customerOperatorService;
    }

    @GetMapping("/{name}")
    public Result<String> getCustomerOperator(@PathVariable String name) {
        return Result.succeed(customerOperatorService.selectByName(name), Status.SUCCESS);
    }

    @PostMapping("/save")
    public Result<String> saveCustomerOperator(@RequestBody CustomerOperator customerOperator) {
        String str = customerOperatorService.saveByName(customerOperator);
        if (str != null) {
            return Result.failed(str);
        }
        return Result.succeed();
    }

    @PostMapping("/oldName/{oldName}/newName/{newName}")
    public Result<String> updateName(@PathVariable String oldName, @PathVariable String newName) {
        String str = customerOperatorService.updateName(oldName, newName);
        if (str != null) {
            return Result.failed(str);
        }
        return Result.succeed();
    }

    @DeleteMapping("/delete/{name}")
    public Result<String> saveCustomerOperator(@PathVariable String name) {
        customerOperatorService.delete(name);
        return Result.succeed();
    }
}
