/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wolfieballdraftkit.entitystructures;

import java.util.ArrayList;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import wolfieballdraftkit.enums.Position;

/**
 *
 * @author Nelnel33
 */
public class Hitter extends Player{
    //hitter stats
    //can only get if position = hitter
    private IntegerProperty runs;
    private IntegerProperty homeRuns;
    private IntegerProperty runsBattedIn;
    private IntegerProperty stolenBases;
    private IntegerProperty atBat;
    private DoubleProperty battingAverage;//hits/atBat
    
    public int rankRuns;
    public int rankHomeRuns;
    public int rankRunsBattedIn;
    public int rankStolenBases;
    public int rankBattingAverage;

    public Hitter(
            String firstName, 
            String lastName, 
            String proTeam, 
            String qualifyingPositions, 
            int yearOfBirth,
            String notes,
            int hits,
            int walks,
            int runs, 
            int homeRuns, 
            int runsBattedIn, 
            int stolenBases,
            int atBat
    ) {
        super(firstName, lastName, proTeam, qualifyingPositions, yearOfBirth, notes, hits, walks);
        this.runs = new SimpleIntegerProperty(runs);
        this.homeRuns = new SimpleIntegerProperty(homeRuns);
        this.runsBattedIn = new SimpleIntegerProperty(runsBattedIn);
        this.stolenBases = new SimpleIntegerProperty(stolenBases);
        this.atBat = new SimpleIntegerProperty(atBat);
        calculateBattingAverage();
    }    
    
    private void calculateBattingAverage(){
        if(atBat.get() > 0){
            battingAverage = new SimpleDoubleProperty((double)this.getHits()/(double)atBat.get());
        }
        else{
            battingAverage = new SimpleDoubleProperty(0);
        }
    }

    public int getRuns() {
        return runs.get();
    }

    public int getHomeRuns() {
        return homeRuns.get();
    }

    public int getRunsBattedIn() {
        return runsBattedIn.get();
    }

    public int getStolenBases() {
        return stolenBases.get();
    }

    public int getAtBat() {
        return atBat.get();
    }

    public double getBattingAverage() {
        return battingAverage.get();
    } 
}
