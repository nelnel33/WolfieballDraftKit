/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wolfieballdraftkit.gui;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import properties_manager.PropertiesManager;
import wolfieballdraftkit.WolfieballDataManager;
import wolfieballdraftkit.Wolfieball_PropertyType;
import static wolfieballdraftkit.Wolfieball_StartupConstant.PATH_CSS;
import static wolfieballdraftkit.Wolfieball_StartupConstant.PATH_IMAGES;
import wolfieballdraftkit.entitystructures.DraftablePlayers;
import wolfieballdraftkit.entitystructures.Player;
import wolfieballdraftkit.events.UtilityButtonHandler;
import wolfieballdraftkit.gui.dialogs.MessageDialog;

/**
 *
 * @author Nelnel33
 */
public class MainPane extends BorderPane{    
    public static final String PRIMARY_STYLE_SHEET = PATH_CSS + "wolfieball_style.css";
    public static final String CLASS_BORDERED_PANE = "bordered_pane";
    public static final String CLASS_SUBJECT_PANE = "subject_pane";
    public static final String CLASS_HEADING_LABEL = "heading_label";
    public static final String CLASS_SUBHEADING_LABEL = "subheading_label";
    public static final String CLASS_PROMPT_LABEL = "prompt_label";
    public static final String EMPTY_TEXT = "";
    public static final int LARGE_TEXT_FIELD_LENGTH = 20;
    public static final int SMALL_TEXT_FIELD_LENGTH = 5;
    
    //FORMATTER FOR WHIP & ERA
    public static final DecimalFormat WhipEra = new DecimalFormat("##.##");
    public static final DecimalFormat battingAvg = new DecimalFormat("##.###");
    
    //LEAGUE DATA MANAGER
    WolfieballDataManager dataManager;
    
    //Workspace makes the pane scrollable
    ScrollPane workspace;
    
    //Handlers
    UtilityButtonHandler utilityHandler;
    
    //DIFFERENT PANES TO SWAP BETWEEN
    PlayersPane playersPane;
    TeamsPane teamsPane;
    StandingsPane standingsPane;
    DraftPane draftPane;
    MLBTeamsPane mlbPane;
    
    // THIS IS THE TOP TOOLBAR AND ITS CONTROLS
    FlowPane fileToolbarPane;
    Button newButton;
    Button loadButton;
    Button saveButton;
    Button exportButton;
    Button exitButton;
    
    // Buttons to switch between each different pane.
    FlowPane switchPane;
    Button playersButton;
    Button teamsButton;
    Button standingsButton;
    Button draftButton;
    Button MLBTeamsButton;
    
    //Application window's primary stage
    Stage primaryStage;
    
    //primaryStage's scene graph
    Scene primaryScene;
    
    // THIS WILL PROVIDE FEEDBACK TO THE USER AFTER
    // WORK BY THIS CLASS HAS COMPLETED
    MessageDialog messageDialog;
    
    public MainPane(Stage primaryStage){
        this.primaryStage = primaryStage;
        
        utilityHandler = new UtilityButtonHandler(this);
        messageDialog = new MessageDialog(this.getPrimaryStage(), "Close");
    }
    
    public WolfieballDataManager getDataManager(){
        return dataManager;
    }
    
    public void initDataManager(DraftablePlayers draftablePlayers){
        dataManager = new WolfieballDataManager(draftablePlayers,this);
    }
    
    public void initAndBuildGUI(String windowTitle){              
        initUtilityButtons();
        initSwitchButtons();
        //initWorkSpace();
        initEventHandlers();
        initWindow(windowTitle);
    }
    
    public void setDisableUtilityButtons(boolean disable){
        saveButton.setDisable(disable);
        exportButton.setDisable(disable);
                
        playersButton.setDisable(disable);
        teamsButton.setDisable(disable);
        standingsButton.setDisable(disable); 
        draftButton.setDisable(disable);
        MLBTeamsButton.setDisable(disable);
    }
    
    private void initUtilityButtons(){
        fileToolbarPane = new FlowPane();
        newButton = MainPane.initChildButton(fileToolbarPane, Wolfieball_PropertyType.NEW_DRAFT_ICON, Wolfieball_PropertyType.NEW_DRAFT_TOOLTIP, false);
        loadButton = MainPane.initChildButton(fileToolbarPane, Wolfieball_PropertyType.LOAD_DRAFT_ICON, Wolfieball_PropertyType.LOAD_DRAFT_TOOLTIP, false);
        saveButton = MainPane.initChildButton(fileToolbarPane, Wolfieball_PropertyType.SAVE_DRAFT_ICON, Wolfieball_PropertyType.SAVE_DRAFT_TOOLTIP, true);
        exportButton = MainPane.initChildButton(fileToolbarPane, Wolfieball_PropertyType.EXPORT_PAGE_ICON, Wolfieball_PropertyType.EXPORT_PAGE_TOOLTIP, true);
        exitButton = MainPane.initChildButton(fileToolbarPane, Wolfieball_PropertyType.EXIT_ICON, Wolfieball_PropertyType.EXIT_TOOLTIP, false);
        
        this.setTop(fileToolbarPane);
    }
    
