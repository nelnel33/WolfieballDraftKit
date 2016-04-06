/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wolfieballdraftkit.events;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.stage.FileChooser;
import properties_manager.PropertiesManager;
import wolfieballdraftkit.WolfieballDataManager;
import wolfieballdraftkit.Wolfieball_PropertyType;
import wolfieballdraftkit.Wolfieball_StartupConstant;
import wolfieballdraftkit.entitystructures.DraftablePlayers;
import wolfieballdraftkit.file.LeagueFileManager;
import wolfieballdraftkit.gui.MainPane;
import wolfieballdraftkit.gui.dialogs.MessageDialog;
import wolfieballdraftkit.gui.dialogs.YesNoCancelDialog;

/**
 *
 * @author Nelnel33
 */
public class UtilityButtonHandler {
    //REFERENCE TO MAINPANE
    MainPane mainPane;
    
    //CHECK IF LEAGUE IS SABED
    private boolean saved;
    
    // THIS WILL PROVIDE FEEDBACK TO THE USER AFTER
    // WORK BY THIS CLASS HAS COMPLETED
    MessageDialog messageDialog;
    
    // AND WE'LL USE THIS TO ASK YES/NO/CANCEL QUESTIONS
    YesNoCancelDialog yesNoCancelDialog;
    
    // WE'LL USE THIS TO GET OUR VERIFICATION FEEDBACK
    PropertiesManager properties;
    
    public UtilityButtonHandler(MainPane mainPane){
        this.mainPane = mainPane;
        messageDialog = new MessageDialog(mainPane.getPrimaryStage(), "Close");
        yesNoCancelDialog = new YesNoCancelDialog(mainPane.getPrimaryStage());
        properties = PropertiesManager.getPropertiesManager();
    }
    
    public void markFileAsSaved(boolean saved){
        this.saved = saved;
    }
    
    public void handleNewLeagueRequest(){
        try {
            // WE MAY HAVE TO SAVE CURRENT WORK
            boolean continueToMakeNew = true;
           
             //TODO: ADD SAVE PROMPT MESSAGE

            // IF THE USER REALLY WANTS TO MAKE A NEW COURSE
            if (continueToMakeNew) {
                // RESET THE DATA, WHICH SHOULD TRIGGER A RESET OF THE UI
                WolfieballDataManager dataManager = mainPane.getDataManager();
                dataManager.reset();
                //saved = false;
                
                DraftablePlayers draftablePlayers = 
                    LeagueFileManager.loadDraftablePlayers(Wolfieball_StartupConstant.JSON_FILE_PATH_PITCHERS,Wolfieball_StartupConstant.JSON_FILE_PATH_HITTERS);
               

                // REFRESH THE GUI, WHICH WILL ENABLE AND DISABLE
                // THE APPROPRIATE CONTROLS
                mainPane.setDisableUtilityButtons(false);
                mainPane.initDataManager(draftablePlayers);
                mainPane.initWorkSpace();

                // TELL THE USER THE COURSE HAS BEEN CREATED
                messageDialog.show(properties.getProperty(Wolfieball_PropertyType.NEW_LEAGUE_MESSAGE));
            }
        } catch (IOException ioe) {
            // SOMETHING WENT WRONG, PROVIDE FEEDBACK
            System.err.println("IOException");
        }
    }
    
    public void handleLoadLeagueRequest(){
        this.mainPane.getDataManager().resetAllLists();
        promptToOpen(mainPane);
    }
    
    public void handleSaveLeagueRequest(){
        try {
            LeagueFileManager.saveLeague(mainPane.getDataManager());
            messageDialog.show("League has been saved!"); 
        } catch (FileNotFoundException ex) {
            messageDialog.show("League failed to be saved.");
        }
                
    }
    
    public void handleExportLeagueRequest(){
        
    }
    
    public void handleExitLeagueRequest(){
        
    }
    
        /**
     * This helper method verifies that the user really wants to save their
     * unsaved work, which they might not want to do. Note that it could be used
     * in multiple contexts before doing other actions, like creating a new
     * Course, or opening another Course. Note that the user will be
     * presented with 3 options: YES, NO, and CANCEL. YES means the user wants
     * to save their work and continue the other action (we return true to
     * denote this), NO means don't save the work but continue with the other
     * action (true is returned), CANCEL means don't save the work and don't
     * continue with the other action (false is returned).
     *
     * @return true if the user presses the YES option to save, true if the user
     * presses the NO option to not save, false if the user presses the CANCEL
     * option to not continue.
     */
    private boolean promptToSave(MainPane gui) throws IOException {
        // PROMPT THE USER TO SAVE UNSAVED WORK
        yesNoCancelDialog.show("Do you want to save your work?");
        
        
        // AND NOW GET THE USER'S SELECTION
        String selection = yesNoCancelDialog.getSelection();

        // IF THE USER SAID YES, THEN SAVE BEFORE MOVING ON
        if (selection.equals(YesNoCancelDialog.YES)) {
            // SAVE THE COURSE
            WolfieballDataManager dataManager = gui.getDataManager();
            LeagueFileManager.saveLeague(dataManager);
            saved = true;
        } // IF THE USER SAID CANCEL, THEN WE'LL TELL WHOEVER
        // CALLED THIS THAT THE USER IS NOT INTERESTED ANYMORE
        else if (selection.equals(YesNoCancelDialog.CANCEL)) {
            return false;
        }

        // IF THE USER SAID NO, WE JUST GO ON WITHOUT SAVING
        // BUT FOR BOTH YES AND NO WE DO WHATEVER THE USER
        // HAD IN MIND IN THE FIRST PLACE
        return true;
    }

    /**
     * This helper method asks the user for a file to open. The user-selected
     * file is then loaded and the GUI updated. Note that if the user cancels
     * the open process, nothing is done. If an error occurs loading the file, a
     * message is displayed, but nothing changes.
     */
    private void promptToOpen(MainPane gui) {
        // AND NOW ASK THE USER FOR THE COURSE TO OPEN
        FileChooser courseFileChooser = new FileChooser();
        courseFileChooser.setInitialDirectory(new File(Wolfieball_StartupConstant.PATH_JSON_DRAFT));
        File selectedFile = courseFileChooser.showOpenDialog(gui.getPrimaryStage());

        // ONLY OPEN A NEW FILE IF THE USER SAYS OK
        if (selectedFile != null) {
            try {
                LeagueFileManager.loadDraft(selectedFile.getAbsolutePath(), this.mainPane.getDataManager());
                gui.refreshTables();
                saved = true;
            } catch (IOException e) {
                System.err.println("Error loading file!");
               this.messageDialog.show("Error loading file!");
            }
        }
    }
}
