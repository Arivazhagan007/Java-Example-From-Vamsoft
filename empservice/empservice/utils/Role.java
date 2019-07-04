package in.vamsoft.empservice.utils;

public class Role {
	private int roleId;
	private String roleName;
	private String descr;
	public Role(int roleId, String roleName, String descr) {
		super();
		this.roleId = roleId;
		this.roleName = roleName;
		this.descr = descr;
	}

	public Role() {

	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	@Override
	public String toString() {
		return "Role [roleId=" + roleId + ", roleName=" + roleName + ", descr="
				+ descr + "]";
	}

}
