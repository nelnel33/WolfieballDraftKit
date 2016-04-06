/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wolfieballdraftkit.events;

import wolfieballdraftkit.WolfieballDataManager;
import wolfieballdraftkit.gui.TeamsPane;

/**
 *
 * @author Nelnel33
 */
public class FantasyTeamsHandler {
    //Reference to class this is used in
    TeamsPane teamsPane;
    
    //Reference to data
    WolfieballDataManager dataManager;
    
    public FantasyTeamsHandler(TeamsPane teamsPane){
        this.teamsPane = teamsPane;
        dataManager = teamsPane.getMainPane().getDataManager();
    }
    
    public void handleDraftNameRequest(String name){
        dataManager.getLeague().setName(name);
    }
    
    public void handleAddTeamRequest(){}
    
    public void handleEditTeamRequest(){}
    
    public void handleRemoveTeamRequest(){}
    
    public void handleViewTeamRequest(){}
    
    public void editPlayerOnTeamRequest(){}
    
}
