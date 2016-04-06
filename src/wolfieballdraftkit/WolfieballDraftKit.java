package wolfieballdraftkit;

import java.util.Locale;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.stage.Stage;
import properties_manager.PropertiesManager;
import static wolfieballdraftkit.Wolfieball_StartupConstant.PATH_DATA;
import static wolfieballdraftkit.Wolfieball_StartupConstant.PROPERTIES_FILE_NAME;
import static wolfieballdraftkit.Wolfieball_StartupConstant.PROPERTIES_SCHEMA_FILE_NAME;
import wolfieballdraftkit.entitystructures.DraftablePlayers;
import wolfieballdraftkit.file.LeagueFileManager;
import wolfieballdraftkit.gui.MainPane;
import xml_utilities.InvalidXMLFileFormatException;

/**
 *
 * @author Nelnel33
 */
public class WolfieballDraftKit extends Application{
    
    MainPane mainpane;

    @Override
    public void start(Stage primaryStage) throws Exception {
        boolean success = loadProperties();
        if(success){
            PropertiesManager props = PropertiesManager.getPropertiesManager();
            String appTitle = props.getProperty(Wolfieball_PropertyType.PROP_APP_TITLE);
            
            DraftablePlayers draftablePlayers = 
                    LeagueFileManager.loadDraftablePlayers(Wolfieball_StartupConstant.JSON_FILE_PATH_PITCHERS,Wolfieball_StartupConstant.JSON_FILE_PATH_HITTERS);
            
            //INIT MAIN USER INTERFACE
            mainpane = new MainPane(primaryStage);
            
            mainpane.initDataManager(draftablePlayers);
            
            mainpane.initAndBuildGUI(appTitle);
            
        }
    }
    
    /**
     * Loads this application's properties file, which has a number of settings
     * for initializing the user interface.
     * 
     * @return true if the properties file was loaded successfully, false otherwise.
     */
    public boolean loadProperties() {
        try {
            // LOAD THE SETTINGS FOR STARTING THE APP
            PropertiesManager props = PropertiesManager.getPropertiesManager();
            props.addProperty(PropertiesManager.DATA_PATH_PROPERTY, PATH_DATA);
            props.loadProperties(PROPERTIES_FILE_NAME, PROPERTIES_SCHEMA_FILE_NAME);
            return true;
       } catch (InvalidXMLFileFormatException ixmlffe) {
            // SOMETHING WENT WRONG INITIALIZING THE XML FILE
            System.err.println("Faulty XML file!");
            return false;
        }        
    }
    
     /**
     * This is where program execution begins. Since this is a JavaFX app
     * it will simply call launch, which gets JavaFX rolling, resulting in
     * sending the properly initialized Stage (i.e. window) to our start
     * method in this class.
     * @param args
     */
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        launch(args);
    }
    
}
