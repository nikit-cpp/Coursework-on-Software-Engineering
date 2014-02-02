package engine.thematicdictionary;

import java.io.Serializable;
import java.util.Iterator;

import engine.Rowable;

/**
 * Обёртывает WordDAO, добавляя различные поля и методы(включение/выключение, отображаемая вероятность).
 * @author Ник
 *
 */
public final class ThematicDicWrapper extends WordDAO implements Rowable, Serializable{
	private static final long serialVersionUID = 1L;
	private boolean isEnabled;
	private double probability;
	
	public ThematicDicWrapper(String name, boolean isEnabled) {
		super(name);
		this.isEnabled=isEnabled;
	}

	public boolean getEnabled() {
		return isEnabled;
	}

	public void setEnabled(boolean isEnabled) {
		this.isEnabled=isEnabled;
		//System.out.println(toString() + " "+isEnabled);
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
}
