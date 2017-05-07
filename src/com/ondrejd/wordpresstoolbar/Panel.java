/**
 * @author  Ondřej Doněk, <ondrejd@gmail.com>
 * @license https://www.gnu.org/licenses/gpl-3.0.en.html GNU General Public License 3.0
 * @link https://github.com/ondrejd/od-downloads-plugin for the canonical source repository
 */

package com.ondrejd.wordpresstoolbar;

import com.ondrejd.wordpresstoolbar.controls.AutoCompleteTextField;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.openide.awt.HtmlBrowser.URLDisplayer;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * @author ondrejd
 */
public class Panel extends javax.swing.JPanel {

    /**
     * Creates new form Panel
     */
    public Panel() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new AutoCompleteTextField();

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(Panel.class, "Panel.jLabel1.text")); // NOI18N

        jTextField1.setText(org.openide.util.NbBundle.getMessage(Panel.class, "Panel.jTextField1.text")); // NOI18N
        jTextField1.addData(loadAutoCompleteData());
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 287, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        try {
            String term = URLEncoder.encode(jTextField1.getText(), "UTF-8");
            String url = "https://developer.wordpress.org/?s=" + term;

            URLDisplayer.getDefault().showURL(new URL(url));
        } catch (Exception ex) {
            return;
        }
    }//GEN-LAST:event_jTextField1ActionPerformed

    /**
     * @return Data for {@see AutoCompleteTextField}.
     * @todo Loading of autocomplete data should be placed inside its own thread to protect time-wasting initialization. After loading of XML file is complete <code>jTextField1</code> will be enabled and user will be able to use the plugin.
     */
    private ArrayList<String> loadAutoCompleteData() {
        ArrayList<String> data = new ArrayList<>();

        //ParserConfigurationException, SAXException, IOException
        try {
            InputStream is = Panel.class.getResourceAsStream("wordpress-api.xml");
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(is);

            NodeList items = doc.getElementsByTagName("item");
            for (int i = 0; i < items.getLength(); i++) {
                Node item = items.item(i);
                String name = item.getAttributes().getNamedItem("name").getNodeValue();
                data.add(name);
            }
        } catch (ParserConfigurationException pce) {
            //...
        } catch (SAXException se) {
            //...
        } catch (IOException ioe) {
            //...
        }

        return data;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private com.ondrejd.wordpresstoolbar.controls.AutoCompleteTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
