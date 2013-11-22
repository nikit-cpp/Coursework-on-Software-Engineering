package ui.view;

import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

/**
 * Хранит изначальную позицию в ArrayList для корректного доступа 
 * к элементам ArrayList после сортировки.
 * @author Ник
 *
 */
public class WrappedTableItem extends TableItem{
	public WrappedTableItem(Table parent, int style) {
		super(parent, style);
		
	}
	
	public WrappedTableItem(Table parent, int style, int index) {
		super(parent, style, index);
		
	}
	
	protected void checkSubclass(){}
	
	int arrListPos;
}