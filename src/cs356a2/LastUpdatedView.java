/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs356a2;

import java.awt.Font;
import javax.swing.JTextArea;
import javax.swing.UIManager;

/**
 *
 * @author Blake
 */
public class LastUpdatedView extends JTextArea implements Observer {
    
    String userID;
    
    public LastUpdatedView(String userID) {
        this.userID = userID;
        this.setSize(300, 340);
        this.setLocation(25, 10);
        this.setEditable(false);
        this.setFont(UIManager.getFont("Label.font").deriveFont(Font.PLAIN, 15));
    }

    @Override
    public void update(UserGroupTree userGroupTree) {
        this.setText("Last Updated: " + userGroupTree.getLastUpdatedTime(userID));
    }
}
