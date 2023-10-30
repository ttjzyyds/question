package com.sisp.controller;

import com.sisp.beans.HttpResponseEntity;
import com.sisp.dao.entity.UserEntity;
import com.sisp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS})

public class UserController {
    @Autowired
    private UserService userService;
    //验证
    //用户登录
    @RequestMapping(value = "/userLogin",method = RequestMethod.POST,headers = "Accept=application/json")
    public HttpResponseEntity userLogin(@RequestBody UserEntity userEntity){
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        try{
            List<UserEntity> maps = userService.selectUserInfo(userEntity);
            if(CollectionUtils.isEmpty(maps)){
                httpResponseEntity.setCode("0");
                httpResponseEntity.setData(maps.get(0));
                httpResponseEntity.setMessage("用户名或密码错误");
            }else {
                httpResponseEntity.setCode("666");
                httpResponseEntity.setData(maps);
                httpResponseEntity.setMessage("登录成功");
            }
        }catch (Exception e){
        }
        return httpResponseEntity;
    }
    //用户列表查询
    @RequestMapping(value = "/queryUserList",method = RequestMethod.POST,headers = "Accept=application/json")
    public HttpResponseEntity queryUserList(@RequestBody UserEntity userEntity){
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        try{
            List<UserEntity> maps = userService.queryUserList(userEntity);
            if(CollectionUtils.isEmpty(maps)){
                httpResponseEntity.setCode("0");
                httpResponseEntity.setData(maps.get(0));
                httpResponseEntity.setMessage("无用户信息");
            }else {
                httpResponseEntity.setCode("666");
                httpResponseEntity.setData(maps);
                httpResponseEntity.setMessage("查询成功");
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return httpResponseEntity;
    }

    //用户添加
    @RequestMapping(value = "/addUserInfo",method = RequestMethod.POST,headers = "Accept=application/json")
    public HttpResponseEntity addUserInfo(@RequestBody UserEntity userEntity){
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        try{
            int result = userService.addUserInfo(userEntity);
            if(result!=0){
                httpResponseEntity.setCode("666");
                httpResponseEntity.setData(result);
                httpResponseEntity.setMessage("创建成功");
            }else {
                httpResponseEntity.setCode("0");
                httpResponseEntity.setData(0);
                httpResponseEntity.setMessage("创建失败");
            }
        }catch (Exception e){
        }
        return httpResponseEntity;
    }

    //用户修改
    @RequestMapping(value = "/modifyUserInfo",method = RequestMethod.POST,headers = "Accept=application/json")
    public HttpResponseEntity modifyUserInfo(@RequestBody UserEntity userEntity){
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        //System.out.println("");
        try{
            int result = userService.modifyUserInfo(userEntity);
            if(result!=0){
                httpResponseEntity.setCode("666");
                httpResponseEntity.setData(result);
                httpResponseEntity.setMessage("修改成功");
            }else {
                httpResponseEntity.setCode("0");
                httpResponseEntity.setData(0);
                httpResponseEntity.setMessage("修改失败");
            }
        }catch (Exception e){
        }
        return httpResponseEntity;
    }
    // 更改用户状态
    @RequestMapping(value = "/changeUserStatus", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity changeUserStatus(@RequestBody UserEntity userEntity) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        try {
            int result = userService.changeUserStatus(userEntity);
            if (result != 0) {
                httpResponseEntity.setCode("666");
                httpResponseEntity.setData(result);
                httpResponseEntity.setMessage("状态更改成功");
            } else {
                httpResponseEntity.setCode("0");
                httpResponseEntity.setData(0);
                httpResponseEntity.setMessage("状态更改失败");
            }
        } catch (Exception e) {
        }
        return httpResponseEntity;
    }

    //用户删除
    @RequestMapping(value = "/deleteUserInfo",method = RequestMethod.POST,headers = "Accept=application/json")
    public HttpResponseEntity deleteUser(@RequestBody UserEntity userEntity){
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        try{
            int result = userService.deleteUserByName(userEntity);
            if(result!=0){
                httpResponseEntity.setCode("666");
                httpResponseEntity.setData(result);
                httpResponseEntity.setMessage("删除成功");
            }else {
                httpResponseEntity.setCode("0");
                httpResponseEntity.setData(0);
                httpResponseEntity.setMessage("删除失败");
            }
        }catch (Exception e){
        }
        return httpResponseEntity;
    }
}
