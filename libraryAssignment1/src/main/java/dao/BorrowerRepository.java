package dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import dto.ValidBorrowerDTO;
import model.Borrower;

@Repository
public interface BorrowerRepository extends JpaRepository<Borrower, String>{
		
	@Query("select new dto.ValidBorrowerDTO(u.cardid, u.ssn) from Borrower as u where u.cardid = :cardId")
	ValidBorrowerDTO checkValid(@Param("cardId") String cardId);
	
	@Query("select count(*) from Borrower where ssn=:search_ssn")
	int SSNCheck(@Param("search_ssn") String search_ssn);
	
	@Query("select max(id) from Borrower")
	long maximumID();

}
