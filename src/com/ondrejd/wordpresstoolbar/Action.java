/**
 * @author  Ondřej Doněk, <ondrejd@gmail.com>
 * @license https://www.gnu.org/licenses/gpl-3.0.en.html GNU General Public License 3.0
 * @link https://github.com/ondrejd/od-downloads-plugin for the canonical source repository
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
