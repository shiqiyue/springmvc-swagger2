package cn.wuwenyao.site.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wuwenyao.site.entity.User;
import cn.wuwenyao.site.entity.enums.Sex;
import cn.wuwenyao.site.service.UserService;

@Api(tags = "用户api", description = "用户操作")
@Controller
@RequestMapping("user")
public class UserController {

	@Autowired
	private UserService userService;

	@ApiOperation("保存")
	@RequestMapping("save")
	@ResponseBody
	public boolean saveUser(User u) {
		User user = new User();
		user.setMobileno("321313");
		user.setUsername("sssss");
		user.setPassword("dsadasd");
		user.setSalt("dsadasd");
		user.setSex(Sex.male);
		return userService.saveUser(user);
	}
}
