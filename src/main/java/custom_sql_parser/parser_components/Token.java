package custom_sql_parser.parser_components;

public class Token {
	private String lexem;
	private TokenType type;
	
	public Token(String lexem, TokenType type) {
		this.lexem = lexem;
		this.type = type;
	}
	
	public String tokenLexem() {
		return lexem;
	}
	public TokenType tokenType() {
		return this.type;
	}
	
	
}
