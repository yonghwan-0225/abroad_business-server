package biz.user;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import bean.ExchangeBean;
import bean.TravelBean;
import bean.UserBean;
import model.ExchangeDAO;
import model.TravelDAO;
import model.UserDAO;


/* @Controller :  controller  기능의  servlet
 *  사용자 정의  메소드 선언구에 url 매핑 정보 설정
 *  가령 호출시 http://ip:port/project명/coding.do
 *  @RequestMapping(value="/coding.do", method=RequestMethod.GET)
 */
@Controller
public class BizController {

	UserDAO userdao;
	ExchangeDAO exchangedao;
	TravelDAO traveldao;

	@Autowired
	public void setDao(UserDAO usrdao, ExchangeDAO exdao, TravelDAO tradao) {
		this.userdao = usrdao;
		this.exchangedao = exdao;
		this.traveldao = tradao;
	}

	@RequestMapping(value="/api/login" , produces="application/json;charset=utf-8")
	@ResponseBody
	public HashMap<String, Object> singin (HttpServletRequest request) throws Exception {
		request.setCharacterEncoding("utf-8");
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		UserBean check = userdao.loginCheck(id, pw);
		ArrayList<ExchangeBean> userReq = exchangedao.selectUserReq(id);
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		if(check != null) {
			HashMap<String, Object> userMap = new HashMap<String, Object>();
			ArrayList<Object> orderData = new ArrayList<Object>();
			HashMap<String, Object> exMap;
			userMap.put("id", check.getU_id());
			userMap.put("pw", check.getU_pw());
			userMap.put("email", check.getU_email());
			userMap.put("phone", check.getU_phone());
			userMap.put("name", check.getU_name());
			userMap.put("address", check.getU_address());
			//회원정보 반환
			for(ExchangeBean currReq : userReq) {
			exMap = new HashMap<String, Object>();
			exMap.put("orderNo", currReq.getT_id());
			exMap.put("orderType", currReq.getT_type());
			exMap.put("status", currReq.getT_status());
			exMap.put("amount", currReq.getT_amount());
			exMap.put("serviceRate", currReq.getT_exchange_rate());
			exMap.put("total", currReq.getT_total());
			exMap.put("time", currReq.getT_time());
			orderData.add(exMap);
			}
			//환전 맵 반환
			map.put("orderData", orderData);
			map.put("status", true);
			map.put("userData", userMap);

		}else {
			map.put("status", false);
			map.put("message", "아이디 또는 비밀번호가 틀렸습니다");
		}
		return map;
	}

		@RequestMapping(value="/api/join" , produces="application/json;charset=utf-8")
		@ResponseBody
		public HashMap<String, Object> signup(HttpServletRequest request) throws Exception{
			request.setCharacterEncoding("utf-8");
			String id = request.getParameter("id");
			UserBean check;
			HashMap<String, Object> map = new HashMap<String, Object>();
				check = userdao.selectUser(id);
				if(check==null) {
					boolean check2 = userdao.joinUser(new UserBean(request.getParameter("id"), request.getParameter("pw"),
							request.getParameter("name"), request.getParameter("phone"),
							request.getParameter("email"), request.getParameter("address")));
						if(check2) {
							map.put("status", true);
						}else {
							map.put("status", false);
							map.put("message", "서버 오류로 가입에 실패했습니다");
						}
				}else {
					map.put("status", false);
					map.put("message", "이미 가입된 아이디입니다");
				}
			return map;
		}