    private void initSwitchButtons(){
        //TODO: Change icon size of playerButton!!!
        switchPane = new FlowPane();
        playersButton = MainPane.initChildButton(switchPane, Wolfieball_PropertyType.PLAYERS_PANE_ICON, Wolfieball_PropertyType.PLAYERS_PANE_TITLE_LABEL, true);
        teamsButton = MainPane.initChildButton(switchPane, Wolfieball_PropertyType.TEAMS_PANE_ICON, Wolfieball_PropertyType.TEAMS_PANE_TITLE_LABEL, true);
        standingsButton = MainPane.initChildButton(switchPane, Wolfieball_PropertyType.STANDINGS_PANE_ICON, Wolfieball_PropertyType.STANDINGS_PANE_TITLE_LABEL, true);
        draftButton = MainPane.initChildButton(switchPane, Wolfieball_PropertyType.DRAFT_PANE_ICON, Wolfieball_PropertyType.DRAFT_PANE_TITLE_LABEL, true);
        MLBTeamsButton = MainPane.initChildButton(switchPane, Wolfieball_PropertyType.MLB_TEAMS_PANE_ICON, Wolfieball_PropertyType.MLB_TEAMS_TITLE_LABEL, true);
        
        this.setBottom(switchPane);
    }
    
    public void initWorkSpace(){
        playersPane = new PlayersPane(this, Wolfieball_PropertyType.PLAYERS_PANE_TITLE_LABEL);
        teamsPane = new TeamsPane(this, Wolfieball_PropertyType.TEAMS_PANE_TITLE_LABEL);
        standingsPane = new StandingsPane(this, Wolfieball_PropertyType.STANDINGS_PANE_TITLE_LABEL);
        draftPane = new DraftPane(this, Wolfieball_PropertyType.DRAFT_PANE_TITLE_LABEL);
        mlbPane = new MLBTeamsPane(this, Wolfieball_PropertyType.MLB_TEAMS_TITLE_LABEL);
       
        workspace = new ScrollPane();
        this.setCenter(workspace);
        //workspace.getStyleClass().add(MainPane.CLASS_BORDERED_PANE);
        
        setFocusPane(playersPane);
        
        initSwitchButtonEventHandlers();
    }
    
    private void initEventHandlers(){
        newButton.setOnAction(e -> {
            utilityHandler.handleNewLeagueRequest();
            this.dataManager.calculateEstimatedValue();
        });
        loadButton.setOnAction(e -> {
            utilityHandler.handleLoadLeagueRequest();
            refreshTables();
        });
        saveButton.setOnAction(e -> {
            utilityHandler.handleSaveLeagueRequest();
            refreshTables();
        });
        exportButton.setOnAction(e -> {
            utilityHandler.handleExportLeagueRequest();
        });
        exitButton.setOnAction(e -> {
            utilityHandler.handleExitLeagueRequest();
        });
    }
    
    private void initSwitchButtonEventHandlers(){
        playersButton.setOnAction(e -> {
            setFocusPane(playersPane);
            refreshTables();
        });
        teamsButton.setOnAction(e -> {
            setFocusPane(teamsPane);
            refreshTables();
        });
        standingsButton.setOnAction(e -> {
            setFocusPane(standingsPane);
            refreshTables();
        });
        draftButton.setOnAction(e -> {
            setFocusPane(draftPane);
            refreshTables();
        });
        MLBTeamsButton.setOnAction(e -> {
            setFocusPane(mlbPane);
            refreshTables();
        });
    }
    
    public void setFocusPane(Pane pane){
        workspace.setContent(pane);
    }
    
    public Stage getPrimaryStage(){
        return primaryStage;
    }
    
    public Scene getPrimaryScene(){
        return primaryScene;
    }
    
    // INITIALIZE THE WINDOW (i.e. STAGE) PUTTING ALL THE CONTROLS
    // THERE EXCEPT THE WORKSPACE, WHICH WILL BE ADDED THE FIRST
    // TIME A NEW Course IS CREATED OR LOADED
    private void initWindow(String windowTitle) {
        // SET THE WINDOW TITLE
        primaryStage.setTitle(windowTitle);

        // GET THE SIZE OF THE SCREEN
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        // AND USE IT TO SIZE THE WINDOW
        primaryStage.setX(bounds.getMinX());
        primaryStage.setY(bounds.getMinY());
        primaryStage.setWidth(bounds.getWidth());
        primaryStage.setHeight(bounds.getHeight());

        // ADD THE TOOLBAR ONLY, NOTE THAT THE WORKSPACE
        // HAS BEEN CONSTRUCTED, BUT WON'T BE ADDED UNTIL
        // THE USER STARTS EDITING A COURSE
        this.setTop(fileToolbarPane);
        primaryScene = new Scene(this);

        // NOW TIE THE SCENE TO THE WINDOW, SELECT THE STYLESHEET
        // WE'LL USE TO STYLIZE OUR GUI CONTROLS, AND OPEN THE WINDOW
        primaryScene.getStylesheets().add(PRIMARY_STYLE_SHEET);
        primaryStage.setScene(primaryScene);
        primaryStage.show();
    }
    
