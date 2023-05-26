/*
 *
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package org.dinky.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.dinky.common.result.Result;
import org.dinky.service.WatchTableService;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("api")
@Api(value = "api", tags = "数据查看方法 watch 相关接口")
public class WatchTableController {

    private final WatchTableService watchTableService;

    @PutMapping("/subscribe/watch")
    @ApiOperation(value = "订阅表，可在控制台查看表数据", notes = "订阅表，可在控制台查看表数据")
    public Result subscribe(@RequestParam Integer id, @RequestParam String table) {
        String destination = watchTableService.registerListenEntry(id, table);
        return Result.succeed(destination);
    }

    @PutMapping("/unSubscribe/watch")
    @ApiOperation(value = "取消订阅", notes = "取消订阅")
    public Result unsubscribe(@RequestParam Integer id, @RequestParam String table) {
        watchTableService.unRegisterListenEntry(id, table);
        return Result.succeed();
    }
}
