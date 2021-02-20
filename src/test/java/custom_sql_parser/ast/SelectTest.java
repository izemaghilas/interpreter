package custom_sql_parser.ast;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SelectTest {
	
	@Test
	public void should_return_beautiful_select() {
		//Given
		Select select_t=new Select();
		Range range = new Range("1","5");
		Predicate predicate = new Predicate("VAL1");
		Project project = new Project();
		project.setRange(range);
		project.setPredicate(predicate);
		select_t.setProject_t(project);
		select_t.setHolds("ANY");
		
		String expected="		\"select\": {\r\n" + 
				"			\"range\": {\r\n" + 
				"				\"min\": 1,\r\n" + 
				"				\"max\": 5\r\n" + 
				"			},\r\n" + 
				"			\"predicate\": \"VAL1\",\r\n" + 
				"			\"holds\": \"ANY\"\r\n" + 
				"		}";
		expected=expected.replace("\r", "");
		
		//When
		String selectString=select_t.toString();
		
		//Then
		Assertions.assertEquals(expected, selectString);
	}
	
	@Test
	public void should_return_beautiful_complex_select_simple_disjunction() {
		//Given
		Select select_t=new Select();
		Select select1=new Select();
		Select select2=new Select();
		Select select3=new Select();
		
		Range range = new Range("1","5");
		Predicate predicate = new Predicate("VAL1");
		Project project = new Project();
		project.setRange(range);
		project.setPredicate(predicate);
		
		select1.setProject_t(project);
		select1.setHolds("ANY");
		select2.setProject_t(project);
		select2.setHolds("ALL");
		select3.setProject_t(project);
		select3.setHolds("ALL");
		
		select_t.setType("select_disjunction", select1);
		select_t.appendSelectToList(select2);
		select_t.appendSelectToList(select3);
		
		String expected="		\"select\": {\r\n" + 
				"			\"select_disjunction\": [\r\n" + 
				"				{\r\n" + 
				"					\"range\": {\r\n" + 
				"						\"min\": 1,\r\n" + 
				"						\"max\": 5\r\n" + 
				"					},\r\n" + 
				"					\"predicate\": \"VAL1\",\r\n" + 
				"					\"holds\": \"ANY\"\r\n" + 
				"				},\r\n" + 
				"				{\r\n" + 
				"					\"range\": {\r\n" + 
				"						\"min\": 1,\r\n" + 
				"						\"max\": 5\r\n" + 
				"					},\r\n" + 
				"					\"predicate\": \"VAL1\",\r\n" + 
				"					\"holds\": \"ALL\"\r\n" + 
				"				},\r\n" + 
				"				{\r\n" + 
				"					\"range\": {\r\n" + 
				"						\"min\": 1,\r\n" + 
				"						\"max\": 5\r\n" + 
				"					},\r\n" + 
				"					\"predicate\": \"VAL1\",\r\n" + 
				"					\"holds\": \"ALL\"\r\n" + 
				"				}\r\n" + 
				"			]\r\n" + 
				"		}";
		expected = expected.replace("\r", "");
		
		//When
		String selectString=select_t.toString();
		
		//Then
		Assertions.assertEquals(expected, selectString);
	}
	
	@Test
	public void should_return_beautiful_complex_select_simple_conjunction() {
		//Given
				Select select_t=new Select();
				Select select1=new Select();
				Select select2=new Select();
				Select select3=new Select();
				
				Range range = new Range("1","5");
				Predicate predicate = new Predicate("VAL1");
				Project project = new Project();
				project.setRange(range);
				project.setPredicate(predicate);
				
				select1.setProject_t(project);
				select1.setHolds("ANY");
				select2.setProject_t(project);
				select2.setHolds("ALL");
				select3.setProject_t(project);
				select3.setHolds("ALL");
				
				select_t.setType("select_conjunction", select1);
				select_t.appendSelectToList(select2);
				select_t.appendSelectToList(select3);
				
				String expected="		\"select\": {\r\n" + 
						"			\"select_conjunction\": [\r\n" + 
						"				{\r\n" + 
						"					\"range\": {\r\n" + 
						"						\"min\": 1,\r\n" + 
						"						\"max\": 5\r\n" + 
						"					},\r\n" + 
						"					\"predicate\": \"VAL1\",\r\n" + 
						"					\"holds\": \"ANY\"\r\n" + 
						"				},\r\n" + 
						"				{\r\n" + 
						"					\"range\": {\r\n" + 
						"						\"min\": 1,\r\n" + 
						"						\"max\": 5\r\n" + 
						"					},\r\n" + 
						"					\"predicate\": \"VAL1\",\r\n" + 
						"					\"holds\": \"ALL\"\r\n" + 
						"				},\r\n" + 
						"				{\r\n" + 
						"					\"range\": {\r\n" + 
						"						\"min\": 1,\r\n" + 
						"						\"max\": 5\r\n" + 
						"					},\r\n" + 
						"					\"predicate\": \"VAL1\",\r\n" + 
						"					\"holds\": \"ALL\"\r\n" + 
						"				}\r\n" + 
						"			]\r\n" + 
						"		}";
				expected = expected.replace("\r", "");
				
				//When
				String selectString=select_t.toString();
				
				//Then
				Assertions.assertEquals(expected, selectString);
	}
	
	@Test
	public void should_return_beautiful_complex_select_complex_disjunction() {
		//Given
		Select select_t=new Select();
		Select select1=new Select();
		Select select2=new Select();
		Select select3=new Select();
		Select select4=new Select();
		Select select5=new Select();
		Select select6=new Select();
		
		Range range = new Range("1","5");
		Predicate predicate = new Predicate("VAL1");
		Project project = new Project();
		project.setRange(range);
		project.setPredicate(predicate);
		
		select1.setProject_t(project);
		select1.setHolds("ANY");
		select3.setProject_t(project);
		select3.setHolds("ALL");
		select4.setProject_t(project);
		select4.setHolds("ALL");
		select5.setProject_t(project);
		select5.setHolds("ALL");
		select6.setProject_t(project);
		select6.setHolds("ALL");
		
		select_t.setType("select_disjunction", select1);
		select2.setType("select_conjunction", select3);
		select2.appendSelectToList(select4);
		select2.appendSelectToList(select5);
		select_t.appendSelectToList(select2);
		select_t.appendSelectToList(select6);
		
		String expected="		\"select\": {\r\n" + 
				"			\"select_disjunction\": [\r\n" + 
				"				{\r\n" + 
				"					\"range\": {\r\n" + 
				"						\"min\": 1,\r\n" + 
				"						\"max\": 5\r\n" + 
				"					},\r\n" + 
				"					\"predicate\": \"VAL1\",\r\n" + 
				"					\"holds\": \"ANY\"\r\n" + 
				"				},\r\n" + 
				"				{\r\n" + 
				"					\"select_conjunction\": [\r\n" + 
				"						{\r\n" + 
				"							\"range\": {\r\n" + 
				"								\"min\": 1,\r\n" + 
				"								\"max\": 5\r\n" + 
				"							},\r\n" + 
				"							\"predicate\": \"VAL1\",\r\n" + 
				"							\"holds\": \"ALL\"\r\n" + 
				"						},\r\n" + 
				"						{\r\n" + 
				"							\"range\": {\r\n" + 
				"								\"min\": 1,\r\n" + 
				"								\"max\": 5\r\n" + 
				"							},\r\n" + 
				"							\"predicate\": \"VAL1\",\r\n" + 
				"							\"holds\": \"ALL\"\r\n" + 
				"						},\r\n" + 
				"						{\r\n" + 
				"							\"range\": {\r\n" + 
				"								\"min\": 1,\r\n" + 
				"								\"max\": 5\r\n" + 
				"							},\r\n" + 
				"							\"predicate\": \"VAL1\",\r\n" + 
				"							\"holds\": \"ALL\"\r\n" + 
				"						}\r\n" + 
				"					]\r\n" + 
				"				},\r\n" + 
				"				{\r\n" + 
				"					\"range\": {\r\n" + 
				"						\"min\": 1,\r\n" + 
				"						\"max\": 5\r\n" + 
				"					},\r\n" + 
				"					\"predicate\": \"VAL1\",\r\n" + 
				"					\"holds\": \"ALL\"\r\n" + 
				"				}\r\n" + 
				"			]\r\n" + 
				"		}";
			expected=expected.replace("\r", "");

		//When
		String selectString = select_t.toString();
		
		//Then
		Assertions.assertEquals(expected, selectString);
	}
}
