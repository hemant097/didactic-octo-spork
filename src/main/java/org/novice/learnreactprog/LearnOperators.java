package org.novice.learnreactprog;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Comparator;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class LearnOperators {
    public void learnMaps(){

        //creation operators
        Flux<Integer> rangeFlux = Flux.range(4,7); //starts from 4, counts 7 times (thus till 10)
        Flux<String> fruitsFlux = Flux.just("apple","banana","pineapple","orange","cherry","date","mango","kiwi");
        Flux<Long> ticksFlux = Flux.interval(Duration.of(1, ChronoUnit.SECONDS));

//        ticksFlux.subscribe((count)-> log.info("Tick: {}",count));

        Mono<String> fromCallable = Mono.fromCallable(()->slowTask());
//        fromCallable.subscribe(item-> log.info("PRocessing item: {}",item));

        fruitsFlux
                .map(String::toUpperCase)   //returns a flux<String>
                .map(upperCaseFruit -> upperCaseFruit.substring(0,2)) //same as above
//                .map(twoChars -> Flux.just(twoChars+"@", twoChars+"#")) //returns a Flux<Flux<String>>
                .flatMap(twoChars -> Flux.just(twoChars+"@", twoChars+"#")) //flattens that into a single flux stream
                .subscribe(data->log.info("Fruit: {}",data))
                ;



    }

    public void learnAggregationOps(){
        Flux<String> fruitsFlux = Flux.just("apple","banana","pineapple","orange","avocado","date","mango","kiwi","papaya");
        Flux<String> moreFruits = Flux.just("dragonfruit","guava","grapes","watermelon");


        fruitsFlux
//                .filter(f -> f.length()>4)
                .skip(2) //skip n items
//                .take(3) // take n items
//                .count() //returns count when onComplete
                .sort(Comparator.reverseOrder())
                .subscribe(data->log.info("FruitItem: {}",data))
                ;

        fruitsFlux
                .groupBy(fruit -> fruit.charAt(0))
                .flatMap(group -> group.collectList()
                        .map(list-> group.key() + " -> " + list))
                .subscribe(System.out::println);
//        OUTPUT:
//        p -> [pineapple, papaya]
//        a -> [apple, avocado]
//        b -> [banana]

//        Flux.concat(fruitsFlux,moreFruits); OR fruitsFlux.concatWith(moreFruits); Guarantees order by waiting for first stream to complete

//        Flux.merge(fruitsFlux,moreFruits); OR fruitsFlux.mergeWith(moreFruits); does NOT guarantee order

        Flux<String> carsFlux = Flux.just("civic","hector","swift","creta","nexon","seltos","sunny","duster","seal");
        Flux<String> companiesFlux = Flux.just("honda","mg","maruti","hyundai","tata","kia","nissan","renault","BYD");

        //Zip
        //Example1
//        Flux.zip(carsFlux,companiesFlux, (car,company) -> car+ " is "+ company+ "'s star product")
//                .subscribe(System.out::println);
//        OUTPUT:
//        civic is honda's star product

        //Example2
//        carsFlux.zipWith(companiesFlux,(car,company)-> company+ " sells "+car)
//                .subscribe(System.out::println);
//        OUTPUT:
//        honda sells civic
//        mg sells hector

        carsFlux.zipWith(companiesFlux,(car,company)-> company+ " sells "+car)
                .collectList()          //returns a Mono<List<T>>
                .subscribe(System.out::println);
    }



    //emulating a slow task which takes 3000 ms
    private String slowTask(){
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return "some data";
    }
}