    public void refreshTables(){
        this.dataManager.calculateEstimatedValue();
        this.dataManager.getLeague().assignPointsToTeams();
        this.dataManager.getLeague().recalculateStatsForTeams();
        this.teamsPane.refreshTables();
        this.teamsPane.refreshFantasyTeamComboBox();
        this.playersPane.refreshTable();
        this.draftPane.refreshTable();
        this.standingsPane.refreshTable();        
    }
    
    public static RadioButton initAndGroupRadioButton(Pane toolbar, Wolfieball_PropertyType name, ToggleGroup group){
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        String labelText = props.getProperty(name);
        RadioButton button = new RadioButton(labelText);
        button.setToggleGroup(group);
        toolbar.getChildren().add(button);
        return button;
    }
    
    // INIT A LABEL AND SET IT'S STYLESHEET CLASS
    public static Label initLabel(Wolfieball_PropertyType labelProperty, String styleClass) {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        String labelText = props.getProperty(labelProperty);
        Label label = new Label(labelText);
        label.getStyleClass().add(styleClass);
        return label;
    }
    
    // INIT A LABEL AND PLACE IT IN A GridPane INIT ITS PROPER PLACE
    public static Label initGridLabel(GridPane container, Wolfieball_PropertyType labelProperty, String styleClass, int col, int row, int colSpan, int rowSpan) {
        Label label = initLabel(labelProperty, styleClass);
        container.add(label, col, row, colSpan, rowSpan);
        return label;
    }

    // INIT A LABEL AND PUT IT IN A TOOLBAR
    public static Label initChildLabel(Pane container, Wolfieball_PropertyType labelProperty, String styleClass) {
        Label label = initLabel(labelProperty, styleClass);
        container.getChildren().add(label);
        return label;
    }
    
    // INIT A TEXT FIELD AND PUT IT IN A GridPane
    public static TextField initGridTextField(GridPane container, int size, String initText, boolean editable, int col, int row, int colSpan, int rowSpan) {
        TextField tf = new TextField();
        tf.setPrefColumnCount(size);
        tf.setText(initText);
        tf.setEditable(editable);
        container.add(tf, col, row, colSpan, rowSpan);
        return tf;
    }
    
    // INIT A TEXT FIELD AND PUT IT IN A GridPane
    public static TextField initGridTextField(Pane container, int size, String initText, boolean editable) {
        TextField tf = new TextField();
        tf.setPrefColumnCount(size);
        tf.setText(initText);
        tf.setEditable(editable);
        container.getChildren().add(tf);
        return tf;
    }
    
    // INIT A BUTTON AND ADD IT TO A CONTAINER IN A TOOLBAR
    public static Button initChildButton(Pane toolbar, Wolfieball_PropertyType icon, Wolfieball_PropertyType tooltip, boolean disabled) {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        String imagePath = "file:" + PATH_IMAGES + props.getProperty(icon.toString());
        Image buttonImage = new Image(imagePath);
        Button button = new Button();
        button.setDisable(disabled);
        button.setGraphic(new ImageView(buttonImage));
        Tooltip buttonTooltip = new Tooltip(props.getProperty(tooltip.toString()));
        button.setTooltip(buttonTooltip);
        toolbar.getChildren().add(button);
        return button;
    }
    
    public static void setGraphicOnLabel(Label label, String path){
        String imagePath = "file:" + path;
        Image labelImage = new Image(imagePath);
        label.setGraphic(new ImageView(labelImage));
    }
    
    // INIT A COMBO BOX AND PUT IT IN A GridPane
    public static ComboBox initGridComboBox(Pane container) throws IOException{
        ComboBox comboBox = new ComboBox();
        container.getChildren().add(comboBox);
        return comboBox;
    }
    
    // INIT A COMBO BOX AND PUT IT IN A GridPane
    public static ComboBox initGridComboBox(GridPane container, int col, int row, int colSpan, int rowSpan) throws IOException {
        ComboBox comboBox = new ComboBox();
        container.add(comboBox, col, row, colSpan, rowSpan);
        return comboBox;
    }

    // LOAD THE COMBO BOX TO HOLD stuff
    public static void loadComboBox(ComboBox cb, List<String> stuff) {
        for (String s : stuff) {
            cb.getItems().add(s);
        }
    }
    
    // LOAD THE COMBO BOX TO HOLD stuff
    public static void loadComboBox(ComboBox cb, String[] stuff) {
        for (String s : stuff) {
            cb.getItems().add(s);
        }
    }
    
}
