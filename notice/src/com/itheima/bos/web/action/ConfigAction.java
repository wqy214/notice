package com.itheima.bos.web.action;

import com.itheima.bos.domain.Config;
import com.itheima.bos.domain.Staff;
import com.itheima.bos.web.action.base.BaseAction;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 设置
 * @author zhaoqx
 *
 */
@Controller
@Scope("prototype")
public class ConfigAction extends BaseAction<Config>{

	public String toEdit(){
		//显查询数据库中原始数据
		int id = 1;
		Config config = configService.findById(id);

		ServletActionContext.getRequest().setAttribute("config",config);
		return "list";
	}


	public String edit(){
		//显查询数据库中原始数据
		int id = 1;
		Config config = configService.findById(id);

		//再按照页面提交的参数进行覆盖
		config.setEmail(model.getEmail());
		config.setNoticeDay(model.getNoticeDay());

		configService.update(config);
		ServletActionContext.getRequest().setAttribute("config",config);
		return "list";
	}


}
