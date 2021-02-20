package custom_sql_parser.parser_components;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class LexerTest {
	
	@Test
	public void should_return_keywords() {
		//Given
		String query="  from   select where ";
		Lexer lexer=Lexer.INSTANCE;
		lexer.init(query);
		Token[] types_founded=new Token[3];
		int i=0;
		
		//When
		while(i<3) {
			Token token = lexer.nextToken();
			types_founded[i]=token;
			i++;
		}
		
		//Then
		Assertions.assertEquals("", types_founded[0].tokenLexem());
		Assertions.assertEquals(TokenType.KEYWORD_from, types_founded[0].tokenType());
		
		Assertions.assertEquals("", types_founded[1].tokenLexem());
		Assertions.assertEquals(TokenType.KEYWORD_select, types_founded[1].tokenType());
		
		Assertions.assertEquals("", types_founded[2].tokenLexem());
		Assertions.assertEquals(TokenType.KEYWORD_where, types_founded[2].tokenType());
	}
	
	
	@Test
	public void should_return_boolean_operations() {
		//Given
		String query="  and   or ";
		Lexer lexer=Lexer.INSTANCE;
		lexer.init(query);
		Token[] types_founded=new Token[2];
		int i=0;
		
		//When
		while(i<2) {
			Token token = lexer.nextToken();
			types_founded[i]=token;
			i++;
		}
		
		//Then
		Assertions.assertEquals("AND", types_founded[0].tokenLexem());
		Assertions.assertEquals(TokenType.BOOLEAN_OPERATOR, types_founded[0].tokenType());
		
		Assertions.assertEquals("OR", types_founded[1].tokenLexem());
		Assertions.assertEquals(TokenType.BOOLEAN_OPERATOR, types_founded[1].tokenType());
	}
	
	@Test
	public void should_return_operations() {
		//Given
		String query="  =   !=  <   >";
		Lexer lexer=Lexer.INSTANCE;
		lexer.init(query);
		Token[] types_founded=new Token[4];
		int i=0;
		
		//When
		while(i<4) {
			Token token = lexer.nextToken();
			types_founded[i]=token;
			i++;
		}
		
		//Then
		Assertions.assertEquals("=", types_founded[0].tokenLexem());
		Assertions.assertEquals(TokenType.OPERATOR, types_founded[0].tokenType());
		
		Assertions.assertEquals("!=", types_founded[1].tokenLexem());
		Assertions.assertEquals(TokenType.OPERATOR, types_founded[1].tokenType());
		
		Assertions.assertEquals("<", types_founded[2].tokenLexem());
		Assertions.assertEquals(TokenType.OPERATOR, types_founded[2].tokenType());
		
		Assertions.assertEquals(">", types_founded[3].tokenLexem());
		Assertions.assertEquals(TokenType.OPERATOR, types_founded[3].tokenType());
		
	}
	@Test
	public void should_return_seperators() {
		//Given
		String query=",[ ) ]   :(  ";
		Lexer lexer=Lexer.INSTANCE;
		lexer.init(query);
		Token[] types_founded=new Token[6];
		int i=0;
		
		//When
		while(i<6) {
			Token token = lexer.nextToken();
			types_founded[i]=token;
			i++;
		}
		
		//Then
		Assertions.assertEquals(",", types_founded[0].tokenLexem());
		Assertions.assertEquals(TokenType.COMMA, types_founded[0].tokenType());
		
		Assertions.assertEquals("[", types_founded[1].tokenLexem());
		Assertions.assertEquals(TokenType.LEFTHOOK, types_founded[1].tokenType());
		
		Assertions.assertEquals(")", types_founded[2].tokenLexem());
		Assertions.assertEquals(TokenType.RIGHTBRACKET, types_founded[2].tokenType());
		
		Assertions.assertEquals("]", types_founded[3].tokenLexem());
		Assertions.assertEquals(TokenType.RIGHTHOOK, types_founded[3].tokenType());
		
		Assertions.assertEquals(":", types_founded[4].tokenLexem());
		Assertions.assertEquals(TokenType.COLON, types_founded[4].tokenType());
		
		Assertions.assertEquals("(", types_founded[5].tokenLexem());
		Assertions.assertEquals(TokenType.LEFTBRACKET, types_founded[5].tokenType());
		
	}
	
	@Test
	public void should_return_eoq() {
		//Given
		String query=";";
		Lexer lexer=Lexer.INSTANCE;
		lexer.init(query);

		//When
		Token token = lexer.nextToken();
		
		//Then
		Assertions.assertEquals(";", token.tokenLexem());
		Assertions.assertEquals(TokenType.EOQ, token.tokenType());
	}
	
	@Test
	public void should_return_IDENTIFIER() {
		//Given
		String query="__89AAzzz___89Ghhhldsdsnkjh_____8980121";
		Lexer lexer=Lexer.INSTANCE;
		lexer.init(query);

		//When
		Token token = lexer.nextToken();
		
		//Then
		Assertions.assertEquals("__89AAzzz___89Ghhhldsdsnkjh_____8980121", token.tokenLexem());
		Assertions.assertEquals(TokenType.IDENTIFIER, token.tokenType());
	}
	
	@Test
	public void should_return_TOKEN_T() {
		//Given
		String query="\"46658.8dsdjkkhs__èdqsdqdq5556.*/-,;[]dfsdfsfà)895\"";
		Lexer lexer=Lexer.INSTANCE;
		lexer.init(query);

		//When
		Token token = lexer.nextToken();
		
		//Then
		Assertions.assertEquals("\"46658.8dsdjkkhs__èdqsdqdq5556.*/-,;[]dfsdfsfà)895\"", token.tokenLexem());
		Assertions.assertEquals(TokenType.TOKEN_T, token.tokenType());
	}
	@Test
	public void should_return_TOKEN_T_nd() {
		//Given
		String query="458";
		Lexer lexer=Lexer.INSTANCE;
		lexer.init(query);

		//When
		Token token = lexer.nextToken();
		
		//Then
		Assertions.assertEquals("458", token.tokenLexem());
		Assertions.assertEquals(TokenType.TOKEN_T, token.tokenType());
	}
	
	@Test
	public void should_return_UNRECOGNIZED() {
		//Given
		String query="gsdfhé";
		Lexer lexer=Lexer.INSTANCE;
		lexer.init(query);

		//When
		Token token = lexer.nextToken();
		
		//Then
		Assertions.assertEquals("gsdfhé", token.tokenLexem());
		Assertions.assertEquals(TokenType.UNRECOGNIZED, token.tokenType());
	}
}
