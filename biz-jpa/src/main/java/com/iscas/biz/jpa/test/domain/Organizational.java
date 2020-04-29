package com.iscas.biz.jpa.test.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
*@auhor:zhuquanwen
*@date:2016年12月6日
*@desc:组织机构表
*/
@Entity
@Table(name="t_organizational")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer","handler"})
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class Organizational implements Serializable{
	 /**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	
	public Organizational() {
		super();
	}
	@Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private Integer id;//id
	 @Column(length=50)
	 private String name;//组织机构名称
	 @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH})  
	 @JoinColumn(name = "pid")
	 private Organizational parentOrg;//父节点Id
	 
	 @JsonIgnore
	 @OneToMany(targetEntity = Organizational.class,  mappedBy = "parentOrg" ,cascade = {CascadeType.ALL},fetch = FetchType.LAZY)
	 private List<Organizational> childOrgs;//当前拥有的子节点
	 
	 @ManyToOne(fetch = FetchType.LAZY/*, cascade = {CascadeType.MERGE}*/)
	 @JoinColumn(name = "app_id")  
	 private App app;//当前节点所属的应用系统
	 
	 @ManyToMany(mappedBy="orgList",fetch=FetchType.LAZY,cascade={CascadeType.MERGE})
//	 @JoinTable(name = "t_org_role", joinColumns = { @JoinColumn(name = "org_id") }, inverseJoinColumns = {
//	          @JoinColumn(name = "role_id") })
	 private List<Role> roleList;// 一个组织机构节点对应多个角色
	
	 @ManyToMany(mappedBy="orgList",fetch=FetchType.LAZY,cascade={CascadeType.MERGE})
//	 @JoinTable(name = "t_org_user", joinColumns = { @JoinColumn(name = "org_id") }, inverseJoinColumns = {
//	            @JoinColumn(name = "user_id") })
	 @JsonIgnore
	 private List<User> userList;// 一个组织结构节点对应着多个用户



}
