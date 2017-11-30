package com.itheima.bos.service.impl;

import com.itheima.bos.dao.IConfigDao;
import com.itheima.bos.dao.IStaffDao;
import com.itheima.bos.domain.Config;
import com.itheima.bos.domain.Staff;
import com.itheima.bos.service.IConfigService;
import com.itheima.bos.service.IStaffService;
import com.itheima.bos.utils.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ConfigerviceImpl implements IConfigService{
	//注入dao
	@Autowired
	private IConfigDao configDao;


	@Override
	public Config findById(int id) {
		return configDao.findById(id);
	}

	@Override
	public void update(Config config) {

	}
}
