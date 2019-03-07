package dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import dto.SearchDTO;
import model.Searchview;

@Repository
public interface SearchRepository extends JpaRepository<Searchview, String>{
	
	@Query("select new dto.SearchDTO(u.isbn, u.title, u.author_name, u.isavailable) from Searchview as u where LOWER(title) like LOWER(CONCAT('%', :searchWord, '%')) OR LOWER(isbn) like LOWER(CONCAT('%', :searchWord, '%')) OR LOWER(author_name) like LOWER(CONCAT('%', :searchWord, '%'))")
	List<SearchDTO> searchresult(@Param("searchWord") String searchWord);
	
}
