package engine;

import java.util.Collection;
import java.util.List;

import engine.foundedwords.Builder;
import engine.foundedwords.WordInfo;
import engine.foundedwords.WordsMap;
import engine.thematicdictionary.ThematicDicManager;
import entities.Rubric;

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
		tdm = ThematicDicManager.getInstance();
	}
	
	public void rubricate(String text){
		container = builder.buildMap(text);
		Collection<WordInfo> stems = container.getStems();
		for(Rubric dic: tdm.getAllDicts()){
			tdm.calcProbabilityforDic(dic, stems);
		}
	}
	
	public Collection<WordInfo> getStems(){
		return container.getStems();
	}
	
	public Collection<WordInfo> getWords(){
		return container.getWords();
	}
	
	public void turnThematicDictionary(boolean b, int index){
		//System.out.println("turnThematicDictionary(): turning "+index+" to "+b);
		tdm.turn(b, index);
	}
	
	public String referate(String string){
		return string;
	}

	public List<Rubric> getThematicDicts() {
		return tdm.getAllDicts();
	}
	
	public ThematicDicManager getTDM(){
		return this.tdm;
	}
}
