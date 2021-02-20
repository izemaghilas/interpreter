package custom_sql_parser.ast;


public class Query {
	private Project project_t;
	private Select select_t;
	private String table;
	
	public Query() {
		this.project_t=new Project();
		this.select_t=null;
		this.table="";
	}

	public void setProjectT(Project project_t) {
		this.project_t=project_t;
	}
	
	public void setTable(String table) {
		this.table = table;
	}
	
	public void projectIsList() {
		project_t.projectIsList();
	}
	public void appendToProjects(Project project_t){
		this.project_t.appendToProjects(project_t);
	}
	public void setSelect(Select select_t) {
		this.select_t = select_t;
	}

	@Override
	public String toString() {		
		return "{\n"
				+ "\t\"query\": {\n"
				+ project_t.toString()+",\n"
				+ (select_t!=null?select_t.toString()+",\n":"")
				+ "\t\t\"table\": "+"\""+table+"\""
				+ "\n\t}"
				+ "\n}";
	}
	
}
