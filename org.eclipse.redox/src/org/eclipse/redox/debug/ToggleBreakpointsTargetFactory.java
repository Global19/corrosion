/*********************************************************************
 * Copyright (c) 2018 Red Hat Inc. and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Lucas Bullen (Red Hat Inc.) - Initial implementation
 *******************************************************************************/
package org.eclipse.redox.debug;

import java.util.Collections;
import java.util.Set;

import org.eclipse.debug.ui.actions.IToggleBreakpointsTarget;
import org.eclipse.debug.ui.actions.IToggleBreakpointsTargetFactory;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.redox.RedoxPlugin;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.texteditor.ITextEditor;

public class ToggleBreakpointsTargetFactory implements IToggleBreakpointsTargetFactory {
	public static final String FACTORY_ID = RedoxPlugin.PLUGIN_ID + "BreakpointFactory";

	public ToggleBreakpointsTargetFactory() {
	}

	@Override
	public Set<String> getToggleTargets(IWorkbenchPart part, ISelection selection) {
		if (part instanceof ITextEditor && ((FileEditorInput) ((ITextEditor) part).getEditorInput()).getPath()
				.getFileExtension().equals("rs")) {
			return Collections.singleton(FACTORY_ID);
		}
		return Collections.emptySet();
	}

	@Override
	public String getDefaultToggleTarget(IWorkbenchPart part, ISelection selection) {
		if (part instanceof ITextEditor && ((FileEditorInput) ((ITextEditor) part).getEditorInput()).getPath()
				.getFileExtension().equals("rs")) {
			return FACTORY_ID;
		}
		return null;
	}

	@Override
	public IToggleBreakpointsTarget createToggleTarget(String targetID) {
		if (FACTORY_ID.equals(targetID)) {
			return new ToggleBreakpointAdapter();
		}
		return null;
	}

	@Override
	public String getToggleTargetName(String targetID) {
		return "Breakpoint";
	}

	@Override
	public String getToggleTargetDescription(String targetID) {
		return "Breakpoint for native Rust code.";
	}

}