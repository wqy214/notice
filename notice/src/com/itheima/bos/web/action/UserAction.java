package com.itheima.bos.web.action;
import java.io.IOException;

import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import com.itheima.bos.domain.User;

import com.itheima.bos.web.action.base.BaseAction;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.util.ValueStack;

@Controller//("abc")
@Scope("prototype")
public class UserAction extends BaseAction<User>{

	

	public String login(){

		
		//判断用户输入的验证码是否正确
		if("admin".equals(model.getUsername()) && "password".equals(model.getPassword())){

				//登录成功,将User放入session域，跳转到系统首页
				ServletActionContext.getRequest().getSession().setAttribute("loginUser", model);
				return "home";
			}else{
				//登录失败，设置错误提示信息，跳转到登录页面
				this.addActionError("用户名或密码错误");
				return "login";

		}
	}
	
	/**
	 * 用户退出
	 */
	public String logout(){
		//销毁session
		ServletActionContext.getRequest().getSession().invalidate();
		return "login";
	}

}
