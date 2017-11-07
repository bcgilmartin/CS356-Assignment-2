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
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;

/**
 *
 * @author Blake
 */
public class UserView extends JFrame {
    
    User user;
    
    public UserView(User user) {
        
        this.user = user;
        //Establish Twitter connection
        Twitter twitter = new Twitter().getInstance();
        
        //create frame
        this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        this.setSize(500, 600);
        this.getContentPane().setLayout(null);
        this.setLocationRelativeTo(null);
        
        //Text Area - User ID
        JTextArea userID = new JTextArea();
        userID.setFont(UIManager.getFont("Label.font").deriveFont(Font.PLAIN, 18));
        userID.setSize(215, 25);
        userID.setLocation(30, 15);
        userID.setEditable(false);
        userID.setText(" Your ID: " + this.user.getID());
        this.add(userID);
        
        //Follow User ID
        JTextArea followUserID = new JTextArea();
        followUserID.setFont(UIManager.getFont("Label.font").deriveFont(Font.PLAIN, 18));
        followUserID.setSize(215, 25);
        followUserID.setLocation(30, 60);
        this.add(followUserID);
        
        //Label - Follow
        JLabel followUserDir = new JLabel();
        followUserDir.setText("Enter username to follow:");
        followUserDir.setSize(170, 10);
        followUserDir.setLocation(30, 45);
        this.add(followUserDir);
        
        //Button - Follow User
        JButton followUser = new JButton();
        followUser.setText("Follow User");
        followUser.setSize(130, 17);
        followUser.setLocation(275, 65);
        this.add(followUser);
        followUser.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    String userName = followUserID.getText();
                    followUserID.setText("");
                    if(!twitter.contains(userName)) {
                        JOptionPane.showMessageDialog(null , "There is no User with this ID");
                    } else if(userName.equals(user.getID())) {
                        JOptionPane.showMessageDialog(null , "You can't follow yourself");
                    } else if(user.isFollowing(userName)) {
                        JOptionPane.showMessageDialog(null , "You are already following that User");
                    } else if (!userName.equals("")){
                        twitter.follow(user.getID(), userName);
                    }
                }
        });
        
        //Label - Followed
        JLabel followedLabel = new JLabel();
        followedLabel.setText("Users Currently Following:");
        followedLabel.setSize(170, 13);
        followedLabel.setLocation(30, 93);
        this.add(followedLabel);
        
        //Tree View
        FollowedView followedView = new FollowedView(user.getID());
        twitter.registerObserver(followedView);
        JScrollPane treeScroll = new JScrollPane(followedView);
        treeScroll.setBounds(30, 110, 407, 100);
        this.getContentPane().add(treeScroll);
        
        //Text Area - Tweet Message
        JTextArea tweetTextArea = new JTextArea();
        tweetTextArea.setFont(UIManager.getFont("Label.font").deriveFont(Font.PLAIN, 16));
        tweetTextArea.setSize(215, 25);
        tweetTextArea.setLocation(30, 60);
        tweetTextArea.setLineWrap(true);
        tweetTextArea.setWrapStyleWord(true);
        JScrollPane tweetScroll = new JScrollPane(tweetTextArea);
        tweetScroll.setBounds(30, 230, 250, 60); 
        tweetScroll.setHorizontalScrollBarPolicy ( ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER );
        this.add(tweetScroll);
        
        //Label - Enter Tweet:
        JLabel enterTweetDir = new JLabel();
        enterTweetDir.setText("Enter Tweet:");
        enterTweetDir.setSize(170, 10);
        enterTweetDir.setLocation(30, 216);
        this.add(enterTweetDir);
        
        //Button - Share Tweet
        JButton shareTweet = new JButton();
        shareTweet.setText("Share Tweet");
        shareTweet.setSize(130, 59);
        shareTweet.setLocation(300, 230);
        this.add(shareTweet);
        shareTweet.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    String tweet = tweetTextArea.getText();
                    if(tweet.length() > 140) {
                        JOptionPane.showMessageDialog(null , "This tweet is longer than 140 characters");
                        return;
                    } 
                    tweetTextArea.setText("");
                    twitter.tweet(user.getID(), tweet);
                }
        });
        
        //List View - News Feed
        NewsFeedLabel newsFeed = new NewsFeedLabel(user.getID());
        JScrollPane newsFeedScroll = new JScrollPane(newsFeed);
        twitter.registerObserver(newsFeed);
        newsFeedScroll.setBounds(30, 315, 400, 185); 
        newsFeedScroll.setHorizontalScrollBarPolicy ( ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER );
        this.add(newsFeedScroll);
        
        //Label - News Feed:
        JLabel newsFeedDir = new JLabel();
        newsFeedDir.setText("News Feed:");
        newsFeedDir.setSize(170, 10);
        newsFeedDir.setLocation(30, 301);
        this.add(newsFeedDir);
        
    }
}
