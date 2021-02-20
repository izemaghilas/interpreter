package custom_sql_parser.parser_components;

import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;


public class ParserTest {
	private static final String queryResult1="{\n" + 
			"	\"query\": {\n" + 
			"		\"project\": {\n" + 
			"			\"predicate\": \"temperature_min\"\n" + 
			"		},\n" + 
			"		\"table\": \"temperature\"\n" + 
			"	}\n" + 
			"}";
	
	private static final String queryResult2="{\n" + 
			"	\"query\": {\n" + 
			"		\"project\": {\n" + 
			"			\"range\": {\n" + 
			"				\"min\": 12,\n" + 
			"				\"max\": 45\n" + 
			"			},\n" + 
			"			\"predicate\": \"age\"\n" + 
			"		},\n" + 
			"		\"table\": \"EMPLOYE\"\n" + 
			"	}\n" + 
			"}";

	private static final String queryResult3="{\n" + 
			"	\"query\": {\n" + 
			"		\"project\": [\n" + 
			"			{\n" + 
			"				\"predicate\": \"name\"\n" + 
			"			},\n" + 
			"			{\n" + 
			"				\"predicate\": \"age\"\n" + 
			"			}\n" + 
			"		],\n" + 
			"		\"table\": \"ETUDIANT\"\n" + 
			"	}\n" + 
			"}";
	private static String expected4="{\r\n" + 
			"	\"query\": {\r\n" + 
			"		\"project\": {\r\n" + 
			"			\"range\": {\r\n" + 
			"				\"min\": 11,\r\n" + 
			"				\"max\": 13\r\n" + 
			"			},\r\n" + 
			"			\"predicate\": \"VAL1 < 3\"\r\n" + 
			"		},\r\n" + 
			"		\"select\": {\r\n" + 
			"			\"select_conjunction\": [\r\n" + 
			"				{\r\n" + 
			"					\"range\": {\r\n" + 
			"						\"min\": 9,\r\n" + 
			"						\"max\": 12\r\n" + 
			"					},\r\n" + 
			"					\"predicate\": \"VAL1 > 5\",\r\n" + 
			"					\"holds\": \"ANY\"\r\n" + 
			"				},\r\n" + 
			"				{\r\n" + 
			"					\"range\": {\r\n" + 
			"						\"min\": 8,\r\n" + 
			"						\"max\": 9\r\n" + 
			"					},\r\n" + 
			"					\"predicate\": \"VAL1 < 3\",\r\n" + 
			"					\"holds\": \"ALL\"\r\n" + 
			"				}\r\n" + 
			"			]\r\n" + 
			"		},\r\n" + 
			"		\"table\": \"T1\"\r\n" + 
			"	}\r\n" + 
			"}";
	private static final String queryResult4=ParserTest.expected4.replace("\r", "");
	private static String expected="{\r\n" + 
			"	\"query\": {\r\n" + 
			"		\"project\": {\r\n" + 
			"			\"range\": {\r\n" + 
			"				\"min\": 11,\r\n" + 
			"				\"max\": 13\r\n" + 
			"			},\r\n" + 
			"			\"predicate\": \"VAL1 < 3\"\r\n" + 
			"		},\r\n" + 
			"		\"select\": {\r\n" + 
			"			\"range\": {\r\n" + 
			"				\"min\": 9,\r\n" + 
			"				\"max\": 12\r\n" + 
			"			},\r\n" + 
			"			\"predicate\": \"VAL1 > 5\",\r\n" + 
			"			\"holds\": \"ANY\"\r\n" + 
			"		},\r\n" + 
			"		\"table\": \"T1\"\r\n" + 
			"	}\r\n" + 
			"}";
	private static final String queryResult5=ParserTest.expected.replace("\r", "");
	
