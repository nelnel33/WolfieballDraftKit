/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wolfieballdraftkit.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import wolfieballdraftkit.WolfieballDataManager;
import wolfieballdraftkit.Wolfieball_PropertyType;
import wolfieballdraftkit.entitystructures.DraftablePlayers;
import wolfieballdraftkit.entitystructures.Player;
import wolfieballdraftkit.entitystructures.Team;

/**
 *
 * @author Nelnel33
 */
public class MLBTeamsPane extends TitlePane{
    ObservableList<Player> currentPlayersShown;
    
    VBox container;
    HBox ComboBoxContainer;
    Label selectProTeamLabel;
    ComboBox selectProTeamComboBox;
    TableView<Player> MLBTable;
    TableColumn firstName;
    TableColumn lastName;
    TableColumn positions;

    public MLBTeamsPane(MainPane mainPane, Wolfieball_PropertyType propertyType) {
        super(mainPane, propertyType);
        init();
    }
    
    private void init(){
        currentPlayersShown = DraftablePlayers.copy(WolfieballDataManager.MLBTeamList).getPlayers();
        
        container = new VBox();
        ComboBoxContainer = new HBox();
        selectProTeamLabel = new Label("Select Pro Team: ");
        selectProTeamComboBox = new ComboBox();
        MainPane.loadComboBox(selectProTeamComboBox, WolfieballDataManager.PRO_TEAMS);
        MLBTable = new TableView();
        firstName = new TableColumn("First Name");
        lastName = new TableColumn("Last Name");
        positions = new TableColumn("Positions");
        //lastName.setSortType(TableColumn.SortType.ASCENDING);
        //firstName.setSortType(TableColumn.SortType.DESCENDING);
        
        this.add(container, 0,1,4,8);
        container.getChildren().add(ComboBoxContainer);
        ComboBoxContainer.getChildren().add(selectProTeamLabel);
        ComboBoxContainer.getChildren().add(selectProTeamComboBox);
        container.getChildren().add(MLBTable);
        
        MLBTable.getColumns().add(firstName);
        MLBTable.getColumns().add(lastName);
        MLBTable.getColumns().add(positions);
        MLBTable.getSortOrder().add(lastName);
        //MLBTable.getSortOrder().add(firstName);
        
        
        this.firstName.setCellValueFactory(new PropertyValueFactory<String, String>("firstName"));
        this.lastName.setCellValueFactory(new PropertyValueFactory<String, String>("lastName"));
        this.positions.setCellValueFactory(new PropertyValueFactory<String, String>("qualifyingPositions"));
        
        refreshTables();
        
        initHandlers();
    }
    
    private void initHandlers(){
        selectProTeamComboBox.setOnAction(e -> {
            String curr = selectProTeamComboBox.getSelectionModel().getSelectedItem().toString();
            currentPlayersShown = DraftablePlayers.copy(WolfieballDataManager.MLBTeamList).getPlayers();
            for(int i=currentPlayersShown.size()-1;i>=0;i--){
                if(!currentPlayersShown.get(i).getProTeam().equals(curr)){
                    currentPlayersShown.remove(i);
                }
            }
            refreshTables();
        });
    }
    
    public void refreshTables(){
        MLBTable.setItems(currentPlayersShown);
    }
    
    
   
}
