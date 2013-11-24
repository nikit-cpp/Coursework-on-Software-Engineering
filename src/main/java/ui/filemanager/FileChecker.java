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
	        	// При чтении ресурса из jar в начале обязательно д. б. слэш 
	        	InputStream in = obj.getClass().getResourceAsStream("/"+relPath);
				try {
					File f = new File(absPath);
					f.getParentFile().mkdirs(); 
					f.createNewFile();

				    DataOutputStream out = new DataOutputStream((new FileOutputStream(absPath)));
				    /* Буферами читать файл быстрее */
				    byte[] buf = new byte[1024];
				    int len;
				    while ((len=in.read(buf)) > 0) {
				    	out.write(buf, 0, len);
				    	out.flush();
				    }
				    /* побайтово гораздо медленнее, чем буферами
				    int b;
				    while((b = in.read())!=-1)
				    {
				    	out2.writeByte(b);
				    	out2.flush();
				    }*/
					out.close();
					in.close();
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
