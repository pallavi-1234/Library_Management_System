package dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import dto.CheckInDTO;
import dto.SearchDTO;
import model.Checkinview;
import model.Searchview;

@Repository
public interface CheckInRepository extends JpaRepository<Checkinview, String>{
	
	@Query("select new dto.CheckInDTO(u.cardno, u.isbn, u.ssn, u.borrower_name, u.date_in) from Checkinview as u where LOWER(u.cardno) = :searchWord OR LOWER(u.isbn)= :searchWord OR LOWER(u.borrower_name) like LOWER(CONCAT('%', :searchWord, '%')) AND u.date_in is null")
	List<CheckInDTO> resultsearch(@Param("searchWord") String searchWord);
	
}
