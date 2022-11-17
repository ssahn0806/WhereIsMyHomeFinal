package com.ssafy.house.controller;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.house.model.dto.User;
import com.ssafy.house.model.service.UserService;

@RequestMapping("/api/users")
@RestController
public class UserController {

	@Autowired
	private UserService userService;

//	@Override
//	public Object handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		
//		String url = request.getServletPath();
//		
//        if(url.equals("/user/login.do")) {
//            return login(request, response);
//        } else if(url.equals("/user/loginform.do")) {
//            return loginform(request, response);
//        } else if(url.equals("/user/registerform.do")) {
//            return registerform(request, response);
//        } else if(url.equals("/user/register.do")) {
//            return register(request, response);
//        }  else if(url.equals("/user/findPasswordForm.do")) {
//            return findPasswordForm(request, response);
//        } else if(url.equals("/user/findPassword.do")) {
//            return findPassword(request, response);
//        }
//        else {
//            HttpSession session = request.getSession();
//            if(session.getAttribute("userid") == null) {
//                System.out.println("로그인 안됨!!");
//                return new PageInfo(false, "/user/loginform.do");
//            }
//            
//            if(url.equals("/user/logout.do")) {
//                return logout(request, response);
//            } else if(url.equals("/user/detail.do")) {
//                return detailform(request, response);
//            } else if(url.equals("/user/modify.do")) {
//                return modify(request, response);
//            } else if(url.equals("/user/delete.do")) {
//                return delete(request, response);
//            } else if(url.equals("/user/favorite.do")) {
//                return favorite(request,response);
//            }
//        }
//		
//		return null;
//	}

	@GetMapping("/{userid}")
	private ResponseEntity<?> findPassword(@PathVariable String userid, @RequestParam String username) {
		Map<String, Object> info = new HashMap<>();
		info.put("userid", userid);
		info.put("username", username);
		String pw = userService.findPassword(info);

		if (pw != null) {
			return ResponseEntity.ok(pw);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PutMapping("/{userid}")
	private ResponseEntity<?> modify(@PathVariable String userid, @RequestBody User user, HttpSession session)
			throws Exception {

		User target = userService.getUser(userid);

		if (target != null) {
			target.setUsername(user.getUsername());
			target.setUserpw(user.getUserpw());
			boolean res = userService.modify(target);

			if (res) {
//				session.setAttribute("userpw", target.getUserpw());
//				session.setAttribute("userName", target.getUsername());
				return ResponseEntity.ok(target);
			} else {
				return ResponseEntity.internalServerError().build();
			}
		} else {
			return ResponseEntity.notFound().build();
		}

	}

	@DeleteMapping("/{userid}")
	private ResponseEntity<?> delete(@PathVariable String userid, HttpSession session) throws Exception {
		session.invalidate();

		User user = userService.getUser(userid);

		if (user != null) {
			boolean res = userService.delete(userid);
			if (res) {
				return ResponseEntity.noContent().build();
			} else {
				return ResponseEntity.internalServerError().build();
			}
		} 
		else {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping
	private ResponseEntity<?> register(@RequestBody User user) {
		user.setUserlevel("user");
		user.setUserfavdongcode(null);

		
		boolean res = userService.register(user);
		
		if(res) {
			return ResponseEntity.created(URI.create("/api/users/"+user.getUserid())).build();
		}
		else {
			return ResponseEntity.internalServerError().build();
		}
	}

	@PostMapping("/{userid}")
	private ResponseEntity<?> login(@PathVariable String userid,@RequestBody User user,HttpSession session) throws Exception {

		User target = userService.getUser(userid);
		
		if(target!=null) {
			Map<String,Object> info = new HashMap<>();
			info.put("userid",user.getUserid());
			info.put("userpw",user.getUserpw());
			String name = userService.login(info);			
			
			if(name!=null) {
				session.setAttribute("userid", target.getUserid());
				session.setAttribute("userpw", target.getUserpw());
				session.setAttribute("userName", target.getUsername());
				session.setAttribute("userlevel", target.getUserlevel());
				session.setAttribute("userfavDong", target.getUserfavdongcode());
				return ResponseEntity.ok().build();
			}
			else {
				return ResponseEntity.internalServerError().build();
			}
		}
		else {
			return ResponseEntity.notFound().build();
		}
	}

//	protected PageInfo logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
//
//		HttpSession session = request.getSession();
//		session.invalidate();
//
//		return new PageInfo(false, "/index.jsp");
//
//	}

	@PutMapping("/{userid}/favorite")
	protected ResponseEntity<?> favorite(@PathVariable String userid,@RequestBody User user, HttpSession session) throws Exception {
		User target = userService.getUser(userid);
		
		if(target!=null) {
			Map<String,Object> info = new HashMap<>();
			info.put("dongCode",user.getUserfavdongcode());
			info.put("userid",userid);
			boolean res = userService.favorite(info);
			
			if(res) {
				session.setAttribute("userfavDong", user.getUserfavdongcode());
				return ResponseEntity.ok(target);
			}
			else {
				return ResponseEntity.internalServerError().build();
			}
			
		}
		else {
			return ResponseEntity.notFound().build();
		}
	}
}
