package com.iscas.biz.jpa.test.domain;



import java.io.Serializable;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
*@auhor:zhuquanwen
*@date:2016年12月6日
*@desc:角色表
*/
@Entity
@Table(name = "t_role")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer","handler"})
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class Role implements Serializable{

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
	@Column
	private String orgName;

	@NotNull
	@Column(name="rolename")
    private String rolename;
//    @OneToMany(mappedBy = "role", fetch=FetchType.EAGER,cascade = {CascadeType.REFRESH,CascadeType.REMOVE,CascadeType.MERGE,CascadeType.PERSIST})
//    private List<Permission> permissionList;// 一个角色对应多个权限
//    @ManyToMany
//    @JoinTable(name = "t_user_role", joinColumns = { @JoinColumn(name = "role_id") }, inverseJoinColumns = {
//            @JoinColumn(name = "user_id") })
//    private List<User> userList;// 一个角色对应多个用户

    @ManyToMany(fetch=FetchType.EAGER,cascade = {CascadeType.REFRESH,CascadeType.MERGE,CascadeType.PERSIST})
   	@JoinTable(name = "t_role_resource", joinColumns = { @JoinColumn(name = "role_id") }, inverseJoinColumns = {
   	            @JoinColumn(name = "resource_id") })
   	private List<Resource> resourceList;// 
    
    @ManyToMany(cascade = {CascadeType.REFRESH,CascadeType.MERGE,CascadeType.PERSIST})
	@JoinTable(name = "t_request_data_role", joinColumns = { @JoinColumn(name = "role_id") }, inverseJoinColumns = {
	            @JoinColumn(name = "request_data_id") })
	private List<RequestData> requestDataList;// 
             
    @ManyToMany(cascade={CascadeType.MERGE})
	@JoinTable(name = "t_org_role",  joinColumns = { @JoinColumn(name = "role_id", referencedColumnName = "id") }, inverseJoinColumns = { @JoinColumn(name = "org_id", referencedColumnName = "id") })
    @JsonIgnore
	private List<Organizational> orgList;// 一个角色对应多 个组织结构节点

    @ManyToMany(cascade = {CascadeType.REFRESH,CascadeType.MERGE,CascadeType.PERSIST})
   	@JoinTable(name = "t_menu_role", joinColumns = { @JoinColumn(name = "role_id") }, inverseJoinColumns = {
   	          @JoinColumn(name = "menu_id") })
    private List<Menu> menus;
    
    private String remark;
}