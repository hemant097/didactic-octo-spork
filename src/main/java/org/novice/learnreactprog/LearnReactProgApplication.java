package org.novice.learnreactprog;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class LearnReactProgApplication implements CommandLineRunner {

    private final LearnFlux learnFlux;

    public static void main(String[] args) {
        SpringApplication.run(LearnReactProgApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        learnFlux.learnReactor();
    }
}
