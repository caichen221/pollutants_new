package com.iscas.biz.jpa.test.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface RoleDao extends JpaRepository<Role, Integer>, JpaSpecificationExecutor<Role> {

    @Modifying
    @Query(nativeQuery = true, value = "delete from t_role where id in (select a.idx from (select distinct t1.id as idx "
            + "from t_role t1,t_org_role t2 ,t_organizational t3,  "
            + "t_app t4 where t1.id = t2.role_id and t2.org_id = t3.id "
            + "and t3.app_id = t4.id and t4.id = ?1) a)")
    //暂时不使用
    public void deleteByAppId(Integer id);

    @Modifying
    @Query(nativeQuery = true, value = "delete from t_org_role where role_id in (select a.idx from (select distinct t2.role_id as idx "
            + "from t_role t1,t_org_role t2 ,t_organizational t3,  "
            + "t_app t4 where t1.id = t2.role_id and t2.org_id = t3.id "
            + "and t3.app_id = t4.id and t4.id = ?1) a)")
    //暂时不使用
    public void deleteRoleOrgByAppId(Integer id);


    @Query(nativeQuery = true, value = "select distinct t1.id as idx "
            + "from t_role t1,t_org_role t2 ,t_organizational t3,  "
            + "t_app t4 where t1.id = t2.role_id and t2.org_id = t3.id "
            + "and t3.app_id = t4.id and t4.id = ?1")
    public List<Integer> findByAppId(Integer id);


    @Query(nativeQuery = true, value = "select distinct t1.rolename from t_role t1," +
            "t_role_resource t2,t_resource t3 where t1.id=t2.role_id and " +
            "t2.resource_id=t3.id and t3.permissionname=?1")
    public List<String> findRolesByPermissionName(String permissionname);

    @Query(nativeQuery = true, value = "select distinct t1.rolename from t_role t1, " +
            "t_org_role t2,t_org_user t3, t_user t4 where t1.id=t2.role_id and t2.org_id=t3.org_id and " +
            "t3.user_id=t4.id and t4.username=?1")
    public Set<String> findRoleNamesByUserName(String username);

    @Query(nativeQuery = true, value = "select * from t_role t1, " +
            "t_org_role t2,t_org_user t3, t_user t4 ,t_app t5,t_organizational t6 where t1.id=t2.role_id and t2.org_id=t3.org_id and " +
            "t3.user_id=t4.id and t4.username=?1 and t6.id = t2.org_id and t6.app_id=t5.id and t5.name=?2")
    public List<Role> findRolesByUserName(String username, String appname);

}
