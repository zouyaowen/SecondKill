package org.zyw.secondkill.service;

import org.zyw.secondkill.service.model.SecKillModel;

public interface SecKillService {
	/**
	 * @Desc 获取即将进行的或正在进行的秒杀活动
	 * @param itemId
	 * @return SecKillModel
	 * @Author zyw
	 * @Date 2019年6月10日下午10:54:33
	 */
	SecKillModel getItemByItemId(Integer itemId);
}
