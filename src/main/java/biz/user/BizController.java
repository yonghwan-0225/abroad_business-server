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
import bean.UserBean;
import model.ExchangeDAO;
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

	@Autowired
	public void setDao(UserDAO usrdao, ExchangeDAO exdao) {
		this.userdao = usrdao;
		this.exchangedao = exdao;
	}

	@RequestMapping(value="/api/login" , produces="application/json;charset=utf-8")
	@ResponseBody
	public HashMap<String, Object> singin (HttpServletRequest request) throws Exception {
		request.setCharacterEncoding("utf-8");
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		UserBean check = userdao.loginCheck(id, pw);

		HashMap<String, Object> map = new HashMap<String, Object>();

		if(check != null) {
			HashMap<String, Object> userMap = new HashMap<String, Object>();
			ArrayList<String> orderData = new ArrayList<String>();
			userMap.put("id", check.getU_id());
			userMap.put("pw", check.getU_pw());
			userMap.put("email", check.getU_email());
			userMap.put("phone", check.getU_phone());
			userMap.put("name", check.getU_name());
			userMap.put("address", check.getU_address());
			//userMap
			orderData.add("orderData");
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
		//오라클에 null값이 들어가지 않게끔 프로그래밍 진행
		//질문ㄱ ㄱ
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

	@RequestMapping(value="/api/modify" , produces="application/json;charset=utf-8")
	@ResponseBody
	public HashMap<String, Object> modify(HttpServletRequest request) throws Exception {
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		UserBean check = userdao.loginCheck(id, pw);
		HashMap<String, Object> map = new HashMap<String, Object>();
		if(check!=null) {
			boolean check2 = userdao.updateUser(request.getParameter("id"), request.getParameter("pw"),
				request.getParameter("name"), request.getParameter("phone"),
				request.getParameter("email"), request.getParameter("address"));
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
		String id = request.getParameter("id");
		ExchangeBean checkuser = exchangedao.selectUserReq(id);
		HashMap<String, Object> map = new HashMap<String, Object>();

		if(checkuser != null) {
			HashMap<String, Object> userMap = new HashMap<String, Object>();
			userMap.put("id", checkuser.getU_id());
			userMap.put("reqID", checkuser.getT_id());
			userMap.put("time", checkuser.getT_time());
			userMap.put("type", checkuser.getT_type());
			userMap.put("amount", checkuser.getT_amount());
			userMap.put("exchange_rate", checkuser.getT_exchange_rate());
			userMap.put("total", checkuser.getT_total());
			map.put("status", true);
			map.put("reqData", userMap);
		}else {
			map.put("status", false);
			map.put("message", "서버 오류로 환전요청에 실패했습니다");
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
