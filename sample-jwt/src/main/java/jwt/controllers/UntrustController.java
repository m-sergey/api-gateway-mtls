package jwt.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class UntrustController {

    private static Logger log = LoggerFactory.getLogger(UntrustController.class);


    @RequestMapping("/untrust")
    public String echo() {
        log.info("Requested untrust controller");
        return "public";
    }
}
