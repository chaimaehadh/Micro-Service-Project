package org.sid.billlingservice.feign;

import org.sid.billlingservice.model.Customer;
import org.sid.billlingservice.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="PRODUCT-SERVICE")


public interface ProductItemRestClient{
    @GetMapping("/products/{id}?projection=fullProduct")
    Product findProductById(@PathVariable("id") Long id);
    @GetMapping("/products?projection=fullProduct")
     PagedModel<Product> findAll();

}









/*public interface ProductItemRestClient {
    @GetMapping("/products")
    PagedModel<Product> pageProducts(@RequestParam(name = "page") int page,
                                     @RequestParam(name = "size") int size);
    @GetMapping(path = "/products/{id}")
    Product getProductById(@PathVariable Long id);

}*/
