/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wolfieballdraftkit.gui.dialogs;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import wolfieballdraftkit.entitystructures.League;
import wolfieballdraftkit.entitystructures.Team;
import wolfieballdraftkit.gui.MainPane;

/**
 *
 * @author Nelnel33
 */
public class TeamDialog extends Stage{
    League league;
    
    Scene dialogScene;    
    GridPane gridPane;
    
    //DIALOG PROMPT INFO
    Label headingLabel;
    TextField nameTextField;
    Label nameLabel;
    TextField ownerTextField;
    Label ownerLabel;
    Button completeButton;
    Button cancelButton;
    
    // THIS IS FOR KEEPING TRACK OF WHICH BUTTON THE USER PRESSED
    String selection;
    
    // CONSTANTS FOR OUR UI
    public static final String COMPLETE = "Complete";
    public static final String CANCEL = "Cancel";
    public static final String NAME_PROMPT = "Name: ";
    public static final String OWNER_PROMPT = "Owner: ";
    public static final String EDIT_TEAM = "Edit Fantasy Team";
    public static final String ADD_TEAM = "Add Fantasy Team";
    public static final String HEADING = "Fantasy Team Details";
    
        public TeamDialog(Stage primaryStage, League league) {       
        // MAKE THIS DIALOG MODAL, MEANING OTHERS WILL WAIT
        // FOR IT WHEN IT IS DISPLAYED
        initModality(Modality.WINDOW_MODAL);
        initOwner(primaryStage);
        
        this.league = league;
        
        // FIRST OUR CONTAINER
        gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 20, 20, 20));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        
        // PUT THE HEADING IN THE GRID, NOTE THAT THE TEXT WILL DEPEND
        // ON WHETHER WE'RE ADDING OR EDITING
        headingLabel = new Label(HEADING);
        headingLabel.getStyleClass().add(MainPane.CLASS_HEADING_LABEL);
        headingLabel = new Label(EditPlayerDialog.DIALOG_TITLE);
               
        nameTextField = new TextField();
        nameLabel = new Label(NAME_PROMPT);
        nameLabel.getStyleClass().add(MainPane.CLASS_PROMPT_LABEL);
        ownerTextField = new TextField();
        ownerLabel = new Label(OWNER_PROMPT);
        ownerLabel.getStyleClass().add(MainPane.CLASS_PROMPT_LABEL);

        // AND FINALLY, THE BUTTONS
        completeButton = new Button(COMPLETE);
        cancelButton = new Button(CANCEL);
        
        // REGISTER EVENT HANDLERS FOR OUR BUTTONS
        EventHandler completeCancelHandler = (EventHandler<ActionEvent>) (ActionEvent ae) -> {
            Button sourceButton = (Button)ae.getSource();
            TeamDialog.this.selection = sourceButton.getText();
            TeamDialog.this.hide();
        };
        completeButton.setOnAction(completeCancelHandler);
        cancelButton.setOnAction(completeCancelHandler);

        // NOW LET'S ARRANGE THEM ALL AT ONCE
        gridPane.add(headingLabel, 0, 0, 2, 1);
        gridPane.add(this.nameLabel, 0, 1, 1, 1);        
        gridPane.add(this.nameTextField, 1, 1, 1, 1);
        gridPane.add(this.ownerLabel, 0, 2, 1, 1);
        gridPane.add(this.ownerTextField, 1, 2, 1, 1);      
        gridPane.add(completeButton, 0, 3, 1, 1);
        gridPane.add(cancelButton, 1, 3, 1, 1);

        // AND PUT THE GRID PANE IN THE WINDOW
        dialogScene = new Scene(gridPane);
        dialogScene.getStylesheets().add(MainPane.PRIMARY_STYLE_SHEET);
        this.setScene(dialogScene);
    }
            
    public String getSelection() {
        return selection;
    }
        
    public Team showAddTeamDialog(){
        this.showAndWait();
        Team team;
        String teamName = this.nameTextField.getText();
        String ownerName = this.ownerTextField.getText();
        
        if(teamName == null || ownerName == null || teamName.equals("") || ownerName.equals("")){
            return null;
        }
        else{
            team = new Team(teamName, ownerName);
            return team;
        }
    }
    
    public void showEditTeamDialog(Team teamToEdit){
        this.nameTextField.setText(teamToEdit.getName());
        this.ownerTextField.setText(teamToEdit.getOwner());
        
        this.showAndWait();
        
        String teamName = this.nameTextField.getText();
        String ownerName = this.ownerTextField.getText();
        
        if(teamName.equals("") || ownerName.equals("") && !selection.equals(TeamDialog.COMPLETE)){
            //DOES NOT ADD A TEAM(WILL IMPLEMENT ERROR MESSAGE)
        }
        else{
            teamToEdit.setName(teamName);
            teamToEdit.setOwner(ownerName);
        }
    }
    
}
