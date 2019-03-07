package dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ValidBorrowerDTO {
	
	private String cardId;
	private String ssn;

}
