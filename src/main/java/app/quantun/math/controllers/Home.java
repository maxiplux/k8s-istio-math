package app.quantun.math.controllers;

import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@RequestMapping("/api/v1/math")
@Slf4j
public class Home {

    private Counter hitsCounterToMultiplication;
    private Counter hitsCounterToDivicion;


    public Home(MeterRegistry registry) {
        hitsCounterToMultiplication = Counter.builder("hitsCounterToMultiplication")
                .description("Number of hits  to the multiplication")
                .register(registry);
        hitsCounterToDivicion = Counter.builder("hitsCounterTodivision")
                .description("Number of hits  to  division")
                .register(registry);
    }


    @RequestMapping(value = "/multiplication", method = RequestMethod.POST)
    @Timed(value = "multiplication", longTask = true)
    public Double multiplication(@RequestParam(required = true, name = "a") Double a, @RequestParam(required = true, name = "b") Double b) {
        this.sleep();
        if (a == null || b == null) {
            log.error("Invalid input");
            return 0.0;
        }

        return a * b;
    }

    //division and multiplication
    @RequestMapping(value = "/division", method = RequestMethod.POST)
    @Timed(value = "division", longTask = true)
    public ResponseEntity<?> division(@RequestParam("a") Double a, @RequestParam("b") Double b) {
        this.sleep();

        if (a == null || b == null) {
            log.error("Invalid input");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input A or B is null");
        }
        if (b == 0) {
            log.error("Invalid input");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input B");
        }

        return ResponseEntity.ok(a / b);
    }

    public void sleep() {
        Random random = new Random();


        int sleepTime = 1 + random.nextInt(7);

        log.info("Sleeping for " + sleepTime + " seconds...");

        try {
            // Convert seconds to milliseconds and sleep
            Thread.sleep(sleepTime * 1000L);
        } catch (InterruptedException e) {
            log.error("Sleep was interrupted");
        }

        log.info("Woke up!");
    }

}
