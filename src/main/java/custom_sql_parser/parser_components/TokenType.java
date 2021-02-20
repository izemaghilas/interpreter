package custom_sql_parser.parser_components;

/**
 * Lexer tokens
 */

public enum TokenType {
	//keywords
	KEYWORD_select,
	KEYWORD_from,
	KEYWORD_where,
	HOLD,
	BOOLEAN_OPERATOR,
	OPERATOR,
	
	//separators 
	COMMA,
	COLON, // " : "
	LEFTHOOK,
	LEFTBRACKET,
	RIGHTHOOK,
	RIGHTBRACKET,
	
	IDENTIFIER,
	TOKEN_T,//any kind of number or string or '*'
	
	//Special Tokens
	EOQ,//;
	UNRECOGNIZED,
}
