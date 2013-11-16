package lexer;

import java.util.ArrayList;

/**
 * Разбивает строку на токены.
 * TODO Реализовать с помощью antlr.
 */
public class Lexer {
	
	public Lexer(){}
	
	public ArrayList<Token> tokenize(String input){
		if(input==null) return null;
		ArrayList<Token> retArr = new ArrayList<Token>();
			
		String regex = //"\\s|,|\\(|\\)|\\[|\\]|\\}|\\{|:|\\\"";
				"[^абвгдеёжзийклмнопрстуфхцчшщъыьэюяАБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯabcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ]";
		String[] splitted = input.split(regex);
		
		for (String s : splitted){
			if(s!=null && !s.isEmpty()){
				/*if(s.length()==1)
					retArr.add(new Token(s, s.equals(".") ? Tag.DOT : Tag.WORD));
				else{
					char dot = s.charAt(s.length()-1);
					if(dot=='.' || dot=='?' || dot=='!'){
						retArr.add(new Token(s.substring(0, s.length()-1), Tag.WORD));
						retArr.add(new Token(String.valueOf(dot), Tag.DOT));
					}else
						retArr.add(new Token(s, Tag.WORD));
				}*/
				retArr.add(new Token(s));
			}
		}
		return retArr;
	}
}
