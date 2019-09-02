package org.zyw.secondkill.controller;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zyw.secondkill.controller.dto.UserRegisterDto;
import org.zyw.secondkill.controller.vo.UserVO;
import org.zyw.secondkill.error.BusinessException;
import org.zyw.secondkill.error.EmBusinessError;
import org.zyw.secondkill.response.CommonReturnType;
import org.zyw.secondkill.service.UserService;
import org.zyw.secondkill.service.model.UserModel;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private RedisTemplate<Object, Object> redisTemplate;

	/**
	 * @Desc 获取用户信息
	 * @param id
	 * @return CommonReturnType
	 * @Author zyw
	 * @Date 2019年6月4日下午9:38:41
	 */
	@GetMapping("/userInfo/{id}")
	public CommonReturnType userInfo(@PathVariable Integer id) {
		log.info("id={}", id);
		UserModel userModel = userService.getUserById(id);
		if (userModel == null) {
			throw new BusinessException(EmBusinessError.USER_NOT_EXIST);
		}
		UserVO userVO = convertFromModel(userModel);
		return CommonReturnType.create(userVO);
	}

	/**
	 * @Desc 获取短信验证码
	 * @param phoneNumber
	 * @return CommonReturnType
	 * @Author zyw
	 * @Date 2019年6月4日下午9:38:59
	 */
	@PostMapping("/getOneTimePassword")
	public CommonReturnType getOneTimePassword(String phoneNumber) {
		// 按照一定的规则生成验证码
		/*
		 * Random random = new Random(); int randomInt = random.nextInt(9999);
		 */
		// 生成一个随机四位数
		int randomInt = (int) ((Math.random() * 9 + 1) * 1000);
		String otpCode = String.valueOf(randomInt);

		// 将验证码与对应的手机号关联
		request.getSession().setAttribute(phoneNumber, otpCode);
		// 将验证码通过短信渠道发送给用户
		System.out.println("phoneNumber=" + phoneNumber + ",otpCode=" + otpCode);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("phoneNumber", phoneNumber);
		map.put("otpCode", otpCode);
		return CommonReturnType.create(map);
	}

	/**
	 * @Desc 用户注册接口
	 * @param userRegisterDto
	 * @param bindingResult
	 * @return CommonReturnType
	 * @Author zyw
	 * @Date 2019年6月4日下午9:39:36
	 */
	@PostMapping("/register")
	public CommonReturnType register(@Valid UserRegisterDto userRegisterDto, BindingResult bindingResult) {
		log.info("userRegisterDto={}", userRegisterDto);
		if (bindingResult.hasErrors()) {
			String errMessage = bindingResult.getFieldError().getDefaultMessage();
			throw new BusinessException(EmBusinessError.INVALID_PAREMETER, errMessage);
		}
		String otpCode = (String) request.getSession().getAttribute(userRegisterDto.getTelphone());
		if (!userRegisterDto.getOtp().equals(otpCode)) {
			throw new BusinessException(EmBusinessError.INVALID_PAREMETER, "验证码不符合要求");
		}
		UserModel userModel = new UserModel();
		BeanUtils.copyProperties(userRegisterDto, userModel);
		// 随机生成的数据8位字符串拼接密码进行MD5加密后再拼接随机生成的数据作为加密后的密码存储，保证一样的密码不一样的加密效果
		userModel.setEncriptPassword(DigestUtils.md5DigestAsHex(userRegisterDto.getPassword().getBytes()));
		userService.register(userModel);
		return CommonReturnType.create(null);
	}

	@PostMapping("/login")
	public CommonReturnType login(String telphone, String password) {

		// 入参校验
		if (StringUtils.isEmpty(telphone) || StringUtils.isEmpty(password)) {
			throw new BusinessException(EmBusinessError.INVALID_PAREMETER, "用户登录参数不能为空");
		}

		// 用户登录服务，用来校验用户登录是否合法
		UserModel userModel = userService.validatorLogin(telphone, password);

		// 生成登录凭证
		String token = UUID.randomUUID().toString();

		redisTemplate.opsForValue().set(token, userModel);
		// 设置超时时间为一个小时
		redisTemplate.expire(token, 1, TimeUnit.HOURS);

		// 将登录凭证添加至用户登录成功的Session中
		// request.getSession().setAttribute("LOGIN", true);
		// request.getSession().setAttribute("LOGIN_USER", userModel);
		return CommonReturnType.create(token);
	}

	private UserVO convertFromModel(UserModel userModel) {
		if (userModel == null) {
			return null;
		}
		UserVO userVO = new UserVO();
		BeanUtils.copyProperties(userModel, userVO);
		return userVO;
	}

}
