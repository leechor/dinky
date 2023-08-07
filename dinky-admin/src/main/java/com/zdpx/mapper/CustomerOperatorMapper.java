package com.zdpx.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zdpx.model.CustomerOperator;
import org.apache.ibatis.annotations.Mapper;
import org.dinky.mybatis.mapper.SuperMapper;

@Mapper
public interface CustomerOperatorMapper extends SuperMapper<CustomerOperator> {
}
