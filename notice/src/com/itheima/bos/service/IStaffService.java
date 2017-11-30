package com.itheima.bos.service;

import com.itheima.bos.domain.Staff;
import com.itheima.bos.utils.PageBean;

import java.util.List;

public interface IStaffService {

	public void save(Staff model);

	public void pageQuery(PageBean pageBean);

	public void deleteBatch(String ids);

	public Staff findById(int id);

	public void update(Staff staff);

    void saveBatch(List<Staff> list);

	List<Staff> findByName(String name);

	void updateByEndTime(String endTime);

	List<Staff> findByNotice();

}
