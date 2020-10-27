package com.teleport.fwoj_backend.service.Index;

import com.teleport.fwoj_backend.mapper.stateMapper;
import com.teleport.fwoj_backend.pojo.state;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service
public class stateServiceImpl implements stateService {

    @Autowired
    private stateMapper stateMapperObject;
    @Override
    public List<state> getStateList(Integer page, Integer pre) {
        int start = pre * (page - 1);
        int num = pre;
        return stateMapperObject.getStateList(start,num);
    }

    @Override
    public Integer getStateSum() {
        return stateMapperObject.getStateSum();
    }

    @Override
    public state getStateDetail(Integer id) {
        return stateMapperObject.getStateDetail(id);
    }

    @Override
    public boolean addState(int problemId, int authorId, Date date, String language, String code) {
        if(stateMapperObject.addState(problemId,authorId,date,language,code) == 1)
            return true;
        else
            return false;
    }
}
