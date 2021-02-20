package custom_sql_parser.ast;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class QueryTest {
	@Test
	public void should_return_defined_structure() {
		//Given
		Query query=new Query();
		Range range = new Range("1","5");
		Predicate predicate = new Predicate("VAL1");
		
		Project project = new Project();
		project.setRange(range);
		project.setPredicate(predicate);
		
		query.setProjectT(project);
		query.setTable("T1");
		String expected="{\n" + 
				"	\"query\": {\n" + 
				"		\"project\": {\n" + 
				"			\"range\": {\n" + 
				"				\"min\": 1,\n" + 
				"				\"max\": 5\n" + 
				"			},\n" + 
				"			\"predicate\": \"VAL1\"\n" + 
				"		},\n" + 
				"		\"table\": \"T1\"\n" + 
				"	}\n" + 
				"}";
		
		//When
		String queryString=query.toString();
		
		//Then
		Assertions.assertEquals(expected, queryString);
	}
}
