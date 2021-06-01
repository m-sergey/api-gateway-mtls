package jwt.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class TrustController {

    private static Logger log = LoggerFactory.getLogger(TrustController.class);

    @RequestMapping("/trust")
    public String echo() {
        log.info("Requested trust controller");
        return "private";
    }

}
