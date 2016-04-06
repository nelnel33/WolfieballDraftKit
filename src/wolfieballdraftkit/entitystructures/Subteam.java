/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wolfieballdraftkit.entitystructures;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Nelnel33
 */
public class Subteam {
    private int maxPlayers;
    private Team parentTeam;
    private ObservableList<Player> players;

    public Subteam(int maxPlayers, Team parentTeam) {
        this.maxPlayers = maxPlayers;
        this.parentTeam = parentTeam;
        players = FXCollections.observableArrayList();
    }
    
    public boolean addPlayer(Player p){
        if (maxPlayers <= 0) {
            return players.add(p);
        } 
        else {
            if (players.size() < maxPlayers) {
                players.add(p);
                return true;
            } else {
                //TEAM IS TOO LARGE
                return false;
            }
        }
    }
    
    public void addPlayer(int index, Player p){
        if (maxPlayers <= 0) {
            players.add(index, p);
        } 
        else {
            if (players.size() < maxPlayers) {
                players.add(index, p);
            } else {
                //TEAM IS TOO LARGE
            }
        }
    }
    
    public Player getPlayer(int i){
        return players.get(i);
    }
    
    public boolean removePlayer(Player p){
        return players.remove(p);
    }
       
    public Player removePlayer(int i){
        return players.remove(i);
    }
    
    public boolean isFull(){
        return players.size() >= maxPlayers;
    }
    
    public boolean equals(Subteam subteam){
        if(subteam.getMaxPlayers() != this.getMaxPlayers()){
            return false;
        }
        if(subteam.getPlayers().size() != this.getPlayers().size()){
            return false;
        }
        
        for(int i=0;i<subteam.getPlayers().size();i++){
            if(!subteam.getPlayer(i).equals(this.getPlayer(i))){
                return false;
            }
        }
        return true;        
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public Team getParentTeam() {
        return parentTeam;
    }
    
    public ObservableList<Player> getPlayers() {
        return players;
    }
    
    /**
     * Deep copy of Subteam.
     * Shallow copy of all Players in Subteam.
     * @param other
     * @return 
     */
    public static Subteam copy(Subteam other){
        Subteam s = new Subteam(other.getMaxPlayers(), other.getParentTeam());
        
        for(Player p: other.getPlayers()){
            s.addPlayer(p);
        }
        
        return s;
    }
    
}
