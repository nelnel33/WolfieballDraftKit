/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wolfieballdraftkit.gui;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import static javafx.scene.input.KeyCode.S;
import static javafx.scene.input.KeyCode.T;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import javafx.util.Pair;
import wolfieballdraftkit.Wolfieball_PropertyType;
import wolfieballdraftkit.entitystructures.Hitter;
import wolfieballdraftkit.entitystructures.Pitcher;
import wolfieballdraftkit.entitystructures.Player;
import wolfieballdraftkit.events.PlayersButtonHandler;
import wolfieballdraftkit.gui.dialogs.AddPlayerDialog;
import wolfieballdraftkit.gui.dialogs.EditPlayerDialog;

/**
 *
 * @author Nelnel33
 */
public class PlayersPane extends TitlePane{       
    //EVENT HANDLER FOR PLAYERSPANE
    PlayersButtonHandler handler;
    
    //Container for add/remove/search
    FlowPane tableUtilityContainer;
    Button addPlayer;
    Button removePlayer;
    Label searchLabel;
    TextField searchTextField;
    
    //Container for radio filter buttons
    FlowPane filterButtonContainer;
    ToggleGroup positionButtonGrouper;
    RadioButton allButton;
    RadioButton catcherButton;
    RadioButton firstBasemenButton;
    RadioButton thirdBasemenButton;
    RadioButton cornerInfielderButton;
    RadioButton secondBasemenButton;
    RadioButton shortstopButton;
    RadioButton middleInfielderButton;
    RadioButton outfielderButton;
    RadioButton utilityPlayerButton;
    RadioButton pitcherButton;
    
    TableView<Player> playerTable;
    TableColumn firstName;
    TableColumn lastName;
    TableColumn proTeam;
    TableColumn positions;
    TableColumn yearOfBirth;
    TableColumn runs_wins;
    TableColumn homeRuns_saves;
    TableColumn runsBattedIn_strikeouts;
    TableColumn battingAverage_WHIP;
    TableColumn estimatedValue;
    TableColumn notes;
    
    TextField noteEditor;
    
    //DIALOGS
    AddPlayerDialog addPlayerDialog;
    EditPlayerDialog editPlayerDialog;
    
    
    public PlayersPane(MainPane mainPane, Wolfieball_PropertyType propertyType){
        super(mainPane, propertyType);        
        initPane();
    }
    
    private void initPane(){       
        initTableUtilityComponents();
        
        initTableRadioButtonComponents();
        
        initPlayerTable();
        
        initDialogs();
        
        initHandlers();
       
    }
    
    private void initTableUtilityComponents(){
        tableUtilityContainer = new FlowPane();
        addPlayer = MainPane.initChildButton(tableUtilityContainer, Wolfieball_PropertyType.ADD_ICON, Wolfieball_PropertyType.ADD_ITEM_TOOLTIP, false);
        removePlayer = MainPane.initChildButton(tableUtilityContainer, Wolfieball_PropertyType.MINUS_ICON, Wolfieball_PropertyType.REMOVE_ITEM_TOOLTIP, false);
        searchLabel = MainPane.initChildLabel(tableUtilityContainer, Wolfieball_PropertyType.SEARCH_TEXTFIELD_LABEL, MainPane.CLASS_PROMPT_LABEL);
        searchTextField = MainPane.initGridTextField(tableUtilityContainer, MainPane.LARGE_TEXT_FIELD_LENGTH, "", true);
        this.add(tableUtilityContainer,0,1,20,1);
    }
    
