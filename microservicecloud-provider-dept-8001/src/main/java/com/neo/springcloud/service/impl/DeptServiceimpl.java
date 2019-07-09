package com.neo.springcloud.service.impl;
import com.neo.springcloud.entities.Dept;
import com.neo.springcloud.dao.DeptDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import com.neo.springcloud.service.DeptService;

import java.util.List;

@Service
public class DeptServiceimpl implements DeptService {
    @Autowired
    private DeptDao dao;

    @Override
    public boolean add(Dept dept)
    {
        return dao.addDept(dept);
    }

    @Override
    public Dept get(Long id)
    {
        return dao.findById(id);
    }

    @Override
    public List<Dept> list()
    {
        return dao.findAll();
    }

}