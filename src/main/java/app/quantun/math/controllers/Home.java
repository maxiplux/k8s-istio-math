package app.quantun.math.controllers;

import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@RequestMapping("/api/v1/math")
@Slf4j
public class Home {
    private Counter hitsCounterToSubtract;
    private Counter hitsCounterToadd;

    public void setRegistryHitsCounterToSubtract(MeterRegistry registry) {
        hitsCounterToSubtract = Counter.builder("hitsCounterToSubtract")
                .description("Number of hits  to the subtract")
                .register(registry);
    }

    public void setRegistryHitsCounterToAdd(MeterRegistry registry) {
        hitsCounterToadd = Counter.builder("hitsCounterToadd")
                .description("Number of hits  to  add")
                .register(registry);
    }

        @RequestMapping(value = "/add", method = RequestMethod.POST)
        @Timed(value = "add", longTask = true)
        public Integer add(@RequestParam( required = true,  name = "a") Integer a, @RequestParam(required = true,  name = "b") Integer b) {
            this.sleep();
            this.hitsCounterToadd.increment();
            if (a == null || b == null) {
                log.error("Invalid input" );
                return 0;
            }

            return a + b;
        }






    @RequestMapping(value = "/subtract", method = RequestMethod.POST)
    
    @Timed(value = "subtract", longTask = true)

    public Integer subtract(@RequestParam("a") Integer a, @RequestParam("b") Integer b) {
            this.sleep();
            this.hitsCounterToSubtract.increment();
        if (a == null || b == null) {
            return 0;
        }
        return a - b;
    }

    public   void sleep() {
        Random random = new Random();

        // Generate a random number between 1 and 10 (inclusive)
        int sleepTime = 1 + random.nextInt(7); // 10 is the maximum sleep time in seconds

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
