package org.zyw.secondkill.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zyw.secondkill.entity.ItemDO;
import org.zyw.secondkill.entity.ItemStockDO;
import org.zyw.secondkill.error.BusinessException;
import org.zyw.secondkill.error.EmBusinessError;
import org.zyw.secondkill.mapper.ItemDOMapper;
import org.zyw.secondkill.mapper.ItemStockDOMapper;
import org.zyw.secondkill.service.ItemService;
import org.zyw.secondkill.service.SecKillService;
import org.zyw.secondkill.service.model.ItemModel;
import org.zyw.secondkill.service.model.SecKillModel;
import org.zyw.secondkill.validator.ValidationImpl;
import org.zyw.secondkill.validator.ValidationResult;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ItemServiceImpl implements ItemService {

	@Autowired
	private ValidationImpl validator;

	@Autowired
	private ItemDOMapper itemDOMapper;

	@Autowired
	private ItemStockDOMapper itemStockDOMapper;

	@Autowired
	private SecKillService secKillService;

	@Override
	@Transactional
	public ItemModel createItem(ItemModel itemModel) {
		// 校验入参
		ValidationResult validate = validator.validate(itemModel);
		if (validate.isHasErrors()) {
			throw new BusinessException(EmBusinessError.INVALID_PAREMETER, validate.getErrMsg());
		}
		// model-——>dataObject
		ItemDO itemDO = convertItemDOFromItemModel(itemModel);
		// 写入数据库
		itemDOMapper.insertItem(itemDO);
		log.info("itemDO={}", itemDO);
		itemModel.setId(itemDO.getId());
		ItemStockDO itemStockDO = convertItemStockDOFromItemModel(itemModel);
		itemStockDOMapper.insertItemStock(itemStockDO);

		// 返回创建好的对象
		return this.getItemById(itemModel.getId());
	}

	private ItemDO convertItemDOFromItemModel(ItemModel itemModel) {
		if (itemModel == null) {
			return null;
		}
		ItemDO itemDO = new ItemDO();
		BeanUtils.copyProperties(itemModel, itemDO);
		return itemDO;
	}

	private ItemStockDO convertItemStockDOFromItemModel(ItemModel itemModel) {
		if (itemModel == null) {
			return null;
		}
		ItemStockDO itemStockDO = new ItemStockDO();
		BeanUtils.copyProperties(itemModel, itemStockDO);
		itemStockDO.setItemId(itemModel.getId());
		return itemStockDO;
	}

	@Override
	public List<ItemModel> listItem() {
		List<ItemDO> listItem = itemDOMapper.listItem();
		List<ItemModel> itemModelList = listItem.stream().map(itemDO -> {
			ItemStockDO itemStockDO = itemStockDOMapper.selectByItemId(itemDO.getId());
			ItemModel itemModel = this.convertItemModelFromItemDOAndItemStockDO(itemDO, itemStockDO);
			return itemModel;
		}).collect(Collectors.toList());
		return itemModelList;
	}

	@Override
	public ItemModel getItemById(Integer id) {
		// 获取商品信息
		ItemDO itemDO = itemDOMapper.selectById(id);
		if (itemDO == null) {
			return null;
		}
		ItemStockDO itemStockDO = itemStockDOMapper.selectByItemId(itemDO.getId());
		ItemModel itemModel = convertItemModelFromItemDOAndItemStockDO(itemDO, itemStockDO);

		// 获取商品秒杀活动信息
		SecKillModel secKillModel = secKillService.getItemByItemId(id);
		if (secKillModel != null && secKillModel.getStatus() != 3) {
			itemModel.setSecKillModel(secKillModel);
		}
		return itemModel;
	}

	private ItemModel convertItemModelFromItemDOAndItemStockDO(ItemDO itemDO, ItemStockDO itemStockDO) {
		if (itemDO == null || itemStockDO == null) {
			return null;
		}
		ItemModel itemModel = new ItemModel();
		BeanUtils.copyProperties(itemDO, itemModel);
		itemModel.setStock(itemStockDO.getStock());
		return itemModel;
	}

	@Override
	@Transactional
	public boolean decreaseStock(Integer itemId, Integer quantity) {
		int affectedRow = itemStockDOMapper.decreaseStock(itemId, quantity);
		if (affectedRow > 0) {
			return true;
		}
		return false;
	}

	@Override
	@Transactional
	public void increaseSales(Integer itemId, Integer quantity) {
		itemDOMapper.increaseSales(itemId, quantity);
	}

}
