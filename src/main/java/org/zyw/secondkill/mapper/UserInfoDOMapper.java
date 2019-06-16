package org.zyw.secondkill.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import org.zyw.secondkill.entity.UserInfoDO;

@Repository
public interface UserInfoDOMapper {
	String baseElements = " id,name,age,gender,telphone,register_mode registerMode,third_party_id thirdPartyId ";

	@Select("select" + baseElements + "from user_info where id=#{id}")
	UserInfoDO selectById(Integer id);

	@Insert("insert into user_info (name,age,gender,telphone) values (#{name},#{age},#{gender},#{telphone})")
	@Options(keyProperty = "id", keyColumn = "id", useGeneratedKeys = true)
	int insertUserInfo(UserInfoDO userInfoDO);

	@Select("select" + baseElements + "from user_info where telphone=#{telphone}")
	UserInfoDO selectByPhone(String telphone);

}
