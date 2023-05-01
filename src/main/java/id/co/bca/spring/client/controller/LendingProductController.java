package id.co.bca.spring.client.controller;

import id.co.bca.spring.client.model.HolidayProductModel;
import id.co.bca.spring.client.model.LendingProductModel;
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
@RequestMapping("client/lending")
public class LendingProductController {
    private final WebClient webClient;
    public LendingProductController(){
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
                .baseUrl("http://localhost:8088")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }

    @GetMapping(value = "/product")
    public Mono<ResponseEntity<Flux<LendingProductModel>>> allPayment(){
        return webClient.get()
                .uri("/api/lending/product")
                .retrieve()
                .toEntityFlux(LendingProductModel.class)
                ;
    }

    @GetMapping(value = "/product/{id}")
    public Mono<ResponseEntity<LendingProductModel>> getPayment(@PathVariable("id") int id){
        return webClient.get()
                .uri("/api/lending/product/{id}", id)
                .retrieve()
                .toEntity(LendingProductModel.class)
                ;
    }

    @PostMapping(value = "/product")
    public Mono<ResponseEntity<LendingProductModel>> addPayment(@RequestBody LendingProductModel productModel){

        return webClient
                .post()
                .uri("/api/lending/product")
                .bodyValue(productModel)
                .retrieve()
                .toEntity(LendingProductModel.class)
                ;

    }

    @PutMapping(value = "/product/{id}")
    public Mono<ResponseEntity<LendingProductModel>> updatePayment(@RequestBody LendingProductModel productModel, @PathVariable int id){

        return webClient
                .put()
                .uri("/api/lending/product/{id}", id)
                .bodyValue(productModel)
                .retrieve()
                .toEntity(LendingProductModel.class)
                ;
    }

    @DeleteMapping(value = "/product/{id}")
    public Mono<ResponseEntity<Void>> deletePayment(@PathVariable("id") int id){
        return webClient
                .delete()
                .uri("/api/lending/product/{id}", id)
                .retrieve()
                .toEntity(Void.class)
                ;
    }
}
