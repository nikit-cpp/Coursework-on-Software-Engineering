package runtime.dictionary;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

/**
 * Контейнер для 2-х словарей/множеств_уникальных_элементов с поддержкой подсчёта количества слов и их стемов.
 * TODO Реализовать с помощью БД.
 * @author Ник
 *
 */
public class DictionaryContainer {
	private HashMap<String, WordInfo> words = new HashMap<String, WordInfo>();
	private HashMap<String, WordInfo> stems = new HashMap<String, WordInfo>();
	
	/**
	 * Добавляет слова в данный контейнер, увеличивая счётчик.
	 * @param original
	 * @param stem
	 */
	public void addWord(String original, String stem){
		addWord(original, words, stem);
		addWord(stem, stems, original);
	}
	
	public void addSingleStem(String stem) {
		addWord(stem, stems, null);
	}
	
	private void addWord(String what, HashMap<String, WordInfo> map, String related){
		WordInfo wordInfo = map.get(what);
		if (wordInfo==null){ // добавляем слово, которого нет - счётчик=1
			wordInfo=new WordInfo(what); // count==1	
		}else{ // добавляем слово, которое уже есть - инкрементируем счётчик
			map.remove(what);
			wordInfo.incrementCount();
		}
		wordInfo.addRelated(related);
		
		map.put(what, wordInfo);
	}
	
	public Collection<WordInfo> getWords() {
		return words.values();
	}
	
	public Collection<WordInfo> getStems() {
		return stems.values();
	}

	public void print(){
		print("words", words);
		print("stems", stems);
	}
	
	private void print(String what, HashMap<String, WordInfo> whatMap) {
		System.out.println(what+": ");
		Iterator<Entry<String, WordInfo>> it = whatMap.entrySet().iterator();
		if (it.hasNext()==false) System.out.println("no ");
		while (it.hasNext()) {
			Entry<String, WordInfo> li = it.next();
			//System.out.print("" + li.getKey() + " ");
			li.getValue().print();
		}
		System.out.println("end\n");
	}
}
