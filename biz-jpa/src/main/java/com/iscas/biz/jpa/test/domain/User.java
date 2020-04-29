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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
*@auhor:zhuquanwen
*@date:2016年12月6日
*@desc:用户表
*/

@Entity
@Table(name = "t_user")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer","handler"})
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class User  extends WnsysUser implements Serializable{

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
	@NotNull
    @Column(unique=true,length=32)
    private String username;
    @NotNull
    private String password;

    @Column(length=80)
	private String ticket;
    @Column(length = 80)
	private String groupPwd;//群组共享用户的方式，此时密码需要对称加密，采用AES

//    @ManyToMany(fetch=FetchType.EAGER)
//    @JoinTable(name = "t_user_role", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = {
//            @JoinColumn(name = "role_id") })
//    @JsonIgnore
//    private List<Role> roleList;// 一个用户具有多个角色

    @ManyToMany(fetch=FetchType.EAGER,cascade={CascadeType.MERGE})
    @JoinTable(name = "t_org_user",joinColumns = {  @JoinColumn(name = "user_id", referencedColumnName = "id") }, inverseJoinColumns = { @JoinColumn(name = "org_id", referencedColumnName = "id") })
    @JsonIgnore
    private List<Organizational> orgList;// 一个用户可能在多个组织机构节点
    
    private String salt;
    
    private String remark;
	@Column(length=32)
	private String lastTime;
    
    public User() {
        super();
    }

    public User(String username, String password) {
        super();
        this.username = username;
        this.password = password;
    }

}