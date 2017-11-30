package com.itheima.bos.dao;

import com.itheima.bos.dao.base.IBaseDao;
import com.itheima.bos.domain.Staff;

import java.util.List;

public interface IStaffDao extends IBaseDao<Staff>{

    List<Staff> findByName(String name);

    List<Staff> findByNotice();

}
