/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wolfieballdraftkit.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import wolfieballdraftkit.Wolfieball_PropertyType;
import wolfieballdraftkit.entitystructures.Hitter;
import wolfieballdraftkit.entitystructures.Pitcher;
import wolfieballdraftkit.entitystructures.Player;
import wolfieballdraftkit.entitystructures.Team;
import wolfieballdraftkit.enums.Contract;
import wolfieballdraftkit.events.FantasyTeamsHandler;
import wolfieballdraftkit.gui.dialogs.EditPlayerDialog;
import wolfieballdraftkit.gui.dialogs.TeamDialog;

/**
 *
 * @author Nelnel33
 */
public class TeamsPane extends TitlePane {    
    //HANDLER
    FantasyTeamsHandler handler;
    
    //CURRENT TEAM
    Team currentTeam;
    
    //draft name label+textfield
    FlowPane draftNameContainer;
    Label draftNameLabel;
    TextField draftNameTextField;
    
    //buttons - add, remove, edit, select label+combobox
    FlowPane tableUtilityContainer;
    Button addTeam;
    Button removeTeam;
    Button editTeam;
    Label selectFantasyTeamLabel;
    ComboBox selectFantasyTeam;
    
    //Starting Lineup label+table
    VBox tableContainer;
    Label startingLineupLabel;
    TableView<Player> startingLineupTable;
    TableColumn starting_position;
    TableColumn starting_firstName;
    TableColumn starting_lastName;
    TableColumn starting_proTeam;
    TableColumn starting_positions;
    TableColumn starting_yearOfBirth;
    TableColumn starting_runs_wins;
    TableColumn starting_homeRuns_saves;
    TableColumn starting_runsBattedIn_strikeouts;
    TableColumn starting_battingAverage_WHIP;
    TableColumn starting_estimatedValue;
    TableColumn starting_contract;
    TableColumn starting_salary;
    
    //Taxi Squad label+table
    //VBox taxiSquadTableContainer;
    Label taxiSquadLabel;
    TableView<Player> taxiSquadTable;
    TableColumn taxi_position;
    TableColumn taxi_firstName;
    TableColumn taxi_lastName;
    TableColumn taxi_proTeam;
    TableColumn taxi_positions;
    TableColumn taxi_yearOfBirth;
    TableColumn taxi_runs_wins;
    TableColumn taxi_homeRuns_saves;
    TableColumn taxi_runsBattedIn_strikeouts;
    TableColumn taxi_battingAverage_WHIP;
    TableColumn taxi_estimatedValue;
    TableColumn taxi_contract;
    TableColumn taxi_salary;
    
    //DIALOGS
    TeamDialog teamDialog;
    EditPlayerDialog editDialog;

    public TeamsPane(MainPane mainPane, Wolfieball_PropertyType propertyType) {
        super(mainPane, propertyType);
        initPane();
        teamDialog = new TeamDialog(this.mainPane.getPrimaryStage(), this.mainPane.dataManager.getLeague());
        editDialog = new EditPlayerDialog(mainPane.getPrimaryStage(), this.mainPane.dataManager);
    }
    
    private void initPane(){
        initDraftNameContainer();
        
        initTableUtilityComponent();
        
        initStartingLineupTable();
        
        initTaxiSquadTable();
        
        initHandlers();
    }

    private void initDraftNameContainer() {
        draftNameContainer = new FlowPane();
        draftNameLabel = MainPane.initChildLabel(draftNameContainer, Wolfieball_PropertyType.DRAFT_NAME, MainPane.CLASS_PROMPT_LABEL);
        draftNameTextField = MainPane.initGridTextField(draftNameContainer, MainPane.LARGE_TEXT_FIELD_LENGTH, mainPane.getDataManager().getLeague().getName(), true);
        this.add(draftNameContainer,0,1,20,1);
    }

    private void initTableUtilityComponent(){
        tableUtilityContainer = new FlowPane();
        addTeam = MainPane.initChildButton(tableUtilityContainer, Wolfieball_PropertyType.ADD_ICON, Wolfieball_PropertyType.ADD_ITEM_TOOLTIP, false);
        removeTeam = MainPane.initChildButton(tableUtilityContainer, Wolfieball_PropertyType.MINUS_ICON, Wolfieball_PropertyType.REMOVE_ITEM_TOOLTIP, false);
        editTeam = MainPane.initChildButton(tableUtilityContainer, Wolfieball_PropertyType.EDIT_ICON, Wolfieball_PropertyType.EDIT_ITEM_TOOLTIP, false);
        selectFantasyTeamLabel = MainPane.initChildLabel(tableUtilityContainer, Wolfieball_PropertyType.SELECT_TEAM, MainPane.CLASS_PROMPT_LABEL);
        
        try {
            selectFantasyTeam = MainPane.initGridComboBox(tableUtilityContainer);
        } catch (IOException ex) {
            System.err.println("Error with TeamsPane tableUtilityContainer ComboBox");
        }
        
        this.add(tableUtilityContainer,0,2,20,1);
    }

