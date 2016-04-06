/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wolfieballdraftkit.gui;

import java.util.ArrayList;
import java.util.List;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import wolfieballdraftkit.WolfieballDataManager;
import wolfieballdraftkit.Wolfieball_PropertyType;
import wolfieballdraftkit.entitystructures.DraftablePlayers;
import wolfieballdraftkit.entitystructures.League;
import wolfieballdraftkit.entitystructures.Player;
import wolfieballdraftkit.entitystructures.Team;
import wolfieballdraftkit.enums.Contract;

/**
 *
 * @author Nelnel33
 */
public class DraftPane extends TitlePane {
    ArrayList<Player> draftList;
    
    VBox container;
    
    FlowPane buttonContainer;
    Button selectPlayer;
    Button startAutoDraft;
    Button stopAutoDraft;
    
    TableView<Player> draftTable;
    TableColumn pickNumber;
    TableColumn firstName;
    TableColumn lastName;
    TableColumn team;
    TableColumn contract;
    TableColumn salary;
    
    boolean isOnAuto;
    int currentTeamCounter = 0;
    
    public DraftPane(MainPane mainPane, Wolfieball_PropertyType propertyType) {
        super(mainPane, propertyType);
        init();
        isOnAuto = false;
    }
    
    private void init(){
        initGUI();
        
        initHandlers();
    }

    private void initGUI() {
        draftList = new ArrayList();
        container = new VBox();
        buttonContainer = new FlowPane();
        selectPlayer = MainPane.initChildButton(buttonContainer, Wolfieball_PropertyType.SELECT_ICON, Wolfieball_PropertyType.SELECT_TOOLTIP, false);
        startAutoDraft = MainPane.initChildButton(buttonContainer, Wolfieball_PropertyType.PLAY_ICON, Wolfieball_PropertyType.PLAY_TOOLTIP, false);
        stopAutoDraft = MainPane.initChildButton(buttonContainer, Wolfieball_PropertyType.PAUSE_ICON, Wolfieball_PropertyType.PAUSE_TOOLTIP, false);
        
        draftTable = new TableView();
        pickNumber = new TableColumn("#");
        firstName = new TableColumn("First Name");
        lastName = new TableColumn("Last Name");
        team = new TableColumn("Team");
        contract = new TableColumn("Contract");
        salary = new TableColumn("Salary");
        
        draftTable.getColumns().add(pickNumber);
        draftTable.getColumns().add(firstName);
        draftTable.getColumns().add(lastName);
        draftTable.getColumns().add(team);
        draftTable.getColumns().add(contract);
        draftTable.getColumns().add(salary);

        pickNumber.setCellValueFactory(new Callback<CellDataFeatures<String, String>, ObservableValue<String>>() {
            @Override 
            public ObservableValue<String> call(CellDataFeatures<String, String> p) {
                return new ReadOnlyObjectWrapper((draftTable.getItems().indexOf(p.getValue())+1) + "");
            }
        });   
        pickNumber.setSortable(false);
        firstName.setCellValueFactory(new PropertyValueFactory<String, String>("firstName"));
        lastName.setCellValueFactory(new PropertyValueFactory<String, String>("lastName"));
        team.setCellValueFactory(new PropertyValueFactory<String, String>("team"));
        contract.setCellValueFactory(new PropertyValueFactory<String, String>("contract"));
        salary.setCellValueFactory(new PropertyValueFactory<String, String>("salary"));
        
        container.getChildren().add(buttonContainer);
        container.getChildren().add(draftTable);
        
        this.add(container,0,1,6,6);
    }

    private void initHandlers() {
        selectPlayer.setOnAction(e -> {
            selectPlayerAction();
        });
        startAutoDraft.setOnAction(e -> {
           isOnAuto = true;
           handle(e);         
        });
        stopAutoDraft.setOnAction(e -> {
            isOnAuto = false;
        });
    }
    
    private boolean areAllStarterTeamsFull(List<Team> teams){
        for(Team t: teams){
            if(!t.getStarters().isFull()){
                return false;
            }
        }
        
        return true;
    }
    
    public void incrementCurrentTeamCounter(List<Team> teams){
        if(this.currentTeamCounter<teams.size()-1){
            currentTeamCounter++;
        }
        else{
            currentTeamCounter = 0;
        }
    }
    
    private void selectPlayerAction(){
        List<Team> teams = this.mainPane.dataManager.getTeamsInLeague();
            if (!teams.isEmpty()) {
                Team t = teams.get(currentTeamCounter);
                if (!t.getStarters().isFull()) {
                    addDraftablePlayerToTeam(t);
                } else {
                    this.incrementCurrentTeamCounter(teams);                    
                }

                if (areAllStarterTeamsFull(teams)) {
                    if (!t.getTaxi().isFull()) {
                        addDraftablePlayerToTeam(t);
                    } else {
                        this.incrementCurrentTeamCounter(teams);
                    }
                }
            }
        refreshTable();
        this.mainPane.refreshTables();
    }
    
    public void handle(ActionEvent event){
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                while (isOnAuto) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {

                            selectPlayerAction();

                        }
                    });

                    // SLEEP EACH FRAME
                    try {
                        Thread.sleep(400);
                    } catch (InterruptedException ie) {
                        ie.printStackTrace();
                    }
                }

                return null;
            }
        };
        
        Thread t = new Thread(task);
        t.start();
    }
    
    public void addDraftablePlayerToTeam(Team team){
        DraftablePlayers draftables = this.mainPane.dataManager.getFullDraftablePlayers();
        for(int i=0;i<draftables.getPlayers().size();i++){
            Player p = draftables.getPlayers().get(i); 
            ArrayList<String> qualifyingPositions = p.qualifyingPositionsInStringArray();
            if (!team.getStarters().isFull()) {
                String eligiblePositions = team.getStarters().getEligiblePositions();
                for(String s: qualifyingPositions){
                    if(eligiblePositions.contains(s)){
                        p.setDraftAttributes(s, Contract.S2, 1, team);
                        if (team.addPlayer(p, draftables)) {
                            return;
                        }
                    }
                }
            }
            else{
                String setPosition = qualifyingPositions.get(qualifyingPositions.size()-1);
                p.setDraftAttributes(setPosition, Contract.X, 1, team);
                team.addPlayer(p,draftables);
                return;
            }
        }
    }
    
    public void refreshTable(){
        draftList = new ArrayList();
        for(Team t: mainPane.getDataManager().getTeamsInLeague()){
            for(Player p: t.getStarters().getPlayers()){
                draftList.add(p);
            }
        }
        for(Team te: mainPane.getDataManager().getTeamsInLeague()){
            for(Player p: te.getTaxi().getPlayers()){
                draftList.add(p);
            }
        }
        
        draftTable.setItems(FXCollections.observableArrayList(draftList));
    }
    
    
    
}
