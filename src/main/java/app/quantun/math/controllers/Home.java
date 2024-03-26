package app.quantun.math.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/math")
@Slf4j
public class Home {

        @RequestMapping(value = "/multiplication", method = RequestMethod.POST)

        public Double multiplication(@RequestParam( required = true,  name = "a") Double a, @RequestParam(required = true,  name = "b") Double b) {
            if (a == null || b == null) {
                log.error("Invalid input" );
                return 0.0;
            }

            return a * b;
        }
    //division and multiplication
    @RequestMapping(value = "/division", method = RequestMethod.POST)
    public ResponseEntity<?> division(@RequestParam("a") Double a, @RequestParam("b") Double b) {

        if (a == null || b == null) {
            log.error("Invalid input" );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input A or B is null");
        }
        if (b == 0) {
            log.error("Invalid input" );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input B");
        }

        return  ResponseEntity.ok(a / b);
    }

}
