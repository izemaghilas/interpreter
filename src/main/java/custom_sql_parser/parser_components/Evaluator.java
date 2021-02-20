package custom_sql_parser.parser_components;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Function;

public class Evaluator {
	private String lexem_pattern;
	private Function<String, Token> nextToken;
	
	public Evaluator(String lexem_pattern, Function<String, Token> nextToken) {
		this.lexem_pattern = lexem_pattern;
		this.nextToken = nextToken;
	}

	public Token nextToken(String lexem) {
		return this.nextToken.apply(lexem);
	}

	
	private final static Evaluator KEYWORD_SELECT_EVALUATOR = 
			new Evaluator(
				"^(s|S)(e|E)(l|L)(e|E)(c|C)(t|T)$",
				lexem->{
					return new Token("", TokenType.KEYWORD_select);
				}
			);
	private final static Evaluator KEYWORD_FROM_EVALUATOR = 
			new Evaluator(
				"^(f|F)(r|R)(o|O)(m|M)$",
				lexem->{
					return new Token("", TokenType.KEYWORD_from);
				}
			);
	private final static Evaluator KEYWORD_WHERE_EVALUATOR = 
			new Evaluator(
				"^(w|W)(h|H)(e|E)(r|R)(e|E)$",
				lexem->{
					return new Token("", TokenType.KEYWORD_where);
				}
			);
	private final static Evaluator HOLD_EVALUATOR = 
			new Evaluator(
				"^((a|A)(l|L)(l|L))|((a|A)(n|N)(y|Y))$",
				lexem->{
					return new Token(lexem.toUpperCase(), TokenType.HOLD);
				}
			);
	private final static Evaluator BOOLEAN_OPERATOR_EVALUATOR = 
			new Evaluator(
				"^((a|A)(n|N)(d|D))|((o|O)(r|R))$",
				lexem->{
					return new Token(lexem.toUpperCase(), TokenType.BOOLEAN_OPERATOR);
				}
			);
	private final static Evaluator OPERATOR_EVALUATOR = 
			new Evaluator(
				"^=|(!=)|<|>$",
				lexem->{
					return new Token(lexem, TokenType.OPERATOR);
				}
			);
	private final static Evaluator SEPERATOR_EVALUATOR = 
			new Evaluator(
				"^\\,|\\:|\\[|\\]|\\(|\\)$",
				lexem->{
					List<Token> seperators=
						List.of(
							new Token(",", TokenType.COMMA),
							new Token(":", TokenType.COLON),
							new Token(",", TokenType.COMMA),
							new Token("[", TokenType.LEFTHOOK),
							new Token("]", TokenType.RIGHTHOOK),
							new Token("(", TokenType.LEFTBRACKET),
							new Token(")", TokenType.RIGHTBRACKET)
						);
					return seperators.stream()
							.filter(seperator->seperator.tokenLexem().equals(lexem))
							.findAny()
							.orElseThrow();
				}
			);
	private final static Evaluator IDENTIFIER_EVALUATOR = 
			new Evaluator(
				"^([a-zA-Z]|_)(([a-zA-Z]|_)|[0-9])*$",
				lexem->{
					return new Token(lexem, TokenType.IDENTIFIER);
				}
			);
	private final static Evaluator TOKEN_T_EVALUATOR = 
			new Evaluator(
				"^[0-9]*|(\".*\")|\\*$",
				lexem->{
					return new Token(lexem, TokenType.TOKEN_T);
				}
			);
	private final static Evaluator EOQ_EVALUATOR = 
			new Evaluator(
				"^;$",
				lexem->{
					return new Token(lexem, TokenType.EOQ);
				}
			);
	private final static Evaluator UNRECOGNIZED_EVALUATOR = 
			new Evaluator(
				"^$",
				lexem->{
					return new Token(lexem, TokenType.UNRECOGNIZED);
				}
			);
	
	
	private final static List<Evaluator> evaluators = 
			List.of(
					KEYWORD_SELECT_EVALUATOR, 
					KEYWORD_FROM_EVALUATOR, 
					KEYWORD_WHERE_EVALUATOR,
					TOKEN_T_EVALUATOR,
					HOLD_EVALUATOR,
					OPERATOR_EVALUATOR,
					BOOLEAN_OPERATOR_EVALUATOR,
					SEPERATOR_EVALUATOR,
					EOQ_EVALUATOR
			);
	
	//factory
	public static Evaluator of(String lexem) {
		try {
			return evaluators.stream()
					.filter(evaluator->lexem.matches(evaluator.lexem_pattern))
					.findAny()
					.orElseThrow();
		} catch (NoSuchElementException e) {
			if(lexem.matches(IDENTIFIER_EVALUATOR.lexem_pattern))
				return IDENTIFIER_EVALUATOR;
			
			return UNRECOGNIZED_EVALUATOR;
		}
	}
}
