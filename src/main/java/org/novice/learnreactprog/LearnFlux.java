package org.novice.learnreactprog;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
@Slf4j
public class LearnFlux {

    public void learnReactor(){
        Flux<String> fruits = Flux.just("apple","lichi","banana")
                .map(f -> {
                    if (f.equals("lichi")) throw new RuntimeException("hate lichi");
                    return f;
                });

        fruits
                .subscribe(new Subscriber<>() {
                    Subscription subscription;
                    @Override
                    public void onSubscribe(Subscription s) {
                        subscription =s;
                        log.info("learnReactor onSubscribe");
                        s.request(1);
                    }

                    @Override
                    public void onNext(String s) {
                        log.info("Fruit: {}",s);
                        subscription.request(1);
                    }

                    @Override
                    public void onError(Throwable t) {
                        log.error("onError {}", t.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        log.info("learnReactor oncomplete");
                    }
                });
    }
}
