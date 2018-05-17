package com.e3mall.content.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.druid.support.json.JSONUtils;
import com.e3mall.common.jedis.JedisClient;
import com.e3mall.common.untils.JsonUtils;
import com.e3mall.content.service.ContentService;
import com.e3mall.mapper.TbContentMapper;
import com.e3mall.pojo.TbContent;
import com.e3mall.pojo.TbContentExample;
import com.e3mall.pojo.TbContentExample.Criteria;

/**
 * 
 * @author zjt
 * @Description: 初始化首页
 * @date 2018年3月17日 下午2:41:06
 *
 */
@Service("contentServiceImpl")
public class ContentServiceImpl implements ContentService{

	@Autowired
	private TbContentMapper tbContentMapper;
	@Autowired
	private JedisClient jedisClient;

	@Value("${CONTENT_LIST}")
	private String CONTENT_LIST;
	
	public static void main(String[] args) {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring/applicationContext-redis.xml");
		JedisClient jd = applicationContext.getBean(JedisClient.class);
		System.out.println(jd.get("a"));
	}

	/**首页显示图片*/
	@Override
	public List<TbContent> getContentListByCid(long categoryId) {

		//查询缓存
		try {
			String json = jedisClient.hget(CONTENT_LIST, categoryId+"");
			if (!StringUtils.isEmpty(json)) {
				List<TbContent> tbContents = JsonUtils.jsonToList(json, TbContent.class);
				return tbContents;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		TbContentExample tbContentExample = new TbContentExample();
		Criteria criteria = tbContentExample.createCriteria();
		criteria.andCategoryIdEqualTo(categoryId);
		List<TbContent> tbContents = tbContentMapper.selectByExampleWithBLOBs(tbContentExample);

		//向缓存中添加数据
		try {
			jedisClient.hset(CONTENT_LIST, categoryId+"",JsonUtils.objectToJson(tbContents));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tbContents;
	}

}
