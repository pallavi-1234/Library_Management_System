package controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import dao.BorrowerRepository;
import dao.SearchRepository;
import dto.StatusAndMessageDTO;
import model.Borrower;

@RestController
@RequestMapping("/api/borrower")
@EnableJpaRepositories("dao")
public class Creation {
	
	@Autowired
	private SearchRepository searchRepository;
	
	@Autowired
	private BorrowerRepository borrowerRepository;

	@RequestMapping("/create/{values}")
	@ResponseBody
    public StatusAndMessageDTO bookSearch(@PathVariable("values") String enter) {
		String str = enter.trim();
		System.out.println("clean string: " + str);
		if(str.isEmpty()) {
			return new StatusAndMessageDTO(false, "Fields are empty.");
		}
		System.out.println(str);
		String[] fields = str.split("\\|\\|");
		System.out.println(fields[0]);
		String naming = fields[0].trim();
		System.out.println(naming);
		String SSN = fields[1].trim();
		System.out.println(SSN);
		String add = fields[2].trim();
		System.out.println(add);
		String ph = fields[3].trim();
		System.out.println(ph);
		long maxIDFromBookLoans = borrowerRepository.maximumID() + 1;
		String stringID = String.valueOf(maxIDFromBookLoans);
		String cardId = ("000000" + stringID).substring(stringID.length());
		
		if(naming == null || SSN == null || add == null || ph == null) {
			return new StatusAndMessageDTO(false, "No null values allowed.");
		}
		else if(naming.isEmpty() || SSN.isEmpty() || add.isEmpty() || ph.isEmpty() ) {
			return new StatusAndMessageDTO(false, "Field should not be empty.");
		}
		else if(SSN.length()<9 || SSN.length()>11){
			return new StatusAndMessageDTO(false, "SSN format is incorrect.");
		}
		else if(ph.length()<10 || ph.length()>14){
			return new StatusAndMessageDTO(false, "Phone no format is incorrect.");
		}
		else if(borrowerRepository.SSNCheck(SSN)==1) {
			System.out.println(borrowerRepository.SSNCheck(SSN) + SSN);
			return new StatusAndMessageDTO(false, "Borrower is already there.");
		}
		else {			
			Borrower return1 = new Borrower(cardId, SSN, naming, add, ph, maxIDFromBookLoans+1);
			System.out.println(return1);
			borrowerRepository.save(return1);
			return new StatusAndMessageDTO(true, "Borrower has been added.");
		}		
    }
}
