/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wolfieballdraftkit.gui;

import java.util.ArrayList;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import wolfieballdraftkit.Wolfieball_PropertyType;
import wolfieballdraftkit.entitystructures.Hitter;
import wolfieballdraftkit.entitystructures.Pitcher;
import wolfieballdraftkit.entitystructures.Player;
import wolfieballdraftkit.entitystructures.Starters;
import wolfieballdraftkit.entitystructures.Team;

/**
 *
 * @author Nelnel33
 */
public class StandingsPane extends TitlePane{

    TableView<Team> teamTable;
    TableColumn teamName;
    TableColumn playersNeeded;
    TableColumn moneyLeft;
    TableColumn moneyPerPlayer;
    TableColumn runs;
    TableColumn homeRuns;
    TableColumn runsBattedIn;
    TableColumn stolenBases;
    TableColumn battingAverage;
    TableColumn wins;
    TableColumn saves;
    TableColumn strikeouts;
    TableColumn earnedRunAverage;
    TableColumn whip;
    TableColumn totalPoints;

    public StandingsPane(MainPane mainPane, Wolfieball_PropertyType propertyType) {
        super(mainPane, propertyType);
        init();
    }
    private void init(){
        teamTable = new TableView();
        teamName = new TableColumn("Team Name");
        playersNeeded = new TableColumn("Players Needed");
        moneyLeft = new TableColumn("$ Left");
        moneyPerPlayer = new TableColumn("$ PP");
        runs = new TableColumn("R");
        homeRuns = new TableColumn("HR");
        runsBattedIn = new TableColumn("RBI");
        stolenBases = new TableColumn("SB");
        battingAverage = new TableColumn("BA");
        wins = new TableColumn("W");
        saves = new TableColumn("SV");
        strikeouts = new TableColumn("K");
        earnedRunAverage = new TableColumn("ERA");
        whip = new TableColumn("WHIP");
        totalPoints = new TableColumn("Points");
        
        teamTable.getColumns().add(teamName);
        teamTable.getColumns().add(playersNeeded);
        teamTable.getColumns().add(moneyLeft);
        teamTable.getColumns().add(moneyPerPlayer);
        teamTable.getColumns().add(runs);
        teamTable.getColumns().add(homeRuns);
        teamTable.getColumns().add(runsBattedIn);
        teamTable.getColumns().add(stolenBases);
        teamTable.getColumns().add(battingAverage);
        teamTable.getColumns().add(wins);
        teamTable.getColumns().add(saves);
        teamTable.getColumns().add(strikeouts);
        teamTable.getColumns().add(earnedRunAverage);
        teamTable.getColumns().add(whip);
        teamTable.getColumns().add(totalPoints);
        
        teamName.setCellValueFactory(new PropertyValueFactory<String, String>("name"));
        playersNeeded.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Team, Integer>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Team, Integer> p) {
                Team t = (Team)p.getValue();
                return new SimpleStringProperty(""+(t.getPlayersLeft()));
            }
        });
        moneyLeft.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Team, Integer>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Team, Integer> p) {
                Team t = (Team)p.getValue();
                return new SimpleStringProperty(""+(t.getMoney()));
            }
        });
        moneyPerPlayer.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Team, Integer>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Team, Integer> p) {
                Team t = (Team)p.getValue();
                return new SimpleStringProperty(""+(t.getMoneyPerPlayer()));
            }
        });
        runs.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Team, Integer>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Team, Integer> p) {
                Team t = (Team)p.getValue();
                return new SimpleStringProperty(""+t.getTotalRuns());
            }
        });
        homeRuns.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Team, Integer>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Team, Integer> p) {
                Team t = (Team)p.getValue();
                return new SimpleStringProperty(""+(t.getTotalHomeRuns()));
            }
        });
        runsBattedIn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Team, Integer>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Team, Integer> p) {
                Team t = (Team)p.getValue();
                int size = t.getStarters().getPlayers().size();
                return new SimpleStringProperty(""+(t.getTotalRunsBattedIn()));
            }
        });
        stolenBases.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Team, Integer>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Team, Integer> p) {
                Team t = (Team)p.getValue();
                return new SimpleStringProperty(""+(t.getTotalStolenBases()));
            }
        });
        battingAverage.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Team, Double>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Team, Double> p) {
                Team t = (Team)p.getValue();
                return new SimpleStringProperty(""+(MainPane.battingAvg.format(t.getBattingAverage())));
            }
        });
        wins.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Team, Integer>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Team, Integer> p) {
                Team t = (Team)p.getValue();
                return new SimpleStringProperty(""+(t.getWins()));
            }
        });
        saves.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Team, Integer>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Team, Integer> p) {
                Team t = (Team)p.getValue();
                return new SimpleStringProperty(""+(t.getSaves()));
            }
        });
        strikeouts.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Team, Integer>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Team, Integer> p) {
                Team t = (Team)p.getValue();
                return new SimpleStringProperty(""+(t.getStrikeouts()));
            }
        });
        earnedRunAverage.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Team, Double>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Team, Double> p) {
                Team t = (Team)p.getValue();
                return new SimpleStringProperty(""+(MainPane.WhipEra.format(t.getEarnedRunAverage())));
            }
        });
        whip.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Team, Double>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Team, Double> p) {
                Team t = (Team)p.getValue();
                return new SimpleStringProperty(""+(MainPane.WhipEra.format(t.getWhip())));
            }
        });
        totalPoints.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Team, Integer>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Team, Integer> p) {
                Team t = (Team)p.getValue();
                int size = t.getStarters().getPlayers().size();
                return new SimpleStringProperty(""+(t.getTotalPoints()));
            }
        });
        
        this.add(teamTable, 0, 1);
        
        teamTable.setItems(FXCollections.observableArrayList(mainPane.dataManager.getLeague().getTeams()));
    }
    
    public void refreshTable(){
        teamTable.getItems().clear();
        teamTable.setItems(FXCollections.observableArrayList(mainPane.dataManager.getLeague().getTeams()));
        
        ArrayList<Team> teams = mainPane.dataManager.getLeague().getTeams();
        for(Team t: teams){
            System.out.println(t.getAllInfo());
        }
    }
    
}
