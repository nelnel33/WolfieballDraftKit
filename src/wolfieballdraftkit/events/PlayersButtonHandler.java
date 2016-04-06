/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wolfieballdraftkit.events;

import java.util.ArrayList;
import javafx.collections.ObservableList;
import javafx.scene.control.RadioButton;
import wolfieballdraftkit.WolfieballDataManager;
import wolfieballdraftkit.entitystructures.Player;
import wolfieballdraftkit.gui.PlayersPane;

/**
 *
 * @author Nelnel33
 */
public class PlayersButtonHandler {
    //Reference to class this is used in
    PlayersPane playersPane;
    
    WolfieballDataManager dataManager;
    
    public PlayersButtonHandler(PlayersPane playersPane){
        this.playersPane = playersPane;
        dataManager = playersPane.getMainPane().getDataManager();
    }
    
    public void handleSearchRequest(String search){
        dataManager.restorePlayerList();

        ObservableList<Player> list = dataManager.getShownOnTableDraftablePlayers().getPlayers();

        for (int i = list.size()-1;i >=0;i--) {
            if (list.get(i).getFirstName().toLowerCase().contains(search.toLowerCase())) {
                //DO NOTHING CONTAINS CORRECT KEY!
            } else if (list.get(i).getLastName().toLowerCase().contains(search.toLowerCase())) {
                //DO NOTHING
            } else if ((list.get(i).getFirstName().toLowerCase() + " " + list.get(i).getLastName().toLowerCase()).contains(search.toLowerCase())) {
                //DO NOTHING
            } else {
                list.remove(i);
            }
        }
        
        playersPane.setTableItemsTo(list);
    }
    
    public void handleRadioButtonFilterRequest(RadioButton filterBy){
        dataManager.restorePlayerList();
        String filterKey = filterBy.getText();
        System.out.println(filterKey);
        
        ObservableList<Player> list = dataManager.getShownOnTableDraftablePlayers().getPlayers();
        
        if(filterKey.equals("P")){
            playersPane.setToPitcherTable();
        }
        else if(filterKey.equals("All")){
            playersPane.setToDefaultTable();
        }
        else{
            playersPane.setToHitterTable();
        }
        
        if (!filterKey.equals("All")) {
            for (int i = list.size()-1; i >= 0; i--) {
                if (list.get(i).getQualifyingPositions().contains(filterKey)) {
                    //DO NOTHING CONTAINS CORRECT KEY!
                    //System.out.println(list.get(i).getQualifyingPositions()+"|"+filterKey);
                } else {
                    list.remove(i);
                }
            }
        }
        
        playersPane.setTableItemsTo(list);
    }    
    public void handleEditNotesRequest(String note){
        
    }
}
