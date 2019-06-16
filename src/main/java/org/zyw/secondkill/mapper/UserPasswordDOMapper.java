package org.zyw.secondkill.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import org.zyw.secondkill.entity.UserPasswordDO;

@Repository
public interface UserPasswordDOMapper {
	String baseElements = " id,encript_password encriptPassword,user_id userId ";

	@Select("select" + baseElements + "from user_password where id=#{id}")
	UserPasswordDO selectById(Integer id);

	@Insert("insert into user_password (encript_password,user_id) values (#{encriptPassword},#{userId})")
	@Options(keyProperty = "id", keyColumn = "id", useGeneratedKeys = true)
	int insert(UserPasswordDO userPasswordDO);

	@Select("select" + baseElements + "from user_password where user_id=#{userId}")
	UserPasswordDO selectByUserId(Integer userId);
}
