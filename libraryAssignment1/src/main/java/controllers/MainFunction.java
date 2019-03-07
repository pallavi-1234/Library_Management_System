package controllers;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@EnableJpaRepositories("repository")
@EntityScan(basePackages = "model")
public class MainFunction {

	@RequestMapping("/")
    public String index() {
        return "homePage";
    }
	
	@RequestMapping(value = "/search", method = RequestMethod.GET)
    public String search() {
        return "find";
    }
	
	@RequestMapping(value = "/checkin", method = RequestMethod.GET)
    public String checkin() {
        return "checkmethod";
    }
	
	@RequestMapping(value = "/borrower", method = RequestMethod.GET)
    public String borrower() {
        return "borrowbook";
    }
	
	@RequestMapping(value = "/fines", method = RequestMethod.GET)
    public String fines() {
        return "finecalculate";
    }
}
