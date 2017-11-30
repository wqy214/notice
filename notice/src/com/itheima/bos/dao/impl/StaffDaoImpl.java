package com.itheima.bos.dao.impl;

import com.itheima.bos.domain.Staff;

import org.springframework.stereotype.Repository;

import com.itheima.bos.dao.IStaffDao;
import com.itheima.bos.dao.base.impl.BaseDaoImpl;

import java.util.List;

@Repository
public class StaffDaoImpl extends BaseDaoImpl<Staff> implements IStaffDao{

    @Override
    public List<Staff> findByName(String name) {
        String hql = "FROM Staff u WHERE u.name = ?";
        List<Staff> list = this.getHibernateTemplate().find(hql, name);
        return list;
    }

    @Override
    public List<Staff> findByNotice() {
        String hql = "FROM Staff u WHERE u.needNotice = ?";
        List<Staff> list = this.getHibernateTemplate().find(hql, "1");
        return list;
    }
}
