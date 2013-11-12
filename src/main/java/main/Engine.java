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
	/**
	 * Конструктор
	 */
	public Engine() {
		builder = new DictionaryBuilder();
		tdm = new ThematicDicManager();
	}
	
	public Collection<WordInfo> rubricate(String text){
		DictionaryContainer container = builder.buildSentence(text);
		Collection<WordInfo> stems = container.getStems();
		
		for(ThematicDic dic: tdm.getThematicDicts()){
			double unitP = calcProbabilityforDic(dic, stems); // TODO придумать, куда записывать полученную вероятность
		}
		
		return stems;
	}
	
	/**
	 * Вычисляет вероятность того, что данный текст относится к рубрике, представленной словарём.
	 * @param dic - Тематический словарь, представляющий данную рубрику
	 * @param stems - стемы(начальные формы)
	 * @return
	 * TODO Исправить эту ф-ю, чтобы проходила тест СalcProbabilityforDicTest.test2
	 */
	public double calcProbabilityforDic(ThematicDic dic, Collection<WordInfo> stems) {
		double p = 0;
		for(WordInfo word : stems){
			String s = word.getString();
			int count = word.getCount();
			p += (dic.getProbability(s) * count);
		}
		p /= stems.size();
		
		return p;
	}

	public void turnThematicDictionaries(){
		tdm.turn();
	}
	
	public String referate(String string){
		return string;
	}

	public ArrayList<ThematicDic> getThematicDicts() {
		return tdm.getThematicDicts();
	}
}
