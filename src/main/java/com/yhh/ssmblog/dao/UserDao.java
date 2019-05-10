package com.yhh.ssmblog.dao;

import com.yhh.ssmblog.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDao {
    public int countAllUser();
    public List<User> listUsers(@Param("column")String column
            ,@Param("order")String order
            ,@Param("start")int start
            ,@Param("length")int length);
    public int getUserId(@Param("username")String username);
    public int checkColumn(@Param("column")String column);
    public int insertUser(@Param("username")String username
            ,@Param("password")String password
            ,@Param("name")String name);
    public int insertUserRoles(@Param("username")String username
            ,@Param("rolename")String rolename);
    public int updateName(@Param("name")String name
            ,@Param("id")int id);
    public int updatePassword(@Param("password")String password
            ,@Param("id")int id);

}
