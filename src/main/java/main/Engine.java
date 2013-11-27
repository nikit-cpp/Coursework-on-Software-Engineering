package main;

import java.util.ArrayList;
import java.util.Collection;

import foundedwords.Builder;
import foundedwords.WordInfo;
import foundedwords.WordsMap;
import thematicdictionary.ThematicDic;
import thematicdictionary.ThematicDicManager;

public class Engine {
	private Builder builder;
	private ThematicDicManager tdm;
	//private Collection<WordInfo> words;
	//private Collection<WordInfo> stems;
	private WordsMap container;
	
	// Синглтон
    private static Engine instance;
 
    public static Engine getInstance()
    {
        if (instance == null)
        {
            instance = new Engine();
        }
        return instance;
    }
	
	/**
	 * Конструктор
	 */
	private Engine() {
		builder = new Builder();
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
