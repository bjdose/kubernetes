package org.bjsalcedo.springcloud.svc.users;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SvcUsersApplication {

	public static void main(String[] args) {
		SpringApplication.run(SvcUsersApplication.class, args);
	}

}
