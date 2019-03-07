package dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import dto.SearchDTO;
import model.Displayfines;
import model.Searchview;

@Repository
public interface DisplayFinesRepository extends JpaRepository<Displayfines, String>{
	
	@Query("select new model.Displayfines(u.loan_ids, u.card_id, u.total_fine) from Displayfines u")
	List<Displayfines> detailoffines();
	
	@Query("select new model.Displayfines(u.loan_ids, u.card_id, u.total_fine) from Displayfines u where u.card_id=:card_id")
	List<Displayfines> detailsoffinepays(@Param("card_id") String card_id);
	
}
