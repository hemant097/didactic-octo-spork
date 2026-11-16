package org.novice.learnreactprog;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class LearnError {

    //try catch doesn't help in this case, as the error occurs later in the pipeline, thus we need different mechanisms
    public void errorHandlingWithTryCatch(){
        try{
            Mono<String> fromCallable = Mono.fromCallable(  ()->{
                Thread.sleep(4000);
                throw new RuntimeException("Error occurred");
            });

            fromCallable.subscribe(
                    data -> log.info("Data is :{}",data)
            );
        }catch (Exception e){
            log.error("caught: ",e);
        }
    }

    public void learnReactiveErrorHandling(){
        Mono<String> fromCallable = Mono.fromCallable(  ()->{
            Thread.sleep(4000);
            throw new RuntimeException("Error occurred in Reactive programming");
        });

        fromCallable
                .doOnError(err -> log.error("Error logged: {}", err.getMessage(),err)) //can be used for side effects like logging
//                .onErrorComplete() //ignores the error, and moves on
//                .onErrorReturn("fallback value is being returned") //returns a default value, when error occurs and complete the stream
//                .onErrorResume(err -> Mono.just("on error resume data")) // fallback to another stream (alternative Flux/Mono)
                .onErrorMap((err)-> new IllegalArgumentException("Illegal argument")) //transforming exception
                .subscribe(data->log.info("data: {}",data))
                ;

        log.info("after 4 sec log line");
    }
}
