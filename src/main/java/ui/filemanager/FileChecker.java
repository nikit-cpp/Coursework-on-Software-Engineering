package ui.filemanager;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

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
					f.createNewFile();

				    DataOutputStream out2 = new DataOutputStream((new FileOutputStream(absPath)));
				    /* Буферами читать файл быстрее, но нужно решить проблему когда в буфер считывается < байт чем его длина -
					 * тогда в нём на оставшихся местах остаётся старая информация, за счёто чего в файл записывается
					 * новая вместе со старой - файл повреждается.
				    byte[] buf = new byte[1024];
				    while ((in.read(buf)) > 0) {
				    	out2.write(buf);
				    	out2.flush();
				    }*/
				    // побайтово гораздо медленнее, чем буферами
				    int b;
				    while((b = in.read())!=-1)
				    {
				    	out2.writeByte(b);
				    	out2.flush();
				    }
					out2.close();
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
