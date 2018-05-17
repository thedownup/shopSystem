package com.e3mall.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.e3mall.common.pojo.EasyUITreeNode;
import com.e3mall.mapper.TbItemCatMapper;
import com.e3mall.pojo.TbItemCat;
import com.e3mall.pojo.TbItemCatExample;
import com.e3mall.pojo.TbItemCatExample.Criteria;
import com.e3mall.service.ItemCatService;

/**
 * 
 * @author zjt
 * @Description: 获取选择类目的列表
 * @date 2018年2月25日 下午7:47:51
 *
 */
@Service
public class ItemCatServiceImpl implements ItemCatService{
	
	@Autowired
	private TbItemCatMapper tbItemCatMapper;

	/**
	 * 获取选择类目的列表
	 */
	@Override
	public List<EasyUITreeNode> getEasyUITreeNodes(long parentID) {
		
		TbItemCatExample tbItemCatExample = new TbItemCatExample();
		Criteria criteria = tbItemCatExample.createCriteria();
		
		criteria.andParentIdEqualTo(parentID);
		List<TbItemCat> selectByExample = tbItemCatMapper.selectByExample(tbItemCatExample);
		List<EasyUITreeNode> resultList = new ArrayList<>();
		for (TbItemCat tbItemCat : selectByExample) {
			EasyUITreeNode easyUITreeNode = new EasyUITreeNode();
			easyUITreeNode.setId(tbItemCat.getId());
			easyUITreeNode.setText(tbItemCat.getName());
			easyUITreeNode.setState(tbItemCat.getIsParent()?"closed":"open");
			resultList.add(easyUITreeNode);
		}
		return resultList;
	}

}
