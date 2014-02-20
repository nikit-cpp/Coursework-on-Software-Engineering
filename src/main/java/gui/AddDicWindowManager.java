package gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import engine.thematicdictionary.ThematicDicManager;

public class AddDicWindowManager extends ViewSuper {
	private final Text name;
	private final Button btnAdd;
	private Shell shell;
	
	AddDicWindowManager(AddDicWindow addDicWindow) {
		this.name=addDicWindow.textAddableDic;
		btnAdd=addDicWindow.btnAdd;
		this.shell = addDicWindow.shell;
	}

	void addDic() {
		final String dicname = name.getText();
		System.out.println("добавляем словарь "+dicname);
		
		try {
			engine.getTDM().addDic(dicname, false);
		} catch (Exception e) {
			super.showErrorWindow(shell, e);
		}
	}

	@Override
	public void updateContainingWordsImpl() {		
	}

	@Override
	public void updateThematicDictsTableImpl() {
	}

	void changeEnabledAddButton() {
		if(!name.getText().isEmpty())
			btnAdd.setEnabled(true);
		else
			btnAdd.setEnabled(false);
	}

}
