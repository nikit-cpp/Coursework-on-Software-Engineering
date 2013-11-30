package ui.view.listeners;

import options.Options;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

import ui.view.MainWindowManager;

abstract public class OpenFileDialog {
	protected static Shell shell;
	static MainWindowManager view;
	static Options options;
	FileDialog fd;
	String selected;

	public static void staticInit(Shell _shell, MainWindowManager mainWindowManager) {
		shell = _shell;
		view = mainWindowManager;
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
