package gui;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Table;

import engine.Engine;
import engine.thematicdictionary.ThematicDicWrapper;

public abstract class ViewSuper implements Updateable {
	protected static ArrayList<Updateable> upds = new ArrayList<Updateable>();
	protected Engine engine;
	protected Table tableThematicDicts;
	
	/**
	 *  Общая инициализация
	 */
	public ViewSuper(){
		engine = Engine.getInstance();
		addToUpdateable();
	}
	
	/**
	 * Удаляет старую и создаёт таблицу словарей на основе их списка,
	 * полученного с помощью engine.getThematicDicts()
	 */
	protected void createThematicDicTable(Table tableThematicDicts) {
	    tableThematicDicts.removeAll();
		
	    int i=0;
	    for (ThematicDicWrapper dic : engine.getThematicDicts()) {
	    	WrappedTableItem wti = new WrappedTableItem(tableThematicDicts, SWT.NONE);
	    	wti.arrListPos=i;
	        
	        wti.setText(dic.getRow());
	        
	        wti.setChecked(dic.getEnabled());
	        i++;
	    }
	}
	
	/**
	 * Добавляет наследников(ибо абстрактный) в список обновляемых(оповещаемых)
	 */
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
	public static void updateContainingWords(){
		for(Updateable u : upds){
			u.updateContainingWordsImpl();
		}
	}

	public static void updateThematicDictsTable() {
		for(Updateable u : upds){
			u.updateThematicDictsTableImpl();
		}
	}
}
