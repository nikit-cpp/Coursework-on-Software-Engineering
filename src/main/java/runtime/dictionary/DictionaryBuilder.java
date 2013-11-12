package runtime.dictionary;

import java.util.ArrayList;

import normalizer.Normalizer;
import lexer.Token;
import lexer.Tokenizer;

public class DictionaryBuilder {
	
	private Tokenizer tokenizer = new Tokenizer();
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
			for(String stem : normalizer.normalize(word)){
				sentence.addWord(word, stem);
			}
		}
		
		return sentence;
	}
}
