package com.shopme.admin.order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shopme.admin.paging.PagingAndSortingHelper;
import com.shopme.admin.paging.PagingAndSortingParam;
import com.shopme.admin.setting.SettingService;
import com.shopme.common.entity.order.Order;
import com.shopme.common.entity.setting.Setting;
import com.shopme.common.exception.OrderNotFoundException;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class OrderController {
	private String defaultRedirectURL = "redirect:/orders/page/1?sortField=orderTime&sortDir=desc";
	
	@Autowired private OrderService orderService;
	@Autowired private SettingService settingService;
	
	@GetMapping("/orders")
	public String listFirstPage() {
		return defaultRedirectURL;
	}
	@GetMapping("/orders/page/{pageNum}")
	public String listByPage(
			@PagingAndSortingParam(listName = "listOrders", moduleURL = "/orders") PagingAndSortingHelper helper, 
			@PathVariable(name = "pageNum") int pageNum, 
			HttpServletRequest request) {
		
		orderService.listByPage(pageNum, helper);
		loadCurrencySetting(request);
		
		return "orders/orders";
	}
	
	private void loadCurrencySetting(HttpServletRequest request) {
		List<Setting> currencySetting = settingService.getCurrencySettings();
		
		for (Setting setting : currencySetting) {
			request.setAttribute(setting.getKey(), setting.getValue());
		}
	}
	
	@GetMapping("/orders/detail/{id}")
	public String viewOrder(@PathVariable("id") Integer id, Model model, 
			RedirectAttributes ra, HttpServletRequest request) {
		try {
			Order order = orderService.get(id);
			loadCurrencySetting(request);
			model.addAttribute("order", order);
			
			return "orders/order_detail_modal";
			
		} catch (OrderNotFoundException ex) {
			ra.addFlashAttribute("message", ex.getMessage());
			
			return defaultRedirectURL;
		}
	}
	
	@GetMapping("/orders/delete/{id}")
	public String deleteOrder(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {
		try {
			orderService.delete(id);
			ra.addFlashAttribute("message", "The order ID " + id + "has been deleted.");
		} catch (OrderNotFoundException ex) {
			ra.addFlashAttribute("message", ex.getMessage());
		}
		
		return defaultRedirectURL;
	}

}