    private void initTableRadioButtonComponents(){
        filterButtonContainer = new FlowPane();
        positionButtonGrouper = new ToggleGroup();        
        allButton = MainPane.initAndGroupRadioButton(filterButtonContainer, Wolfieball_PropertyType.ALL_RBUTTON, positionButtonGrouper);
        allButton.setSelected(true);
        catcherButton = MainPane.initAndGroupRadioButton(filterButtonContainer, Wolfieball_PropertyType.C_RBUTTON, positionButtonGrouper);
        firstBasemenButton = MainPane.initAndGroupRadioButton(filterButtonContainer, Wolfieball_PropertyType.B1_RBUTTON, positionButtonGrouper);
        thirdBasemenButton = MainPane.initAndGroupRadioButton(filterButtonContainer, Wolfieball_PropertyType.B3_RBUTTON, positionButtonGrouper);
        cornerInfielderButton = MainPane.initAndGroupRadioButton(filterButtonContainer, Wolfieball_PropertyType.CI_RBUTTON, positionButtonGrouper);
        secondBasemenButton = MainPane.initAndGroupRadioButton(filterButtonContainer, Wolfieball_PropertyType.B2_RBUTTON, positionButtonGrouper);
        shortstopButton = MainPane.initAndGroupRadioButton(filterButtonContainer, Wolfieball_PropertyType.SS_RBUTTON, positionButtonGrouper);
        middleInfielderButton = MainPane.initAndGroupRadioButton(filterButtonContainer, Wolfieball_PropertyType.MI_RBUTTON, positionButtonGrouper);
        outfielderButton = MainPane.initAndGroupRadioButton(filterButtonContainer, Wolfieball_PropertyType.OF_RBUTTON, positionButtonGrouper);
        utilityPlayerButton = MainPane.initAndGroupRadioButton(filterButtonContainer, Wolfieball_PropertyType.U_RBUTTON, positionButtonGrouper);
        pitcherButton = MainPane.initAndGroupRadioButton(filterButtonContainer, Wolfieball_PropertyType.P_RBUTTON, positionButtonGrouper);
        filterButtonContainer.getStyleClass().add(MainPane.CLASS_BORDERED_PANE);
        this.add(filterButtonContainer,0,2,20,1);
        
    }
    
