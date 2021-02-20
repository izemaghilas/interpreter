package custom_sql_parser.ast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Select {
	private List<Select> select_t;
	private Project project_t;
	private String holds;
	private String type;
	
	private int numberOfTabs;
	
	public Select() {
		this.select_t=new ArrayList<>();
		this.project_t=null;
		this.holds=null;
		this.type=null;//conjunction or disjunction
		this.numberOfTabs=2;
	}
	
	public void setProject_t(Project project_t) {
		this.project_t = project_t;
	}

	public void setHolds(String holds) {
		this.holds = holds;
	}
	
	public void setType(String type, Select select) {
		this.type = type;
		addNewSelectToList(select);
	}
	private void addNewSelectToList(Select select) {
		this.appendSelectToList(select);
		
		this.project_t=null;
		this.holds=null;
	}
	
	public void appendSelectToList(Select select_t) {
		this.select_t.add(select_t);
		select_t.numberOfTabs+=1;
	}
	
	@Override
	public String toString() {
		if(this.select_t.size()==1)
			return "";
		
		if(this.select_t.size()>1)
			return beautifulComplexSelect();
		return beautifulSelect();
	}
	
	private String beautifulComplexSelect() {
		String tabsForSelect=buildTabsString(numberOfTabs);
		String tabsForSelectType=buildTabsString(numberOfTabs+1);
		
		String selectString=tabsForSelect+"\"select\": {\n"
				+tabsForSelectType+"\""+type+"\": [\n";
		
		for(Select select: select_t) {
			select.numberOfTabs=numberOfTabs+2;
			selectString+=select.toString()
					.replace("\"select\": ", "")
					+",\n";
		}
		selectString = selectString.substring(0, selectString.lastIndexOf(','));
		
		return selectString+="\n"
				+tabsForSelectType
				+"]\n"
				+tabsForSelect+"}";
	}
	private String beautifulSelect() {
		String projectString=project_t.selectToString(numberOfTabs);
		projectString=projectString.replace("\"project\": ", "\"select\": ");
		
		String tabsForBraces=buildTabsString(numberOfTabs);
		String tabsForHolds=buildTabsString(numberOfTabs+1);
		
		String holdsString="\"holds\": \""+holds+"\"";
		int lastNewLineIndex=projectString.lastIndexOf('\n');
		String selectString = projectString.substring(0, lastNewLineIndex)+",\n"+
				tabsForHolds+
				holdsString+
				"\n"+tabsForBraces+"}";
		
		return selectString;
	}
	
	private String buildTabsString(int numberOfTabs) {
		return String.join("", Collections.nCopies(numberOfTabs, "\t"));
	}
	
}
