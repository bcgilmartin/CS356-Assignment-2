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
public class Driver {
    
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                AdminControlPanel ACP = new AdminControlPanel();
                ACP.setVisible(true);
            }
        });
    }
    
}
