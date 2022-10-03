package ru.bigint.webapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ru.bigint.webapp.data.entity.user.User;
import ru.bigint.webapp.service.iface.user.UserService;

import java.security.Principal;

@Controller
public class UserController {

	private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	/**
	 * Главная страница профиля пользователя
	 * */
	@RequestMapping(value="/personal/", method = RequestMethod.GET)
	public ModelAndView showUserPage(Principal principal) {
		User authUser = userService.getByEmail( principal.getName() );
		if (authUser != null) {
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.setViewName("personal/index");
			return modelAndView;
		} else {
			return new ModelAndView("redirect:/login/");
		}
	}

}