    private void initStartingLineupTable() {
        this.tableContainer = new VBox();
        tableContainer.getStyleClass().add(MainPane.CLASS_BORDERED_PANE);
        this.startingLineupLabel = MainPane.initChildLabel(this.tableContainer, Wolfieball_PropertyType.STARTING_LINEUP, MainPane.CLASS_PROMPT_LABEL);
        
        this.startingLineupTable = new TableView();
        this.starting_position = new TableColumn("Position");
        starting_firstName = new TableColumn("First");
        starting_lastName = new TableColumn("Last");
        starting_proTeam = new TableColumn("Pro Team");
        starting_positions = new TableColumn("QP");
        starting_yearOfBirth = new TableColumn("Year of Birth");
        starting_runs_wins = new TableColumn("R/W");
        starting_homeRuns_saves = new TableColumn("HR/SV");
        starting_runsBattedIn_strikeouts = new TableColumn("RBI/K");
        starting_battingAverage_WHIP = new TableColumn("BA/WHIP");
        starting_estimatedValue = new TableColumn("Estimated Value");
        starting_contract = new TableColumn("Contract");
        starting_salary = new TableColumn("Salary");
        
        startingLineupTable.getColumns().add(starting_position);
        startingLineupTable.getColumns().add(starting_firstName);
        startingLineupTable.getColumns().add(starting_lastName);
        startingLineupTable.getColumns().add(starting_proTeam);
        startingLineupTable.getColumns().add(starting_positions);
        startingLineupTable.getColumns().add(starting_yearOfBirth);
        startingLineupTable.getColumns().add(starting_runs_wins);
        startingLineupTable.getColumns().add(starting_homeRuns_saves);
        startingLineupTable.getColumns().add(starting_runsBattedIn_strikeouts);
        startingLineupTable.getColumns().add(starting_battingAverage_WHIP);
        startingLineupTable.getColumns().add(starting_estimatedValue);
        startingLineupTable.getColumns().add(starting_contract);
        startingLineupTable.getColumns().add(starting_salary);
        
        this.bindStarterTableColumns();
        
        this.tableContainer.getChildren().add(startingLineupTable);        
    }

    private void initTaxiSquadTable() {
        this.taxiSquadLabel = MainPane.initChildLabel(this.tableContainer, Wolfieball_PropertyType.TAXI_SQUAD, MainPane.CLASS_PROMPT_LABEL);      
        
        this.taxiSquadTable = new TableView();
        this.taxi_position = new TableColumn("Position");
        taxi_firstName = new TableColumn("First");
        taxi_lastName = new TableColumn("Last");
        taxi_proTeam = new TableColumn("Pro Team");
        taxi_positions = new TableColumn("QP");
        taxi_yearOfBirth = new TableColumn("Year of Birth");
        taxi_runs_wins = new TableColumn("R/W");
        taxi_homeRuns_saves = new TableColumn("HR/SV");
        taxi_runsBattedIn_strikeouts = new TableColumn("RBI/K");
        taxi_battingAverage_WHIP = new TableColumn("BA/WHIP");
        taxi_estimatedValue = new TableColumn("Estimated Value");
        taxi_contract = new TableColumn("Contract");
        taxi_salary = new TableColumn("Salary");
        
        taxiSquadTable.getColumns().add(taxi_position);
        taxiSquadTable.getColumns().add(taxi_firstName);
        taxiSquadTable.getColumns().add(taxi_lastName);
        taxiSquadTable.getColumns().add(taxi_proTeam);
        taxiSquadTable.getColumns().add(taxi_positions);
        taxiSquadTable.getColumns().add(taxi_yearOfBirth);
        taxiSquadTable.getColumns().add(taxi_runs_wins);
        taxiSquadTable.getColumns().add(taxi_homeRuns_saves);
        taxiSquadTable.getColumns().add(taxi_runsBattedIn_strikeouts);
        taxiSquadTable.getColumns().add(taxi_battingAverage_WHIP);
        taxiSquadTable.getColumns().add(taxi_estimatedValue);
        taxiSquadTable.getColumns().add(taxi_contract);
        taxiSquadTable.getColumns().add(taxi_salary);
        
        this.bindTaxiTableColumns();
        
        this.tableContainer.getChildren().add(taxiSquadTable);        
        this.add(tableContainer,0,3,20,20);
    }

