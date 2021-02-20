package custom_sql_parser.ast;

public class Range {
	private String min;
	private String max;
	
	public Range(String min, String max) {
		this.min=min;
		this.max=max;
	}

	@Override
	public String toString() {
		return "\"range\": {\n"
				+ "\t\"min\": "+min +",\n"
				+ "\t\"max\": "+max
				+ "\n}";
	}
	
	
}
