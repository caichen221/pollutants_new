package com.iscas.biz.jpa.test.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UserDao extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {
	
	 
	public User findByUsername(String name);
	@Query(nativeQuery=true,value="select  t1.* from t_user t1, "
			+ "t_organizational t2, t_app t3, t_org_user t4 "
			+ "where t1.id = t4.user_id and t2.id = t4.org_id and "
			+ "t3.id = t2.app_id and t3.name = ?1 and t1.username = ?2")
	public User findByUsernameAndAppName(String appName, String username);
	public User findByUsernameAndPassword(String username, String password);
	@org.springframework.transaction.annotation.Transactional
	@Modifying
	@Query(value = "insert into t_org_user(user_id,org_id) values(?1, ?2)", nativeQuery = true)
	public void insertOrgUser(Integer userId, Integer orgId);
	
//	@Modifying(clearAutomatically = true)
//	@Query("update User u set u.username=?2,u.remark = ?1 where u.id = ?3") 
//	public void updateUser(String remark, String name, Integer id);
}
