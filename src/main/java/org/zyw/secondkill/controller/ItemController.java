package org.zyw.secondkill.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zyw.secondkill.controller.dto.ItemDto;
import org.zyw.secondkill.controller.vo.ItemVO;
import org.zyw.secondkill.error.BusinessException;
import org.zyw.secondkill.error.EmBusinessError;
import org.zyw.secondkill.response.CommonReturnType;
import org.zyw.secondkill.service.ItemService;
import org.zyw.secondkill.service.model.ItemModel;
import org.zyw.secondkill.service.model.SecKillModel;
import org.zyw.secondkill.validator.ValidationImpl;
import org.zyw.secondkill.validator.ValidationResult;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
public class ItemController {
	@Autowired
	private ItemService itemService;

	@Autowired
	private ValidationImpl validator;

	/**
	 * @Desc 添加商品
	 * @param itemDto
	 * @return CommonReturnType
	 * @Author zyw
	 * @Date 2019年6月9日下午5:08:50
	 */
	@PostMapping("/createItem")
	public CommonReturnType createItem(ItemDto itemDto) {
		log.info("itemDto={}", itemDto);
		ValidationResult validate = validator.validate(itemDto);
		if (validate.isHasErrors()) {
			throw new BusinessException(EmBusinessError.INVALID_PAREMETER, validate.getErrMsg());
		}
		ItemModel itemModel = new ItemModel();
		BeanUtils.copyProperties(itemDto, itemModel);
		ItemModel createItem = itemService.createItem(itemModel);
		ItemVO itemVO = convertItemVOFromItemModel(createItem);
		return CommonReturnType.create(itemVO);

	}

	/**
	 * @Desc 商品详情
	 * @param id
	 * @return CommonReturnType
	 * @Author zyw
	 * @Date 2019年6月9日下午5:11:42
	 */
	@GetMapping("/item/{id}")
	public CommonReturnType itemDetail(@PathVariable Integer id) {
		log.info("id={}", id);
		ItemModel itemModel = itemService.getItemById(id);
		ItemVO itemVO = convertItemVOFromItemModel(itemModel);
		return CommonReturnType.create(itemVO);
	}

	/**
	 * @Desc 获取所有的商品列表
	 * @return CommonReturnType
	 * @Author zyw
	 * @Date 2019年6月9日下午5:25:19
	 */
	@GetMapping("/itemList")
	public CommonReturnType itemList() {
		List<ItemModel> listItem = itemService.listItem();
		List<ItemVO> itemVOList = listItem.stream().map(itemModel -> {
			ItemVO itemVO = convertItemVOFromItemModel(itemModel);
			return itemVO;
		}).collect(Collectors.toList());
		return CommonReturnType.create(itemVOList);
	}

	private ItemVO convertItemVOFromItemModel(ItemModel itemModel) {
		if (itemModel == null) {
			return null;
		}
		ItemVO itemVO = new ItemVO();
		BeanUtils.copyProperties(itemModel, itemVO);

		SecKillModel secKillModel = itemModel.getSecKillModel();
		if (secKillModel != null) {
			itemVO.setStatus(secKillModel.getStatus());
			itemVO.setSecKillId(secKillModel.getId());
			itemVO.setSecKillPrice(secKillModel.getSecKillPrice());
			itemVO.setStartTime(secKillModel.getStartTime());
		} else {
			itemVO.setStatus(0);
		}
		return itemVO;

	}
}
