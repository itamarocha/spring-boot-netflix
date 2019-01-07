package com.invillia.acme.orderservice.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.invillia.acme.orderservice.util.MyThreadLocalsHolder;
import com.invillia.acme.orderservice.web.models.PaymentResponse;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PaymentServiceClient {
	
	
	@Value("${url.api.payment}")
	private String url;
//	@Autowired
//    public PaymentServiceClient(RestTemplate restTemplate){
//    	this.restTemplate = restTemplate;
//    }
	@Bean
	   public RestTemplate getRestTemplate() {
	      return new RestTemplate();
	   }
    @HystrixCommand(fallbackMethod = "getPaymentByOrder")
    public Optional<PaymentResponse> getPaymentByOrder(String order)
    {
        log.info("CorrelationID: "+ MyThreadLocalsHolder.getCorrelationId());
        ResponseEntity<PaymentResponse> itemResponseEntity =
        		getRestTemplate().getForEntity(url + "/{code}",
                		PaymentResponse.class,
                        order);
  
        if (itemResponseEntity.getStatusCode() == HttpStatus.OK) {
            log.info("Payment status: " + itemResponseEntity.getBody().getStatus());
            return Optional.ofNullable(itemResponseEntity.getBody());
        } else {
            log.error("Unable to get payment level for order: " + order + ", StatusCode: " + itemResponseEntity.getStatusCode());
            return Optional.empty();
        }
    }

}
