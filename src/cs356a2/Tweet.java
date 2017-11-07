/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs356a2;

/**
 *
 * @author Blake
 */
class Tweet {
    
    String senderID;
    String message;

    public Tweet(String id, String tweetMsg) {
        senderID = id;
        message = tweetMsg;
    }
    
    public String getSenderID() {
        return senderID;
    }
    
    public String getTweetMsg() {
        return message;
    }
}
