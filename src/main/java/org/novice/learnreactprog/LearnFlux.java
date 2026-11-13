package org.novice.learnreactprog;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.springframework.stereotype.Component;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.SignalType;

import java.time.Duration;

@Component
@Slf4j
public class LearnFlux {

    public void learnReactor(){
        //a publisher
        Flux<String> fruits = Flux.just("apple","lichi","banana")
                .map(f -> {
                    if (f.equals("banana")) throw new RuntimeException("hate banana");
                    return f;
                });

        Flux<String> ticker = Flux.interval(Duration.ofSeconds(1))
                .map(tick -> "tick: "+tick);

//Implementing the Subscriber ourselves

//        fruits
//                .subscribe(new Subscriber<>() {
//                    Subscription subscription;
//                    @Override
//                    public void onSubscribe(Subscription s) {
//                        subscription =s;
//                        log.info("learnReactor onSubscribe");
//                        s.request(1);
//                    }
//
//                    @Override
//                    public void onNext(String s) {
//                        log.info("Fruit: {}",s);
//                        subscription.request(1);
//                    }
//
//                    @Override
//                    public void onError(Throwable t) {
//                        log.error("onError {}", t.getMessage());
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        log.info("learnReactor oncomplete");
//                    }
//                });

        //for full control, we can use BaseSubscriber

//        fruits.
//                subscribe(new BaseSubscriber<>() {
//            @Override
//            protected void hookOnSubscribe(Subscription subscription) {
//                log.info("hookOnSubscribe");
//                request(1);
//            }
//
//            @Override
//            protected void hookOnNext(String value) {
//                log.info("Processing, {}",value);
//                if (value.equals("lichi")) cancel();
//                request(1);
//            }
//
//            @Override
//            protected void hookOnComplete() {
//                log.info("hookOnComplete");
//            }
//
//            @Override
//            protected void hookOnError(Throwable throwable) {
//                log.info("hookOnError {}",throwable.getMessage(),throwable);
//            }
//
//            @Override
//            protected void hookOnCancel() {
//                log.info("cancelled, hookOnCancel");
//            }
//
//            @Override
//            protected void hookFinally(SignalType type) {
//                log.info("Finally hookOnFinally");
//            }
//        });

        //another way of subscribing (can be used sometimes), here we don't call next manually
//        ticker.subscribe(
//                item -> {log.info("Processing: {}",item);}, //consumer
//                err ->{log.error("onError ",err);}, //errorConsumer
//                ()->{log.info("onComplete");} // completeConsumer
//        );

        //using operators
        fruits
                .doOnSubscribe((subscription)->log.info("onSubscriptionHook"))
                .doOnNext(item-> log.info("doing nothing"))
                .doFinally(signalType -> log.info("doFinally; {}",signalType))
                .subscribe(item->log.info("Processing: {}",item),
                        err ->log.error("onError: {} ",err.getMessage(),err),
                        ()-> log.info("flux completed")
                        );

    }


}
