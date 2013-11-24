package ui.filemanager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import options.OptId;
import options.Options;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;

import ui.view.listeners.OpenFileDialog;

public class FileChecker extends OpenFileDialog{
	public static String getCheckedExistsAbsolutePath(String relPath, Object obj){
		final File checkableFile = new File(relPath);
		final String absPath = checkableFile.getAbsolutePath();
		if(!checkableFile.exists()){
			// This will hold the style to pass to the MessageBox constructor
	        int style = 0;
	        style |= SWT.ICON_ERROR;
	        //style |= SWT.OK;
	        style |= SWT.YES | SWT.NO;
			// Display the message box
	        MessageBox mb = new MessageBox(shell, style);
	        mb.setText("Опаньки");
	        mb.setMessage("Не найден словарь "+relPath+" ("+absPath+").\nРаспаковать словарь из jar по требуему пути?");
	        int val = mb.open();
	        if(val==SWT.YES){
	        	InputStream in = obj.getClass().getResourceAsStream("/"+relPath);
				try {
					File f = new File(absPath);
					f.getParentFile().mkdirs(); 
					//f.createNewFile();
					Files.createFile(Paths.get(absPath));

					byte[] buf = new byte[1024];
				    int len;
				    Path p = Paths.get(absPath);
				    while ((len = in.read(buf)) > 0) {
				    	OpenOption o = /*StandardOpenOption.CREATE |*/ StandardOpenOption.APPEND;
				        Files.write(p, buf, o);
				    }
					
			    } catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
	        }
		}
		return absPath;	
	}
}
