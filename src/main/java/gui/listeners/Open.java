package gui.listeners;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

// http://www.java2s.com/Code/Java/SWT-JFace-Eclipse/FileDialogExample.htm

/**
 * Показывает диалог открытия файла и если пользователь выбрал файл, вызывает
 * {@code viewProxy.openFile()}
 * 
 * @author Ник
 * 
 */
public class Open extends OpenFileDialog implements SelectionListener {
	public void widgetSelected(SelectionEvent event) {
		createDialog();
		fd.setText("Открыть");
		openDialog();

		if (checkPath(selected))
			view.openFile(selected);
	}

	public void widgetDefaultSelected(SelectionEvent event) {
	}
}