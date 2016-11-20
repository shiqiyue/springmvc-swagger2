package cn.wuwenyao.site.controller;

import io.swagger.annotations.*;

import org.springframework.web.bind.annotation.*;

import cn.wuwenyao.site.entity.UamGroup;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by yuananyun on 2015/11/23.
 */
@RestController
@RequestMapping(value = "/group", produces = { "application/json;charset=utf-8" })
@Api(tags = { "group" }, description = "团队api")
public class GroupController {
	
	@RequestMapping(value = "addGroup", method = RequestMethod.PUT)
	@ApiOperation("添加一个新的群组")
	@ResponseBody
	public UamGroup addGroup(
			@ApiParam(required = true, value = "group data") @RequestBody UamGroup group) {
		return group;
	}

	@RequestMapping(value = "getAccessibleGroups", method = RequestMethod.GET)
	@ApiOperation(notes = "getAccessibleGroups", httpMethod = "GET", value = "获取我可访问的群组列表 ")
	@ResponseBody
	public List<UamGroup> getAccessibleGroups() {
		UamGroup group1 = new UamGroup();
		group1.setGroupId("1");
		group1.setName("testGroup1");

		UamGroup group2 = new UamGroup();
		group2.setGroupId("2");
		group2.setName("testGroup2");

		List<UamGroup> groupList = new LinkedList<UamGroup>();
		groupList.add(group1);
		groupList.add(group2);

		return groupList;
	}
}
