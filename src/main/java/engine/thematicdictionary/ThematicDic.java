package engine.thematicdictionary;

import java.io.Serializable;
import java.util.Iterator;

import engine.Rowable;
import engine.thematicdictionary.hibernate.DAO.ProbabilityDAO;
import engine.thematicdictionary.hibernate.DAO.WordDAO;

public final class ThematicDic implements Rowable, Iterable<String[]>, Serializable{
	private static final long serialVersionUID = 1L;
	private boolean isEnabled;
	private final String name;
	private double calculatedProbability;
	private final WordDAO wordDAO;
	private final ProbabilityDAO probabilityDAO;
	//private final 
	private final long rubricId;
	
	public ThematicDic(String dicName, boolean isEnabled) {
		this.name=dicName;
		this.isEnabled=isEnabled;
		wordDAO = new WordDAO(dicName);
		probabilityDAO = new ProbabilityDAO();
		rubricId = -1;
	}

	public String getDicName() {
		return name;
	}

	@Override
	public String toString() {
		return name;
	}
	
	/**
	 * Добавляет в эту рубрику слово и вероятность
	 * @param word
	 * @param probability
	 */
	public void addWord(String word, double probability) {
		checkProbabilityBounds(probability);
		long wordId = wordDAO.put(word);
		probabilityDAO.put(wordId, probability, rubricId);
	}

	public void deleteWord(String word) {
		wordDAO.remove(word);
	}

	/**
	 * Возвращает вероятность того, что слово относится к предметной
	 * области, слова которой описаны в данном словаре.
	 * Если слова в словаре нет, то возвращает 0.
	 * @param word
	 * @return
	 */
	public double getProbability4Word(String word){
		Double d = wordDAO.get(word);
		if(d==null) return 0.0;
		return d;
	}
	
	public boolean getDicEnabled() {
		return isEnabled;
	}

	public void setDicEnabled(boolean isEnabled) {
		this.isEnabled=isEnabled;
		//System.out.println(toString() + " "+isEnabled);
	}

	public static void checkProbabilityBounds(double probability){
		if (probability>1.0 || probability<0.0) throw new IllegalArgumentException("Вероятность может быть только 0.0...1.0");
	}

	public int getSize() {
		return wordDAO.size();
	}
	
	public void setCalculatedProbability(double p){
		this.calculatedProbability=p;
	}
	
	public String getCalculatedProbabilityString(){
		if(isEnabled)
			return String.valueOf(calculatedProbability);
		return "";
	}
	
	/**
	 * Возвращает массив строк : [Название словаря, Вычисленная вероятность]
	 */
	public String[] getRow() {
		String dicName = toString();
        String probabilitty = getCalculatedProbabilityString();
        String[] row = {dicName, probabilitty};
		
		return row;
	}

	/**
	 * Итератор по массивам слов, относящимся к данному словарю, которые выглядят так:
	 * [Слово, Вероятность]
	 * Возвращает массив строк, которые образуют 1 строку таблицы "Содержащиеся с словаре слова"
	 */
	@Override
	public Iterator<String[]> iterator() {
		return new Iterator<String[]>() {
			Iterator<String> keyIterator = wordDAO.getIterator();
			public boolean hasNext() {
				return keyIterator.hasNext();
			}

			public String[] next() {
				String key = keyIterator.next();
				String[] row = {key, String.valueOf(wordDAO.get(key))};
				return row;
			}

			public void remove() { // He реализован
				throw new UnsupportedOperationException();
			}
		};
	}	
}
