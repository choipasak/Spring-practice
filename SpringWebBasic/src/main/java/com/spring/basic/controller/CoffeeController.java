package com.spring.basic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/coffee")
public class CoffeeController {

	//커피 주문 화면을 열어주는 메서드
	@GetMapping("/order")
	public String coffeeOrder() {
		System.out.println("/coffee/order: GET 요청 발생!");
		return "response/coffee-form";
	}

	//주문하기 버튼을 누르면 들어오는 요청을 받는 메서드
	@PostMapping("/result")
	public String coffeeResult(String menu,
			@RequestParam(defaultValue = "3000") int price, Model model) {
		//이벤트가 발생하지X: 페이지 들어가서 select를 건드리지 않는다는말 or 그냥 정해져 있는 아메리카노에 바로 주문하기를 눌렀다는 뜻
		//이벤트가 발생하지X -> input태그에 null이 들어가게된다. int에 null이 들어가서 error
		//@RequestParam를 이용해서 이벤트가 발생하지 않았을 경우, 기본 value값을 지정 해 준다.
		System.out.println("menu: " + menu);
		System.out.println("price: " + price);

		model.addAttribute("menu", menu);
		model.addAttribute("p", price);
		
		return "response/coffee-result";
	}

}
