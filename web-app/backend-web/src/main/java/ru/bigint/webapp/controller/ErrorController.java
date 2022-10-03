package ru.bigint.webapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ru.bigint.webapp.utils.LoggerUtil;

import javax.servlet.http.HttpServletRequest;


@Controller
public class ErrorController {

    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value="/error/403/", method = RequestMethod.GET)
    public ModelAndView handleError403(HttpServletRequest request, Exception e) {
        LoggerUtil.writeError(LOGGER, "Request: " + request.getRequestURL() + " raised " + e);
        return new ModelAndView("/error/403");
    }

}