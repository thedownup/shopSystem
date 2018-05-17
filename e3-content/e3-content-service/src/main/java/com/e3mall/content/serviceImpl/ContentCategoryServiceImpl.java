package com.e3mall.content.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.e3mall.common.pojo.EasyUITreeNode;
import com.e3mall.common.untils.E3Result;
import com.e3mall.content.service.ContentCategoryService;
import com.e3mall.mapper.TbContentCategoryMapper;
import com.e3mall.pojo.TbContentCategory;
import com.e3mall.pojo.TbContentCategoryExample;
import com.e3mall.pojo.TbContentCategoryExample.Criteria;

/**
 * 
 * @author zjt
 * @Description: 内容分类管理
 * @date 2018年3月15日 下午6:09:06
 *
 */
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService{

	@Autowired
	private TbContentCategoryMapper contentCategoryMapper;

	@Override
	public List<EasyUITreeNode> getContentCategoryList(long patentId) {
		TbContentCategoryExample tbContentCategoryExample = new TbContentCategoryExample();
		Criteria criteria = tbContentCategoryExample.createCriteria();
		criteria.andParentIdEqualTo(patentId);
		List<TbContentCategory> catList = contentCategoryMapper.selectByExample(tbContentCategoryExample);
		List<EasyUITreeNode> nodelist = new ArrayList<EasyUITreeNode>();
		for (TbContentCategory tbContentCategory : catList) {
			EasyUITreeNode node = new EasyUITreeNode();
			node.setId(tbContentCategory.getId());
			node.setText(tbContentCategory.getName());
			node.setState(tbContentCategory.getIsParent()?"closed":"open");
			nodelist.add(node);
		}
		return nodelist;
	}

	/**添加类目*/
	@Override
	public E3Result addContentCategoryList(long patentId, String name) {
		try {
			TbContentCategory category = new TbContentCategory();
			category.setName(name);
			category.setSortOrder(1);
			category.setParentId(patentId);
			category.setStatus(1);
			category.setIsParent(false);
			category.setUpdated(new Date());
			//插入
			contentCategoryMapper.insert(category);
			TbContentCategory parCategory = contentCategoryMapper.selectByPrimaryKey(patentId);
			//如果不是父节点
			if (!parCategory.getIsParent()) {
				parCategory.setIsParent(true);;
				contentCategoryMapper.updateByPrimaryKey(parCategory);
			}
			return E3Result.ok(category);
		} catch (Exception e) {
		}
		return E3Result.build(500, "添加类目error");
	}

	/**删除类目*/
	@Override
	public E3Result deleteContentCategoryList(long id) {

		try {
			TbContentCategory category = contentCategoryMapper.selectByPrimaryKey(id);

			Boolean isParent = category.getIsParent();
			//是否为父节点
			if (isParent) {
				TbContentCategoryExample example = new TbContentCategoryExample();
				Criteria criteria = example.createCriteria();
				criteria.andParentIdEqualTo(id);
				//获得所有子节点
				List<TbContentCategory> categorys = contentCategoryMapper.selectByExample(example);
				for (TbContentCategory tbContentCategory : categorys) {
					contentCategoryMapper.deleteByPrimaryKey(tbContentCategory.getId());
				}
				//最后在删除本节点
				contentCategoryMapper.deleteByPrimaryKey(id);
				return E3Result.ok();
			}else {
				contentCategoryMapper.deleteByPrimaryKey(id);
				return E3Result.ok();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		return E3Result.build(500, "删除类目error");

	}

	/**重命名类目*/
	@Override
	public E3Result updateContentCategory(long id, String name) {

		try {
			TbContentCategory category = new TbContentCategory();
			category.setName(name);
			contentCategoryMapper.updateByPrimaryKeySelective(category);

			return E3Result.ok();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return E3Result.build(500, "重命名类目error");
	}

}
