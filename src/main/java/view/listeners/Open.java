package view.listeners;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.FileDialog;

// http://www.java2s.com/Code/Java/SWT-JFace-Eclipse/FileDialogExample.htm

public class Open extends ListenersSuper implements SelectionListener {
    public void widgetSelected(SelectionEvent event) {
      FileDialog fd = new FileDialog(shell, SWT.OPEN);
      fd.setText("Открыть");
      //fd.setFilterPath("C:/");
      String[] filterExt = { "*.txt", "*.*" };
      fd.setFilterExtensions(filterExt);
      String selected = fd.open();
      if(checkPath(selected))
    	  viewProxy.openFile(selected);
    }

    public void widgetDefaultSelected(SelectionEvent event) {
    }
  }