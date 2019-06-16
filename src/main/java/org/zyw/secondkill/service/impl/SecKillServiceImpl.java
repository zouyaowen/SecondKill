package org.zyw.secondkill.service.impl;

import java.time.LocalDateTime;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zyw.secondkill.entity.SecKillDO;
import org.zyw.secondkill.mapper.SecKillDOMapper;
import org.zyw.secondkill.service.SecKillService;
import org.zyw.secondkill.service.model.SecKillModel;

@Service
public class SecKillServiceImpl implements SecKillService {

	@Autowired
	private SecKillDOMapper secKillDOMapper;

	
	@Override
	public SecKillModel getItemByItemId(Integer itemId) {
		SecKillDO secKillDO = secKillDOMapper.getByItemId(itemId);

		SecKillModel secKillModel = convertFromSecKillDO(secKillDO);
		if (secKillModel == null) {
			return null;
		}
		LocalDateTime now = LocalDateTime.now();
		if (now.isBefore(secKillModel.getStartTime())) {
			secKillModel.setStatus(1);
		} else if (now.isAfter(secKillModel.getEndTime())) {
			secKillModel.setStatus(3);
		} else {
			secKillModel.setStatus(2);
		}
		return secKillModel;
	}

	private SecKillModel convertFromSecKillDO(SecKillDO secKillDO) {
		if (secKillDO == null) {
			return null;
		}
		SecKillModel secKillModel = new SecKillModel();
		BeanUtils.copyProperties(secKillDO, secKillModel);
		return secKillModel;
	}

}
