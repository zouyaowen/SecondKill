package org.zyw.secondkill.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.zyw.secondkill.entity.OrderDO;
import org.zyw.secondkill.entity.SequenceInfoDO;
import org.zyw.secondkill.error.BusinessException;
import org.zyw.secondkill.error.EmBusinessError;
import org.zyw.secondkill.mapper.OrderDOMapper;
import org.zyw.secondkill.mapper.SequenceInfoDOMapper;
import org.zyw.secondkill.service.ItemService;
import org.zyw.secondkill.service.OrderService;
import org.zyw.secondkill.service.UserService;
import org.zyw.secondkill.service.model.ItemModel;
import org.zyw.secondkill.service.model.OrderModel;
import org.zyw.secondkill.service.model.UserModel;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private ItemService itemService;

	@Autowired
	private UserService userService;

	@Autowired
	private OrderDOMapper orderDOMapper;

	@Autowired
	private SequenceInfoDOMapper sequenceInfoDOMapper;

	@Override
	@Transactional
	public OrderModel createOrder(Integer userId, Integer itemId, Integer secKillId, Integer quantity) {
		// 1、校验下单状态：商品是否存在；用户是否合法；数量是否正确
		ItemModel itemModel = itemService.getItemById(itemId);
		if (itemModel == null) {
			throw new BusinessException(EmBusinessError.INVALID_PAREMETER, "商品信息不存在");
		}
		// 校验商品信息
		UserModel userModel = userService.getUserById(userId);
		if (userModel == null) {
			throw new BusinessException(EmBusinessError.USER_NOT_EXIST);
		}
		// 校验商品信息
		if (quantity < 0 || quantity > 10000) {
			throw new BusinessException(EmBusinessError.INVALID_PAREMETER, "订单购买数量不正确");
		}

		// 校验秒杀活动信息
		if (secKillId != null) {
			// 1、校验活动是否存在
			if (secKillId != itemModel.getSecKillModel().getId()) {
				throw new BusinessException(EmBusinessError.INVALID_PAREMETER, "活动信息不存在");
			} else if (itemModel.getSecKillModel().getStatus() != 2) {
				// 2、校验活动是否进行中
				throw new BusinessException(EmBusinessError.INVALID_PAREMETER, "活动还未开始");
			}

		}

		// 2、减库存有两种方式：落单减库存、支付减库存（支付减库存可能会产生超卖现象）,此处使用落单减库存
		boolean decreaseStock = itemService.decreaseStock(itemId, quantity);
		if (!decreaseStock) {
			throw new BusinessException(EmBusinessError.STOCK_NOT_ENOUGH);
		}
		// 3、订单入库
		OrderModel orderModel = new OrderModel();
		orderModel.setItemId(itemModel.getId());
		orderModel.setUserId(userId);
		if (secKillId != null) {
			orderModel.setItemPrice(itemModel.getSecKillModel().getSecKillPrice());
		} else {
			orderModel.setItemPrice(itemModel.getPrice());
		}
		orderModel.setOrderAmount(orderModel.getItemPrice().multiply(new BigDecimal(quantity)));
		// 添加促销活动唯一标识
		orderModel.setSecKillId(secKillId);
		orderModel.setItemQuantity(quantity);

		// 生成交易订单号
		String orderNo = generateOrderNo();
		// 订单ID处理
		orderModel.setId(orderNo);

		OrderDO orderDO = convertFromOrderModel(orderModel);
		orderDOMapper.insertOrder(orderDO);
		itemService.increaseSales(itemId, quantity);

		// 4、返回前端
		return orderModel;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	private String generateOrderNo() {
		StringBuilder res = new StringBuilder();
		// 订单号有16位
		// 前8位为时间信息：年月日——>20190609
		LocalDateTime now = LocalDateTime.now();
		String nowStr = now.format(DateTimeFormatter.BASIC_ISO_DATE);
		res.append(nowStr);

		// 中间六位为自增序列
		int sequence = 0;
		SequenceInfoDO sequenceInfoDO = sequenceInfoDOMapper.selectByName("order_info");
		sequence = sequenceInfoDO.getCurrentValue();
		sequenceInfoDO.setCurrentValue(sequence + sequenceInfoDO.getStep());
		sequenceInfoDOMapper.updateById(sequenceInfoDO);
		String sequenceNo = String.format("%06d", sequence);
		res.append(sequenceNo);

		// 最后两位为分库分表位（00-99）：用户ID对100取余的结果数据对应的订单表名数据,此处暂时写死
		res.append("00");
		return res.toString();
	}

	private OrderDO convertFromOrderModel(OrderModel orderModel) {
		if (orderModel == null) {
			return null;
		}
		OrderDO orderDO = new OrderDO();
		BeanUtils.copyProperties(orderModel, orderDO);
		return orderDO;
	}

}
