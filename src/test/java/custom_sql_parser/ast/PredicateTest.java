package custom_sql_parser.ast;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class PredicateTest {
	@Test
	public void should_return_expected_values() {
		//Given
		Predicate predicate_1 = new Predicate("VAL1");
		predicate_1.setOperator(">");predicate_1.setToken_t("5");
		Predicate predicate_2 = new Predicate("VAL1");
		String expected_1="\"predicate\": \"VAL1 > 5\"";
		String expected_2="\"predicate\": \"VAL1\"";
		
		//When
		String str_1 = predicate_1.toString();
		String str_2 = predicate_2.toString();
		
		//Then
		Assertions.assertEquals(expected_1, str_1);
		Assertions.assertEquals(expected_2, str_2);
	}
}
