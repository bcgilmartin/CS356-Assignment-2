/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs356a2;

import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;

/**
 *
 * @author Blake
 */
public class AdminControlPanel extends JFrame {
    public AdminControlPanel() {
        //Establish Twitter connection
        Twitter twitter = new Twitter().getInstance();
        
        //create frame
        this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        this.setSize(750, 450);
        this.getContentPane().setLayout(null);
        this.setLocationRelativeTo(null);
        
        //Text Area - User ID
        JTextArea userID = new JTextArea();
        userID.setFont(UIManager.getFont("Label.font").deriveFont(Font.PLAIN, 18));
        userID.setSize(170, 25);
        userID.setLocation(370, 25);
        this.add(userID);
        
        //Text Area - Group ID
        JTextArea groupID = new JTextArea();
        groupID.setFont(UIManager.getFont("Label.font").deriveFont(Font.PLAIN, 18));
        groupID.setSize(170, 25);
        groupID.setLocation(370, 85);
        this.add(groupID);
        
        //Label - Enter User ID
        JLabel userIDdir = new JLabel();
        userIDdir.setText("Enter Username for new user:");
        userIDdir.setSize(170, 10);
        userIDdir.setLocation(370, 8);
        this.add(userIDdir);
        
        //Label - Enter group ID
        JLabel groupIDdir = new JLabel();
        groupIDdir.setText("Enter GroupID for new group:");
        groupIDdir.setSize(170, 17);
        groupIDdir.setLocation(370, 66);
        this.add(groupIDdir);
        
        //Button - Add User
        JButton addUser = new JButton();
        addUser.setText("Enter New User");
        addUser.setSize(130, 17);
        addUser.setLocation(560, 25);
        this.add(addUser);
        addUser.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    String userName = userID.getText();
                    userID.setText("");
                    if(twitter.contains(userName)) {
                        JOptionPane.showMessageDialog(null , "A User with this name already exists");
                    } else if (!userName.equals("")){
                        twitter.add(new User(userName));
                        //UPDATE
                    }
                }
        });
        
        //Button - Add Group
        JButton addGroup = new JButton();
        addGroup.setText("Enter New Group");
        addGroup.setSize(130, 17);
        addGroup.setLocation(560, 85);
        this.add(addGroup);
        addGroup.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    String groupName = groupID.getText();
                    groupID.setText("");
                    if(twitter.contains(groupName)) {
                        JOptionPane.showMessageDialog(null , "A Group with this name already exists");
                    } else if (!groupName.equals("")){
                        twitter.add(new Group(groupName));
                        //UPDATE
                    }
                }
        });
        
        //Tree View
        UserGroupTreeView userGroupTreeView = new UserGroupTreeView();
        twitter.registerObserver(userGroupTreeView);
        JScrollPane treeScroll = new JScrollPane(userGroupTreeView);
        treeScroll.setBounds(10, 10, 300, 340);
        this.getContentPane().add(treeScroll);
        
        //Text Area - Enter username to open
        JTextArea openUserID = new JTextArea();
        openUserID.setFont(UIManager.getFont("Label.font").deriveFont(Font.PLAIN, 18));
        openUserID.setSize(170, 25);
        openUserID.setLocation(370, 145);
        this.add(openUserID);
        
        //Label - Enter user name to open
        JLabel openUserIDdir = new JLabel();
        openUserIDdir.setText("Enter user to open view for: ");
        openUserIDdir.setSize(170, 17);
        openUserIDdir.setLocation(370, 126);
        this.add(openUserIDdir);
        
        //Button Open User View
        JButton openUser = new JButton();
        openUser.setText("Open User View");
        openUser.setSize(130, 17);
        openUser.setLocation(560, 145);
        this.add(openUser);
        openUser.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    String openUserName = openUserID.getText();
                    openUserID.setText("");
                    if(!twitter.contains(openUserName) || !twitter.isUser(openUserName)) {
                        JOptionPane.showMessageDialog(null , "The username you entered is invalid");
                    } else if (!openUserName.equals("")){
                        System.out.println("OPEN USER VIEW FOR " + openUserName);
                        java.awt.EventQueue.invokeLater(new Runnable() {
                            public void run() {
                                UserView UV = new UserView(twitter.getUser(openUserName));
                                UV.setVisible(true);
                            }
                        });
                        twitter.notifyObservers();
                    }
                }
        });
        
        //Button - User/Group ID verification
        JButton verifyUserGroups = new JButton();
        verifyUserGroups.setText("Verify User/Grp");
        verifyUserGroups.setSize(130, 17);
        verifyUserGroups.setLocation(370, 239);
        this.add(verifyUserGroups);
        verifyUserGroups.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {   
                    JOptionPane.showMessageDialog(null , "Total Unverified Users: " + twitter.getTree().accept(new Unverified()));
                }
        });
        
        //Button - User/Group ID verification
        JButton lastFollowed = new JButton();
        lastFollowed.setText("Last Updated");
        lastFollowed.setSize(130, 17);
        lastFollowed.setLocation(560, 239);
        this.add(lastFollowed);
        lastFollowed.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) { 
                    twitter.getTree().accept(new LastUpdated());
                    JOptionPane.showMessageDialog(null , "LAST UPDATED IS IN THE CONSOLE");
                }
        });
        
        //Button - show user total
        JButton showUserTotal = new JButton();
        showUserTotal.setText("User Total");
        showUserTotal.setSize(130, 17);
        showUserTotal.setLocation(370, 270);
        this.add(showUserTotal);
        showUserTotal.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {   
                    JOptionPane.showMessageDialog(null , "User Total: " + twitter.getTree().accept(new UserTotal()));
                }
        });
        
        //Button - show user total
        JButton showGroupTotal = new JButton();
        showGroupTotal.setText("Group Total");
        showGroupTotal.setSize(130, 17);
        showGroupTotal.setLocation(560, 270);
        this.add(showGroupTotal);
        showGroupTotal.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {   
                    JOptionPane.showMessageDialog(null , "Group Total: " + twitter.getTree().accept(new GroupTotal()));
                }
        });
        
        //Button - show user total
        JButton showTotalTweets = new JButton();
        showTotalTweets.setText("Tweets Total");
        showTotalTweets.setSize(130, 17);
        showTotalTweets.setLocation(370, 301);
        this.add(showTotalTweets);
        showTotalTweets.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {   
                    JOptionPane.showMessageDialog(null , "Tweets Total: " + twitter.getTree().accept(new TotalTweets()));
                }
        });
        
        //Button - show positive percentage
        JButton showPositivePercentage = new JButton();
        showPositivePercentage.setText("Show Positive %");
        showPositivePercentage.setSize(130, 17);
        showPositivePercentage.setLocation(560, 301);
        this.add(showPositivePercentage);
        showPositivePercentage.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {   
                    JOptionPane.showMessageDialog(null , "Positive Tweet Percentage: " + twitter.getTree().accept(new PositivePercentage()) + "%");
                }
        });
    }
}
