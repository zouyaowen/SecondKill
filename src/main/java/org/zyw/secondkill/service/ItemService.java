package org.zyw.secondkill.service;

import java.util.List;

import org.zyw.secondkill.service.model.ItemModel;

public interface ItemService {
	// 创建商品
	ItemModel createItem(ItemModel itemModel);

	// 商品列表展示
	List<ItemModel> listItem();

	// 商品详情展示
	ItemModel getItemById(Integer id);

	// 库存扣减
	boolean decreaseStock(Integer itemId, Integer quantity);

	// 商品销量增加
	void increaseSales(Integer itemId, Integer quantity);

}
