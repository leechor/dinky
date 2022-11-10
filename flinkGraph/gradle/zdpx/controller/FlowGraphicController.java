package com.zdpx.controller;

import com.dlink.common.result.Result;
import com.dlink.dto.StudioExecuteDTO;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

/**
 *
 */
@Slf4j
@RestController
@RequestMapping("/api/zdpx")
public class FlowGraphicController {

    public FlowGraphicController() {
    }

    @PostMapping("submitSql")
    public Result<Void> submitSql(@RequestBody StudioExecuteDTO studioExecuteDTO) {

        return Result.succeed("");
    }

}