	@RequestMapping(value="/api/edit" , produces="application/json;charset=utf-8")
	@ResponseBody
	public HashMap<String, Object> modify(HttpServletRequest request) throws Exception {
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		UserBean check = userdao.loginCheck(id, pw);
		HashMap<String, Object> map = new HashMap<String, Object>();
		if(check!=null) {
			boolean check2;
			if(request.getParameter("newPw")!=null) {
				check2 = userdao.updateUser(request.getParameter("id"), request.getParameter("newPw"),
						request.getParameter("name"), request.getParameter("phone"),
						request.getParameter("email"), request.getParameter("address"));
			}else {
				check2 = userdao.updateUser(request.getParameter("id"), request.getParameter("Pw"),
						request.getParameter("name"), request.getParameter("phone"),
						request.getParameter("email"), request.getParameter("address"));
			}
				if(check2) {
					map.put("status", true);
				}else {
					map.put("status", false);
					map.put("message", "서버 오류로 수정에 실패했습니다");
				}
				}else {
				map.put("status", false);
				map.put("message", "비밀번호가 틀렸습니다");
		}
			
		return map;
	}

	@RequestMapping(value="/api/exchange" , produces="application/json;charset=utf-8")
	@ResponseBody
	public HashMap<String, Object> exchange(HttpServletRequest request) throws Exception {
		request.setCharacterEncoding("utf-8");
		String id = request.getParameter("id");
		UserBean check = null;
		HashMap<String, Object> map = new HashMap<String, Object>();
		HashMap<String, Object> map2 = new HashMap<String, Object>();
		check = userdao.selectUser(id);
		if (check != null) {
			// 이미 회원이 존재할 경우 환전 가능한 insert
			boolean check2 = exchangedao.insertReq(new ExchangeBean(request.getParameter("id"), request.getParameter("time"), 
																								request.getParameter("orderType"), "결제 완료", request.getParameter("amount"),
																								request.getParameter("serviceRate"), request.getParameter("total")));
			
			if (check2) {
				map2.put("orderNo", exchangedao.selectT_id(request.getParameter("id"), request.getParameter("time")));
				map2.put("orderType", request.getParameter("orderType"));
				map2.put("status", "결제완료");
				map2.put("amount", request.getParameter("amount"));
				map2.put("serviceRate", request.getParameter("serviceRate"));
				map2.put("total", request.getParameter("total"));
				map2.put("time", request.getParameter("time"));
				
				map.put("newOrderData", map2);
				map.put("status", true);
			} else {
				map.put("status", false);
				map.put("message", "환전요청불가");
			}
		} else {
			map.put("status", false);
			map.put("message", "아이디가 없습니다.");
		}
		return map;
	}
	
	@RequestMapping(value="/api/travel" , produces="application/json;charset=utf-8")
	@ResponseBody
	public HashMap<String, Object> travel(HttpServletRequest request) throws Exception {
		request.setCharacterEncoding("utf-8");
		String id = request.getParameter("id");
		UserBean check = null;
		HashMap<String, Object> map = new HashMap<String, Object>();
		check = userdao.selectUser(id);
		if (check != null) {
			// 이미 회원이 존재할 경우 환전 가능한 insert
			boolean check2 = traveldao.insertTravel(new TravelBean(request.getParameter("id"), request.getParameter("dateDepart"), 
																								request.getParameter("dateArrival"),request.getParameter("destination")));
			
			if (check2) {
				map.put("status", true);
			} 
			else {
			map.put("status", false);
			map.put("message", "요청이 실패하였습니다..");
			}
		}
		return map;
	}
	
	
	

	/* 발생되는 모든 Exception 처리 가능한 어노테이션 선언하기
	 * 발생된 예외 메세지를 요청 객체에 저장
	 * message.jsp로  포워드 방식으로 page이동 하기
	 * @param Exception.class
	 * @return  ModelAndView  */
	@ExceptionHandler(Exception.class)
	public ModelAndView exceptionTest(Exception e){
		e.printStackTrace();
		ModelAndView m = new ModelAndView();// 데이터와 이동되어 처리되는  jsp의 파일명 정보 보유하게 되는 스프링 자체 API

		m.addObject("message",  e.getMessage());//request.setAttriburte("message", e.getMessage()) 동일한 코드
		m.setViewName("message"); //스프링 설정 파일의 /WEB-INF/views/message.jsp로  forward 로 이동 의미
		return m;
	}
}
