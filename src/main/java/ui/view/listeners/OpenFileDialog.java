package ui.view.listeners;

import options.Options;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

import ui.view.ViewProxy;

abstract public class OpenFileDialog {
	static Shell shell;
	static ViewProxy viewProxy;
	static Options options;
	FileDialog fd;
	String selected;

	public static void staticInit(Shell _shell, ViewProxy _viewProxy) {
		shell = _shell;
		viewProxy = _viewProxy;
		options = Options.getInstance();
	}

	boolean checkPath(String path) {
		if (path == null || path.equals(""))
			return false;
		return true;
	}

	void createDialog() {
		fd = new FileDialog(shell, SWT.OPEN);
	}

	void openDialog() {
		String[] filterExt = { "*.txt", "*.*" };
		fd.setFilterExtensions(filterExt);
		selected = fd.open();
	}
}
