package wolfieballdraftkit.entitystructures;

import java.util.ArrayList;
import wolfieballdraftkit.enums.Contract;

/**
 *
 * @author Nelnel33
 */
public class Starters extends Subteam{
    
    public static final int MAX_NUMBER_OF_PITCHERS = 9;
    public static final int MAX_NUMBER_OF_HITTERS = 14;
    public static final int MAX_PLAYERS_STARTING_LINEUP = MAX_NUMBER_OF_HITTERS+MAX_NUMBER_OF_PITCHERS;
    public static final int MAX_NUMBER_OF_C = 2;
    public static final int MAX_NUMBER_OF_OF = 5;
    public static final String[] positionOrder = {
        "C","C", 
        "1B", "CI", "3B", "2B", "MI", "SS", 
        "OF", "OF", "OF", "OF", "OF", 
        "U", 
        "P", "P", "P", "P", "P", "P", "P", "P", "P"
    };
    
    //NUMBER OF PITCHERS/HITTERS on the starting lineup
    private int numberOfPitchers;
    private int numberOfHitters;

    public Starters(Team parentTeam) {
        super(MAX_PLAYERS_STARTING_LINEUP, parentTeam);
        numberOfPitchers = 0;
        numberOfHitters = 0;
    }
    
    @Override //will have another more useful function that deducts money/eliminates positions already taken
    public boolean addPlayer(Player player){
        calculatePositionTypes();
        //if(player.getContract().equals(Contract.X) || player.getContract().equals(Contract.UNDRAFTED)){
        //    return false;
        //}
        if(player.isPitcher()){
            if(numberOfPitchers<MAX_NUMBER_OF_PITCHERS){
                super.addPlayer(player);
                positionPlayersInOrder();
                return true;
            }
            else{
                System.err.println("Too many PITCHERS on Starting Lineup of "+this.getParentTeam().getName());
                return false;
            }
        }
        else{
            if(numberOfHitters<MAX_NUMBER_OF_HITTERS && checkIfPositionIsAvailable((Hitter)player)){
                super.addPlayer(player);
                positionPlayersInOrder();
                return true;
            }
            else{
                System.err.println("Too many HITTERS on Starting Lineup of "+this.getParentTeam().getName());
                return false;
            }
        }
    }
    
    public boolean checkIfPositionIsAvailable(Hitter h){
        String position = h.getPosition();
        int counter = 0;
        for(Player p: this.getPlayers()){
            if(p.getPosition().equals(position)){
                counter++;
            }
        }
        
        return  (position.equals("C") && counter<2)||
                (position.equals("OF") && counter<5)||
                (position.equals("1B") && counter<1)||
                (position.equals("CI") && counter<1)||
                (position.equals("3B") && counter<1)||
                (position.equals("2B") && counter<1)||
                (position.equals("MI") && counter<1)||
                (position.equals("SS") && counter<1)||
                (position.equals("U") && counter<1);
    }
    
    public int getPitchersLeft(){
        int counter = 0;
        for(Player p: this.getPlayers()){
            if(p.getPosition().equals("P")){
                counter++;
            }
        }
        int left = Starters.MAX_NUMBER_OF_PITCHERS - counter;
        
        return left;
    }
    
    public int getHittersLeft(){
        return (this.getParentTeam().getPlayersLeft() - this.getPitchersLeft());
    }
    
    public void positionPlayersInOrder(){
        Player[] playerOrder = new Player[MAX_PLAYERS_STARTING_LINEUP];
        ArrayList<Player> newList = new ArrayList();
        for(Player p: super.getPlayers()){//loops through starters
            for(int i=0;i<positionOrder.length;i++){//loops through the set position order
                if(p.getPosition().equals(positionOrder[i]) && playerOrder[i] == null){
                //checks if the position of the current equals to the position order @ i
                //and if the this index can be filled.
                //If both conditions are true, it is placed into playerOrder at the current index
                    playerOrder[i] = p;
                    break;
                }
            }
        }
        
        super.getPlayers().clear();//clears the starters
        
        for(Player pl: playerOrder){
            if(pl != null){
                super.getPlayers().add(pl);//readds players from playerOrder back into starters list.
            }
        }
    }    
    
    public String getEligiblePositions(){
        String allPositions = "";
        for(String s: positionOrder){
            allPositions += s;
        }
        
        for(int i=0;i<this.getPlayers().size(); i++){
            Player p = this.getPlayers().get(i);
            String s = p.getPosition();
            allPositions = allPositions.replaceFirst(s, " ");
        }
        
        //System.out.println("EligiblePositions in Team "+this.getParentTeam().getName()+": "+allPositions);                
        return allPositions;
    }
    
    //recalculates the number of hitter/pitchers
    private void calculatePositionTypes(){
        numberOfPitchers = 0;
        numberOfHitters = 0;
        for(Player p: this.getPlayers()){
            if(p.isPitcher()){
                numberOfPitchers++;
            }
            else{
                numberOfHitters++;
            }                           
        }
    }

    public int getNumberOfPitchers() {
        return numberOfPitchers;
    }

    public int getNumberOfHitters() {
        return numberOfHitters;
    }    
    
}
