package custom_sql_parser.parser_components;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Lexer class
 * define a lexer to return the tokens
 */
public enum Lexer {
	INSTANCE;
	
	private String query;
	private int position;
	private char currentChar;
	
	private final List<Character> SEPARATORS = new ArrayList<Character>(
			Arrays.asList(';',',', ':', '[', ']', '(', ')')
			);
	private final List<Character> OPERATORS = new ArrayList<Character>(
			Arrays.asList('=', '!', '<', '>')
			);
	
	
	public void init(String query) {
		this.query=query;
		position=0;
		currentChar=this.query.charAt(0);
	}
	
	public Token nextToken() {
		this.skipWhiteSpace();
		String lexem = this.buildLexem();
		Evaluator evaluator = Evaluator.of(lexem);
		return evaluator.nextToken(lexem);
	}
	
	private void skipWhiteSpace() {
		while(Character.isWhitespace(this.currentChar))
			this.advance();
	}
	
	private String buildLexem() {
		String lexem="";
		while(!this.SEPARATORS.contains(this.currentChar)
				&& !this.OPERATORS.contains(this.currentChar)
				&& !Character.isWhitespace(this.currentChar) 
				&& this.currentChar!='\u0000') {
			if(currentChar=='"')
				lexem+=buildQuotedLexem();
			lexem+=this.currentChar;
			this.advance();
		}
		
		return this.checkLexem(lexem) ;
	}
	
	private String buildQuotedLexem() {
		String lexem="\"";
		advance();
		
		while(currentChar!='"') {
			lexem+=currentChar;
			advance();
		}
		
		return lexem;
	}

	private String checkLexem(String lexem) {
		String lexem1 = lexem;
		if(lexem1.length()==0) {
			if(this.OPERATORS.contains(this.currentChar) ) {
				if(this.currentChar=='!') {
					lexem1+=this.currentChar;
					this.advance();
					lexem1+=this.currentChar;
					this.advance();
				}
				else {
					lexem1+=this.currentChar;
					this.advance();
				}
				
			}
			
			if(SEPARATORS.contains(this.currentChar)) {
				lexem1+=this.currentChar;
				this.advance();
			}
		}
		
		return lexem1;
	}
	
	private void advance() {
		this.position+=1;
		if(this.position>this.query.length()-1)
			this.currentChar='\u0000';
		else
			this.currentChar=this.query.charAt(this.position);
	}
	
}
