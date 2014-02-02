package gui;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Text;

import engine.thematicdictionary.Rubricator;

public class AddDicWindowManager extends ViewSuper {
	private final Text name;
	private final Button btnAdd;
	
	AddDicWindowManager(AddDicWindow addDicWindow) {
		this.name=addDicWindow.textAddableDic;
		btnAdd=addDicWindow.btnAdd;
	}

	void addDic() {
		final String dicname = name.getText();
		System.out.println("добавляем словарь "+dicname);
		
		engine.getRubricator().addRubric(dicname, false);
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
