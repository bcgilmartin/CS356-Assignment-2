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
public class Unverified implements Visitor {

    @Override
    public int visit(UserGroupTree userGroupTree) {
        return userGroupTree.getUnverified();
    }
    
}
