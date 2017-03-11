/**
 * @author  Ondřej Doněk, <ondrejd@gmail.com>
 * @license https://www.gnu.org/licenses/gpl-3.0.en.html GNU General Public License 3.0
 * @link https://github.com/ondrejd/od-downloads-plugin for the canonical source repository
 */

package com.ondrejd.wordpresstoolbar.controls;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Extension of {@see JTextField} with autocomplete.
 * 
 * Based on this <a href="http://www.java2s.com/Code/Java/Swing-Components/AutoCompleteTextField.htm">article</a>.
 *
 * @author ondrejd
 */
public class AutoCompleteTextField extends JTextField implements KeyListener, DocumentListener {
    private ArrayList<String> data;
    private int currentGuess;
    private boolean areGuessing;
    private boolean caseSensitive;

    /**
     * Construct new autocomplete textfield with 7 columns and no case sensitivity.
     */
    public AutoCompleteTextField() {
        this(7, false);
    }

    /**
     * Construct new autocomplete textfield with given count of columns 
     * and no case sensitivity.
     * @param cols The count of columns of text.
     */
    public AutoCompleteTextField(int cols) {
        this(cols, false);
    }

    /**
     * Constructs new autocomplete textfield with given count of columns 
     * and case sensitivity.
     * @param cols The count of columns of text.
     * @param caseSensitive Use <code>true</code> to make autocompleting case sensitive.
     */
    public AutoCompleteTextField(int cols, boolean caseSensitive) {
        super.setColumns(cols);
        this.data = new ArrayList<>();
        this.currentGuess = -1;
        this.areGuessing = false;
        this.caseSensitive = caseSensitive;

        this.addKeyListener(this);
        this.getDocument().addDocumentListener(this);
    }

    /**
     * Adds whole autocomplete data list.
     * @param data New autocomplete data list.
     */
    public void addData(ArrayList<String> data) {
        this.data = data;
    }

    /**
     * Adds new item into the autocomplete data list.
     * @param item New data item.
     */
    public void addDataItem(String item) {
        this.data.add(item);
        Collections.sort(this.data);
    }

    /**
     * Removes item from the autocomplete data list.
     * @param item New data item.
     */
    public void removeDataItem(String item) {
        this.data.remove(item);
    }

    /**
     * Removes all items from the autocomplete data list.
     */
    public void removeAllDataItems() {
        //this.data = new ArrayList<AutoCompleteTextFieldItem>();
        this.data = new ArrayList<>();
    }

    /**
     * Sets case sensitivity.
     * @param caseSensitive Use <code>true</code> to make autocompleting case sensitive.
     */
    public void setCaseSensitive(boolean caseSensitive) {
        this.caseSensitive = caseSensitive;
    }

    /**
     * Returns the current guess from the autocomplete data list.
     * @return The current guess as a <code>String</code>.
     */
    private String getCurrentGuess() {
        if (this.currentGuess != -1) {
            return this.data.get(this.currentGuess);
        }
        return this.getText();
    }

    /**
     * Tries to find currently entered text (guess) in the autocomplete data list.
     */
    private void findCurrentGuess() {
        String entered = this.getText();

        if (!this.caseSensitive) {
            entered = entered.toLowerCase();
        }

        for (int i = 0; i < this.data.size(); i++) {
            currentGuess = -1;

            String item = this.data.get(i);

            if (!this.caseSensitive) {
                item = item.toLowerCase();
            }

            if (item.startsWith(entered)) {
                this.currentGuess = i;
                break;
            }
        }
    }

    @Override
    public void setText(String text) {
        super.setText(text);
        this.areGuessing = false;
        this.currentGuess = -1;
    }

    @Override
    public void paintComponent(Graphics g) {
        String guess = this.getCurrentGuess();
        String drawGuess = guess;

        super.paintComponent(g);
        String entered = this.getText();
        Rectangle2D enteredBounds = g.getFontMetrics().getStringBounds(entered, g);

        if (!(this.caseSensitive)) {
            entered = entered.toLowerCase();
            guess = guess.toLowerCase();
        }
        
        if (!(guess.startsWith(entered)))
            this.areGuessing = false;

        if (entered != null && !(entered.equals("")) && this.areGuessing) {
            String subGuess = drawGuess.substring(entered.length(), drawGuess.length());
            Rectangle2D subGuessBounds = g.getFontMetrics().getStringBounds(drawGuess, g);

            int centeredY = ((getHeight() / 2) + (int)(subGuessBounds.getHeight() / 2));

            g.setColor(Color.BLUE);
            g.drawString(subGuess + "   press \u2192 to fill", (int)(enteredBounds.getWidth()) + 6, centeredY - 2);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) { }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (this.areGuessing) {
                this.setText(this.getCurrentGuess());
                this.areGuessing = false;
                e.consume();
            }
        }
    }
    
    @Override
    public void keyReleased(KeyEvent e) { }

    @Override
    public void insertUpdate(DocumentEvent e) {
        String temp = this.getText();

        if (temp.length() == 1) {
            this.areGuessing = true;
        }

        if (this.areGuessing) {
            this.findCurrentGuess();
        }
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        String temp = this.getText();

        if (!(this.areGuessing)) {
            this.areGuessing = true;
        }

        if (temp.length() == 0) {
            this.areGuessing = false;
        }
        else if (this.areGuessing) {
            this.findCurrentGuess();
        }
    }

    @Override
    public void changedUpdate(DocumentEvent e) { }
}
