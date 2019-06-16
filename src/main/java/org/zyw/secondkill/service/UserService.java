package org.zyw.secondkill.service;

import org.zyw.secondkill.service.model.UserModel;

public interface UserService {
	UserModel getUserById(Integer id);

	void register(UserModel userModel);

	UserModel validatorLogin(String telphone, String password);
}
