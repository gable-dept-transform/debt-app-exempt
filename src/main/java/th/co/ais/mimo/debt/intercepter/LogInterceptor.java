package th.co.ais.mimo.debt.intercepter;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class LogInterceptor implements HandlerInterceptor {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	private String appName;

	public LogInterceptor(String appName) {
		this.appName = appName;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String traceIdName = "x-b3-traceid";
		String sampledName = "x-b3-sampled";
		String spanIdName = "x-b3-spanid";
		String parentspanIdName = "x-b3-parentspanid";
		
		if (request.getHeader(traceIdName) != null) {
			MDC.put(traceIdName, request.getHeader(traceIdName));
		}
		if (request.getHeader(sampledName) != null) {
			MDC.put(sampledName, request.getHeader(sampledName));
		}
		if (request.getHeader(spanIdName) != null) {
			MDC.put(spanIdName, request.getHeader(spanIdName));
		}

		if (request.getHeader(parentspanIdName) != null) {
			MDC.put(parentspanIdName, request.getHeader(parentspanIdName));
		}

		MDC.put("startTime", new Date().getTime() + "");
		MDC.put("application", appName);
		MDC.put("status", "start");
		MDC.put("uri", request.getRequestURI());
		MDC.put("method", request.getMethod());
		MDC.put("httpStatus", "");
		response.setHeader("traceid", MDC.get(traceIdName));
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

		String startTime = MDC.get("startTime");

		if (startTime != null) {
			long diff = new Date().getTime() - new Date(Long.parseLong(startTime)).getTime();
			log.info( "{} execute time in {} ms.",appName,diff);
			MDC.put("executeTime", diff + "");
			MDC.put("status", "done");
			MDC.put("uri", request.getRequestURI());
			MDC.put("method", request.getMethod());
			MDC.put("httpStatus", response.getStatus() + "");
		}

		log.info("completed.");

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		        log.info("afterCompletion");
	}
}