	private static String expected6 = "{\r\n"
			+ "	\"query\": {\r\n"
			+ "		\"project\": {\r\n"
			+ "			\"range\": {\r\n"
			+ "				\"min\": 11,\r\n"
			+ "				\"max\": 13\r\n"
			+ "			},\r\n"
			+ "			\"predicate\": \"VAL1 < 3\"\r\n"
			+ "		},\r\n"
			+ "		\"select\": {\r\n"
			+ "			\"select_disjunction\": [\r\n"
			+ "				{\r\n"
			+ "					\"range\": {\r\n"
			+ "						\"min\": 9,\r\n"
			+ "						\"max\": 12\r\n"
			+ "					},\r\n"
			+ "					\"predicate\": \"VAL1 > 5\",\r\n"
			+ "					\"holds\": \"ANY\"\r\n"
			+ "				},\r\n"
			+ "				{\r\n"
			+ "					\"select_conjunction\": [\r\n"
			+ "						{\r\n"
			+ "							\"range\": {\r\n"
			+ "								\"min\": 9,\r\n"
			+ "								\"max\": 12\r\n"
			+ "							},\r\n"
			+ "							\"predicate\": \"VAL1\",\r\n"
			+ "							\"holds\": \"ANY\"\r\n"
			+ "						},\r\n"
			+ "						{\r\n"
			+ "							\"range\": {\r\n"
			+ "								\"min\": 9,\r\n"
			+ "								\"max\": 12\r\n"
			+ "							},\r\n"
			+ "							\"predicate\": \"VAL1\",\r\n"
			+ "							\"holds\": \"ALL\"\r\n"
			+ "						}\r\n"
			+ "					]\r\n"
			+ "				},\r\n"
			+ "				{\r\n"
			+ "					\"select_conjunction\": [\r\n"
			+ "						{\r\n"
			+ "							\"predicate\": \"VAL1\",\r\n"
			+ "							\"holds\": \"ALL\"\r\n"
			+ "						},\r\n"
			+ "						{\r\n"
			+ "							\"select_disjunction\": [\r\n"
			+ "								{\r\n"
			+ "									\"predicate\": \"VAL1\",\r\n"
			+ "									\"holds\": \"ALL\"\r\n"
			+ "								},\r\n"
			+ "								{\r\n"
			+ "									\"predicate\": \"VAL1\",\r\n"
			+ "									\"holds\": \"ALL\"\r\n"
			+ "								}\r\n"
			+ "							]\r\n"
			+ "						}\r\n"
			+ "					]\r\n"
			+ "				}\r\n"
			+ "			]\r\n"
			+ "		},\r\n"
			+ "		\"table\": \"T1\"\r\n"
			+ "	}\r\n"
			+ "}";
	private static final String queryResult6=expected6.replace("\r", "");
	
	
	//used as method source for parameterized unit test
	private static Stream<Arguments> provideQueryandParsedQueryResult(){
		return Stream.of(
				Arguments.of("select temperature_min from temperature;", 
						ParserTest.queryResult1),
				Arguments.of("select age[12:45] from EMPLOYE;", 
						ParserTest.queryResult2),
				Arguments.of("select name, age from ETUDIANT;", 
						ParserTest.queryResult3),
				Arguments.of("SELECT VAL1[11:13]<3 FROM T1 "
								+"WHERE  ANY VAL1[9:12]>5 AND ALL VAL1[8:9]<3;", 
						ParserTest.queryResult4),
				Arguments.of("SELECT VAL1[11:13]<3 FROM T1 WHERE  ANY VAL1[9:12]>5;", 
						ParserTest.queryResult5),
				Arguments.of("SELECT VAL1[11:13]<3 FROM T1 WHERE "
						+ "		ANY VAL1[9:12]>5 "
						+ "			OR "
						+ "		(ANY VAL1[9:12] AND ALL VAL1[9:12])"
						+ "			OR"
						+ "		(ALL VAL1 AND(ALL VAL1 OR ALL VAL1))"
						+ "	;", 
						ParserTest.queryResult6)
				);
		
	}
	
	@ParameterizedTest
	@MethodSource("provideQueryandParsedQueryResult")
	public void parsed_query_should_return_expected_result(
			String query, String parsedQueryExpectedResult) throws ParserException {
		//Given
		Lexer.INSTANCE.init(query);
		
		//When
		String result = Parser.INSTANCE.parse();
		
		//Then
		Assertions.assertEquals(parsedQueryExpectedResult, result);
	}
	
	
	
}
