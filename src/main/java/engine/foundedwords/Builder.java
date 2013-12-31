package engine.foundedwords;

import java.util.ArrayList;
import java.util.List;

import engine.lexer.Lexer;
import engine.lexer.Token;
import engine.normalizer.Normalizer;

public class Builder {
	
	private Lexer tokenizer = new Lexer();
	private Normalizer normalizer = new Normalizer();
	
	/**
	 * Строит словарь слово:стем (m:n)
	 */
	public WordsMap buildSentence(String in){
		WordsMap sentence = new WordsMap();
		
		// Разбиваем строку на токены
		ArrayList<Token> tokens = tokenizer.tokenize(in);
		
		int i = 0;
		for(Token t : tokens){
			String word = t.value;
			List<String> stems = normalizer.normalize(word);
			if(stems.size()==0){ // Если не нашли стемов, то добавляем само слово как стем
				sentence.addSingleStem(word, i);
			}else{
				for(String stem : stems){ // Иначе добавляем все стемы
					sentence.addWord(word, stem, i);
				}
			}
			
			i++;
		}
		
		return sentence;
	}
}
