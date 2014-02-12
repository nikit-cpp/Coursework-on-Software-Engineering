package engine;

import java.util.ArrayList;
import java.util.Collection;

import engine.foundedwords.Builder;
import engine.foundedwords.WordInfo;
import engine.foundedwords.WordsMap;
import engine.thematicdictionary.Rubric;
import engine.thematicdictionary.ThematicDicManager;

public final class Engine {
	private final Builder builder;
	private final ThematicDicManager tdm;
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
		container = builder.buildMap(text);
		Collection<WordInfo> stems = container.getStems();
		for(Rubric dic: tdm.getAllDicts()){
			double unitP = calcProbabilityforDic(dic, stems);
			dic.setCalculatedProbability(unitP);
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
	public double calcProbabilityforDic(Rubric dic, Collection<WordInfo> stems) {
		double p = 0;
		int size = 0;
		
		for(WordInfo word : stems){
			String s = word.getString();
			int count = word.getCount();
			p += (dic.getProbability4Word(s) * count);
			
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

	public ArrayList<Rubric> getThematicDicts() {
		return tdm.getAllDicts();
	}
	
	public ThematicDicManager getTDM(){
		return this.tdm;
	}
}
