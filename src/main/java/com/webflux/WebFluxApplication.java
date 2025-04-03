package com.webflux;

import java.time.Duration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Slf4j
@SpringBootApplication
public class WebFluxApplication {

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(WebFluxApplication.class, args);
        Flux.interval(Duration.ofMillis(1L))
                .onBackpressureDrop()
                .doOnNext(data -> log.info("doOnNext: {}", data))
                .publishOn(Schedulers.parallel())
                .subscribe(data -> {
                            try {
                                Thread.sleep(5L);
                            } catch (InterruptedException e) {
                            }
                            log.info("onNext: {}", data);
                        },
                        error -> log.error("onError"));

        Thread.sleep(2000L);
    }

}
