package custom_sql_parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import custom_sql_parser.parser_components.Lexer;
import custom_sql_parser.parser_components.Parser;

public enum Console {
	INSTANCE;
	
	private final Scanner scanner = new Scanner(System.in);
	private List<String> buffer;
	private final String label = "query > ";
	private final String secondLabel = "      > ";
	
	public void openConsole() {
		buffer = new ArrayList<>();
		while(true) {
			System.out.print(label);
			String userInput = scanner.nextLine();
			processUserInput(userInput);
			buffer.clear();
		}
	}
	
	private void processUserInput(String userInput) {
		userInput = userInput.trim();
		
		if(userInput.equals("exit"))
			closeConsole();
		
		if(!userInput.equals("")) {
			buffer.add(userInput);
			
			if(!userInput.contains(";"))
				loopUntilEOQ(); //EOQ : End Of Query ";"
			
			String query = String.join("\s", buffer);
			Lexer.INSTANCE.init(query);
			System.out.println(Parser.INSTANCE.parse());
		}
	}
	
	private void loopUntilEOQ() {
		while(true) {
			System.out.print(secondLabel);
			String userInput = scanner.nextLine();
			userInput = userInput.trim();
			buffer.add(userInput);
			
			if(userInput.contains(";"))
				break;
			
			if(userInput.equals("exit"))
				closeConsole();
		}
	}

	private void closeConsole() {
		scanner.close();
		System.exit(0);
	}
}
