package cn.wuwenyao.blog.site.service;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.wuwenyao.blog.site.dao.UserDao;
import cn.wuwenyao.blog.site.entity.User;
import cn.wuwenyao.blog.site.service.base.BaseService;

@Service
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class UserService extends BaseService {
	private static final Logger log = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private UserDao userDao;

	public boolean saveUser(User user) {
		user = userDao.save(user);
		if (user == null) {
			log.error("save user fail");
			return false;
		}
		return true;
	}
}
