package com.bolsaideas.springboot.form.app.interceptors;

import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component("tiempoTranscurridoInterceptor")
public class TiempoTranscurridoInterceptor implements HandlerInterceptor {

	private static final Logger logger = LoggerFactory.getLogger(TiempoTranscurridoInterceptor.class);

	/**
	 * En el handler tenemos toda la información del método que se está invocando
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		/**
		 * Uso del getMethod para comprobar el tipo de petición
		 */
//		if (request.getMethod().equalsIgnoreCase("get")) {
//			return true;
//		}
		/**
		 * Preguntamos si es una petición del controlador
		 */
		if (handler instanceof HandlerMethod) {
			HandlerMethod metodo = (HandlerMethod) handler;
			logger.info("Es un método del controlador: " + metodo.getMethod().getName());
		}
		logger.info("TiempoTranscurridoInterceptor: preHandler() entrando...");
		logger.info("Interceptando: " + handler);
		long tiempoInicio = System.currentTimeMillis();
		request.setAttribute("tiempoInicio", tiempoInicio);
		Random random = new Random();
		Integer delay = random.nextInt(500);
		// paramos el hilo entre 0 y 499 ms random, simulamos una carga
		Thread.sleep(delay);
		// Si retormanos true continua la ejecución, si es false termina el proceso
		return true;
	}

	/**
	 * En el modelAndView podemos pasar datos a la vista
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		if (request.getAttribute("tiempoInicio") == null) {
			return;
		}
		long tiempoFin = System.currentTimeMillis();
		long tiempoInicio = 0;

		tiempoInicio = (Long) request.getAttribute("tiempoInicio");

		long tiempoTranscurrido = tiempoFin - tiempoInicio;
		/**
		 * Todos los recursos que tenemos lanzan peticiones http (bootstrap, thymeleaf
		 * etc), eso no va a llevar modelAndView, por lo que si no ponemos la condición,
		 * falla. La primera condición es extra, con el != null sobra
		 */
		if (handler instanceof HandlerMethod && modelAndView != null) {
			modelAndView.addObject("tiempoTranscurrido", tiempoTranscurrido);
		}
		logger.info("tiempoTranscurrido: " + tiempoTranscurrido + " milisegundos");
		logger.info("TiempoTranscurridoInterceptor: postHandle() saliendo...");
	}

}
