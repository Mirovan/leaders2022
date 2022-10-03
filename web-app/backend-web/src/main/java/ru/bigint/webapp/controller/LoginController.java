package ru.bigint.webapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ru.bigint.webapp.service.iface.user.RoleService;
import ru.bigint.webapp.service.iface.user.UserService;

@Controller
public class LoginController {

	private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	private final UserService userService;
	private final RoleService roleService;

	public LoginController(UserService userService, RoleService roleService) {
		this.userService = userService;
		this.roleService = roleService;
	}

	/**
	 * Авторизация пользователя - форма
	 * */
	@RequestMapping(value="/login/", method = RequestMethod.GET)
	public ModelAndView login(Authentication authentication) {
		if (authentication != null && authentication.isAuthenticated()) {
			return new ModelAndView("redirect:/cabinet/");
		} else {
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.setViewName("login/index");
			return modelAndView;
		}
	}


	/**
	 * Регистрация пользователя - форма
	 * */
//	@RequestMapping(value="/register/", method = RequestMethod.GET)
//	public ModelAndView registerUser(Authentication authentication) {
//		if (authentication != null && authentication.isAuthenticated()) {
//			return new ModelAndView("redirect:/cabinet/");
//		} else {
//			ModelAndView modelAndView = new ModelAndView();
//			User user = new User();
//			modelAndView.addObject("user", user);
//			modelAndView.setViewName("register/index");
//			return modelAndView;
//		}
//	}


	/**
	 * Регистрация пользователя - POST
	 * */
//	@RequestMapping(value = "/register/", method = RequestMethod.POST)
//	public ModelAndView registerUserPost(@Valid User user, BindingResult bindingResult) {

//		ModelAndView modelAndView = new ModelAndView();
//
//		//Есть ли пользователь с таким же email ?
//		User userExists = userService.getByEmail(user.getEmail());
//		if (userExists != null) {
//			bindingResult.rejectValue("email", "error.user", "There is already a user registered with the email provided");
//		}
//
//		//Если есть ошибки при регистрации
//		if (bindingResult.hasErrors()) {
//			modelAndView.setViewName("register/index");
//		} else { //регистрируем
//			user.setActive(true);
//
//			//random password
//			String password = userService.generateRandomPassword();
//			user.setPassword( userService.encodePassword(password) );
//
//			//get Roles
//			Set<Role> roles = new HashSet<>();
//			roles.add( roleService.findRoleByCode("DEFAULT") );
//			user.setRoles(roles);
//
//			userService.add(user);
//
//			modelAndView.addObject("user", new User());
//			modelAndView.addObject("password", password);
//			modelAndView.setViewName("register/register-complite");
//		}
//		return modelAndView;
//	}


}
