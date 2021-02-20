package custom_sql_parser.parser_components;

import custom_sql_parser.ast.Predicate;
import custom_sql_parser.ast.Project;
import custom_sql_parser.ast.Query;
import custom_sql_parser.ast.Range;
import custom_sql_parser.ast.Select;

/**
 * Parser
 * CFG (Context Free Grammar)
 * recursive-descent parser (top-down)
 * 
 *	QUERY: KEYWORD_SELECT (PROJECTION)+ KEYWORD_FROM TABLE_NAME 
 * 			( (KEYWORD_WHERE SELECTION EOQ)|EOQ )
 *	
 *	PROJECTION: PROJECT ','?
 *	PROJECT: COLUMN_NAME RANGE? PREDICATE? 'NONE'
 *	RANGE: ('[' (TOKEN_T ':' TOKEN_T?)|(':' TOKEN_T) ']')
 *	PREDICATE: OPERATOR TOKEN_T
 *	OPERATOR: '='|'!='|'<'|'>'
 *	TOKEN_T: any kind of number or string or '*'
 *	TABLE_NAME: valid table name
 *	COLUMN_NAME: valid column name	
 *	//TABLE_NAME and COLUMN_NAME considered as IDENTIFIERS
 *
 *	SELECTION: SELECT_DISJUNCTION //if KEYWORD_WHERE check SELECTION
 *	SELECT_DISJUNCTION: SELECT_CONJUNCTION ('OR' SELECT_CONJUNCTION)*
 *	SELECT_CONJUNCTION: SELECT ('AND' SELECT)*
 *	SELECT: HOLD PROJECT | '(' SELECTION ')'
 *	HOLD: 'ALL'|'ANY'
 *	EOQ: ';'  
 */

public enum Parser {
	INSTANCE;
	
	private Token currentToken;
	private Query query_t;
	private static String query_ast="";
	
	public String parse() {
		try {
			currentToken = Lexer.INSTANCE.nextToken();
			this.query_t=new Query();
			
			this.query();
			query_ast = query_t.toString();
		} catch (ParserException e) {
			System.out.println(e.getMessage());
			query_ast = "";
		}
		
		return query_ast;
	}
	
	private void query() throws ParserException {
		this.eat(TokenType.KEYWORD_select);
		
		this.projection();
		
		this.eat(TokenType.KEYWORD_from);
		
		query_t.setTable(currentToken.tokenLexem());
		
		this.eat(TokenType.IDENTIFIER);//TABLE_NAME
		
		if(currentToken.tokenType().equals(TokenType.KEYWORD_where)) {
			eat(TokenType.KEYWORD_where);
			query_t.setSelect(selection()); 
		}
		this.eat(TokenType.EOQ);
	}

	private void projection() throws ParserException {
		Project project_t = this.project();
		
		if(currentToken.tokenType().equals(TokenType.COMMA)) {
			query_t.projectIsList();
			query_t.appendToProjects(project_t);
		}
		else
			query_t.setProjectT(project_t);
			
		while(currentToken.tokenType().equals(TokenType.COMMA)) {
			eat(TokenType.COMMA);
			query_t.appendToProjects(this.project());
		}
			
	}
	private Project project() throws ParserException {
		Project project_t=new Project();
		Predicate predicate_t=new Predicate(currentToken.tokenLexem());
		
		this.eat(TokenType.IDENTIFIER);//COLUMN_NAME
		
		
		if(currentToken.tokenType().equals(TokenType.LEFTHOOK)) {
			Range range_t = this.range();
			project_t.setRange(range_t);
		}
			
		if(currentToken.tokenType().equals(TokenType.OPERATOR)) {
			predicate(predicate_t);
			
		}
		
		project_t.setPredicate(predicate_t);
		return project_t;
			
	}
	private Range range() throws ParserException {
		String min = "", max="";
		
		this.eat(TokenType.LEFTHOOK);
		if(currentToken.tokenType().equals(TokenType.TOKEN_T)) {
			min = currentToken.tokenLexem();
			eat(TokenType.TOKEN_T);
			this.eat(TokenType.COLON);
			if(currentToken.tokenType().equals(TokenType.TOKEN_T)) {
				max=currentToken.tokenLexem();
				eat(TokenType.TOKEN_T);
			}	
		}
		else {
			this.eat(TokenType.COLON);
			max=currentToken.tokenLexem();
			eat(TokenType.TOKEN_T);
		}
		
		this.eat(TokenType.RIGHTHOOK);
		
		return new Range(min, max);
	}
	private void predicate(Predicate predicate_t) throws ParserException {
		operator(predicate_t);
		predicate_t.setToken_t(currentToken.tokenLexem());
		eat(TokenType.TOKEN_T);
	}
	private void operator(Predicate predicate_t) throws ParserException {
		predicate_t.setOperator(currentToken.tokenLexem());
		this.eat(TokenType.OPERATOR);
	}
	
	private Select selection() throws ParserException {
		return select_disjunction();
	}
	

	private Select select_disjunction() throws ParserException {
		Select select_disjunction = null;
		Select select_conjunction = select_conjunction();
		
		if(currentToken.tokenType().equals(TokenType.BOOLEAN_OPERATOR)
				&&
				currentToken.tokenLexem().equals("OR")
			) {
			select_disjunction=new Select();
			select_disjunction.setType("select_disjunction", select_conjunction);
		}
		
		while(currentToken.tokenType().equals(TokenType.BOOLEAN_OPERATOR)
				&&
				currentToken.tokenLexem().equals("OR")
			) {
			eat(TokenType.BOOLEAN_OPERATOR);
			select_disjunction.appendSelectToList(select_conjunction());
		}
		return select_disjunction!=null?select_disjunction:select_conjunction;
	}

	private Select select_conjunction() throws ParserException {
		Select select=select();
		Select select_conjunction=null;
		if(currentToken.tokenType().equals(TokenType.BOOLEAN_OPERATOR)
				&& 
				currentToken.tokenLexem().equals("AND")
			) {
			select_conjunction=new Select();
			select_conjunction.setType("select_conjunction", select);
		}
		
		while(currentToken.tokenType().equals(TokenType.BOOLEAN_OPERATOR)
				&& 
				currentToken.tokenLexem().equals("AND")
			) {
			
			eat(TokenType.BOOLEAN_OPERATOR);
			select_conjunction.appendSelectToList(select());
		}
		return select_conjunction!=null?select_conjunction:select;
	}

	private Select select() throws ParserException {
		if(currentToken.tokenType().equals(TokenType.HOLD)) {
			Select select = new Select();
			select.setHolds(currentToken.tokenLexem());
			eat(TokenType.HOLD);
			
			
			Project project_t=project();
			select.setProject_t(project_t);
			
			
			return select;
		}
		else {
			
			eat(TokenType.LEFTBRACKET);
			Select select_t = selection();
			eat(TokenType.RIGHTBRACKET);
			
			return select_t;
		}
	}

	private void eat(TokenType tokentype) throws ParserException{
		if(tokentype.equals(currentToken.tokenType()))
			currentToken=Lexer.INSTANCE.nextToken();
		else
			this.error(tokentype);
	}
	private void error(TokenType tokentype) throws ParserException {
		throw new ParserException("Expect : "+tokentype+" Found : "+currentToken.tokenLexem());
	}
	
	
	
}
