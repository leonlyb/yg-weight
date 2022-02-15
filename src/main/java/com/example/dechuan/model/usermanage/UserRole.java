package com.example.dechuan.model.usermanage;

/**
 * @author eden
 * @description:
 * @menu
 * @date 2022/2/9 14:24
 */
public class UserRole {
    /**
     * 用户key
     */
    private Integer userKy;
    /**
     * 角色key
     */
    private Integer roleKy;



    public Integer getUserKy() {
        return userKy;
    }
    public void setUserKy(Integer userKy) {
        this.userKy = userKy;
    }
    public Integer getRoleKy() {
        return roleKy;
    }
    public void setRoleKy(Integer roleKy) {
        this.roleKy = roleKy;
    }
}
