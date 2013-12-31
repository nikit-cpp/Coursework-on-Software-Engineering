package gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

public class ThematicDictsWindowManager extends ViewSuper /*implements Updateable*/ {
	private final Table tableContainsWords;
			
	/**
	 * Этот корнструктор используется вместе с ThematicDictionaries
	 * @param w
	 */
	ThematicDictsWindowManager(ThematicDictsWindow w) {
		super();

		this.tableThematicDicts=w.tableDicts;
		this.tableContainsWords=w.tableWords;
		
		super.createThematicDicTable(tableThematicDicts);
	}
		
	private void createContainsWordsTable(int selectedIndex) {
		tableContainsWords.removeAll();
		
		// Получаем табличную строку из словаря ThematicDic с номером selectedIndex в цикле,
		// т. к. он реализует интерфейс Iterable<String[]>
		for(String[] row : engine.getThematicDicts().get(selectedIndex)){
			TableItem tableItem = new TableItem(tableContainsWords, SWT.NONE);
	        tableItem.setText(row);
		}
	}
	
	void deleteWord(String word, int dicIndex) {
		final String dic = tableThematicDicts.getItem(dicIndex).getText();
		System.out.println("удаляем "+dic+"("+dicIndex+"): "+word);
		engine.getTDM().deleteWord(word, dicIndex);
	}

	@Override
	public void updateContainingWordsImpl() {
		try{
			createContainsWordsTable(tableThematicDicts.getSelectionIndex());
		}catch(ArrayIndexOutOfBoundsException e){
		}
	}

	@Override
	public void updateThematicDictsTableImpl() {
		super.createThematicDicTable(tableThematicDicts);
	}

	void deleteDic(int dicIndex) {
		engine.getTDM().deleteDic(dicIndex);
	}
}
