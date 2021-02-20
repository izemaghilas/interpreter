package custom_sql_parser.ast;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RangeTest {
	
	@Test
	public void should_return_expected_values() {
		//Given
		Range range1 = new Range("1","5");
		Range range2 = new Range("","5");
		Range range3 = new Range("1","");
		String expected1="\"range\": {\n" + 
				"	\"min\": 1,\n" + 
				"	\"max\": 5\n" + 
				"}";
		String expected2="\"range\": {\n" + 
				"	\"min\": ,\n" + 
				"	\"max\": 5\n" + 
				"}";
		String expected3="\"range\": {\n" + 
				"	\"min\": 1,\n" + 
				"	\"max\": \n" + 
				"}";
		
		//When
		String str1 = range1.toString();
		String str2 = range2.toString();
		String str3 = range3.toString();
		
		//Then
		Assertions.assertEquals(expected1, str1);
		Assertions.assertEquals(expected2, str2);
		Assertions.assertEquals(expected3, str3);
	}
}
