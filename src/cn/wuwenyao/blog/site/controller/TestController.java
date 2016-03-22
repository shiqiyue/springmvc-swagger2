package cn.wuwenyao.blog.site.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/test")
public class TestController {

	@RequestMapping(value = "/t1", params = { "info" })
	public String test1(@RequestParam("info") String info, Model model) {
		System.out.println("test1");
		model.addAttribute("info", info);
		return "test";
	}

	@RequestMapping(value = "/t2")
	public String test2(Model model) {
		System.out.println("test2");
		model.addAttribute("info", "not info");

		return "test";
	}
}
