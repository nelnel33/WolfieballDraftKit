/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wolfieballdraftkit.gui.dialogs;

import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import wolfieballdraftkit.WolfieballDataManager;
import wolfieballdraftkit.Wolfieball_StartupConstant;
import wolfieballdraftkit.entitystructures.League;
import wolfieballdraftkit.entitystructures.Player;
import wolfieballdraftkit.entitystructures.Team;
import wolfieballdraftkit.enums.Contract;
import wolfieballdraftkit.gui.MainPane;

/**
 *
 * @author Nelnel33
 */
public class EditPlayerDialog extends Stage{
    League league; //League that contains all fantasy teams
    WolfieballDataManager dataManager;
    
    // GUI COMPONENTS FOR OUR DIALOG
    GridPane gridPane;
    Scene dialogScene;
    
    //UNEDITABLE INFO
    Label headingLabel;
    HBox pictureInfoBoxContainer;
    VBox flagNamePositionContainer;
    Label playerPicture;
    Label countryFlag;
    Label playerName;
    Label playerQP;
    
    //EDITABLE INFO
    Label fantasyTeamLabel;
    ComboBox fantasyTeamComboBox;
    Label positionLabel;
    ComboBox positionComboBox;
    Label contractLabel;
    ComboBox contractComboBox;
    Label salaryLabel;
    TextField salaryTextField; 
    Button completeButton;
    Button cancelButton;
    
    // THIS IS FOR KEEPING TRACK OF WHICH BUTTON THE USER PRESSED
    String selection;
    
