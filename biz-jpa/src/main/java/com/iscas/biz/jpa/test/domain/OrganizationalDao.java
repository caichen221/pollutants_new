package com.iscas.biz.jpa.test.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
*@auhor:zhuquanwen
*@date:2016年12月6日
*@desc:
*/


public interface OrganizationalDao extends JpaRepository<Organizational, Integer>{
	public Organizational findByAppAndParentOrg(App app, Organizational parentOrg);
	public Organizational findByAppAndName(App app, String name);
//	@Override
//	public Optional<Organizational> findById(Integer id);
	
	@Modifying(clearAutomatically = true)
	@Query("update Organizational o set o.name = ?1 where o.id = ?2") 
	public void updateOrgName(String name, Integer id);

	@Query(nativeQuery = true,value = "select distinct * from t_organizational t where t.app_id=?1 and t.name = ?2")
	public List<Organizational> findByAppidAndName(Integer id, String name);
}
