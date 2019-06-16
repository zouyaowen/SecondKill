package org.zyw.secondkill.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import org.zyw.secondkill.entity.ItemDO;
import org.zyw.secondkill.entity.SequenceInfoDO;

@Repository
public interface SequenceInfoDOMapper {
	String baseElements = " id,name,current_value currentValue,step ";

	@Insert("insert into sequence_info (name,current_value,step) values (#{name},#{currentValue},#{step})")
	@Options(keyProperty = "id", keyColumn = "id", useGeneratedKeys = true)
	int insertSequenceInfo(SequenceInfoDO sequenceInfoDO);

	@Select("select " + baseElements + " from sequence_info where id=#{id}")
	SequenceInfoDO selectById(Integer id);

	/**
	 * @Desc 规则是：FOR UPDATE语句将锁住查询结果中的元组，这些元组将不能被其他事务的UPDATE，DELETE和FOR UPDATE操作，直到本事务提交
	 * @param name
	 * @return SequenceInfoDO
	 * @Author zyw
	 * @Date 2019年6月16日下午8:34:31
	 */
	@Select("select " + baseElements + " from sequence_info where name=#{name} for update")
	SequenceInfoDO selectByName(String name);

	@Select("select " + baseElements + " from sequence_info")
	List<ItemDO> listSequenceInfo();

	@Update("update sequence_info set current_value = #{currentValue} where id = #{id}")
	int updateById(SequenceInfoDO sequenceInfoDO);

}
