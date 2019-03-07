package controllers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import dao.SearchRepository;
import dto.BookDTO;
import dto.SearchDTO;
import model.Book;

@RestController
@RequestMapping("/api/book")
@EnableJpaRepositories("dao")
public class BookLookup {
	
	@Autowired
	private SearchRepository searchRepository;

	@RequestMapping("/search/{values}")
	@ResponseBody
    public List<ArrayList<SearchDTO>> bookSearch(@PathVariable("values") String values) {
		String sb = values.trim();
		Set<String> set1 = new HashSet<String>();
		List<ArrayList<SearchDTO>> totalResults = new ArrayList<ArrayList<SearchDTO>>();
		for(String x: sb.split(" ")) {
			String temp = x.trim();
			if(!set1.contains(temp.toLowerCase())) {
				set1.add(temp.toLowerCase());
			}			
		}		
		for(String letter : set1) {
			List<SearchDTO> mem = searchRepository.searchresult(letter);
			System.out.println(mem);
			totalResults.add((ArrayList<SearchDTO>) mem);
		}
		return totalResults;
    }
}
