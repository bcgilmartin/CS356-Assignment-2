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
class NewsFeedLabel extends JTextArea implements Observer{

    private String userID;
    
    public NewsFeedLabel(String userName) {
        userID = userName;
        this.setFont(UIManager.getFont("Label.font").deriveFont(Font.PLAIN, 16));
        this.setSize(215, 25);
        this.setLocation(30, 60);
        this.setLineWrap(true);
        this.setWrapStyleWord(true);
        this.setEditable(false);
    }
    
    @Override
    public void update(UserGroupTree userGroupTree) {
        this.setText(userGroupTree.getNewsFeed(userID));
    }
    
}
