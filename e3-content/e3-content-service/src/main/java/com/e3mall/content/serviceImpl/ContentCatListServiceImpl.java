package com.e3mall.content.serviceImpl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import com.e3mall.common.jedis.JedisClient;
import com.e3mall.common.pojo.EasyUIDataGridResult;
import com.e3mall.common.untils.E3Result;
import com.e3mall.content.service.ContentCatListService;
import com.e3mall.mapper.TbContentMapper;
import com.e3mall.pojo.TbContent;
import com.e3mall.pojo.TbContentExample;
import com.e3mall.pojo.TbContentExample.Criteria;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import sun.tools.jar.resources.jar;

/**
 * 
 * @author zjt
 * @Description: 内容管理
 * @date 2018年3月16日 下午4:15:17
 *
 */
@Service
public class ContentCatListServiceImpl implements ContentCatListService{

	@Autowired
	private TbContentMapper tbContentMapper;
	@Autowired
	private JedisClient jedisClient;
	@Value("${CONTENT_LIST}")
	private String CONTENT_LIST;
	
	
	@Override
	public EasyUIDataGridResult getEasyUITreeNodes(long categoryId, Integer page, Integer rows) {

		PageHelper.startPage(page, rows);
//		TbContentExample tbContentExample = new TbContentExample();
//		Criteria criteria = tbContentExample.createCriteria();
//		criteria.andCategoryIdEqualTo(categoryId);
		List<TbContent> contents = tbContentMapper.selectByCategoryId(categoryId);
		
		EasyUIDataGridResult dataGridResult = new EasyUIDataGridResult();
		dataGridResult.setRows(contents);
		//取分页结果
		PageInfo<TbContent> pageInfo = new PageInfo<>(contents);
		//取总记录数
		dataGridResult.setTotal(pageInfo.getTotal());
		return dataGridResult;
	}

	/**删除内容*/
	@Override
	public E3Result deleteContent(long[] ids) {
		try {
			for (long id : ids) {
				tbContentMapper.deleteByPrimaryKey(id);
				jedisClient.hdel(CONTENT_LIST, id+"");
			}
			return E3Result.ok();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return E3Result.build(500, "删除内容error");
	}

	/**更新内容*/
	@Override
	public E3Result updateContent(TbContent tbContent) {
		try {
			tbContent.setUpdated(new Date());
			tbContentMapper.updateByPrimaryKeySelective(tbContent);
			jedisClient.hdel(CONTENT_LIST, tbContent.getCategoryId().toString());
			return E3Result.ok();
		} catch (Exception e) {
		}
		return E3Result.build(500, "更新内容error");
	}
	
	/**新增内容*/
	@Override
	public E3Result addContent(TbContent tbContent){
		try {
			tbContent.setCreated(new Date());
			tbContent.setUpdated(new Date());
			tbContentMapper.insertSelective(tbContent);
			jedisClient.hdel(CONTENT_LIST, tbContent.getCategoryId().toString());
			return E3Result.ok();
		} catch (Exception e) {
		}
		return E3Result.build(500, "新增内容error");
	}
	
}
