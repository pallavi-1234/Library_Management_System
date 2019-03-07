package dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import dto.TotalLoansDTO;
import model.Book_Loans;

@Repository
public interface TotalLoansRepository extends JpaRepository<Book_Loans, String>{
		
	@Query("select new dto.TotalLoansDTO(count(u.card_id)) from Book_Loans u where u.card_id = :cardId and u.date_in is null")
	TotalLoansDTO LoanofCardID(@Param("cardId") String cardId);
	
	@Query("select count(*) from Book_Loans where isbn=:isbn and date_in is null")
	int checkIfthebookischeckedout(@Param("isbn") String isbn);
	
	@Query("select new model.Book_Loans(u.loan_id, u.isbn, u.card_id, u.date_out, u.due_date, u.date_in) from Book_Loans u where isbn=:isbn and card_id=:cardId and date_in is null")
	Book_Loans Tupleofloans(@Param("isbn") String isbn, @Param("cardId") String cardId);

}