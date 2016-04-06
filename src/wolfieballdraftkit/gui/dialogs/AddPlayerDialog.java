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
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import properties_manager.PropertiesManager;
import wolfieballdraftkit.WolfieballDataManager;
import wolfieballdraftkit.entitystructures.Hitter;
import wolfieballdraftkit.entitystructures.League;
import wolfieballdraftkit.entitystructures.Pitcher;
import wolfieballdraftkit.entitystructures.Player;
import wolfieballdraftkit.gui.MainPane;

/**
 *
 * @author Nelnel33
 */
public class AddPlayerDialog extends Stage{
    public static final int DEFAULT_YOB = 1990;
    
    Player player; //Player being added
    
    // GUI COMPONENTS FOR OUR DIALOG
    GridPane gridPane;
    Scene dialogScene;
    Label headingLabel;
    Label firstNameLabel;
    TextField firstNameTextField;
    Label lastNameLabel;
    TextField lastNameTextField;
    Label proTeamLabel;
    ComboBox proTeamComboBox;
    
    FlowPane checkBoxContainer;
    CheckBox position_C;
    Label label_C;
    CheckBox position_1B;
    Label label_1B;
    CheckBox position_3B;
    Label label_3B;
    CheckBox position_2B;
    Label label_2B;
    CheckBox position_SS;
    Label label_SS;
    CheckBox position_OF;
    Label label_OF;
    CheckBox position_P;
    Label label_P;   
    Button completeButton;
    Button cancelButton;
    
    // THIS IS FOR KEEPING TRACK OF WHICH BUTTON THE USER PRESSED
    String selection;
    
