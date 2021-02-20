package custom_sql_parser.ast;

public class Predicate {
	private String column_name;
	private String operator;
	private String token_t;
	
	public Predicate(String column_name) {
		this.column_name = column_name;
		this.operator="";
		this.token_t="";
	}

	public void setColumn_name(String column_name) {
		this.column_name = column_name;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public void setToken_t(String token_t) {
		this.token_t = token_t;
	}

	@Override
	public String toString() {
		return "\"predicate\": "
				+ "\""+column_name
				+(operator.length()==0 || token_t.length()==0?"\""
						:" "+operator+" "+token_t+"\"");
	}
	
}
