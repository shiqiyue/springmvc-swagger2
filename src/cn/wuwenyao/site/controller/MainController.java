package cn.wuwenyao.site.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import springfox.documentation.annotations.ApiIgnore;
import cn.wuwenyao.site.dto.base.RepBaseDTO;
import cn.wuwenyao.site.dto.base.ResultCode;
import cn.wuwenyao.site.dto.rep.user.RepUserInfo;


@ApiIgnore
@Controller
@RequestMapping("/")
public class MainController {

	@RequestMapping("/")
	public String main() {
		System.out.println("main");
		return "main";
	}

	@RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "根据用户id获取用户对象", httpMethod = "GET", response = RepBaseDTO.class, notes = "根据用户id获取用户对象")
	public RepBaseDTO getUserByName(
			@ApiParam(required = true, name = "id", value = "用户id") @PathVariable String id)
			throws Exception {
		RepUserInfo data = new RepUserInfo();
		RepBaseDTO repDTO = new RepBaseDTO();

		repDTO.setData(data);
		repDTO.setCode(ResultCode.SUCCESS);
		repDTO.setMes("成功");
		return repDTO;
	}
}
