package app.quantun.math.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/math")
@Slf4j
public class Home {

        @RequestMapping(value = "/add", method = RequestMethod.POST)

        public Integer add(@RequestParam( required = true,  name = "a") Integer a, @RequestParam(required = true,  name = "b") Integer b) {
            if (a == null || b == null) {
                log.error("Invalid input" );
                return 0;
            }

            return a + b;
        }

    @RequestMapping(value = "/subtract", method = RequestMethod.POST)
    public Integer subtract(@RequestParam("a") Integer a, @RequestParam("b") Integer b) {
        if (a == null || b == null) {
            return 0;
        }
        return a - b;
    }

}
