package com.webflux;

import java.time.Duration;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class Backpressure {

    public static void main(String[] args) {
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
    }
}
