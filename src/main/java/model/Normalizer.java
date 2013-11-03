package model;

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
		final String word = t.value;
		List<String> stemList = hunspell.stem(word);
		ArrayList<Token> retStemArr = new ArrayList<Token>();
		//System.out.println("Printing stem for \""+ word + "\"; founded " +stemList.size()+" elements:");
		for(String s : stemList){
			//System.out.println(s);
			retStemArr.add(new Token(s));
		}
		return retStemArr;
	}
}
