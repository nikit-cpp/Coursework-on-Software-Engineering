package model;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import com.atlascopco.hunspell.Hunspell;

public class Normalizer {
	private Hunspell hunspell;
	
	public Normalizer(Hunspell hunspell) {
		this.hunspell=hunspell;
	}

	/**
	 * Возвращает список корней(стемов) для данного слова
	 * @param t
	 * @return
	 */
	public ArrayList<Token> normalize(Token t){
		String word = t.value;
		word = convertFromUTF8ToSystemDefault(word);
		
		List<String> stemList = hunspell.stem(word);
		ArrayList<Token> retStemArr = new ArrayList<Token>();
		for(String s : stemList){
			s = convertFromSystemDefaultToUTF8(s);
			retStemArr.add(new Token(s));
		}
		return retStemArr;
	}
	
	/**
	 * Костыль для зависимости hunspell от умолчальной кодировки 1251 под виндой
	 */
	public static String convertFromUTF8ToSystemDefault(String in){
		if(Charset.defaultCharset().equals(Charset.forName("UTF-8")))
			return in;
		return new String(in.getBytes(Charset.forName("UTF-8")), Charset.defaultCharset());
	}
	
	/**
	 * Костыль для зависимости hunspell от умолчальной кодировки 1251 под виндой
	 */
	public static String convertFromSystemDefaultToUTF8(String in){
		if(Charset.defaultCharset().equals(Charset.forName("UTF-8")))
			return in;
		return new String(in.getBytes(Charset.defaultCharset()), Charset.forName("UTF-8"));
	}
}
