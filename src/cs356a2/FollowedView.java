/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs356a2;

import java.util.List;
import javax.swing.JTextArea;

/**
 *
 * @author Blake
 */
public class FollowedView extends JTextArea implements Observer {
    
    String userID;
    
    public FollowedView(String userID) {
        this.userID = userID;
        this.setSize(300, 340);
        this.setLocation(25, 10);
        this.setEditable(false);
    }

    @Override
    public void update(UserGroupTree userGroupTree) {
        this.setText(userGroupTree.showFollowed(userID));
    }
    
    

    
}
