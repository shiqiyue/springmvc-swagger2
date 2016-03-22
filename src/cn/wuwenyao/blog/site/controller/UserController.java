package cn.wuwenyao.blog.site.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wuwenyao.blog.site.entity.User;
import cn.wuwenyao.blog.site.entity.enums.Sex;
import cn.wuwenyao.blog.site.service.UserService;

@Controller
@RequestMapping("user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@RequestMapping("save")
	@ResponseBody
	public boolean saveUser(){
		User user = new User();
		user.setMobileno("321313");
		user.setUsername("sssss");
		user.setPassword("dsadasd");
		user.setSalt("dsadasd");
		user.setSex(Sex.male);
		return userService.saveUser(user);
	}
}