    private void initHandlers() {
        handler = new FantasyTeamsHandler(this);
        
        draftNameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            String text = draftNameTextField.getText();
            this.mainPane.dataManager.getLeague().setName(text);
        });
        
        addTeam.setOnAction(e -> {
            Team team = teamDialog.showAddTeamDialog();
            if(team != null && teamDialog.getSelection().equals(TeamDialog.COMPLETE)){
                this.mainPane.dataManager.getLeague().getTeams().add(team);
            }
            refreshFantasyTeamComboBox();
        });
        removeTeam.setOnAction(e -> {
            try{
                String teamName = selectFantasyTeam.getSelectionModel().getSelectedItem().toString();
                Team selectedTeam = this.mainPane.dataManager.getLeague().getTeamByName(teamName);
                this.mainPane.dataManager.getLeague().getTeams().remove(selectedTeam);
                refreshFantasyTeamComboBox();
                refreshTables();
            }catch(NullPointerException nfe){
                System.err.println("NullPointerException");
            }
        });
        editTeam.setOnAction(e -> {
            if(currentTeam != null){
                teamDialog.showEditTeamDialog(currentTeam);
                refreshFantasyTeamComboBox();
                refreshTables();
            }
            else{
                System.err.println("Current Team is null! Cannot edit when no teams are available.");
            }
        });
        selectFantasyTeam.setOnAction(e -> {
            try{
                refreshTables();
            }catch(NullPointerException nfe){
                System.err.println("NullPointerException");
            }
        });
        this.startingLineupTable.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                Player p = startingLineupTable.getSelectionModel().getSelectedItem();
                editDialog.showEditPlayerDialog(p, this.mainPane); 
                refreshTables();
            }
        });
        this.taxiSquadTable.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                Player p = startingLineupTable.getSelectionModel().getSelectedItem();
                editDialog.showEditPlayerDialog(p, this.mainPane); 
                refreshTables();
            }
        });
    }
    
    public void refreshFantasyTeamComboBox(){
        ArrayList<Team> teamList = this.mainPane.dataManager.getTeamsInLeague();
        this.selectFantasyTeam.getItems().clear();
        for(Team t: teamList){
            String name = t.getName();
            this.selectFantasyTeam.getItems().add(t.getName());
        }
    }
    
    public void refreshTables(){
        if(selectFantasyTeam.getSelectionModel().getSelectedItem() == null){
            this.startingLineupTable.getItems().removeAll();
            this.taxiSquadTable.getItems().removeAll();
        }
        else{
            String teamName = selectFantasyTeam.getSelectionModel().getSelectedItem().toString();
            currentTeam = this.mainPane.dataManager.getLeague().getTeamByName(teamName);
            this.startingLineupTable.setItems(currentTeam.getStarters().getPlayers());
            this.taxiSquadTable.setItems(currentTeam.getTaxi().getPlayers());
        }
    }
    
    private void bindStarterTableColumns(){
        this.starting_position.setCellValueFactory(new PropertyValueFactory<String, String>("position"));
        this.starting_firstName.setCellValueFactory(new PropertyValueFactory<String, String>("firstName"));
        this.starting_lastName.setCellValueFactory(new PropertyValueFactory<String, String>("lastName"));
        this.starting_proTeam.setCellValueFactory(new PropertyValueFactory<String, String>("proTeam"));
        this.starting_positions.setCellValueFactory(new PropertyValueFactory<String, String>("qualifyingPositions"));
        this.starting_yearOfBirth.setCellValueFactory(new PropertyValueFactory<String, String>("yearOfBirth"));
        
        this.starting_runs_wins.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Player, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Player, String> p) {
                if(p.getValue() instanceof Pitcher){
                    return new SimpleStringProperty(""+((Pitcher)p.getValue()).getWins());
                }
                else if(p.getValue() instanceof Hitter){
                    return new SimpleStringProperty(""+((Hitter)p.getValue()).getRuns());
                }
                else{
                    return null;
                }
            }
        });
        this.starting_homeRuns_saves.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Player, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Player, String> p) {
                if(p.getValue() instanceof Pitcher){
                    return new SimpleStringProperty(""+((Pitcher)p.getValue()).getSaves());
                }
                else if(p.getValue() instanceof Hitter){
                    return new SimpleStringProperty(""+((Hitter)p.getValue()).getHomeRuns());
                }
                else{
                    return null;
                }
            }
        });
        this.starting_runsBattedIn_strikeouts.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Player, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Player, String> p) {
                if(p.getValue() instanceof Pitcher){
                    return new SimpleStringProperty(""+((Pitcher)p.getValue()).getStrikeouts());
                }
                else if(p.getValue() instanceof Hitter){
                    return new SimpleStringProperty(""+((Hitter)p.getValue()).getRunsBattedIn());
                }
                else{
                    return null;
                }
            }
        });
        this.starting_battingAverage_WHIP.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Player, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Player, String> p) {
                if(p.getValue() instanceof Pitcher){
                    if(Double.isNaN(((Pitcher)p.getValue()).getWhip())){
                        return new SimpleStringProperty("No Innings Pitched");
                    }
                    else{
                        return new SimpleStringProperty(""+((MainPane.WhipEra.format(((Pitcher)p.getValue()).getWhip()))));
                    }
                }
                else if(p.getValue() instanceof Hitter){
                    if(Double.isNaN(((Hitter)p.getValue()).getBattingAverage())){
                        return new SimpleStringProperty("Never Been At Bat");
                    }
                    else{
                        return new SimpleStringProperty(""+(MainPane.battingAvg.format(((Hitter)p.getValue()).getBattingAverage())));
                    }
                }
                else{
                    System.err.println("Problem in PlayerPane playersTable");
                   return null;
                }
            }
        });
        
        starting_estimatedValue.setCellValueFactory(new PropertyValueFactory<String, Double>("estimatedValue"));
        starting_contract.setCellValueFactory(new PropertyValueFactory<String, Contract>("contract"));
        starting_salary.setCellValueFactory(new PropertyValueFactory<String, Integer>("salary"));
    }
    
        private void bindTaxiTableColumns(){
        this.taxi_position.setCellValueFactory(new PropertyValueFactory<String, String>("position"));
        this.taxi_firstName.setCellValueFactory(new PropertyValueFactory<String, String>("firstName"));
        this.taxi_lastName.setCellValueFactory(new PropertyValueFactory<String, String>("lastName"));
        this.taxi_proTeam.setCellValueFactory(new PropertyValueFactory<String, String>("proTeam"));
        this.taxi_positions.setCellValueFactory(new PropertyValueFactory<String, String>("qualifyingPositions"));
        this.taxi_yearOfBirth.setCellValueFactory(new PropertyValueFactory<String, String>("yearOfBirth"));
        
        this.taxi_runs_wins.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Player, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Player, String> p) {
                if(p.getValue() instanceof Pitcher){
                    return new SimpleStringProperty(""+((Pitcher)p.getValue()).getWins());
                }
                else if(p.getValue() instanceof Hitter){
                    return new SimpleStringProperty(""+((Hitter)p.getValue()).getRuns());
                }
                else{
                    return null;
                }
            }
        });
        this.taxi_homeRuns_saves.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Player, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Player, String> p) {
                if(p.getValue() instanceof Pitcher){
                    return new SimpleStringProperty(""+((Pitcher)p.getValue()).getSaves());
                }
                else if(p.getValue() instanceof Hitter){
                    return new SimpleStringProperty(""+((Hitter)p.getValue()).getHomeRuns());
                }
                else{
                    return null;
                }
            }
        });
        this.taxi_runsBattedIn_strikeouts.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Player, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Player, String> p) {
                if(p.getValue() instanceof Pitcher){
                    return new SimpleStringProperty(""+((Pitcher)p.getValue()).getStrikeouts());
                }
                else if(p.getValue() instanceof Hitter){
                    return new SimpleStringProperty(""+((Hitter)p.getValue()).getRunsBattedIn());
                }
                else{
                    return null;
                }
            }
        });
        this.taxi_battingAverage_WHIP.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Player, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Player, String> p) {
                if(p.getValue() instanceof Pitcher){
                    if(Double.isNaN(((Pitcher)p.getValue()).getWhip())){
                        return new SimpleStringProperty("No Innings Pitched");
                    }
                    else{
                        return new SimpleStringProperty(""+((MainPane.WhipEra.format(((Pitcher)p.getValue()).getWhip()))));
                    }
                }
                else if(p.getValue() instanceof Hitter){
                    if(Double.isNaN(((Hitter)p.getValue()).getBattingAverage())){
                        return new SimpleStringProperty("Never Been At Bat");
                    }
                    else{
                        return new SimpleStringProperty(""+(MainPane.battingAvg.format(((Hitter)p.getValue()).getBattingAverage())));
                    }
                }
                else{
                    System.err.println("Problem in PlayerPane playersTable");
                   return null;
                }
            }
        });
        
        taxi_estimatedValue.setCellValueFactory(new PropertyValueFactory<String, Double>("estimatedValue"));
        taxi_contract.setCellValueFactory(new PropertyValueFactory<String, Contract>("contract"));
        taxi_salary.setCellValueFactory(new PropertyValueFactory<String, Integer>("salary"));
    }
    
    
}
