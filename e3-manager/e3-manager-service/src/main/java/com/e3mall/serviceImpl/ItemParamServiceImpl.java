package com.e3mall.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.e3mall.common.pojo.EasyUIDataGridResult;
import com.e3mall.mapper.TbItemParamMapper;
import com.e3mall.pojo.TbItem;
import com.e3mall.pojo.TbItemParam;
import com.e3mall.pojo.TbItemParamExample;
import com.e3mall.service.ItemParamService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
/**
 * 
 * @author zjt
 * @Description: 规格参数
 * @date 2018年3月16日 下午10:30:16
 *
 */
@Service
public class ItemParamServiceImpl implements ItemParamService{

	@Autowired
	private TbItemParamMapper tbItemParamMapper;

	@Override
	public EasyUIDataGridResult getEasyUIDataGridResult(Integer page, Integer rows) {
		PageHelper.startPage(page, rows);
		TbItemParamExample tbItemParamExample = new TbItemParamExample();
		List<TbItemParam> tbItemParams = tbItemParamMapper.selectByExample(tbItemParamExample);
		//创建一个返回值对象
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		result.setRows(tbItemParams);
		//取分页结果
		PageInfo<TbItemParam> pageInfo = new PageInfo<>(tbItemParams);
		//取总记录数
		long total = pageInfo.getTotal();
		result.setTotal(total);
		return result;
	}

}
