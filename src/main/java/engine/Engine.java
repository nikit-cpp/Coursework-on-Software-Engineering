package engine;

import java.util.ArrayList;
import java.util.Collection;

import engine.foundedwords.Builder;
import engine.foundedwords.WordInfo;
import engine.foundedwords.WordsMap;
import engine.hibernate.entities.Rubric;
import engine.thematicdictionary.RubricView;
import engine.thematicdictionary.Rubricator;

public final class Engine {
	private final Builder builder;
	private final Rubricator rubricator;
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
		rubricator = new Rubricator();
	}
	
	public void rubricate(String text){
		container = builder.buildMap(text);
		Collection<WordInfo> stems = container.getStems();
		for(Rubric rubric: rubricator.getAllRubrics()){
			double unitP = calcProbabilityforDic(rubric, stems);
			rubric.setProbability(unitP);
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
	 * @param rubric - Тематический словарь, представляющий данную рубрику
	 * @param stems - стемы(начальные формы)
	 * @return вероятность
	 */
	public double calcProbabilityforDic(Rubric rubric, Collection<WordInfo> stems) {
		double p = 0;
		int size = 0;
		
		for(WordInfo word : stems){
			String s = word.getString();
			int count = word.getCount();
			p += (rubricator.getProbability(rubric, s) * count);
			
			size+=count;
		}
		p /= size;
		
		return p;
	}

	public void turnThematicDictionary(boolean b, int index){
		//System.out.println("turnThematicDictionary(): turning "+index+" to "+b);
		rubricator.turn(b, index);
	}
	
	public String referate(String string){
		return string;
	}

	public ArrayList<RubricView> getAllRubrics() {
		return rubricator.getAllRubrics();
	}
	
	public Rubricator getRubricator(){
		return this.rubricator;
	}
}
