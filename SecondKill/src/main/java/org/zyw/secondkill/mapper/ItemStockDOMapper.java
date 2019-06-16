package org.zyw.secondkill.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import org.zyw.secondkill.entity.ItemStockDO;

@Repository
public interface ItemStockDOMapper {
	String baseElements = "id,stock,item_id itemId";

	@Insert("insert into item_stock (stock,item_id) values (#{stock},#{itemId})")
	@Options(keyProperty = "id", keyColumn = "id", useGeneratedKeys = true)
	int insertItemStock(ItemStockDO itemStockDO);

	@Select("select " + baseElements + " from item_stock where item_id=#{itemId}")
	ItemStockDO selectByItemId(Integer itemId);

	@Update("update item_stock set stock = stock-#{quantity} where item_id = #{itemId} and stock > #{quantity}")
	int decreaseStock(@Param("itemId") Integer itemId, @Param("quantity") Integer quantity);
}
