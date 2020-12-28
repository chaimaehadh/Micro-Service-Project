package org.sid.billlingservice.web;

import org.sid.billlingservice.Repository.BillRepository;
import org.sid.billlingservice.Repository.ProductItemRepository;
import org.sid.billlingservice.entities.Bill;
import org.sid.billlingservice.feign.CustomerRestClient;
import org.sid.billlingservice.feign.ProductItemRestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
class BillRestController{
    @Autowired
    private BillRepository billRepository;
    @Autowired private ProductItemRepository productItemRepository;
    @Autowired private CustomerRestClient customerRestClient;
    @Autowired private ProductItemRestClient productItemRestClient;
    @GetMapping("/bills/full/{id}")
    Bill getBill(@PathVariable(name="id") Long id){
        Bill bill=billRepository.findById(id).get();
        bill.setCustomer(customerRestClient.findCustomerById(bill.getCustomerID()));
        bill.setProductItems(productItemRepository.findByBillId(id));
        bill.getProductItems().forEach(pi->{
            pi.setProduct(productItemRestClient.findProductById(pi.getProductID()));
        });
        return bill; }
}
