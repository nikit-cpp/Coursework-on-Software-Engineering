package model;
import java.io.File;
import java.util.ArrayList;

import com.atlascopco.hunspell.Hunspell;

public class Model {
	private Tokenizer tokenizer;
	private Normalizer normalizer;
	private Hunspell hunspell;
	
	/**
	 * Конструктор
	 */
	public Model() {
		tokenizer = new Tokenizer();

		final String dicPath = new File("resources/ru_RU.dic").getAbsolutePath();
		final String affPath = new File("resources/ru_RU.aff").getAbsolutePath();
		
		hunspell = new Hunspell(dicPath, affPath);
		
		normalizer = new Normalizer(hunspell);
	}
	
	/**
	 * Разбивает строку на токены
	 * @param input
	 * @return
	 */
	public ArrayList<Token> tokenize(String input){
		return tokenizer.tokenize(input);
	}
	
	/**
	 * Возвращает список корней(стемов) для данной строки
	 * @param inArray - входной список слов, обёрнутых в токены 
	 * @return
	 */
	public ArrayList<Token> normalize(ArrayList<Token> inArray){
		ArrayList<Token> retArray = new ArrayList<Token>();
		for(Token t: inArray){
			retArray.addAll(normalizer.normalize(t));
		}
		return retArray;
	}
	
	/**
	 * Возвращает список корней(стемов) для данного токена-слова
	 * @param inArray - входной список слов, обёрнутых в токены 
	 * @return
	 */
	public ArrayList<Token> normalize(Token inArray){
		ArrayList<Token> retArray = new ArrayList<Token>();
		Token t = inArray;
		retArray.addAll(normalizer.normalize(t));
		return retArray;
	}
	
}
