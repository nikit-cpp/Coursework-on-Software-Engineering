package ui.view;

import java.util.ArrayList;

public abstract class ViewSuper implements Updateable {
	protected static ArrayList<Updateable> upds = new ArrayList<Updateable>();
	
	protected void addToUpdateable(){
		System.out.println("addToUpdateable "+this);
		upds.add(this);
	}
	
	protected void delFromUpdateable(){
		System.out.println("delFromUpdateable "+this);
		upds.remove(this);
	}
	
	/**
	 * Обновляет список содержащихся в словаре слов
	 * */
	public static void updateContainingWordsSuper(){
		for(Updateable u : upds){
			u.updateContainingWords();
		}
	}
}
