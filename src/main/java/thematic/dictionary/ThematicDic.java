package thematic.dictionary;

import java.util.HashMap;

import main.Selectable;

public class ThematicDic implements Selectable{
	private boolean isEnabled;
	private final String name;
	private HashMap<String, Double> dic;
	
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
	
	@Override
	public boolean getEnabled() {
		return isEnabled;
	}

	@Override
	public void setEnabled(boolean isEnabled) {
		this.isEnabled=isEnabled;
		System.out.println(toString() + " "+isEnabled);
	}

	public void add(String string, double probability) {
		if (probability>1.0 || probability<0.0) throw new IllegalArgumentException("Вероятность может быть только 0.0...1.0");
		dic.put(string, probability);
	}

	public int getSize() {
		return dic.size();
	}
}
