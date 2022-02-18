package com.bolsaideas.springboot.form.app.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class TiempoTranscurridoInterceptor implements HandlerInterceptor {

	/**
	 * En el handler tenemos toda la información del método que se está invocando
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// Si retormanos true continua la ejecución, si es false termina el proceso
		return true;
	}

	/**
	 * En el modelAndView podemos pasar datos a la vista
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

}
