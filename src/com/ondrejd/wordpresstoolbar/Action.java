/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ondrejd.wordpresstoolbar;

import java.awt.Component;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.util.actions.Presenter;
import org.openide.util.NbBundle.Messages;

@ActionID(
        category = "File",
        id = "com.ondrejd.wordpresstoolbar.ToolbarAction"
)
@ActionRegistration(
        lazy = false,
        displayName = "NOT-USED"
)
@ActionReference(
        path = "Toolbars/File",
        position = 5000
)
@Messages("CTL_ToolbarAction=WordPress")
public final class Action extends AbstractAction implements Presenter.Toolbar {

    @Override
    public Component getToolbarPresenter() {
        return new Panel();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //delegated to toolbar
    }
}