    // CONSTANTS FOR OUR UI
    public static final String COMPLETE = "Complete";
    public static final String CANCEL = "Cancel";
    public static final String FANTASY_TEAM_PROMPT = "Fantasy Team: ";
    public static final String POSITION_PROMPT = "Position: ";
    public static final String CONTRACT_PROMPT = "Contract: ";
    public static final String SALARY_PROMPT = "Salary($$$): ";    
    public static final String DIALOG_HEADING = "Edit Player";
    public static final String DIALOG_TITLE = "Player Details";
    /**
     * Initializes this dialog so that it can be used for either adding
     * new schedule items or editing existing ones.
     * 
     * @param primaryStage The owner of this modal dialog.
     * @param course
     * @param messageDialog
     */
    public EditPlayerDialog(Stage primaryStage, WolfieballDataManager dataManager) {       
        // MAKE THIS DIALOG MODAL, MEANING OTHERS WILL WAIT
        // FOR IT WHEN IT IS DISPLAYED
        initModality(Modality.WINDOW_MODAL);
        initOwner(primaryStage);
        
        this.dataManager = dataManager;
        this.league = dataManager.getLeague();
        
        // FIRST OUR CONTAINER
        gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 20, 20, 20));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        
        // PUT THE HEADING IN THE GRID, NOTE THAT THE TEXT WILL DEPEND
        // ON WHETHER WE'RE ADDING OR EDITING
        headingLabel = new Label(DIALOG_TITLE);
        headingLabel.getStyleClass().add(MainPane.CLASS_HEADING_LABEL);

        headingLabel = new Label(EditPlayerDialog.DIALOG_TITLE);
        pictureInfoBoxContainer = new HBox();
        flagNamePositionContainer = new VBox();
        playerPicture = new Label();
        countryFlag = new Label();
        playerName = new Label();
        playerQP = new Label();
    
        fantasyTeamLabel = new Label(EditPlayerDialog.FANTASY_TEAM_PROMPT);
        fantasyTeamComboBox = new ComboBox();
        positionLabel = new Label(EditPlayerDialog.POSITION_PROMPT);
        positionComboBox = new ComboBox();
        contractLabel = new Label(EditPlayerDialog.CONTRACT_PROMPT);
        contractComboBox = new ComboBox();
        salaryLabel = new Label(EditPlayerDialog.SALARY_PROMPT);
        salaryTextField = new TextField(); 

        // AND FINALLY, THE BUTTONS
        completeButton = new Button(COMPLETE);
        cancelButton = new Button(CANCEL);
        
        // REGISTER EVENT HANDLERS FOR OUR BUTTONS
        EventHandler completeCancelHandler = (EventHandler<ActionEvent>) (ActionEvent ae) -> {
            Button sourceButton = (Button)ae.getSource();
            EditPlayerDialog.this.selection = sourceButton.getText();
            EditPlayerDialog.this.hide();
        };
        completeButton.setOnAction(completeCancelHandler);
        cancelButton.setOnAction(completeCancelHandler);

        // NOW LET'S ARRANGE THEM ALL AT ONCE
        gridPane.add(headingLabel, 0, 0, 2, 1);
        gridPane.add(this.pictureInfoBoxContainer, 0, 1, 1, 1);        
        flagNamePositionContainer.getChildren().add(countryFlag);
        flagNamePositionContainer.getChildren().add(playerName);
        flagNamePositionContainer.getChildren().add(playerQP);
        pictureInfoBoxContainer.getChildren().add(playerPicture);
        pictureInfoBoxContainer.getChildren().add(flagNamePositionContainer);
        gridPane.add(this.fantasyTeamLabel, 0,2,1,1);
        gridPane.add(this.fantasyTeamComboBox, 1,2,1,1);
        gridPane.add(this.positionLabel, 0,3,1,1);
        gridPane.add(this.positionComboBox, 1,3,1,1);
        gridPane.add(this.contractLabel, 0,4,1,1);
        gridPane.add(this.contractComboBox, 1,4,1,1);
        gridPane.add(this.salaryLabel, 0,5,1,1);
        gridPane.add(this.salaryTextField, 1,5,1,1);        
        gridPane.add(completeButton, 0, 6, 1, 1);
        gridPane.add(cancelButton, 1, 6, 1, 1);

        // AND PUT THE GRID PANE IN THE WINDOW
        dialogScene = new Scene(gridPane);
        dialogScene.getStylesheets().add(MainPane.PRIMARY_STYLE_SHEET);
        this.setScene(dialogScene);
    }
    
    public String getSelection() {
        return selection;
    }
    
    public void showEditPlayerDialog(Player playerToEdit, MainPane pane){
        if(playerToEdit == null){
            System.err.println("EditPlayerDialog.showEditPlayerDialog|playerToEdit is null(not avaliable)");
        }
        else {
            //INIT ALL THE UNEDITABLE INFO
            String playerImagePath = Wolfieball_StartupConstant.PATH_PLAYERS
                    + playerToEdit.getLastName() + playerToEdit.getFirstName() + ".jpg";
            String nationImagePath = Wolfieball_StartupConstant.PATH_FLAGS + playerToEdit.getNation() + ".png";
            String playerName = playerToEdit.getFirstName() + " " + playerToEdit.getLastName();
            String playerQP = playerToEdit.getQualifyingPositions();
            MainPane.setGraphicOnLabel(this.playerPicture, playerImagePath);
            MainPane.setGraphicOnLabel(this.countryFlag, nationImagePath);
            this.playerName.setText(playerName);
            this.playerQP.setText(playerQP);

            //ADD INFO TO COMBOBOXES + TEXTFIELDS
            loadFantasyTeamsComboBox();
            loadPositionComboBox(playerToEdit);
            loadContractComboBox();
            
            try {
                this.salaryTextField.setText("" + playerToEdit.getSalary());
            } catch (NullPointerException npe) {
                this.salaryTextField.setText("");
            }

            this.showAndWait();

            if (selection != null && selection.equals(EditPlayerDialog.COMPLETE)
                    && positionComboBox.getSelectionModel().getSelectedItem() != null
                    && contractComboBox.getSelectionModel().getSelectedItem() != null
                    && !salaryTextField.getText().equals("")
                    && fantasyTeamComboBox.getSelectionModel().getSelectedItem()!=null
                    ) {
                try {
                    String position = positionComboBox.getSelectionModel().getSelectedItem().toString();
                    Contract contract = Contract.valueOf(contractComboBox.getSelectionModel().getSelectedItem().toString());
                    int salary = Integer.parseInt(salaryTextField.getText());
                    Team team = league.getTeamByName(fantasyTeamComboBox.getSelectionModel().getSelectedItem().toString());
                    
                    if(contract.equals(Contract.S1)){
                        contract = Contract.S2;
                    }
                   
                    assignPlayerToTeam(playerToEdit, position, contract, salary, team);                  
                    
                } catch (NumberFormatException nfe) {
                    //Salary TextField DOES NOT contain a number!
                    //Put error message here
                    System.err.println("YOU ENTERED IN AN ILLEGAL CHARACTER!");
                }
            }
            else{
                System.err.println("EditPlayerDialog. One of the fields is INVALID");
                //ADD ERROR DIALOG.
            }
        }
    }
    
    private void loadPositionComboBox(Player playerToEdit){
        positionComboBox.getItems().clear();
        loadComboBox(this.positionComboBox, playerToEdit.qualifyingPositionsInStringArray());
    }
    
    private void loadFantasyTeamsComboBox(){
        fantasyTeamComboBox.getItems().clear();
        fantasyTeamComboBox.getItems().add("UNDRAFTED");
        for(Team t: league.getTeams()){
            this.fantasyTeamComboBox.getItems().add(t.getName());
        }
    }
    
    private void loadContractComboBox(){
        this.contractComboBox.getItems().clear();
        this.contractComboBox.getItems().add(Contract.UNDRAFTED);
        this.contractComboBox.getItems().add(Contract.S1);
        this.contractComboBox.getItems().add(Contract.S2);
        this.contractComboBox.getItems().add(Contract.X);        
    }
    
    private void loadComboBox(ComboBox cb, ArrayList<String> s){
        for(String z: s){
            cb.getItems().add(z);
        }
    }
    
    private void assignPlayerToTeam(Player toAssign, String dialogPosition, Contract dialogContract, int dialogSalary, Team dialogTeam){
        if(dialogContract.equals(Contract.UNDRAFTED) || fantasyTeamComboBox.getSelectionModel().getSelectedItem().equals("UNDRAFTED")){
            if(toAssign.getTeam() != null){
                toAssign.getTeam().removePlayer(toAssign.getFirstName(), toAssign.getLastName(), dataManager.getFullDraftablePlayers());
                toAssign.setDraftAttributes(dialogPosition, dialogContract, dialogSalary, dialogTeam);
            }
        }
        else{
            try{
                if(!toAssign.getTeam().equals(dialogTeam)){
                    toAssign.setDraftAttributes(dialogPosition, dialogContract, dialogSalary, dialogTeam);
                    dialogTeam.addPlayer(toAssign, dataManager.getFullDraftablePlayers());
                }
                else{
                    toAssign.setDraftAttributes(dialogPosition, dialogContract, dialogSalary, dialogTeam);
                }
            }catch(NullPointerException npe){
                toAssign.setDraftAttributes(dialogPosition, dialogContract, dialogSalary, dialogTeam);
                dialogTeam.addPlayer(toAssign, dataManager.getFullDraftablePlayers());
            }
        }
    }
}
