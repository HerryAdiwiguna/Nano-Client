package id.co.bca.spring.client.controller;

import id.co.bca.spring.client.model.CustomerModel;
import id.co.bca.spring.client.model.PaymentModel;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("client")
public class PaymentController {
    private final WebClient webClient;
    public PaymentController(){
        // add timeout
        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .responseTimeout(Duration.ofMillis(5000))
                .doOnConnected(connection -> {
                    connection.addHandlerLast(new ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS))
                            .addHandlerLast(new WriteTimeoutHandler(5000, TimeUnit.MILLISECONDS));
                })
                ;
        // add webclient with timeout
        webClient = WebClient.builder()
                .baseUrl("http://localhost:3333")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }

    @GetMapping(value = "/pay")
    public Mono<ResponseEntity<Flux<PaymentModel>>> allPayment(){
        return webClient.get()
                .uri("/api/pay")
                .retrieve()
                .toEntityFlux(PaymentModel.class)
                ;
    }

    @GetMapping(value = "/pay/{id}")
    public Mono<ResponseEntity<PaymentModel>> getPayment(@PathVariable("id") int id){
        return webClient.get()
                .uri("/api/pay/{id}", id)
                .retrieve()
                .toEntity(PaymentModel.class)
                ;
    }

    @PostMapping(value = "/pay")
    public Mono<ResponseEntity<PaymentModel>> addPayment(@RequestBody PaymentModel paymentModel){

        return webClient
                .post()
                .uri("/api/pay")
                .bodyValue(paymentModel)
                .retrieve()
                .toEntity(PaymentModel.class)
                ;

    }

    @PutMapping(value = "/pay/{id}")
    public Mono<ResponseEntity<PaymentModel>> updatePayment(@RequestBody PaymentModel paymentModel, @PathVariable int id){

        return webClient
                .put()
                .uri("/api/pay/{id}", id)
                .bodyValue(paymentModel)
                .retrieve()
                .toEntity(PaymentModel.class)
                ;
    }

    @DeleteMapping(value = "/pay/{id}")
    public Mono<ResponseEntity<Void>> deletePayment(@PathVariable("id") int id){
        return webClient
                .delete()
                .uri("/api/pay/{id}", id)
                .retrieve()
                .toEntity(Void.class)
                ;
    }
}
