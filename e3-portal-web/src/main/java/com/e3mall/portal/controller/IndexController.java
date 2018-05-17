package com.e3mall.portal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.e3mall.content.service.ContentService;
import com.e3mall.pojo.TbContent;
/**
 * 
 * @author zjt
 * @Description: 首页展示
 * @date 2018年3月15日 下午3:42:27
 *
 */
@Controller
public class IndexController {
	
	@Autowired
	private ContentService contentService;
	
	@Value("${CONTENT_LONBO_ID}")
	private long CONTENT_LONBO_ID;
	

	@RequestMapping("/index")
	public String showIndex(ModelMap modelMap){
		//初始化首页轮播图
		List<TbContent> contentListByCid = contentService.getContentListByCid(CONTENT_LONBO_ID);
		for (TbContent tbContent : contentListByCid) {
			System.out.println(tbContent.toString());
		}
		modelMap.addAttribute("ad1List", contentListByCid);
		return "index";
	}
}