    private void initPlayerTable(){
        playerTable = new TableView();
        firstName = new TableColumn("First");
        lastName = new TableColumn("Last");
        proTeam = new TableColumn("Pro Team");
        positions = new TableColumn("QP");
        yearOfBirth = new TableColumn("Year of Birth");
        runs_wins = new TableColumn("R/W");
        homeRuns_saves = new TableColumn("HR/SV");
        runsBattedIn_strikeouts = new TableColumn("RBI/K");
        battingAverage_WHIP = new TableColumn("BA/WHIP");
        estimatedValue = new TableColumn("Estimated Value");
        notes = new TableColumn("Notes");
        noteEditor = new TextField();
        
        //TODO: LINK ALL COLUMNS TO CORRESPONDING PLAYER ATTRIBUTE!!
        //i.e: itemDescriptionsColumn.setCellValueFactory(new PropertyValueFactory<String, String>("description"));
        
        firstName.setCellValueFactory(new PropertyValueFactory<String, String>("firstName"));
        lastName.setCellValueFactory(new PropertyValueFactory<String, String>("lastName"));
        proTeam.setCellValueFactory(new PropertyValueFactory<String, String>("proTeam"));
        positions.setCellValueFactory(new PropertyValueFactory<String, String>("qualifyingPositions"));
        yearOfBirth.setCellValueFactory(new PropertyValueFactory<String, String>("yearOfBirth"));
        
        runs_wins.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Player, String>, ObservableValue<String>>() {
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
        homeRuns_saves.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Player, String>, ObservableValue<String>>() {
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
        runsBattedIn_strikeouts.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Player, String>, ObservableValue<String>>() {
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
        battingAverage_WHIP.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Player, String>, ObservableValue<String>>() {
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
        
        estimatedValue.setCellValueFactory(new PropertyValueFactory<Integer, Integer>("estimatedValue"));
        
        notes.setCellValueFactory(new PropertyValueFactory<Player, String>("notes"));
        playerTable.setEditable(true);
        
        playerTable.getColumns().add(firstName);
        playerTable.getColumns().add(lastName);
        playerTable.getColumns().add(proTeam);
        playerTable.getColumns().add(positions);
        playerTable.getColumns().add(yearOfBirth);
        playerTable.getColumns().add(runs_wins);
        playerTable.getColumns().add(homeRuns_saves);
        playerTable.getColumns().add(runsBattedIn_strikeouts);
        playerTable.getColumns().add(battingAverage_WHIP);
        playerTable.getColumns().add(estimatedValue);
        playerTable.getColumns().add(notes);
        
        playerTable.setItems(mainPane.getDataManager().getShownOnTableDraftablePlayers().getPlayers());

        this.add(playerTable, 0,3,20,20);
    }

    private void initDialogs(){
        addPlayerDialog = new AddPlayerDialog(mainPane.getPrimaryStage());
        editPlayerDialog = new EditPlayerDialog(mainPane.getPrimaryStage(), mainPane.getDataManager());
    }
   
    private void initHandlers(){
        handler = new PlayersButtonHandler(this);

        addPlayer.setOnAction(e -> {
            Player p = addPlayerDialog.showAddPlayerDialog();
            this.mainPane.getDataManager().getFullDraftablePlayers().addPlayer(p);
        });
        removePlayer.setOnAction(e -> {
            Player p = playerTable.getSelectionModel().getSelectedItem();
            this.mainPane.getDataManager().getFullDraftablePlayers().removePlayer(p);
            this.mainPane.getDataManager().restorePlayerList();
            playerTable.setItems(this.mainPane.getDataManager().getShownOnTableDraftablePlayers().getPlayers());
        });
        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            handler.handleSearchRequest(searchTextField.getText());
        });
        allButton.setOnAction(e -> {
            handler.handleRadioButtonFilterRequest(allButton);
        });
        catcherButton.setOnAction(e -> {
            handler.handleRadioButtonFilterRequest(catcherButton);
        });
        firstBasemenButton.setOnAction(e -> {
            handler.handleRadioButtonFilterRequest(firstBasemenButton);
        });
        thirdBasemenButton.setOnAction(e -> {
            handler.handleRadioButtonFilterRequest(thirdBasemenButton);
        });
        cornerInfielderButton.setOnAction(e -> {
            handler.handleRadioButtonFilterRequest(cornerInfielderButton);
        });
        secondBasemenButton.setOnAction(e -> {
            handler.handleRadioButtonFilterRequest(secondBasemenButton);
        });
        shortstopButton.setOnAction(e -> {
            handler.handleRadioButtonFilterRequest(shortstopButton);
        });
        middleInfielderButton.setOnAction(e -> {
            handler.handleRadioButtonFilterRequest(middleInfielderButton);
        });
        outfielderButton.setOnAction(e -> {
            handler.handleRadioButtonFilterRequest(outfielderButton);
        });
        utilityPlayerButton.setOnAction(e -> {
            handler.handleRadioButtonFilterRequest(utilityPlayerButton);
        });
        pitcherButton.setOnAction(e -> {
            handler.handleRadioButtonFilterRequest(pitcherButton);
        });
    
        playerTable.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                Player p = playerTable.getSelectionModel().getSelectedItem();
                editPlayerDialog.showEditPlayerDialog(p, this.mainPane);
                notes.setCellFactory(TextFieldTableCell.forTableColumn()); 
                refreshTable();
            }
        });
        notes.setOnEditCommit(
                new EventHandler<CellEditEvent<Player, String>>() {
                    @Override
                    public void handle(CellEditEvent<Player, String> t) {
                        ((Player) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())).setNotes(t.getNewValue());
                    }
                }
        );
        
    }
    
    public void setToPitcherTable(){
        runs_wins.setText("W");
        homeRuns_saves.setText("SV");
        runsBattedIn_strikeouts.setText("K");
        battingAverage_WHIP.setText("WHIP");
    }
    
    public void setToHitterTable(){
        runs_wins.setText("R");
        homeRuns_saves.setText("HR");
        runsBattedIn_strikeouts.setText("RBI");
        battingAverage_WHIP.setText("BA");
    }
    
    public void setToDefaultTable(){
        runs_wins.setText("R/W");
        homeRuns_saves.setText("HR/SV");
        runsBattedIn_strikeouts.setText("RBI/K");
        battingAverage_WHIP.setText("BA/WHIP");
    }
    
    public void setTableItemsTo(ObservableList<Player> players){
        playerTable.setItems(players);
    }
    
    public void refreshTable(){
        this.mainPane.dataManager.restorePlayerList();
        playerTable.setItems(this.mainPane.dataManager.getShownOnTableDraftablePlayers().getPlayers());
    }
    
}



