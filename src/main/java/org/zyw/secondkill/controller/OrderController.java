package org.zyw.secondkill.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zyw.secondkill.controller.vo.OrderVO;
import org.zyw.secondkill.error.BusinessException;
import org.zyw.secondkill.error.EmBusinessError;
import org.zyw.secondkill.response.CommonReturnType;
import org.zyw.secondkill.service.OrderService;
import org.zyw.secondkill.service.model.OrderModel;
import org.zyw.secondkill.service.model.UserModel;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@Autowired
	private HttpServletRequest request;

	@PostMapping("/createOrder")
	public CommonReturnType createOrder(Integer itemId, Integer secKillId, Integer quantity) {
		log.info("itemId={},quantity={}", itemId, quantity);
		Boolean isLogin = (Boolean) request.getSession().getAttribute("LOGIN");
		if (isLogin == null || !isLogin.booleanValue()) {
			throw new BusinessException(EmBusinessError.USER_UNLOGIN);
		}
		UserModel userModel = (UserModel) request.getSession().getAttribute("LOGIN_USER");
		if (userModel == null) {
			throw new BusinessException(EmBusinessError.USER_UNLOGIN);
		}
		OrderModel orderModel = orderService.createOrder(userModel.getId(), itemId, secKillId, quantity);
		convertFromOrderModel(orderModel);
		return CommonReturnType.create(orderModel);
	}

	private OrderVO convertFromOrderModel(OrderModel orderModel) {
		if (orderModel == null) {
			return null;
		}
		OrderVO orderVO = new OrderVO();
		BeanUtils.copyProperties(orderModel, orderVO);
		return orderVO;
	}
}
