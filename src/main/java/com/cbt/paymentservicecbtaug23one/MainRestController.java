package com.cbt.paymentservicecbtaug23one;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.CoreSubscriber;
import reactor.core.publisher.Mono;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Time;
import java.time.Instant;
import java.util.Date;


@RestController
@RequestMapping("api/v1")
public class MainRestController
{
    Logger logger = LoggerFactory.getLogger(MainRestController.class);

    OrderRepository orderRepository;

    ProductofferRepository productofferRepository;
    PaymentRepository paymentRepository;

    OrderportlinkRepository orderportlinkRepository;

    OfferportlinkRepository offerportlinkRepository;

    UsernamewalletlinkRepository usernamewalletlinkRepository;

    PaymentwalletlinkRepository paymentwalletlinkRepository;

    WalletRepository walletRepository;

    LogisticrfqRepository logisticrfqRepository;

    private WebClient.Builder webClientBuilder;

    PaymentxnRepository paymentxnRepository;

    MainRestController(
            OrderRepository orderRepository,
            PaymentRepository paymentRepository,
            OrderportlinkRepository orderportlinkRepository,
            OfferportlinkRepository offerportlinkRepository,
            LogisticrfqRepository logisticrfqRepository,
            WebClient.Builder webClientBuilder,
            ProductofferRepository productofferRepository,
            UsernamewalletlinkRepository usernamewalletlinkRepository,
            PaymentwalletlinkRepository paymentwalletlinkRepository,
            WalletRepository walletRepository,
            PaymentxnRepository paymentxnRepository
    )
    {
        this.orderRepository = orderRepository;
        this.paymentRepository = paymentRepository;
        this.orderportlinkRepository = orderportlinkRepository;
        this.offerportlinkRepository = offerportlinkRepository;
        this.logisticrfqRepository = logisticrfqRepository;
        this.webClientBuilder = webClientBuilder;
        this.productofferRepository = productofferRepository;
        this.usernamewalletlinkRepository = usernamewalletlinkRepository;
        this.paymentwalletlinkRepository = paymentwalletlinkRepository;
        this.walletRepository = walletRepository;
        this.paymentxnRepository = paymentxnRepository;
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
        payment.setPaymentwalletlink( this.createPaymentWalletLink(orderstatus.getOrderid()));
        paymentRepository.save(payment);       ;
        return new ResponseEntity<>(payment, HttpStatus.OK);

    }


    public String createPaymentWalletLink(String orderid)
    {
        Order order =  orderRepository.findById(orderid).get();
        Usernamewalletlink buyerWalletLink =  usernamewalletlinkRepository.findById(orderRepository.findById(orderid).get().getBuyername()).get();
        Usernamewalletlink sellerWalletLink =  usernamewalletlinkRepository.
                findById(  productofferRepository.findById(orderRepository.findById(orderid).get().getOfferid()).get().getSellername() ).get();
        //ADMIN / KEY ACCOUNT MANAGER SELECTION LOGIC HAS TO BE IMPLEMENTED
        Usernamewalletlink escrowWalletLink = usernamewalletlinkRepository.findById("indiagator").get();
        Paymentwalletlink paymentwalletlink = new Paymentwalletlink();
        paymentwalletlink.setLinkid(String.valueOf((int)(Math.random()*100000)));
        paymentwalletlink.setAmount(order.getBid());
        paymentwalletlink.setPayerwallet(buyerWalletLink.getWalletid());
        paymentwalletlink.setPayeewallet(sellerWalletLink.getWalletid());
        paymentwalletlink.setEscrowwallet(escrowWalletLink.getWalletid());
        paymentwalletlink.setPaymentrefid(orderid);
        paymentwalletlink.setPaymenttype("ORDER");
        paymentwalletlinkRepository.save(paymentwalletlink);

        return paymentwalletlink.getLinkid();
    }

    @PostMapping("make/payment/{paymentid}")
    public ResponseEntity<Mono<Logisticrfq>> makePayment(@PathVariable String paymentid,
                                                 @RequestParam String type, @RequestParam String status)
    {
        if(type.equals("ORDER"))
        {
            if(status.equals("ESCROW"))
            {
                Payment payment = paymentRepository.findById(paymentid).get();
                paymentRepository.updateStatusById(status,paymentid);

                Paymentwalletlink paymentwalletlink =  paymentwalletlinkRepository.findById(payment.getPaymentwalletlink()).get();

                //DEBIT-CREDIT
                Integer initBalance = walletRepository.findById(paymentwalletlink.getPayerwallet()).get().getBalance();
                Integer finBalance = initBalance - paymentwalletlink.getAmount();

                Integer initBalanceEscrow = walletRepository.findById(paymentwalletlink.getEscrowwallet()).get().getBalance();
                Integer finBalanceEscrow = initBalanceEscrow + paymentwalletlink.getAmount();

                walletRepository.updateBalanceByWalletid(finBalance, paymentwalletlink.getPayerwallet()); //DEBIT
                walletRepository.updateBalanceByWalletid(finBalanceEscrow, paymentwalletlink.getEscrowwallet()); //CREDIT
                //TO BE IMPLEMENTED IN A SEPERATE METHOD LATER

                Paymentxn paymentxn = new Paymentxn();
                paymentxn.setTxnid(String.valueOf((int)(Math.random()*100000)));
                paymentxn.setPaymenttype("ORDER");
                paymentxn.setPymntrefid(payment.getOrderid());
                paymentxn.setPayerwallet(paymentwalletlink.getPayerwallet());
                paymentxn.setPayeewallet(paymentwalletlink.getPayeewallet());
                paymentxn.setAmount(paymentwalletlink.getAmount());
                paymentxn.setTime(Instant.now());

                paymentxnRepository.save(paymentxn);
                //Logisticrfq logisticrfq = new Logisticrfq();
                //logisticrfq.setRfqid(String.valueOf((int)(Math.random()*100000)));
                //logisticrfq.setOrderid(payment.getOrderid());
                //logisticrfq.setOriginport(offerportlinkRepository.findByOfferid(payment.getOfferid()).get().getPortid());
                //logisticrfq.setDestinationport(orderportlinkRepository.findByOrderid(payment.getOfferid()).get().getPortid());
                //logisticrfqRepository.save(logisticrfq);

                logger.info("sending rfq creation request to Logistics-Service");

                Mono<Logisticrfq> rfqResponse = webClientBuilder.build().post().
                        uri("http://localhost:8072/logistic-service/api/v1/save/logistic/rfq").
                        body(Mono.just(payment),Payment.class).retrieve().bodyToMono(Logisticrfq.class);

                return new ResponseEntity<>( rfqResponse, HttpStatus.OK);

            }
        }
        else if(type.equals("LOGISTIC"))
        {

        }
        else
        {
            throw new RuntimeException();
        }

        return new ResponseEntity<>(new Mono<Logisticrfq>() {
            @Override
            public void subscribe(CoreSubscriber<? super Logisticrfq> coreSubscriber) {

            }
        }, HttpStatus.OK);
    }


}
