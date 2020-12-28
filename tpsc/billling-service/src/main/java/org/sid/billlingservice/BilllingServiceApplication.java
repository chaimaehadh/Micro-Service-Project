package org.sid.billlingservice;

import org.sid.billlingservice.Repository.BillRepository;
import org.sid.billlingservice.Repository.ProductItemRepository;
import org.sid.billlingservice.entities.Bill;
import org.sid.billlingservice.entities.ProductItem;
import org.sid.billlingservice.feign.CustomerRestClient;
import org.sid.billlingservice.feign.ProductItemRestClient;
import org.sid.billlingservice.model.Customer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@EnableFeignClients
@SpringBootApplication
public class BilllingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BilllingServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner start(BillRepository billRepository, ProductItemRepository productItemRepository,
                            ProductItemRestClient productItemRestClient, CustomerRestClient customerRestClient){
        return args -> {
            Bill bill=new Bill();
            bill.setBillingDate(new Date());
            Customer customer=customerRestClient.findCustomerById(1L);
            bill.setCustomerID(customer.getId());
            billRepository.save(bill);
            productItemRestClient.findAll().getContent().forEach(p->{
                productItemRepository.save(new ProductItem(null,null,p.getId(),p.getPrice(),(int)(1+Math.random()*1000),bill));
            });
        };
    }}
