package com.itheima.bos.service;

import com.itheima.bos.domain.Config;
import com.itheima.bos.domain.Staff;
import com.itheima.bos.utils.PageBean;

import java.util.List;

public interface IConfigService {

	public Config findById(int id);

	public void update(Config config);

}
