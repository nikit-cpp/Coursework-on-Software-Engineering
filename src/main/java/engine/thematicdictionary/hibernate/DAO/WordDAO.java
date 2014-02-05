package engine.thematicdictionary.hibernate.DAO;

import java.util.HashMap;
import java.util.Iterator;

/**
 * Data Access Object для части таблицы "Слова", относящейся к определённому тематическому словарю
 * @author Ник
 *
 */
public class WordDAO {
	
	private HashMap<String, Double> dic;

	/**
	 * Добавляет / извлекает строку в / из таблицу "Рубрики" с названнием  тематического словаря
	 * @param name название словаря, используемое для выборки относящихся к нему слов
	 */
	public WordDAO(String name) {
		// TODO Auto-generated constructor stub
		dic = new HashMap<String, Double>();
	}

	/**
	 * Возвращает вероятность для заданного слова. Если слова нет, возвращает null.
	 * @param word слово
	 * @return вероятность
	 */
	public Double get(String word) {
		// TODO Auto-generated method stub
		return null;
	}

	public void put(String string, double probability) {
		// TODO Auto-generated method stub
		
	}

	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	public Iterator<String> getIterator() {
		// TODO Auto-generated method stub
		return dic.keySet().iterator();
	}

	public void remove(String word) {
		// TODO Auto-generated method stub
		
	}

}
