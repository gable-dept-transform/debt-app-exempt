package th.co.ais.mimo.debt.intercepter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import io.opentracing.Tracer;

@Component
public class TracingInterceptor implements RequestInterceptor {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	Tracer tracer;

	@Override
	public void apply(RequestTemplate requestTemplate) {

		requestTemplate.header("x-b3-traceid", MDC.get("x-b3-traceid"));
		requestTemplate.header("x-b3-sampled", MDC.get("x-b3-sampled"));
		requestTemplate.header("x-b3-spanid", MDC.get("x-b3-spanid"));
		requestTemplate.header("x-b3-parentspanid", MDC.get("x-b3-parentspanid"));

		log.info("TracingInterceptor apply : ");
	}
}
