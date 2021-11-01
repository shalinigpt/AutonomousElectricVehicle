package com.example.vehicle;

import com.example.vehicle.external.VehicleConsumerService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import org.springframework.web.client.RestTemplate;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class ElectricVehicleApplication {



	public static void main(String[] args) {
		SpringApplication.run(ElectricVehicleApplication.class, args);
	}

	@Bean
    public Docket productApi() {
	   return new Docket(DocumentationType.SWAGGER_2).select()
	  	 .apis(RequestHandlerSelectors.basePackage("com.example.vehicle")).build();
    }

	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

	@Bean
	public VehicleConsumerService getVehicleConsumerService() {
		return new VehicleConsumerService();
	}

}
