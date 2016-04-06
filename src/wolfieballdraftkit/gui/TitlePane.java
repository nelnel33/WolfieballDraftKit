/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wolfieballdraftkit.gui;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import wolfieballdraftkit.Wolfieball_PropertyType;
import wolfieballdraftkit.gui.MainPane;

/**
 *
 * @author Nelnel33
 */
public abstract class TitlePane extends GridPane{
    //Reference to the mainpane that holds all other screens
    MainPane mainPane;
    
    //Title of pane
    Label titleLabel;
    
    public TitlePane(MainPane mainPane, Wolfieball_PropertyType propertyType){
        this.mainPane = mainPane;
        initTitleLabel(propertyType);
    }
    private void initTitleLabel(Wolfieball_PropertyType propertyType){
        titleLabel = MainPane.initGridLabel(this, propertyType, MainPane.CLASS_HEADING_LABEL, 0,0,20,1);
        this.getStyleClass().add(MainPane.CLASS_BORDERED_PANE);
    }
    
    public MainPane getMainPane(){
        return mainPane;
    }
}
