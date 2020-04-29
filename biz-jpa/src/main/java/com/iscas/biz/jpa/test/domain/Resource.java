package com.iscas.biz.jpa.test.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
*@auhor:zhuquanwen
*@date:2016年12月6日
*@desc:权限资源表
*/
@Entity
@Table(name="t_resource")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer","handler"})
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class Resource implements Serializable{
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private Integer id;

	@Column(name="permissionname")
	 private String permissionname;
	 
//	 @ManyToMany(cascade = {CascadeType.REFRESH})
//	   	@JoinTable(name = "t_role_resource", joinColumns = { @JoinColumn(name = "resource_id") }, inverseJoinColumns = {
//	   	            @JoinColumn(name = "role_id") })
	 @ManyToMany(mappedBy="resourceList")
	 @JsonIgnore
	 private List<Role> roleList;//
	 
	 @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH})
	 @JoinColumn(name = "app_id")  
	 private App app;//当前节点所属的应用系统
	 
	
	 
	 @OneToMany(mappedBy = "resourcex", fetch=FetchType.LAZY,cascade={CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.REMOVE})
	 @JsonIgnore
	 private List<UrlAddr> urlAddrList;
	 
	 private String remark;

	 /*是否具有此资源*/
	 @Transient
	 private boolean permissionFlag = false;

}
