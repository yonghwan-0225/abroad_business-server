package com.t.hc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/* @Controller :  controller  기능의  servlet
 *  사용자 정의  메소드 선언구에 url 매핑 정보 설정
 *  가령 호출시 http://ip:port/project명/coding.do
 *  @RequestMapping(value="/coding.do", method=RequestMethod.GET)
 *  
 * 
 */
@Controller
public class HandcodingController {
	
	@RequestMapping(value="/coding.do", method=RequestMethod.GET)
	public String coding(Model model) {
		
		//request.setAttribute("test", 저장데이터); 동일
		model.addAttribute("test", "data view");
		return "coding";//요청 객체를 유지하면서 coding.do 로 이동(forward)
		//return "redirect://list.jsp" - 리다이렉트로 이동
	}

	
	
}
