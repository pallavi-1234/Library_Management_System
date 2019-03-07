package controllers;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import dao.DisplayFinesRepository;
import dao.FinesRepository;
import dao.OverdueRepository;
import dto.StatusAndMessageDTO;
import model.Displayfines;
import model.Fines;
import model.Overdue;

@RestController
@RequestMapping("/api/fines")
@EnableJpaRepositories("dao")
public class TotalFines {
	
	@Autowired
	private OverdueRepository overdueRepository;
	
	@Autowired
	private FinesRepository finesRepository;
	
	@Autowired
	private DisplayFinesRepository displayFinesRepository;
	
	final static double perDayFine = 0.25;

	@RequestMapping("/calculate")
	@ResponseBody
    public StatusAndMessageDTO calculateFines() {
		List<Overdue> detailofoverdues = overdueRepository.Detailsofoverdue();
		Map<Integer, Double> mapoffine = new HashMap<Integer, Double>();
		for(Overdue object : detailofoverdues) {
			Integer loanid = object.getLoan_id();
			int daysoverdue = object.getDaysoverdue();
			double fines = daysoverdue * perDayFine;
			mapoffine.put(loanid, fines);
		}
		List<Fines> details = finesRepository.detailsoffine();
		Set<Integer> setoffines = new HashSet<Integer>();
		Map<String, Double> unpaid = new HashMap<String, Double>();
		for(Fines object: details) {
			int loanId = object.getLoan_id();
			boolean paid = object.isPaid();
			setoffines.add(loanId);
			if(!paid) {
				double finetable = object.getFine_amt();
				double finecalculate = mapoffine.get(loanId);
				if(finetable != finecalculate) {
					Fines tupleoffines = finesRepository.Tuplefines(loanId);
					tupleoffines.setFine_amt(finecalculate);
					finesRepository.save(tupleoffines);					
				}
				setoffines.add(loanId);
			}
		}
		for(Map.Entry<Integer, Double> entry : mapoffine.entrySet()) {			
			int loanId = entry.getKey();
			Double fine = entry.getValue();
			if(!setoffines.contains(loanId)) {
				Fines fineTuple = new Fines(loanId, fine, false);
				finesRepository.save(fineTuple);
			}
		}
		return new StatusAndMessageDTO(true, "all the fines have been updated.");	
    }
	
	@RequestMapping("/display")
	@ResponseBody
	public List<Displayfines> displayFines() {
		return displayFinesRepository.detailoffines();
	}
	
	@RequestMapping("/pay/{values}")
	@ResponseBody
	public StatusAndMessageDTO payFines(@PathVariable("values") String values) {
		String cleanString = values.trim();
		List<Displayfines> payFineDetails = displayFinesRepository.detailsoffinepays(cleanString);
		for(Displayfines u: payFineDetails) {
			String ls = u.getLoan_ids();
			String[] l = ls.split(",");
			for(String loan: l) {
				Fines finesTuple = finesRepository.Tuplefines(Integer.parseInt(loan));
				finesTuple.setPaid(true);
				finesRepository.save(finesTuple);
			}
			
		}
		return new StatusAndMessageDTO(true, "Fine has been paid.");
	}
}
