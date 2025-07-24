package training.iqgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaServerCpsApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekaServerCpsApplication.class, args);
	}

}
