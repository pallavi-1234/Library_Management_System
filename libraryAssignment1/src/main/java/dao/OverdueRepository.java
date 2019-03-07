package dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import model.Overdue;

@Repository
public interface OverdueRepository extends JpaRepository<Overdue, Integer>{
	
	@Query("select new model.Overdue(u.loan_id, u.card_id, u.daysoverdue, u.isbookreturned) from Overdue u")
	List<Overdue> Detailsofoverdue();
}
