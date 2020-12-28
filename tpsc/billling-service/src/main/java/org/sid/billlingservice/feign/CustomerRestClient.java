package org.sid.billlingservice.feign;

import org.sid.billlingservice.model.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="CUSTOMER-SERVICE")

public interface CustomerRestClient{


    @GetMapping("/customers/{id}?projection=fullCustomer")
     Customer findCustomerById(@PathVariable("id") Long id);

}
















/*@FeignClient(name="customer-service")
interface CustomerRestClient{
    @GetMapping(path = "/customers/{id}")
    Customer getCustomerById(@PathVariable("id") Long id);
}*/
/*@FeignClient(name="inventory-service")
interface InventoryServiceClient{
    @GetMapping("/products/{id}?projection=fullProduct")
    Product findProductById(@PathVariable("id") Long id);
    @GetMapping("/products?projection=fullProduct")
    PagedModel<Product> findAll();
}*/
