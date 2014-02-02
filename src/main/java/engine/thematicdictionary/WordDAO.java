package engine.thematicdictionary;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;

public class WordDAO implements Iterable<String[]>, Serializable{
	private static final long serialVersionUID = 1L;

	private final String name;
	private final HashMap<String, Double> dic;

	public WordDAO(String name) {
		this.name=name;
		dic = new HashMap<String, Double>();
	}

	/**
	 * Возвращает вероятность того, что слово относится к предметной
	 * области, слова которой описаны в данном словаре.
	 * Если слова в словаре нет, то возвращает 0.
	 * @param word
	 * @return
	 */
	public double getProbability(String word) {
		Double d = dic.get(word);
		if(d==null) return 0.0;
		return d;
	}

	public void add(String string, double probability) {
		checkProbability(probability);
		dic.put(string, probability);
	}
	
	public static void checkProbability(double probability){
		if (probability>1.0 || probability<0.0) throw new IllegalArgumentException("Вероятность может быть только 0.0...1.0");
	}

	public void delete(String word) {
		dic.remove(word);
	}
	
	public int getSize() {
		return dic.size();
	}
	
	public String getName() {
		return name;
	}	
	
	@Override
	public String toString() {
		return name;
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
}