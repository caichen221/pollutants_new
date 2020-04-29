package com.iscas.biz.jpa.test.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
*@auhor:zhuquanwen
*@date:2016年12月6日
*@desc:
*/
public interface AppDao extends JpaRepository<App, Integer>{
	public App findByName(String name);

//	public App findById(Integer id);

	@Query(nativeQuery = true,value="select distinct * from t_app t where t.id <> ?1")
	public List<App> findOtherApp(Integer id);

}
