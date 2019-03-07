package controllers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import dao.CheckInRepository;
import dao.TotalLoansRepository;
import dto.CheckInDTO;
import dto.StatusAndMessageDTO;
import model.Book_Loans;
import service.Book_LoansService;

@RestController
@RequestMapping("/api/book/checkin")
@EnableJpaRepositories("dao")
public class CheckBooks {
	
	@Autowired
	private CheckInRepository CheckInRepository;
	
	@Autowired
	private TotalLoansRepository totalLoansRepository;
	
	@Autowired
	private Book_LoansService book_LoansService;

	@RequestMapping("/search/{values}")
	@ResponseBody
    public List<ArrayList<CheckInDTO>> bookSearch(@PathVariable("values") String a) {
		String str = a.trim();
		Set<String> list = new HashSet<String>();
		List<ArrayList<CheckInDTO>> finals = new ArrayList<ArrayList<CheckInDTO>>();
		for(String ab: str.split(" ")) {
			String z = ab.trim();
			if(!list.contains(z.toLowerCase())) {
				list.add(z.toLowerCase());
			}			
		}
		for(String letd : list) {
			List<CheckInDTO> temp = CheckInRepository.resultsearch(letd);
			System.out.println(temp);
			finals.add((ArrayList<CheckInDTO>) temp);
		}
		return finals;
    }
		
	@RequestMapping("/check/{values}")
	@ResponseBody
	public StatusAndMessageDTO bookCheckIn(@PathVariable("values") String values) {
		String entry = values.trim();
		String Id = entry.split(" ")[0];
		String isbnforbooks = entry.split(" ")[1];
		Book_Loans loanofbook = totalLoansRepository.Tupleofloans(isbnforbooks, Id);				
		DateFormat dates = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
		Date todaysdate = new Date();
		loanofbook.setDate_in(dates.format(todaysdate));
		totalLoansRepository.save(loanofbook);
		return new StatusAndMessageDTO(true, "Book are checked in.");
    }
}
