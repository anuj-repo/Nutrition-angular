package com.fertilizer.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fertilizer.model.Role;

/**
 * @author Dhiraj
 *
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

	Optional<Role> findByRoleIdentifier(String string);

	List<Role> findAllByParentRoleIdAndRoleWeightLessThan(Integer agencySuperuserRoleId, int roleWeight);

}
