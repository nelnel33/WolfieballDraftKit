/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wolfieballdraftkit.entitystructures;

/**
 *
 * @author Nelnel33
 */
public class DraftablePlayers extends Subteam{

    public DraftablePlayers() {
        super(-1, null);
    }
    
    @Override
    public String toString(){
        String ret = "---\n";
        
        for(Player p: this.getPlayers()){
            ret += p.toString()+"\n";
        }
        
        ret += "---";
        
        return ret;
    }
    
        /**
     * Deep copy of Subteam.
     * Shallow copy of all Players in Subteam.
     * @param other
     * @return 
     */
    public static DraftablePlayers copy(DraftablePlayers other){
        DraftablePlayers s = new DraftablePlayers();
        
        for(Player p: other.getPlayers()){
            s.addPlayer(p);
        }
        
        return s;
    }
    
}
