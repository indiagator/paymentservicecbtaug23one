package com.cbt.paymentservicecbtaug23one;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/v1")
public class MainRestController
{

    Logger logger = LoggerFactory.getLogger(MainRestController.class);

    OrderRepository orderRepository;
    PaymentRepository paymentRepository;

    OrderportlinkRepository orderportlinkRepository;

    OfferportlinkRepository offerportlinkRepository;

    LogisticrfqRepository logisticrfqRepository;

    private WebClient.Builder webClientBuilder;

    MainRestController(
            OrderRepository orderRepository,
            PaymentRepository paymentRepository,
            OrderportlinkRepository orderportlinkRepository,
            OfferportlinkRepository offerportlinkRepository,
            LogisticrfqRepository logisticrfqRepository,
            WebClient.Builder webClientBuilder
    )
    {
        this.orderRepository = orderRepository;
        this.paymentRepository = paymentRepository;
        this.orderportlinkRepository = orderportlinkRepository;
        this.offerportlinkRepository = offerportlinkRepository;
        this.logisticrfqRepository = logisticrfqRepository;
        this.webClientBuilder = webClientBuilder;
    }

    @PostMapping("save/payment")
    public ResponseEntity<Payment> createPayment(@RequestBody Orderstatus orderstatus)
    {

        logger.info("Creating a new Payment with status DUE");

        Payment payment = new Payment();
        payment.setId(String.valueOf((int)(Math.random()*100000)));
        payment.setOrderid(orderstatus.getOrderid());
        payment.setOfferid(orderRepository.findById(orderstatus.getOrderid()).get().getOfferid());
        payment.setStatus("DUE");
        paymentRepository.save(payment);

        //PAYMENT_ORDER_WALLETLINKS HAVE TO BE CREATED

        return new ResponseEntity<>(payment, HttpStatus.OK);



    }

    @PostMapping("make/payment/{paymentid}")
    public ResponseEntity<Paymentxn> makePayment(@PathVariable String paymentid,
                                                 @RequestParam String type, @RequestParam String status)
    {
        if(type.equals("ORDER"))
        {
            if(status.equals("ESCROW"))
            {
                Payment payment = paymentRepository.findById(paymentid).get();

                paymentRepository.updateStatusById(status,paymentid);

                logger.info("Logistic RFQ creation request forwarded to Logistic-Service");

                Mono<Logisticrfq> rfqResponse = webClientBuilder.build().post().
                        uri("http://localhost:8072/logistic-service/api/v1/save/logistic/rfq").
                        body(Mono.just(payment),Payment.class).retrieve().bodyToMono(Logisticrfq.class);

                Paymentxn paymentxn = new Paymentxn();
                paymentxn.setTxnid(String.valueOf((int)(Math.random()*100000)));
                paymentxn.setPaymenttype("ORDER");
                paymentxn.setPymntrefid(payment.getOrderid());
                paymentxn.setPayerwallet(orderRepository.findById(payment.getOrderid()).get().getBuyername());

                //Logisticrfq logisticrfq = new Logisticrfq();
                //logisticrfq.setRfqid(String.valueOf((int)(Math.random()*100000)));
                //logisticrfq.setOrderid(payment.getOrderid());
                //logisticrfq.setOriginport(offerportlinkRepository.findByOfferid(payment.getOfferid()).get().getPortid());
                //logisticrfq.setDestinationport(orderportlinkRepository.findByOrderid(payment.getOfferid()).get().getPortid());
                //logisticrfqRepository.save(logisticrfq);



                return new ResponseEntity<>();

            }


        }
        else if(type.equals("LOGISTIC"))
        {


        }
        else
        {

            throw new RuntimeException();
        }

        return new ResponseEntity<>(new Paymentxn(),HttpStatus.OK);
    }


}
