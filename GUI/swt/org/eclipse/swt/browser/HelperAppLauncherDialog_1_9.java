/*******************************************************************************
 * Copyright (c) 2003, 2013 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.browser;

import org.eclipse.swt.*;
import org.eclipse.swt.internal.C;
import org.eclipse.swt.internal.mozilla.*;
import org.eclipse.swt.widgets.*;

/**
 * This class implements the nsIHelperAppLauncherDialog interface for mozilla
 * versions 1.9.x.x.  For other mozilla versions this interface is implemented
 * by class HelperAppLauncherDialog(_10).  HelperAppLauncherDialogFactory
 * determines at runtime which of these classes to instantiate. 
 */
class HelperAppLauncherDialog_1_9 {
	XPCOMObject supports;
	XPCOMObject helperAppLauncherDialog;
	int refCount = 0;

HelperAppLauncherDialog_1_9 () {
	createCOMInterfaces ();
}

int AddRef () {
	refCount++;
	return refCount;
}

void createCOMInterfaces () {
	/* Create each of the interfaces that this object implements */
	supports = new XPCOMObject (new int[] {2, 0, 0}) {
		@Override
		public long /*int*/ method0 (long /*int*/[] args) {return QueryInterface (args[0], args[1]);}
		@Override
		public long /*int*/ method1 (long /*int*/[] args) {return AddRef ();}
		@Override
		public long /*int*/ method2 (long /*int*/[] args) {return Release ();}
	};
	
	helperAppLauncherDialog = new XPCOMObject (new int[] {2, 0, 0, 3, 6}) {
		@Override
		public long /*int*/ method0 (long /*int*/[] args) {return QueryInterface (args[0], args[1]);}
		@Override
		public long /*int*/ method1 (long /*int*/[] args) {return AddRef ();}
		@Override
		public long /*int*/ method2 (long /*int*/[] args) {return Release ();}
		@Override
		public long /*int*/ method3 (long /*int*/[] args) {return Show (args[0], args[1], (int)/*64*/args[2]);}
		@Override
		public long /*int*/ method4 (long /*int*/[] args) {return PromptForSaveToFile (args[0], args[1], args[2], args[3], (int)/*64*/args[4], args[5]);}
	};		
}

void disposeCOMInterfaces () {
	if (supports != null) {
		supports.dispose ();
		supports = null;
	}	
	if (helperAppLauncherDialog != null) {
		helperAppLauncherDialog.dispose ();
		helperAppLauncherDialog = null;	
	}
}

long /*int*/ getAddress () {
	return helperAppLauncherDialog.getAddress ();
}

int QueryInterface (long /*int*/ riid, long /*int*/ ppvObject) {
	if (riid == 0 || ppvObject == 0) return XPCOM.NS_ERROR_NO_INTERFACE;
	nsID guid = new nsID ();
	XPCOM.memmove (guid, riid, nsID.sizeof);

	if (guid.Equals (IIDStore.GetIID (nsISupports.class))) {
		XPCOM.memmove (ppvObject, new long /*int*/[] {supports.getAddress ()}, C.PTR_SIZEOF);
		AddRef ();
		return XPCOM.NS_OK;
	}
	if (guid.Equals (XPCOM.NS_IHELPERAPPLAUNCHERDIALOG_1_9_IID)) {
		XPCOM.memmove (ppvObject, new long /*int*/[] {helperAppLauncherDialog.getAddress ()}, C.PTR_SIZEOF);
		AddRef ();
		return XPCOM.NS_OK;
	}

	XPCOM.memmove (ppvObject, new long /*int*/[] {0}, C.PTR_SIZEOF);
	return XPCOM.NS_ERROR_NO_INTERFACE;
}
        	
int Release () {
	refCount--;
	/*
	* Note.  This instance lives as long as the download it is bound to.
	* Its reference count is expected to go down to 0 when the download
	* has completed or when it has been cancelled. E.g. when the user
	* cancels the File Dialog, cancels or closes the Download Dialog
	* and when the Download Dialog goes away after the download is completed.
	*/
	if (refCount == 0) disposeCOMInterfaces ();
	return refCount;
}

/* nsIHelperAppLauncherDialog */

int Show (long /*int*/ aLauncher, long /*int*/ aContext, int aReason) {
	nsIHelperAppLauncher_1_8 helperAppLauncher = new nsIHelperAppLauncher_1_8 (aLauncher);
	return helperAppLauncher.SaveToDisk (0, 0);
}

int PromptForSaveToFile (long /*int*/ aLauncher, long /*int*/ aWindowContext, long /*int*/ aDefaultFileName, long /*int*/ aSuggestedFileExtension, int aForcePrompt, long /*int*/ _retval) {
	int length = XPCOM.strlen_PRUnichar (aDefaultFileName);
	char[] dest = new char[length];
	XPCOM.memmove (dest, aDefaultFileName, length * 2);
	String defaultFile = new String (dest);

	length = XPCOM.strlen_PRUnichar (aSuggestedFileExtension);
	dest = new char[length];
	XPCOM.memmove (dest, aSuggestedFileExtension, length * 2);
	String suggestedFileExtension = new String (dest);

	Shell shell = new Shell ();
	FileDialog fileDialog = new FileDialog (shell, SWT.SAVE);
	fileDialog.setFileName (defaultFile);
	fileDialog.setFilterExtensions (new String[] {suggestedFileExtension});
	String name = fileDialog.open ();
	shell.close ();
	if (name == null) {
		nsIHelperAppLauncher_1_8 launcher = new nsIHelperAppLauncher_1_8 (aLauncher);
		int rc = launcher.Cancel (XPCOM.NS_BINDING_ABORTED);
		if (rc != XPCOM.NS_OK) Mozilla.error (rc);
		return XPCOM.NS_ERROR_FAILURE;
	}
	nsEmbedString path = new nsEmbedString (name);
	long /*int*/[] result = new long /*int*/[1];
	int rc = XPCOM.NS_NewLocalFile (path.getAddress (), 1, result);
	path.dispose ();
	if (rc != XPCOM.NS_OK) Mozilla.error (rc);
	if (result[0] == 0) Mozilla.error (XPCOM.NS_ERROR_NULL_POINTER);
	/* Our own nsIDownload has been registered during the Browser initialization. It will be invoked by Mozilla. */
	XPCOM.memmove (_retval, result, C.PTR_SIZEOF);	
	return XPCOM.NS_OK;
}
}
