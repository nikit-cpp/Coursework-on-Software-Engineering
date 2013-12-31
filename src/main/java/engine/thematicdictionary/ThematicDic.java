package engine.thematicdictionary;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;

import engine.Rowable;

public final class ThematicDic implements Rowable, Iterable<String[]>, Serializable{
	private static final long serialVersionUID = 1L;
	private boolean isEnabled;
	private final String name;
	private final HashMap<String, Double> dic;
	private double probability;
	
	public ThematicDic(String name, boolean isEnabled) {
		this.name=name;
		this.isEnabled=isEnabled;
		dic = new HashMap<String, Double>();
	}

	@Override
	public String toString() {
		return name;
	}
	/**
	 * Возвращает вероятность того, что слово относится к предметной
	 * области, слова которой описаны в данном словаре.
	 * Если слова в словаре нет, то возвращает 0.
	 * @param word
	 * @return
	 */
	public double getProbability(String word){
		Double d = dic.get(word);
		if(d==null) return 0.0;
		return d;
	}
	
	public boolean getEnabled() {
		return isEnabled;
	}

	public void setEnabled(boolean isEnabled) {
		this.isEnabled=isEnabled;
		//System.out.println(toString() + " "+isEnabled);
	}

	public void add(String string, double probability) {
		checkProbability(probability);
		dic.put(string, probability);
	}
	
	public void checkProbability(double probability){
		if (probability>1.0 || probability<0.0) throw new IllegalArgumentException("Вероятность может быть только 0.0...1.0");
	}

	public int getSize() {
		return dic.size();
	}
	
	public void setProbability(double p){
		this.probability=p;
	}
	
	public String getProbabilityString(){
		if(isEnabled)
			return String.valueOf(probability);
		return "";
	}
	
	public String[] getRow() {
		String dicName = toString();
        String probabilitty = getProbabilityString();
        String[] row = {dicName, probabilitty};
		
		return row;
	}

	/**
	 * Возвращает массив строк, которые образуют 1 строку таблицы
	 */
	@Override
	public Iterator<String[]> iterator() {
		return new Iterator<String[]>() {
			Iterator<String> keyIterator = dic.keySet().iterator();
			public boolean hasNext() {
				return keyIterator.hasNext();
			}

			public String[] next() {
				String key = keyIterator.next();
				String[] row = {key, String.valueOf(dic.get(key))};
				return row;
			}

			public void remove() { // He реализован
				throw new UnsupportedOperationException();
			}
		};
	}

	public String getName() {
		return name;
	}

	public void delete(String word) {
		dic.remove(word);
	}	
}
