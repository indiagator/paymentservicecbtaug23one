package com.cbt.paymentservicecbtaug23one;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
public class MainRestController
{
    @Autowired
    PaymentRepository paymentRepository;
    @Autowired
    OrderRepository orderRepository;
    @PostMapping("save/payment")
    public ResponseEntity<Payment> createPayment(@RequestBody Orderstatus orderstatus)
    {
        Payment payment = new Payment();
        payment.setId(String.valueOf((int)(Math.random()*100000)));
        payment.setOrderid(orderstatus.getOrderid());
        payment.setOfferid(orderRepository.findById(orderstatus.getOrderid()).get().getOfferid());
        payment.setStatus("DUE");
        paymentRepository.save(payment);

        return new ResponseEntity<Payment>(payment, HttpStatus.OK);
    }

}
