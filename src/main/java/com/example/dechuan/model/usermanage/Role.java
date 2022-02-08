package com.example.dechuan.model.usermanage;

/**
 * @author eden
 * @description:
 * @menu
 * @date 2022/2/8 16:04
 */
public class Role {
    /**
     * 角色主键
     */
    private Integer roleKy;
    /**
     * 角色名
     */
    private String roleName;




    public Integer getRoleKy() {
        return roleKy;
    }
    public void setRoleKy(Integer roleKy) {
        this.roleKy = roleKy;
    }
    public String getRoleName() {
        return roleName;
    }
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
