package addressdataservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class AddressDataServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AddressDataServiceApplication.class, args);
	}
}
