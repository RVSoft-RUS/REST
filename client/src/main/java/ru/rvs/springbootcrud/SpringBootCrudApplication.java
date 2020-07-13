package ru.rvs.springbootcrud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class SpringBootCrudApplication {
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
		return restTemplateBuilder
				.basicAuthentication("serverAdmin", "secretPassword")
				.build();
	}
//	public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
//		return restTemplateBuilder
//				.basicAuthentication("admin", "$2a$10$ZUglmRM8nLQFnINc.w4ZqO5hC4rBuIeIhglth1o2Zl9fPrzvyAI8W")
//				.build();
//	}

	public static void main(String[] args) {
		SpringApplication.run(SpringBootCrudApplication.class, args);
	}

}
