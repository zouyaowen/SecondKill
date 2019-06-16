package org.zyw.secondkill.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import org.zyw.secondkill.entity.OrderDO;

@Repository
public interface OrderDOMapper {
	String baseElements = " id,user_id userId,item_id itemId,item_price itemPrice,item_quantity itemQuantity,order_amount orderAmount,sec_kill_id secKillId ";

	@Insert("insert into order_info (id,user_id,item_id,item_price,item_quantity,order_amount) values (#{id},#{userId},#{itemId},#{itemPrice},#{itemQuantity},#{orderAmount})")
	@Options(keyProperty = "id", keyColumn = "id", useGeneratedKeys = true)
	int insertOrder(OrderDO orderDO);

	@Select("select " + baseElements + " from order_info where id=#{id}")
	OrderDO selectById(Integer id);

	@Select("select " + baseElements + " from order_info")
	List<OrderDO> listOrder();
}
