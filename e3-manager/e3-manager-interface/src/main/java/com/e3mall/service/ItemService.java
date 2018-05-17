package com.e3mall.service;

import com.e3mall.common.pojo.EasyUIDataGridResult;
import com.e3mall.common.untils.E3Result;
import com.e3mall.pojo.TbItem;
import com.e3mall.pojo.TbItemDesc;

public interface ItemService {
	public TbItem getTbItemById(long id);
	public EasyUIDataGridResult getEasyUIDataGridResult(Integer page,Integer rows);
	public E3Result addItem(TbItem item ,String desc);
	public E3Result deleteItem(long... ids);
	public E3Result reshelf(long... ids);
	public E3Result instock(long... ids);
	public TbItemDesc getItemDescById(long itemId);
}
