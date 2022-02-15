package com.example.dechuan.controller.usermmanage;

import com.example.dechuan.core.TokenUtil;
import com.example.dechuan.model.usermanage.Role;
import com.example.dechuan.model.usermanage.User;
import com.example.dechuan.model.usermanage.UserData;
import com.example.dechuan.model.usermanage.UserMessage;
import com.example.dechuan.service.usermanage.UserLoginService;
import com.example.dechuan.utils.Md5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author eden
 * @description:
 * @menu 用户登录
 * @date 2022/2/8 14:11
 */
@RestController
@RequestMapping(value = "/user")
public class UserLoginController {

    @Autowired
    private UserLoginService userLoginService;

    /**
     * @param
     * @return
     * @Author eden
     * @Description 登录
     * @Date 2022/02/09 23:20
     * @status done
     */

    @RequestMapping(value = "/login")
    @ResponseBody
    public UserData login(User user, HttpServletRequest req) {
        UserData dataResult = new UserData();
        //登录
        if (user.getUserName() == null || user.getPassword() == null) {
            dataResult.setCode(999);
            dataResult.setData(UserMessage.USERNAME_PASSWORD);
            return dataResult;
        }else{
            user.setPassword(Md5Utils.md5(user.getPassword()));
        }
        //加密后去查询数据库是否存在用户
        List<User> list = userLoginService.doGetUserSelect(user);
        if(list.size() <= 0){
            dataResult.setCode(999);
            dataResult.setData(UserMessage.CORRECT_USERNAME_PASSWORD);
            return dataResult;
        }else{
            dataResult.setCode(200);
            dataResult.setUserKy(list.get(0).getUserKy());
            dataResult.setUserName(list.get(0).getUserName());
            List<Role> listRole = userLoginService.doGetRole(list.get(0).getUserKy());
            if(listRole.size() <= 0 ){
                dataResult.setCode(200);
                dataResult.setUserName(user.getUserName());
                dataResult.setData(UserMessage.ASSIGN_PERMISSIONS);
                return dataResult;
            }
            dataResult.setData(listRole);
        }
        //创建session对象
        HttpSession session = req.getSession();
        //把用户数据保存在session域对象中
        session.setAttribute("loginName", user.getUserName());
        // 设置toke
        String token= TokenUtil.sign(new User(user.getUserName(),user.getPassword()));
        dataResult.setToken(token);
        return dataResult;
    }

    /**
     * @param
     * @return
     * @Author eden
     * @Description 退出
     * @Date 2022/02/09 23:20
     * @status done
     */
    @RequestMapping(value = "/loginout")
    @ResponseBody
    public Map<String, Object> loginout(HttpServletRequest req){
        Map<String, Object> params = new HashMap<>();
        HttpSession session = req.getSession();
        session.removeAttribute("loginName");
         params.put("code", 200);
         params.put("msg", "成功退出");
        return params;
    }

}
