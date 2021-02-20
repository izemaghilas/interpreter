package custom_sql_parser.ast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Project {
	private List<Project> project_t;
	private Range range;
	private Predicate predicate;
	private boolean isList;
	private final int numberOfTabs=2;
	
	public Project() {
		this.project_t=new ArrayList<>();
		this.range=null;
		this.predicate=null;
		this.isList=false;
	}

	public void setRange(Range range) {
		this.range = range;
	}

	public void setPredicate(Predicate predicate) {
		this.predicate = predicate;
	}
	
	public void projectIsList() {
		isList=true;
		range=null;
		predicate=null;
	}
	
	public void appendToProjects(Project project_t) {
		this.project_t.add(project_t);
	}
	
	public String selectToString(int number_of_tabs) {
		return beautifulProject(number_of_tabs, number_of_tabs+1);
	}
	
	@Override
	public String toString() {
		if(isList)
			return beautifulProjectIsList(this.numberOfTabs+1);
		
		return beautifulProject(this.numberOfTabs, this.numberOfTabs+1);
		
	}
	
	private String beautifulProjectIsList(int numberOfTabsProject) {
		String tabsForKey=buildTabsString(this.numberOfTabs);
		String projectString=tabsForKey+"\"project\": [\n";
		
		if(project_t.size()==0)
			return projectString+"]";
		
		for(int i=0; i<project_t.size(); i++) {
			Project project=project_t.get(i);
			if(i==project_t.size()-1) {
				projectString+=
						project.beautifulProject(
								numberOfTabsProject, 
								numberOfTabsProject+1
							).replace("\"project\": ", "");
				continue;
			}
			projectString+=
					project.beautifulProject(
							numberOfTabsProject, 
							numberOfTabsProject+1).replace("\"project\": ", "")
					+",\n";
		}
		return projectString+"\n"+tabsForKey+"]";
	}
	
	private String beautifulProject(int numberOfTabsProject, int numberOfTabsRange) {
		
		String tabsForBraces = buildTabsString(numberOfTabsProject);
		String tabsForRangeAndPredicate=buildTabsString(numberOfTabsProject+1);
		
		String range_str="";
		String predicate_str="";
		if(range != null)
			range_str=beautifulRange(range.toString(), numberOfTabsRange+1);
		if(predicate != null)
			predicate_str=predicate.toString();
		
		String project=tabsForBraces+"\"project\": {\n"
					+ (range_str.equals("")?"":tabsForRangeAndPredicate+range_str+",\n")
					+ (predicate_str.equals("")?"":tabsForRangeAndPredicate+predicate_str)
				+"\n"+tabsForBraces+"}";
		
		return project;
	}
	
	private String beautifulRange(String range_str, int numberOfTabs) {
		String str = range_str;
		
		String tabsForMinMax = buildTabsString(numberOfTabs);
		String tabsForEndingBrace = buildTabsString(numberOfTabs-1);
		
		str=str.replace("\t", tabsForMinMax);
		str=str.replace("\n}", "\n"+tabsForEndingBrace+"}");
		
		return str;
	}
	
	private String buildTabsString(int numberOfTabs) {
		return String.join("", Collections.nCopies(numberOfTabs, "\t"));
	}
	
}
