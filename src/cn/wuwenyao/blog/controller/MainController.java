package cn.wuwenyao.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")
public class MainController {
	
	@RequestMapping("/")
	@ResponseBody
	public String main(){
		return "main";
	}

}
