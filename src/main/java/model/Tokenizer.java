package model;

import java.util.ArrayList;

public class Tokenizer {
	public ArrayList<Token> tokenize(String input){
		if(input==null) return null;
		System.out.println("tokenize()");
		ArrayList<Token> al = new ArrayList<Token>();
		// TODO сделать метод add()
		String[] ab = input.split("\\s|,|\\(|\\)|\\[|\\]|\\}|\\{|:|\\\"");
		for (String s : ab)
			if(s!=null && !s.isEmpty()){
				if(s.length()==1)
					al.add(new Token(s, s.equals(".") ? Tag.DOT : Tag.WORD));
				else{
					if(s.charAt(s.length()-1)=='.'){
						al.add(new Token(s.substring(0, s.length()-2), Tag.WORD));
						al.add(new Token(".", Tag.DOT));
					}else
						al.add(new Token(s, Tag.WORD));
				}
			}
		return al;
	}
}
