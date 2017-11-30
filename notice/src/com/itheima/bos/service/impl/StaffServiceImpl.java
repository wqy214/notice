package com.itheima.bos.service.impl;

import com.itheima.bos.domain.Staff;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.IStaffDao;
import com.itheima.bos.service.IStaffService;
import com.itheima.bos.utils.PageBean;

import java.util.List;

@Service
@Transactional
public class StaffServiceImpl implements IStaffService{
	//注入dao
	@Autowired
	private IStaffDao staffDao;

	public void save(Staff model) {
		staffDao.save(model);
	}

	public void pageQuery(PageBean pageBean) {
		staffDao.pageQuery(pageBean);
	}

	/**
	 * 批量删除
	 */
	public void deleteBatch(String ids) {
		String[] staffIds = ids.split(",");
		for (String id : staffIds) {
			Staff byId = staffDao.findById(new Integer(id));
			staffDao.delete(byId);
		}
	}

	public Staff findById(int id) {
		return staffDao.findById(id);
	}
	
	public void update(Staff staff) {
		staffDao.update(staff);
	}

	@Override
	public void saveBatch(List<Staff> list) {
			for (Staff region : list) {
				staffDao.saveOrUpdate(region);
			}

	}

	@Override
	public List<Staff> findByName(String name) {

		return staffDao.findByName(name);
	}

	@Override
	public void updateByEndTime(String endTime) {

		DetachedCriteria criteria = DetachedCriteria.forClass(Staff.class);
		criteria.add(Restrictions.le("endTime", endTime));
		List<Staff> list = staffDao.findByCriteria(criteria);
		if (list != null && list.size()>0) {
			for (Staff staff : list) {
				staff.setNeedNotice("1");
			}
		}
	}

	@Override
	public List<Staff> findByNotice() {
		return staffDao.findByNotice();
	}
}
