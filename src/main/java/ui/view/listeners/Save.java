package ui.view.listeners;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

//http://www.java2s.com/Code/Java/SWT-JFace-Eclipse/FileDialogExample.htm

/**
 * Показывает диалог сохранения файла и если пользователь выбрал файл, вызывает
 * {@code viewProxy.saveFile()}
 * 
 * @author Ник
 * 
 */
public class Save extends OpenFileDialog implements SelectionListener {
	public void widgetSelected(SelectionEvent event) {
		createDialog();
		fd.setText("Сохранить");
		openDialog();

		if (checkPath(selected))
			viewProxy.saveFile(selected);
	}

	public void widgetDefaultSelected(SelectionEvent event) {
	}
}