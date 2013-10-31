package model;
import java.util.ArrayList;


public class Model {
	private Tokenizer tokenizer;
	public Model(){
		tokenizer = new Tokenizer();
	}
	public ArrayList<Token> tokenize(String input){
		return tokenizer.tokenize(input);
	}
	
	public void normalize(Token[] ta){
		
	}
	
}
