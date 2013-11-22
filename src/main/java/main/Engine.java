package main;

import java.util.ArrayList;
import java.util.Collection;

import runtime.dictionary.DictionaryBuilder;
import runtime.dictionary.DictionaryContainer;
import runtime.dictionary.WordInfo;
import thematic.dictionary.ThematicDic;
import thematic.dictionary.ThematicDicManager;

public class Engine {
	private DictionaryBuilder builder;
	private ThematicDicManager tdm;
	//private Collection<WordInfo> words;
	//private Collection<WordInfo> stems;
	private DictionaryContainer container;
	/**
	 * Конструктор
	 */
	public Engine() {
		builder = new DictionaryBuilder();
		tdm = new ThematicDicManager();
	}
	
	public void rubricate(String text){
		container = builder.buildSentence(text);
		Collection<WordInfo> stems = container.getStems();
		for(ThematicDic dic: tdm.getThematicDicts()){
			double unitP = calcProbabilityforDic(dic, stems);
			dic.setProbability(unitP);
		}
	}
	
	public Collection<WordInfo> getStems(){
		return container.getStems();
	}
	
	public Collection<WordInfo> getWords(){
		return container.getWords();
	}
	
	/**
	 * Вычисляет вероятность того, что данный текст относится к рубрике, представленной словарём.
	 * @param dic - Тематический словарь, представляющий данную рубрику
	 * @param stems - стемы(начальные формы)
	 * @return вероятность
	 */
	public double calcProbabilityforDic(ThematicDic dic, Collection<WordInfo> stems) {
		double p = 0;
		int size = 0;
		
		for(WordInfo word : stems){
			String s = word.getString();
			int count = word.getCount();
			p += (dic.getProbability(s) * count);
			
			size+=count;
		}
		p /= size;
		
		return p;
	}

	public void turnThematicDictionary(boolean b, int index){
		//System.out.println("turnThematicDictionary(): turning "+index+" to "+b);
		tdm.turn(b, index);
	}
	
	public String referate(String string){
		return string;
	}

	public ArrayList<ThematicDic> getThematicDicts() {
		return tdm.getThematicDicts();
	}
}
