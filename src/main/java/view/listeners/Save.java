package view.listeners;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.FileDialog;

//http://www.java2s.com/Code/Java/SWT-JFace-Eclipse/FileDialogExample.htm

public class Save extends ListenersSuper implements SelectionListener {
    public void widgetSelected(SelectionEvent event) {
      FileDialog fd = new FileDialog(shell, SWT.SAVE);
      fd.setText("Сохранить");
      //fd.setFilterPath("C:/");
      String[] filterExt = { "*.txt", "*.*" };
      fd.setFilterExtensions(filterExt);
      String selected = fd.open();
      
      if(checkPath(selected))
    	  viewProxy.saveFile(selected);
    }

    public void widgetDefaultSelected(SelectionEvent event) {
    }
}