    // CONSTANTS FOR OUR UI
    public static final String COMPLETE = "Complete";
    public static final String CANCEL = "Cancel";
    public static final String FIRST_NAME_PROMPT = "First Name: ";
    public static final String LAST_NAME_PROMPT = "Last Name: ";
    public static final String PRO_TEAM_PROMPT = "Pro Team: ";
    public static final String DIALOG_HEADING = "Add New Player";
    public static final String DIALOG_TITLE = "Player Details";
    /**
     * Initializes this dialog so that it can be used for either adding
     * new schedule items or editing existing ones.
     * 
     * @param primaryStage The owner of this modal dialog.
     * @param course
     * @param messageDialog
     */
    public AddPlayerDialog(Stage primaryStage) {       
        // MAKE THIS DIALOG MODAL, MEANING OTHERS WILL WAIT
        // FOR IT WHEN IT IS DISPLAYED
        initModality(Modality.WINDOW_MODAL);
        initOwner(primaryStage);
        
        // FIRST OUR CONTAINER
        gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 20, 20, 20));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        
        // PUT THE HEADING IN THE GRID, NOTE THAT THE TEXT WILL DEPEND
        // ON WHETHER WE'RE ADDING OR EDITING
        headingLabel = new Label(DIALOG_TITLE);
        headingLabel.getStyleClass().add(MainPane.CLASS_HEADING_LABEL);
        
        firstNameLabel = new Label(AddPlayerDialog.FIRST_NAME_PROMPT);
        firstNameTextField = new TextField();
        lastNameLabel = new Label(AddPlayerDialog.LAST_NAME_PROMPT);
        lastNameTextField = new TextField();
        proTeamLabel = new Label(AddPlayerDialog.PRO_TEAM_PROMPT);
        proTeamComboBox = new ComboBox();
        loadCombobox(proTeamComboBox, WolfieballDataManager.PRO_TEAMS);
        checkBoxContainer = new FlowPane();
        position_C = new CheckBox();
        label_C = new Label("C");
        position_1B = new CheckBox();
        label_1B = new Label("1B");
        position_3B = new CheckBox();
        label_3B = new Label("3B");
        position_2B = new CheckBox();
        label_2B = new Label("2B");
        position_SS = new CheckBox();
        label_SS = new Label("SS");
        position_OF = new CheckBox();
        label_OF = new Label("OF");
        position_P = new CheckBox();
        label_P = new Label("P");
        completeButton = new Button(AddPlayerDialog.COMPLETE);
        cancelButton = new Button(AddPlayerDialog.CANCEL);

        // AND FINALLY, THE BUTTONS
        completeButton = new Button(COMPLETE);
        cancelButton = new Button(CANCEL);
        
        // REGISTER EVENT HANDLERS FOR OUR BUTTONS
        EventHandler completeCancelHandler = (EventHandler<ActionEvent>) (ActionEvent ae) -> {
            Button sourceButton = (Button)ae.getSource();
            AddPlayerDialog.this.selection = sourceButton.getText();
            AddPlayerDialog.this.hide();
        };
        completeButton.setOnAction(completeCancelHandler);
        cancelButton.setOnAction(completeCancelHandler);

        // NOW LET'S ARRANGE THEM ALL AT ONCE
        gridPane.add(headingLabel, 0, 0, 2, 1);
        gridPane.add(this.firstNameLabel, 0, 1, 1, 1);
        gridPane.add(this.firstNameTextField, 1, 1, 1, 1);
        gridPane.add(this.lastNameLabel, 0, 2, 1, 1);
        gridPane.add(this.lastNameTextField, 1, 2, 1, 1);
        gridPane.add(this.proTeamLabel, 0, 3, 1, 1);
        gridPane.add(this.proTeamComboBox, 1, 3, 1, 1);
        checkBoxContainer.getChildren().add(this.label_C);
        checkBoxContainer.getChildren().add(this.position_C);
        checkBoxContainer.getChildren().add(this.label_1B);
        checkBoxContainer.getChildren().add(this.position_1B);
        checkBoxContainer.getChildren().add(this.label_3B);
        checkBoxContainer.getChildren().add(this.position_3B);
        checkBoxContainer.getChildren().add(this.label_2B);
        checkBoxContainer.getChildren().add(this.position_2B);
        checkBoxContainer.getChildren().add(this.label_SS);
        checkBoxContainer.getChildren().add(this.position_SS);
        checkBoxContainer.getChildren().add(this.label_OF);
        checkBoxContainer.getChildren().add(this.position_OF);
        checkBoxContainer.getChildren().add(this.label_P);
        checkBoxContainer.getChildren().add(this.position_P);
        gridPane.add(this.checkBoxContainer, 0,4,20,1);

        gridPane.add(completeButton, 0, 5, 1, 1);
        gridPane.add(cancelButton, 1, 5, 1, 1);

        // AND PUT THE GRID PANE IN THE WINDOW
        dialogScene = new Scene(gridPane);
        dialogScene.getStylesheets().add(MainPane.PRIMARY_STYLE_SHEET);
        this.setScene(dialogScene);
    }
    
    public String getSelection() {
        return selection;
    }
    
    public Player getCreatedPlayer() { 
        return player;
    }
    
    public Player showAddPlayerDialog(){    
        try {
            firstNameTextField.setText("");
            lastNameTextField.setText("");
            position_C.setSelected(false);
            position_1B.setSelected(false);
            position_3B.setSelected(false);
            position_2B.setSelected(false);
            position_SS.setSelected(false);
            position_OF.setSelected(false);
            position_P.setSelected(false);

            this.showAndWait();

        //GIANT BLOCK OF STUPID CODE
            //CHECKS IF any of the checkboxes are checked. if they are it added it to qp(qualifying position)
            //addeds the underscore by checking if the string length is greater than 0.
            String qp = "";
            if (position_C.isSelected()) {
                qp += "C ";
            }

            if (position_1B.isSelected()) {
                qp += "1B ";
            }

            if (position_3B.isSelected()) {
                qp += "3B ";
            }

            if (position_2B.isSelected()) {
                qp += "2B ";
            }

            if (position_SS.isSelected()) {
                qp += "SS ";
            }

            if (position_OF.isSelected()) {
                qp += "OF ";
            }

            if (position_P.isSelected()) {
                qp += "P ";
            }

            qp = qp.trim();
            qp = qp.replaceAll(" ", "_");

            if (qp.contains("P")) {
                player = new Pitcher(
                        this.firstNameTextField.getText(),
                        this.lastNameTextField.getText(),
                        this.proTeamComboBox.getSelectionModel().getSelectedItem().toString(),
                        qp,
                        AddPlayerDialog.DEFAULT_YOB,
                        "",//NO NOTES
                        0,//FIRST YEAR OF PLAY, CANNOT HAVE STATS!
                        0,
                        0,
                        0,
                        0,
                        0,
                        0
                );
            } else {
                player = new Hitter(
                        this.firstNameTextField.getText(),
                        this.lastNameTextField.getText(),
                        this.proTeamComboBox.getSelectionModel().getSelectedItem().toString(),
                        qp,
                        AddPlayerDialog.DEFAULT_YOB,
                        "",//NO NOTES
                        0,//FIRST YEAR OF PLAY, CANNOT HAVE STATS!
                        0,
                        0,
                        0,
                        0,
                        0,
                        0
                );
            }

            return player;
        }
        catch(NullPointerException nfe){
            System.err.println("Something in AddPlayerDialog is missing. Please try again!");
            return null;
        }
    }
    
    private void loadCombobox(ComboBox cb, String[] s){
        for(String z: s){
            cb.getItems().add(z);
        }
    }

}
