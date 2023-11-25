package org.bjsalcedo.springcloud.svc.courses;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SvcCoursesApplication {

	public static void main(String[] args) {
		SpringApplication.run(SvcCoursesApplication.class, args);
	}

}
