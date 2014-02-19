package engine.thematicdictionary;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;

import entities.Rubric;

class ThematicDicList {
	private boolean isTracked = true;
	private List<Rubric> thematicDicts = new ArrayList<Rubric>();
	Criteria crit;
	
	void setListNotTracked() {
		isTracked = false;
	}
	
	public List<Rubric> getAllDicts() {
		if(!isTracked){
			refresh();
			isTracked = true;
		}
		return thematicDicts;
	}	
	
	/**
	 * Перед вызовом метода в наследующем классе д. б. проинициализирован критерий
	 */
	void refresh(){
		thematicDicts.clear();
		thematicDicts.addAll(crit.list());
	}

}
