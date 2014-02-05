package engine.thematicdictionary;

import java.io.Serializable;
import java.util.Iterator;

import engine.Rowable;
import engine.thematicdictionary.hibernate.DAO.WordDAO;

public final class ThematicDic implements Rowable, Iterable<String[]>, Serializable{
	private static final long serialVersionUID = 1L;
	private boolean isEnabled;
	private final String name;
	private double probability;
	private final WordDAO dao;
	
	public ThematicDic(String name, boolean isEnabled) {
		this.name=name;
		this.isEnabled=isEnabled;
		dao = new WordDAO(name);
	}

	public String getDicName() {
		return name;
	}

	@Override
	public String toString() {
		return name;
	}
	public void addWord(String string, double probability) {
		checkProbabilityBounds(probability);
		dao.put(string, probability);
	}

	public void deleteWord(String word) {
		dao.remove(word);
	}

	/**
	 * Возвращает вероятность того, что слово относится к предметной
	 * области, слова которой описаны в данном словаре.
	 * Если слова в словаре нет, то возвращает 0.
	 * @param word
	 * @return
	 */
	public double getProbability4Word(String word){
		Double d = dao.get(word);
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
		return dao.size();
	}
	
	public void setCalculatedProbability(double p){
		this.probability=p;
	}
	
	public String getCalculatedProbabilityString(){
		if(isEnabled)
			return String.valueOf(probability);
		return "";
	}
	
	/**
	 * Возвращает строку с Названием словаря и Вычисленной вероятностью
	 */
	public String[] getRow() {
		String dicName = toString();
        String probabilitty = getCalculatedProbabilityString();
        String[] row = {dicName, probabilitty};
		
		return row;
	}

	/**
	 * Возвращает массив строк, которые образуют 1 строку таблицы
	 */
	@Override
	public Iterator<String[]> iterator() {
		return new Iterator<String[]>() {
			Iterator<String> keyIterator = dao.getIterator();
			public boolean hasNext() {
				return keyIterator.hasNext();
			}

			public String[] next() {
				String key = keyIterator.next();
				String[] row = {key, String.valueOf(dao.get(key))};
				return row;
			}

			public void remove() { // He реализован
				throw new UnsupportedOperationException();
			}
		};
	}	
}
