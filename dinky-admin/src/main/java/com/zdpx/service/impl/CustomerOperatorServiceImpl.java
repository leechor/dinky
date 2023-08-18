package com.zdpx.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zdpx.mapper.CustomerOperatorMapper;
import com.zdpx.model.CustomerOperator;
import com.zdpx.service.CustomerOperatorService;
import org.dinky.mybatis.service.impl.SuperServiceImpl;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CustomerOperatorServiceImpl extends SuperServiceImpl<CustomerOperatorMapper, CustomerOperator>
        implements CustomerOperatorService {

    private final CustomerOperatorMapper customerOperatorMapper;

    private static final Long DELETE = 1000*60*60*24*31L;

    public CustomerOperatorServiceImpl(CustomerOperatorMapper customerOperatorMapper) {
        this.customerOperatorMapper=customerOperatorMapper;
    }

    public String saveByName(CustomerOperator customerOperator){
        Wrapper<CustomerOperator> nameWrapper = new QueryWrapper<CustomerOperator>().eq("name", customerOperator.getName()).isNull("delete_time");
        CustomerOperator cus = customerOperatorMapper.selectOne(nameWrapper);
        if(cus!=null){
            return "算子名称重复，添加失败";
        }else{
            customerOperator.setFeature(null);
            customerOperator.setGroups("group-process");
            customerOperator.setCode("custom-node-"+customerOperator.getName()+"-"+customerOperator.getCode());
            if(customerOperator.getCode().equals("group-process")){
                customerOperator.setCode(customerOperator.getCode());
            }

            customerOperator.setType(null);
            customerOperator.setPorts("{\"inputs\":[],\"outputs\":[]}");

            customerOperatorMapper.insert(customerOperator);
        }
        return null;
    }

    public String selectByName(String name){
        return customerOperatorMapper.selectOne( new QueryWrapper<CustomerOperator>().eq("name",name).isNull("delete_time")).getScript();
    }

    @Override
    public String updateName(String oldName, String newName) {

        CustomerOperator oldCustomer = customerOperatorMapper.selectOne(new QueryWrapper<CustomerOperator>().eq("name", oldName).isNull("delete_time"));
        CustomerOperator newCustomer = customerOperatorMapper.selectOne(new QueryWrapper<CustomerOperator>().eq("name", newName).isNull("delete_time"));

        if(newCustomer!=null){
            return "算子名称重复，修改失败";
        }else if(oldCustomer==null){
            return "算子不存在，名称："+oldName;
        }else{
            oldCustomer.setName(newName);
            customerOperatorMapper.updateById(oldCustomer);
        }
        return null;
    }

    @Override
    public int delete(String name) {
        Wrapper<CustomerOperator> wrapper = new QueryWrapper<CustomerOperator>().eq("name", name).isNull("delete_time");
        CustomerOperator customerOperator = customerOperatorMapper.selectOne(wrapper);
        customerOperator.setDeleteTime(new Date());
        return customerOperatorMapper.update(customerOperator,wrapper);
    }

    @Scheduled(fixedDelay = 10*60*1000)
    public void deleteTime(){
        List<CustomerOperator> delete = customerOperatorMapper.selectList(new QueryWrapper<CustomerOperator>().isNotNull("delete_time"));
        delete.forEach(item->{
            if(new Date().getTime()-item.getDeleteTime().getTime()>DELETE){
                customerOperatorMapper.deleteById(item.getId());
            }
        });
    }

}
