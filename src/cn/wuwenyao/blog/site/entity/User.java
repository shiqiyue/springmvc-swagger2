package cn.wuwenyao.blog.site.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import cn.wuwenyao.blog.site.entity.base.IdEntity;
import cn.wuwenyao.blog.site.entity.enums.Sex;

/***
 * 用户
 * @author 文尧
 *
 */
@Entity
@Table(name = "user")
public class User extends IdEntity{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -462409132015864586L;

	private String username;
	
	private String mobileno;
	
	private String password;
	
	private String salt;
	
	private Sex sex;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getMobileno() {
		return mobileno;
	}

	public void setMobileno(String mobileno) {
		this.mobileno = mobileno;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public Sex getSex() {
		return sex;
	}

	public void setSex(Sex sex) {
		this.sex = sex;
	}
	
	
}
