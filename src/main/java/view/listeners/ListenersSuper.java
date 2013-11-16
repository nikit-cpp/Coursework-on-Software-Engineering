package view.listeners;

import options.Options;

import org.eclipse.swt.widgets.Shell;

import view.ViewProxy;

abstract class ListenersSuper {
	static Shell shell;
	static ViewProxy viewProxy;
	static Options options;
	
	public static void staticInit(Shell _shell, ViewProxy _viewProxy){
		shell=_shell;
		viewProxy=_viewProxy;
		options=Options.getInstance();
	}
	
	boolean checkPath(String path){
		if(path==null || path.equals(""))
			return false;
		return true;
	}
}
