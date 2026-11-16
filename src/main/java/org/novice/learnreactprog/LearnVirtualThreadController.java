package org.novice.learnreactprog;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/benchmark")
@Slf4j
public class LearnVirtualThreadController {
    @GetMapping("/users/{id}")
    public String getFullUser(@PathVariable Long id){
        try{
            Thread.sleep(1000);
        }catch (InterruptedException ie){
            throw new RuntimeException(ie);
        }
        log.info("method called: GET, with thread name: {}",Thread.currentThread().getName());
        return "full user: "+id;
    }
}
