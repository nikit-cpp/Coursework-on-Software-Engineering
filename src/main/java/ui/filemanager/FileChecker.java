package ui.filemanager;

import java.io.File;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;

import ui.view.listeners.OpenFileDialog;

public class FileChecker extends OpenFileDialog{
	public static String getCheckedExistsAbsolutePath(String path){
		final File checkableFile = new File(path);
		final String absPath = checkableFile.getAbsolutePath();
		if(!checkableFile.exists()){
			// This will hold the style to pass to the MessageBox constructor
	        int style = 0;
	        style |= SWT.ICON_ERROR;
	        style |= SWT.OK;
			// Display the message box
	        MessageBox mb = new MessageBox(shell, style);
	        mb.setText("Опаньки");
	        mb.setMessage("Не найден словарь "+path+" ("+absPath+")");
	        mb.open();
		}
		return absPath;	
	}
}
