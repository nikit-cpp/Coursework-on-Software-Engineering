package ui.view;

import java.util.ArrayList;

import main.Engine;
import options.OptId;
import options.Options;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import thematicdictionary.ThematicDic;
import ui.filemanager.FileReader;
import ui.view.listeners.OpenFileDialog;
import foundedwords.WordInfo;

public class ThematicDictionariesManager extends View1 {
	private Text txtInput;
	private Table tableWords;
	private Text txtOutput;
	private Table tableThematicDicts;
	private Table tableContainsWords;
	private Shell shell;
	private static ArrayList<Updateable> upds = new ArrayList<Updateable>();
	
	private Engine engine;
		
	/**
	 * Этот корнструктор используется вместе с ThematicDictionaries
	 * @param w
	 */
	public ThematicDictionariesManager(ThematicDictionaries w) {
		this.tableThematicDicts=w.tableDicts;
		this.tableContainsWords=w.tableWords;
		
		initialize();
	}

	/**
	 *  Общая инициализация
	 */
	private void initialize(){
		engine = Engine.getInstance();
		createThematicDicTable();
	}
	
	/**
	 * Удаляет старую и создаёт таблицу словарей на основе их списка,
	 * полученного с помощью engine.getThematicDicts()
	 */
	protected void createThematicDicTable() {
	    tableThematicDicts.removeAll();
		//clearTable(tableThematicDicts);
		
	    int i=0;
	    for (ThematicDic dic : engine.getThematicDicts()) {
	    	WrappedTableItem wti = new WrappedTableItem(tableThematicDicts, SWT.NONE);
	    	wti.arrListPos=i;
	        
	        wti.setText(dic.getRow());
	        
	        wti.setChecked(dic.getEnabled());
	        i++;
	    }
	}
	
	/**
	 * В(ы)ключает словари в соответствии с таблицей.
	 */
	public void msgTurnDicts(){
		for(TableItem tableItem : tableThematicDicts.getItems()){
			WrappedTableItem w = (WrappedTableItem) tableItem;
			engine.turnThematicDictionary(tableItem.getChecked(), w.arrListPos);
		}
	}
	
	public void createContainsWordsTable(int selectedIndex) {
		tableContainsWords.removeAll();
		
		// Получаем табличную строку из словаря ThematicDic с номером selectedIndex в цикле,
		// т. к. он реализует интерфейс Iterable<String[]>
		for(String[] row : engine.getThematicDicts().get(selectedIndex)){
			TableItem tableItem = new TableItem(tableContainsWords, SWT.NONE);
	        tableItem.setText(row);
		}
	}
	
	public void deleteWord(String word, int dicIndex) {
		final String dic = tableThematicDicts.getItem(dicIndex).getText();
		System.out.println("удаляем "+dic+"("+dicIndex+"): "+word);
		engine.getTDM().deleteWord(word, dicIndex);
	}
}
