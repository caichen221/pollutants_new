package com.iscas.biz.jpa.test.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UrlAddrDao extends JpaRepository<UrlAddr, Integer> {
	@Modifying
	@Query(value = "delete from t_url_addr where id in  (select a.idx from (select distinct t1.id as idx from t_url_addr t1,t_resource t2 ,t_role_resource t3, "
			+ "t_role t4 ,t_org_role t5,t_organizational t6,t_app t7 "
			+ " where t1.resource_id = t2.id "
			+ "and t2.id = t3.resource_id "
			+ "and t3.role_id = t4.id "
			+ "and t4.id = t5.role_id "
			+ "and t5.org_id = t6.id "
			+ "and t6.app_id = t7.id "
			+ "and t7.id = ?1) a )", nativeQuery = true)
	// 暂时不使用
	public void deleteByAppId(Integer id);

	@Query(value = "select distinct t1.id as idx from t_url_addr t1,t_resource t2 ,t_role_resource t3, "
			+ "t_role t4 ,t_org_role t5,t_organizational t6,t_app t7 "
			+ " where t1.resource_id = t2.id "
			+ "and t2.id = t3.resource_id "
			+ "and t3.role_id = t4.id "
			+ "and t4.id = t5.role_id "
			+ "and t5.org_id = t6.id "
			+ "and t6.app_id = t7.id "
			+ "and t7.id = ?1", nativeQuery = true)
	public List<Integer> findByAppId(Integer id);

	@Query(value = "select * from t_app t1,t_organizational t2,"
			+ "t_org_role t3,t_role t4,t_role_resource t5,"
			+ "t_resource t6,t_url_addr t7 where t1.id = t2.app_id  "
			+ "and t2.id = t3.org_id and t3.role_id = t4.id and "
			+ "t4.id = t5.role_id and t5.resource_id = t6.id and "
			+ "t6.id = t7.resource_id  and t1.name = ?1 ", nativeQuery = true)
	public List<UrlAddr> findByAppName(String name);

	
	@Modifying(clearAutomatically = true)
	@Query("update UrlAddr u set u.url = ?1,u.description=?2 where u.id = ?3")
	public void updateUrl(String url, String desc, Integer id);

	@Query(value = "select * from t_url_addr t1,t_resource t2 ,t_role_resource t3, "
			+ "t_role t4 ,t_org_role t5,t_organizational t6,t_app t7 "
			+ " where t1.resource_id = t2.id "
			+ "and t2.id = t3.resource_id "
			+ "and t3.role_id = t4.id "
			+ "and t4.id = t5.role_id "
			+ "and t5.org_id = t6.id "
			+ "and t6.app_id = t7.id "
			+ "and t7.id = ?1 limit ?2,?3", nativeQuery = true)
	public List<UrlAddr> findUrlAddrById(Integer id, Integer start, Integer rows);
	
	@Query(value = "select count(*) from t_url_addr t1,t_resource t2 ,t_role_resource t3, "
			+ "t_role t4 ,t_org_role t5,t_organizational t6,t_app t7 "
			+ " where t1.resource_id = t2.id "
			+ "and t2.id = t3.resource_id "
			+ "and t3.role_id = t4.id "
			+ "and t4.id = t5.role_id "
			+ "and t5.org_id = t6.id "
			+ "and t6.app_id = t7.id "
			+ "and t7.id = ?1", nativeQuery = true)
	public Integer countByAppId(Integer id);
	
//	public UrlAddr findById(Integer id);
	
//	@Query(value = "select * from t_url_addr t1 limit ?1,?2")
//	public List<UrlAddr> findAllByPage(Integer start,Integer rows);
	
	
	@Query(value="select distinct t7.id as idx from t_app t1,t_organizational t2,"
			+ "t_org_role t3,t_role t4,t_role_resource t5,"
			+ "t_resource t6,t_url_addr t7 where t1.id = t2.app_id  "
			+ "and t2.id = t3.org_id and t3.role_id = t4.id and "
			+ "t4.id = t5.role_id and t5.resource_id = t6.id and "
			+ "t6.id = t7.resource_id  and t1.name = ?1 ",nativeQuery=true)
	public List<Integer> findIdByAppName(String name); 
	
	@Query(value="select distinct t7.url as urlx from t_app t1,t_organizational t2,"
			+ "t_org_role t3,t_role t4,t_role_resource t5,"
			+ "t_resource t6,t_url_addr t7 where t1.id = t2.app_id  "
			+ "and t2.id = t3.org_id and t3.role_id = t4.id and "
			+ "t4.id = t5.role_id and t5.resource_id = t6.id and "
			+ "t6.id = t7.resource_id  and t1.name = ?1 ",nativeQuery=true)
	public List<String> findUrlByAppName(String name);

	@Query(value="select url from t_url_addr where resource_id  = ?1 ",nativeQuery=true)
	public List<String> findByResourceId(Integer id);
}
