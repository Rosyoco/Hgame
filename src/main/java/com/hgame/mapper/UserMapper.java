package com.hgame.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.hgame.pojo.User;

@Mapper
public interface UserMapper {
	
	@Select("select * from tb_user where username = #{username}")
	User loginMapper(String username);

}
