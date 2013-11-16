package runtime.dictionary;

import java.util.ArrayList;
import java.util.List;

import normalizer.Normalizer;
import lexer.Token;
import lexer.Lexer;

public class DictionaryBuilder {
	
	private Lexer tokenizer = new Lexer();
	private Normalizer normalizer = new Normalizer();
	
	/**
	 * Строит словарь слово:стем (m:n)
	 */
	public DictionaryContainer buildSentence(String in){
		DictionaryContainer sentence = new DictionaryContainer();
		
		// Разбиваем строку на токены
		ArrayList<Token> tokens = tokenizer.tokenize(in);
		
		for(Token t : tokens){
			String word = t.value;
			List<String> stems = normalizer.normalize(word);
			if(stems.size()==0){
				sentence.addSingleStem(word);
			}else{
				for(String stem : stems){
					sentence.addWord(word, stem);
				}
			}
		}
		
		return sentence;
	}
}
