package custom_sql_parser.ast;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ProjectTest {

	@Test
	public void should_return_expected_values() {
		//Given
		Range range = new Range("1","5");
		Predicate predicate = new Predicate("VAL1");
		
		Project project = new Project();
		project.setRange(range);
		project.setPredicate(predicate);
		
		Project project1 = new Project();
		project1.setRange(range);
		project1.setPredicate(predicate);
		
		Project project2 = new Project();
		project2.setRange(range);
		project2.setPredicate(predicate);
		
		Project project_list=new Project();
		project_list.projectIsList();
		project_list.appendToProjects(project);
		project_list.appendToProjects(project1);
		project_list.appendToProjects(project2);
		String expected="		\"project\": {\n" + 
				"			\"range\": {\n" + 
				"				\"min\": 1,\n" + 
				"				\"max\": 5\n" + 
				"			},\n" + 
				"			\"predicate\": \"VAL1\"\n" + 
				"		}";
		
		String str="			{\n" + 
				"				\"range\": {\n" + 
				"					\"min\": 1,\n" + 
				"					\"max\": 5\n" + 
				"				},\n" + 
				"				\"predicate\": \"VAL1\"\n" + 
				"			}";

		String expected1 = "		\"project\": [\n"
				+str+",\n"
				+str+",\n"
				+str
				+"\n		]";
		
		//When
		String projectString=project.toString();
		String listOfProjects=project_list.toString();
		
		
		//Then
		Assertions.assertEquals(expected, projectString);
		Assertions.assertEquals(expected1, listOfProjects);
	}
	
	@Test
	public void should_return_expected_value_project_is_list() {
		//Given
		Range range = new Range("1","5");
		Predicate predicate = new Predicate("VAL1");
		
		Project project = new Project();
		project.setRange(range);
		project.setPredicate(predicate);
		
		//When
		project.projectIsList();
		String expected="		\"project\": [\n]";
		String projectString=project.toString();
		
		//Then
		Assertions.assertEquals(expected, projectString);
		
	}
}
