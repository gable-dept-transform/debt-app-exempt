package th.co.ais.mimo.debt.exempt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
/*@EnableFeignClients*/
/*@EnableScheduling*/
public class AppExemptServiceApplication {

   
	/*@Bean
	public io.opentracing.Tracer initTracer() {
		Configuration.SamplerConfiguration samplerConfig = new Configuration.SamplerConfiguration().withType("const")
				.withParam(1);
		Configuration.ReporterConfiguration reporterConfig = Configuration.ReporterConfiguration.fromEnv()
				.withLogSpans(true);
		return Configuration.fromEnv("debt-app-nego").withSampler(samplerConfig).withReporter(reporterConfig)
				.getTracerBuilder().withScopeManager(new MDCScopeManager()).build();
	}*/

	/*@Bean
	public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
		return restTemplateBuilder.build();
	}
	*/

	public static void main(String[] args) {
		SpringApplication.run(AppExemptServiceApplication.class, args);
	}
	
}