package com.e3mall.cart.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.e3mall.cart.service.CartService;
import com.e3mall.common.jedis.JedisClient;
import com.e3mall.common.untils.CookieUtils;
import com.e3mall.common.untils.E3Result;
import com.e3mall.common.untils.JsonUtils;
import com.e3mall.pojo.TbItem;
import com.e3mall.pojo.TbUser;
import com.e3mall.service.ItemService;

/**
 * @author zjt
 * @Description: 购物车相关控制器
 */
@Controller
public class CartController {

	@Autowired
	private ItemService itemService;
	@Autowired
	private CartService cartService;
	@Value("${TT_CART}")
	private String TT_CART;
	@Value("${CART_EXPIRE}")
	private Integer CART_EXPIRE;

	@RequestMapping("/cart/cart")
	public String showCartList(HttpServletRequest request,HttpServletResponse response , Model model) {
		//取购物车商品列表
		List<TbItem> cartList = getCartList(request);

		TbUser user = (TbUser) request.getAttribute("user");
		// 如果是登录状态
		if (user != null) {
			// 从cookie中取购物车列表
			// 如果不为空，把cookie中的购物车商品和服务端的购物车商品合并。
			cartService.mergeCart(user.getId(), cartList);
			// 把cookie中的购物车删除
			CookieUtils.deleteCookie(request, response, "cart");
			// 从服务端取购物车列表
			cartList = cartService.getCartList(user.getId());
		}
		//传递给页面
		model.addAttribute("cartList", cartList);
		return "cart";
	}

	@RequestMapping("/cart/add/{itemId}")
	public String addCart(@PathVariable long itemId,@RequestParam(defaultValue="1") int num,
			HttpServletRequest request,HttpServletResponse response){

		//判断是否登录
		TbUser user = (TbUser) request.getAttribute("user");
		System.out.println(user == null?"没登录":"登录成功");
		//登录了
		if (user != null) {
			cartService.addCart(user.getId(), itemId, num);
			return "cartSuccess";
		}

		//cookie中取
		List<TbItem> cartList = getCartList(request);

		//判断商品在商品列表中是否存在
		boolean hasItem = false;

		for (TbItem tbItem : cartList) {
			if (tbItem.getId().longValue() == num) {
				//增加数目
				tbItem.setNum(tbItem.getNum()+num);
				hasItem = true;
				break;
			}
		}
		
		
		//如果不存在
		if (!hasItem) {
			//取出商品
			TbItem tbItem = itemService.getTbItemById(itemId);
			//取出一张照片
			String image = tbItem.getImage();
			if (StringUtils.isNoneBlank(image)) {
				String[] images = image.split(",");
				tbItem.setImage(images[0]);
			}
			//设置数量
			tbItem.setNum(num);
			cartList.add(tbItem);
		}
		//添加cookie
		CookieUtils.setCookie(request, response, TT_CART, JsonUtils.objectToJson(cartList),true);
		return "cartSuccess";
	}

	//修改数量
	@RequestMapping("/cart/update/num/{itemId}/{num}")
	@ResponseBody
	public E3Result updateNum(@PathVariable Long itemId, @PathVariable Integer num,
			HttpServletRequest request, HttpServletResponse response) {

		// 判断用户是否为登录状态
		TbUser user = (TbUser) request.getAttribute("user");
		if (user != null) {
			cartService.updateCartNum(user.getId(), itemId, num);
			return E3Result.ok();
		}
		//先从cookie取购物车
		List<TbItem> cartList = getCartList(request);

		for (TbItem tbItem : cartList) {
			if (tbItem.getId().longValue() == itemId.longValue()) {
				tbItem.setNum(num);
			}
		}
		//添加cookie
		CookieUtils.setCookie(request, response, TT_CART, JsonUtils.objectToJson(cartList),true);

		return E3Result.ok();
	}

	@RequestMapping("/cart/delete/{itemId}")
	public String deleteCartItem(@PathVariable Long itemId, HttpServletRequest request,
			HttpServletResponse response) {
		//判断是否登录
		TbUser user = (TbUser) request.getAttribute("user");
		if (user != null) {
			cartService.deleteCartItem(user.getId(), itemId);
			return "redirect:/cart/cart.html";
		}

		// 从cookie中取购物车商品列表
		List<TbItem> cartList = getCartList(request);
		//遍历列表找到对应的商品
		for (TbItem tbItem : cartList) {
			if (tbItem.getId().longValue() == itemId.longValue()) {
				// 4、删除商品。
				cartList.remove(tbItem);
				break;
			}
		}
		//把商品列表写入cookie。
		CookieUtils.setCookie(request, response, TT_CART, JsonUtils.objectToJson(cartList), CART_EXPIRE, true);
		//返回逻辑视图：在逻辑视图中做redirect跳转。
		return "redirect:/cart/cart.html";
	}


	private List<TbItem> getCartList(HttpServletRequest request){

		//获取购物车
		String json = CookieUtils.getCookieValue(request, TT_CART,true);
		//不为空就返回
		if (StringUtils.isNotBlank(json)) {

			List<TbItem> list = JsonUtils.jsonToList(json, TbItem.class);
			return list;
		}
		return new ArrayList<TbItem>();
	}
}
