package id.co.bca.spring.client.controller;

import id.co.bca.spring.client.model.CardModel;
import id.co.bca.spring.client.model.CustomerModel;
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
public class CustCardController {
    private final WebClient webClient;
    public CustCardController(){
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
                .baseUrl("http://localhost:1111")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }

    @GetMapping(value = "/cust")
    public Mono<ResponseEntity<Flux<CustomerModel>>> allCust(){
        return webClient.get()
                .uri("/api/cust")
                .retrieve()
                .toEntityFlux(CustomerModel.class)
                ;
    }

    @GetMapping(value = "/cust/{id}")
    public Mono<ResponseEntity<CustomerModel>> getCust(@PathVariable("id") int id){
        return webClient.get()
                .uri("/api/cust/{id}", id)
                .retrieve()
                .toEntity(CustomerModel.class)
                ;
    }

    @PostMapping(value = "/cust")
    public Mono<ResponseEntity<CustomerModel>> addCust(@RequestBody CustomerModel customerModel){

        return webClient
                .post()
                .uri("/api/cust")
                .bodyValue(customerModel)
                .retrieve()
                .toEntity(CustomerModel.class)
                ;

    }

    @PutMapping(value = "/cust/{id}")
    public Mono<ResponseEntity<CustomerModel>> updateCust(@RequestBody CustomerModel customerModel, @PathVariable int id){

        return webClient
                .put()
                .uri("/api/cust/{id}", id)
                .bodyValue(customerModel)
                .retrieve()
                .toEntity(CustomerModel.class)
                ;
    }

    @DeleteMapping(value = "/cust/{id}")
    public Mono<ResponseEntity<Void>> deleteCust(@PathVariable("id") int id){
        return webClient
                .delete()
                .uri("/api/cust/{id}", id)
                .retrieve()
                .toEntity(Void.class)
                ;
    }

    @GetMapping(value = "/cust/{page}/{size}")
    public Mono<ResponseEntity<Flux<CustomerModel>>> findAllPage(@PathVariable("page") int page,
                                                                 @PathVariable("size") int size){
        return webClient.get()
                .uri("/api/cust/{page}/{size}", page, size)
                .retrieve()
                .toEntityFlux(CustomerModel.class)
                ;
    }

    @GetMapping(value = "/card")
    public Mono<ResponseEntity<Flux<CardModel>>> allCard(){
        return webClient.get()
                .uri("/api/card")
                .retrieve()
                .toEntityFlux(CardModel.class)
                ;
    }
    @GetMapping(value = "/card/cust/{id}")
    public Mono<ResponseEntity<Flux<CardModel>>> getCardCust(@PathVariable("id") int id){
        return webClient.get()
                .uri("/api/card/cust/{id}", id)
                .retrieve()
                .toEntityFlux(CardModel.class)
                ;
    }

    @GetMapping(value = "/card/{id}")
    public Mono<ResponseEntity<CardModel>> getCard(@PathVariable("id") int id){
        return webClient.get()
                .uri("/api/card/{id}", id)
                .retrieve()
                .toEntity(CardModel.class)
                ;
    }

    @PostMapping(value = "/card")
    public Mono<ResponseEntity<CardModel>> addCard(@RequestBody CardModel cardModel){

        return webClient
                .post()
                .uri("/api/card")
                .bodyValue(cardModel)
                .retrieve()
                .toEntity(CardModel.class)
                ;

    }

    @PutMapping(value = "/card/{id}")
    public Mono<ResponseEntity<CardModel>> updateCard(@RequestBody CardModel cardModel, @PathVariable int id){

        return webClient
                .put()
                .uri("/api/card/{id}", id)
                .bodyValue(cardModel)
                .retrieve()
                .toEntity(CardModel.class)
                ;
    }

    @DeleteMapping(value = "/card/{id}")
    public Mono<ResponseEntity<Void>> deleteCard(@PathVariable("id") int id){
        return webClient
                .delete()
                .uri("/api/card/{id}", id)
                .retrieve()
                .toEntity(Void.class)
                ;
    }

    @GetMapping(value = "/card/{page}/{size}")
    public Mono<ResponseEntity<Flux<CardModel>>> findAllCardPage(@PathVariable("page") int page,
                                                                 @PathVariable("size") int size){
        return webClient.get()
                .uri("/api/card/{page}/{size}", page, size)
                .retrieve()
                .toEntityFlux(CardModel.class)
                ;
    }
}
