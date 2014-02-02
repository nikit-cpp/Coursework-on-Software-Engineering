package engine.thematicdictionary;

import java.io.Serializable;
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
	
	public RubricView(String name, boolean isEnabled) {
		super(name, isEnabled);
	}
	
	public void setProbability(double p){
		this.probability=p;
	}
	
	public String getProbabilityString(){
		if(super.getEnabled())
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
			Iterator<String> keyIterator = rubric.keySet().iterator();
			public boolean hasNext() {
				return keyIterator.hasNext();
			}

			public String[] next() {
				String key = keyIterator.next();
				String[] row = {key, String.valueOf(rubric.get(key))};
				return row;
			}

			public void remove() { // He реализован
				throw new UnsupportedOperationException();
			}
		};
	}

	public void setEnabled(boolean b) {
		// TODO Auto-generated method stub
		
	}
}
