package model;

import java.util.ArrayList;

public class Tokenizer {
	/**
	 * Разбивает строку на токены
	 * @param input
	 * @return
	 */
	public ArrayList<Token> tokenize(String input){
		if(input==null) return null;
		ArrayList<Token> retArr = new ArrayList<Token>();
		// TODO сделать метод add()
		String[] ab = input.split("\\s|,|\\(|\\)|\\[|\\]|\\}|\\{|:|\\\"");
		for (String s : ab)
			if(s!=null && !s.isEmpty()){
				if(s.length()==1)
					retArr.add(new Token(s, s.equals(".") ? Tag.DOT : Tag.WORD));
				else{
					char dot = s.charAt(s.length()-1);
					if(dot=='.' || dot=='?' || dot=='!'){
						retArr.add(new Token(s.substring(0, s.length()-1), Tag.WORD));
						retArr.add(new Token(String.valueOf(dot), Tag.DOT));
					}else
						retArr.add(new Token(s, Tag.WORD));
				}
			}
		return retArr;
	}
}
