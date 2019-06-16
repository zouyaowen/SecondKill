package org.zyw.secondkill.mapper;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import org.zyw.secondkill.entity.SecKillDO;

@Repository
public interface SecKillDOMapper {

	String baseElements = " id,secKill_name secKillName,start_time startTime,end_time endTime,item_id itemId,secKill_price secKillPrice ";

	@Select("select " + baseElements + " from seckill where item_id=#{itemId}")
	SecKillDO getByItemId(Integer itemId);

	@Select("select " + baseElements + " from seckill where id=#{id}")
	SecKillDO getById(Integer id);

}
