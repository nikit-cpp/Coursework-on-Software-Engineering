package view;

import main.EngineFacade;
import org.eclipse.swt.widgets.*;

public class ViewProxy{
	private Text text;
	private Table table;
	private EngineFacade model = new EngineFacade();
	
	public ViewProxy(MainWindow w) {
		this.text=w.txtSplitPublicString;
		this.table=w.table;

		// Инициализация окна
		table.removeAll();
	}

	public void msgRubricate() {				
		
	}
}
