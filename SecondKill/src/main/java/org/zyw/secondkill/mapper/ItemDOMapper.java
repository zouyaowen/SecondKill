package org.zyw.secondkill.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import org.zyw.secondkill.entity.ItemDO;

@Repository
public interface ItemDOMapper {

	String baseElements = "id,title,price,description,sales,img_url imgUrl ";

	@Insert("insert into item (title,price,description,img_url) values (#{title},#{price},#{description},#{imgUrl})")
	@Options(keyProperty = "id", keyColumn = "id", useGeneratedKeys = true)
	int insertItem(ItemDO itemDO);

	@Select("select " + baseElements + " from item where id=#{id}")
	ItemDO selectById(Integer id);

	@Select("select " + baseElements + " from item order by sales DESC")
	List<ItemDO> listItem();

	@Update("update item set sales = sales+#{quantity} where id = #{itemId}")
	int increaseSales(@Param("itemId") Integer itemId, @Param("quantity") Integer quantity);
}
