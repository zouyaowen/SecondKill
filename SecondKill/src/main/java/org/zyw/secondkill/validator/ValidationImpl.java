package org.zyw.secondkill.validator;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class ValidationImpl implements InitializingBean {

	private Validator validator;

	public ValidationResult validate(Object bean) {
		ValidationResult validationResult = new ValidationResult();
		Set<ConstraintViolation<Object>> constraintViolation = validator.validate(bean);
		if (constraintViolation.size() > 0) {
			validationResult.setHasErrors(true);
			constraintViolation.forEach(item -> {
				String errMsg = item.getMessage();
				String propetyName = item.getPropertyPath().toString();
				validationResult.getErrMsgMap().put(propetyName, errMsg);
			});
		}
		return validationResult;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// 将hibernate validator将通过工厂初始化的方式将其实例化
		validator = Validation.buildDefaultValidatorFactory().getValidator();
	}

}
