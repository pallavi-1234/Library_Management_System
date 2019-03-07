package controllers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import dao.BorrowerRepository;
import dao.TotalLoansRepository;
import dto.StatusAndMessageDTO;
import dto.TotalLoansDTO;
import dto.ValidBorrowerDTO;
import model.Book_Loans;
import service.Book_LoansService;

@RestController
@RequestMapping("/api/checkout")
@ComponentScan("service")
@EnableJpaRepositories("dao")
public class RemoveBooks {
		
	@Autowired
	private BorrowerRepository validBorrowerRepository;
	
	@Autowired
	private TotalLoansRepository totalLoansRepository;	
	
	@Autowired
	private Book_LoansService book_LoansService;

	@RequestMapping("/{values}")
	@ResponseBody
    public StatusAndMessageDTO checkout(@PathVariable("values") String entry) {
		String str = entry.trim();
		String abcd = str.split(" ")[0];
		String s = str.split(" ")[1];
		
		ValidBorrowerDTO borroww = validBorrowerRepository.checkValid(abcd);
		if(borroww==null) {
			return new StatusAndMessageDTO(false, "No valid cardID.");
		}
		TotalLoansDTO loans = totalLoansRepository.LoanofCardID(abcd);
		System.out.println(loans.getTotalLoans());
		if(loans.getTotalLoans()>=3) {
			return new StatusAndMessageDTO(false, "Cant borrow more than 3 books");
		}
		else if(totalLoansRepository.checkIfthebookischeckedout(s) == 1) {
			return new StatusAndMessageDTO(false, "book checked out, you can refresh the page!");
		}
		else {

			
			DateFormat date = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
			Date current = new Date();
			Calendar c = Calendar.getInstance();
			c.setTime(current);
			c.add(Calendar.DATE, 14);
			Date currentDatePlusFourteen = c.getTime();
			

			Book_Loans loan = new Book_Loans(s, abcd, date.format(current), date.format(currentDatePlusFourteen), null);
			int insert = book_LoansService.insert(loan);
			System.out.println("Inserted record with loanId:" + insert);
			return new StatusAndMessageDTO(true, "Book checked out successfully.");
		}
    }
}
