package com.example.dechuan.model.usermanage;

/**
 * @author eden
 * @description:
 * @menu
 * @date 2022/2/8 14:36
 */
public class UserMessage {

    public static String USERNAME_PASSWORD="请输入用户名和密码";
    public static String CORRECT_USERNAME_PASSWORD="请确认用户名和密码是否正确";//
    public static String LOGIN_SUCCES="登录成功";//
    public static String ASSIGN_PERMISSIONS="管理员未对该用户分配任何权限";
    public static String REGEX= "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$";
    public static String REGEX_PASSWORD= "密码必须由8~16位数字和字母组成";

}
