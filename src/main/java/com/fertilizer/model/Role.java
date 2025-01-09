package com.fertilizer.model;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.NaturalId;

import com.fertilizer.enums.EntityConstant;

@Entity
@Table(name = "roles")
@DynamicUpdate
public class Role extends BaseModel {

	private static final long serialVersionUID = -2252299383357377371L;

	@NaturalId
	private String roleName;
	private String roleWeight;
	private String roleDescription;
	private Long maxUser;
	private Integer maxOrderCreationPerDay;
	private Integer maxOrderEditPerDay;
	private Integer parentRoleId;

	private String roleIdentifier;

	public Role() {
		super();
	}

	public String getRoleIdentifier() {
		if (roleIdentifier.contains(EntityConstant.ROLE_PREAPPANDER_FOR_SECURITY.getName()))
			return roleIdentifier;
		return EntityConstant.ROLE_PREAPPANDER_FOR_SECURITY.getName() + roleIdentifier;
	}

	public void setRoleIdentifier(String roleIdentifier) {
		if (roleIdentifier.contains(EntityConstant.ROLE_PREAPPANDER_FOR_SECURITY.getName()))
			this.roleIdentifier = roleIdentifier;
		this.roleIdentifier = EntityConstant.ROLE_PREAPPANDER_FOR_SECURITY.getName() + roleIdentifier;
	}

	public String getRoleWeight() {
		return roleWeight;
	}

	public void setRoleWeight(String roleWeight) {
		this.roleWeight = roleWeight;
	}

	public String getRoleDescription() {
		return roleDescription;
	}

	public void setRoleDescription(String roleDescription) {
		this.roleDescription = roleDescription;
	}

	public int getParentRoleId() {
		return parentRoleId;
	}

	public void setParentRoleId(int parentRoleId) {
		this.parentRoleId = parentRoleId;
	}

	/**
	 * @return the roleName
	 */
	public String getRoleName() {
		return roleName;
	}

	public Long getMaxUser() {
		return maxUser;
	}

	public void setMaxUser(Long maxUser) {
		this.maxUser = maxUser;
	}

	public Integer getMaxOrderCreationPerDay() {
		return maxOrderCreationPerDay;
	}

	public void setMaxOrderCreationPerDay(Integer maxOrderCreationPerDay) {
		this.maxOrderCreationPerDay = maxOrderCreationPerDay;
	}

	public Integer getMaxOrderEditPerDay() {
		return maxOrderEditPerDay;
	}

	public void setMaxOrderEditPerDay(Integer maxOrderEditPerDay) {
		this.maxOrderEditPerDay = maxOrderEditPerDay;
	}


	public void setParentRoleId(Integer parentRoleId) {
		this.parentRoleId = parentRoleId;
	}

	/**
	 * @param roleName the roleName to set
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@Override
	public String toString() {
		return "Role [id=" + id + ", role_name=" + roleName + "]";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Role))
			return false;
		Role that = (Role) o;
		return Objects.equals(id, that.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
