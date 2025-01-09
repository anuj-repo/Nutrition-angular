package com.fertilizer.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fertilizer.model.Permission;


/**
 * @author Prashant
 *
 */
@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {

	@Query(nativeQuery = true, value = "select p.* from permissions p inner join role_has_permissions rp on p.id = rp.permission_id  where rp.role_id=:roleId")
	List<Permission> findRolePermissions(Long roleId);
}
