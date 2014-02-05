package engine.thematicdictionary;

import java.util.HashMap;
import java.util.Iterator;

import engine.Rowable;

/**
 * Обёртывает WordDAO, добавляя различные поля и методы(отображаемая вероятность).
 * @author Ник
 *
 */
public final class RubricView implements Rowable, Iterable<String[]>{
	private static final long serialVersionUID = 1L;
	private double probability;
	private final HashMap<String, Double> rubrics;
	
	public RubricView(String name, boolean isEnabled) {
		rubrics = new HashMap<String, Double>();
	}
	
	public void setProbability(double p){
		this.probability=p;
	}
	
	public String getProbabilityString(){
		if(getEnabled())
			return String.valueOf(probability);
		return "";
	}
	

	public String[] getRow() {
		String dicName = toString();
        String probabilitty = getProbabilityString();
        String[] row = {dicName, probabilitty};
		
		return row;
	}
	
	public void setEnabled(boolean b) {
		// TODO Auto-generated method stub
		
	}
	
	public boolean getEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	
	/**
	 * Возвращает массив строк, которые образуют 1 строку таблицы
	 */
	@Override
	public Iterator<String[]> iterator() {
		return new Iterator<String[]>() {
			Iterator<String> keyIterator = rubrics.keySet().iterator();
			public boolean hasNext() {
				return keyIterator.hasNext();
			}

			public String[] next() {
				String key = keyIterator.next();
				String[] row = {key, String.valueOf(rubrics.get(key))};
				return row;
			}

			public void remove() { // He реализован
				throw new UnsupportedOperationException();
			}
		};
	}
}
