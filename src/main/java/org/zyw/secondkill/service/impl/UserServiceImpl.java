package org.zyw.secondkill.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.zyw.secondkill.entity.UserInfoDO;
import org.zyw.secondkill.entity.UserPasswordDO;
import org.zyw.secondkill.error.BusinessException;
import org.zyw.secondkill.error.EmBusinessError;
import org.zyw.secondkill.mapper.UserInfoDOMapper;
import org.zyw.secondkill.mapper.UserPasswordDOMapper;
import org.zyw.secondkill.service.UserService;
import org.zyw.secondkill.service.model.UserModel;
import org.zyw.secondkill.validator.ValidationImpl;
import org.zyw.secondkill.validator.ValidationResult;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserInfoDOMapper userInfoDOMapper;
	@Autowired
	private UserPasswordDOMapper userPasswordDOMapper;

	@Autowired
	private ValidationImpl Validator;

	@Override
	public UserModel getUserById(Integer id) {
		UserInfoDO userInfoDO = userInfoDOMapper.selectById(id);
		if (userInfoDO == null) {
			return null;
		}
		UserPasswordDO userPasswordDO = userPasswordDOMapper.selectById(id);
		UserModel userModel = convertFromDataObject(userInfoDO, userPasswordDO);
		return userModel;
	}

	/**
	 * @Desc DO到数据模型转换
	 * @param userInfoDO
	 * @param userPasswordDO
	 * @return UserModel
	 * @Author zyw
	 * @Date 2019年6月2日下午8:09:22
	 */
	private UserModel convertFromDataObject(UserInfoDO userInfoDO, UserPasswordDO userPasswordDO) {
		if (userInfoDO == null) {
			return null;
		}
		UserModel userModel = new UserModel();
		BeanUtils.copyProperties(userInfoDO, userModel);
		if (userPasswordDO != null) {
			userModel.setEncriptPassword(userPasswordDO.getEncriptPassword());
		}
		return userModel;
	}

	@Override
	@Transactional
	public void register(UserModel userModel) {
		if (userModel == null) {
			throw new BusinessException(EmBusinessError.INVALID_PAREMETER);
		}
		ValidationResult validate = Validator.validate(userModel);
		if (validate.isHasErrors()) {
			String errMsg = validate.getErrMsg();
			throw new BusinessException(EmBusinessError.INVALID_PAREMETER, errMsg);
		}
		UserInfoDO userInfoDO = convertFromModel(userModel);
		try {
			int insertUserInfo = userInfoDOMapper.insertUserInfo(userInfoDO);
			if (insertUserInfo < 1) {
				throw new BusinessException(EmBusinessError.REGISTER_FAIL);
			}
		} catch (DuplicateKeyException e) {
			throw new BusinessException(EmBusinessError.REGISTER_FAIL, "手机号已经注册");
		}

		UserPasswordDO userPasswordDO = new UserPasswordDO();
		userPasswordDO.setEncriptPassword(userModel.getEncriptPassword());
		userPasswordDO.setUserId(userInfoDO.getId());
		int insertPassword = userPasswordDOMapper.insert(userPasswordDO);
		if (insertPassword < 1) {
			throw new BusinessException(EmBusinessError.REGISTER_FAIL);
		}
	}

	private UserInfoDO convertFromModel(UserModel userModel) {
		if (userModel == null) {
			return null;
		}
		UserInfoDO userInfoDO = new UserInfoDO();
		BeanUtils.copyProperties(userModel, userInfoDO);
		return userInfoDO;
	}

	@Override
	public UserModel validatorLogin(String telphone, String password) {
		// 通过用户手机获取用户信息
		UserInfoDO userInfoDO = userInfoDOMapper.selectByPhone(telphone);
		if (userInfoDO == null) {
			throw new BusinessException(EmBusinessError.USER_LOGIN_FAIL);
		}

		UserPasswordDO userPasswordDO = userPasswordDOMapper.selectByUserId(userInfoDO.getId());
		if (userPasswordDO == null) {
			throw new BusinessException(EmBusinessError.USER_LOGIN_FAIL);
		}
		UserModel userModel = convertFromDataObject(userInfoDO, userPasswordDO);
		// 比对用户密码
		if (!DigestUtils.md5DigestAsHex(password.getBytes()).equals(userModel.getEncriptPassword())) {
			throw new BusinessException(EmBusinessError.USER_LOGIN_FAIL);
		}
		return userModel;
	}

}
