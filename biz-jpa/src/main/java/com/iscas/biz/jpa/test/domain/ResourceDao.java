package com.iscas.biz.jpa.test.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ResourceDao extends JpaRepository<Resource, Integer> {
	@Modifying
	@Query(value = "delete from t_resource where id in (select a.idx from (select distinct t1.id as idx "
			+ "from t_resource t1,t_role_resource t2,t_role t3,t_org_role t4, "
			+ "t_organizational t5,t_app t6 where t1.id = t2.resource_id "
			+ "and t2.role_id = t3.id and t3.id = t4.role_id and "
			+ "t4.org_id = t5.id and t5.app_id = t6.id and t6.id = ?1) a )", nativeQuery = true)
	// 暂时不使用
	public void deleteByAppId(Integer id);

	@Modifying
	@Query(value = "delete from t_role_resource where resource_id in (select a.idx from (select distinct t2.resource_id as idx "
			+ "from t_resource t1,t_role_resource t2,t_role t3,t_org_role t4, "
			+ "t_organizational t5,t_app t6 where t1.id = t2.resource_id "
			+ "and t2.role_id = t3.id and t3.id = t4.role_id and "
			+ "t4.org_id = t5.id and t5.app_id = t6.id and t6.id = ?1) a )", nativeQuery = true)
	// 暂时不使用
	public void deleteResourceRoleByAppId(Integer id);

	@Query(value = "select distinct t1.id as idx "
			+ "from t_resource t1,t_role_resource t2,t_role t3,t_org_role t4, "
			+ "t_organizational t5,t_app t6 where t1.id = t2.resource_id "
			+ "and t2.role_id = t3.id and t3.id = t4.role_id and "
			+ "t4.org_id = t5.id and t5.app_id = t6.id and t6.id = ?1", nativeQuery = true)
	// 暂时不使用
	public List<Integer> findByAppId(Integer id);

	@Query(value = "select * from t_resource t1 where t1.app_id = ?1 limit ?2,?3", nativeQuery = true)
	public List<Resource> findByApp(Integer appId, Integer start, Integer rows);
	
	@Query(value = "select count(*) from t_resource t1 where t1.app_id = ?1", nativeQuery = true)
	public Integer countByApp(Integer appId);
	
	@Modifying(clearAutomatically = true)
	@Query("update Resource u set u.permissionname = ?1,u.remark=?2 where u.id = ?3")
	public void updateResource(String url, String desc, Integer id);


	public Resource findByPermissionname(String permissionname);
}
