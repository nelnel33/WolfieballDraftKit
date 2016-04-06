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
public class Pitcher extends Player{
    //pitcher stats
    //can only get if position = pitcher
    private IntegerProperty wins;
    private IntegerProperty strikeouts;
    private IntegerProperty saves;
    private DoubleProperty inningsPitched;//effect earnedRunAverage+whip
    private IntegerProperty earnedRuns;//effects earnedRunAverage
    
    private DoubleProperty earnedRunAverage;//earnedruns*9/innings pitched
    private DoubleProperty whip;//walks+hits/innings pitched
    
    public int rankWins;
    public int rankStrikeouts;
    public int rankSaves;
    public int rankEarnedRunAverage;
    public int rankWhip;

    public Pitcher(
            String firstName, 
            String lastName, 
            String proTeam, 
            String qualifyingPositions, 
            int yearOfBirth,
            String notes,
            int hits,
            int walks,
            int wins, 
            int strikeouts, 
            int saves,
            double inningsPitched,
            int earnedRuns) {
        super(firstName, lastName, proTeam, qualifyingPositions, yearOfBirth, notes, hits, walks);
        this.wins = new SimpleIntegerProperty(wins);
        this.strikeouts = new SimpleIntegerProperty(strikeouts);
        this.saves =  new SimpleIntegerProperty(saves);
        this.inningsPitched = new SimpleDoubleProperty(inningsPitched);
        this.earnedRuns = new SimpleIntegerProperty(earnedRuns);
        calculateEarnedRunAverage() ;
        calculateWhip();
    }
    
    private void calculateEarnedRunAverage(){
        if(inningsPitched.get() > 0){
            earnedRunAverage = new SimpleDoubleProperty(((double)earnedRuns.get()*9)/(double)inningsPitched.get());
        }
        else{
            earnedRunAverage = new SimpleDoubleProperty(0);
        }
    }
    
    private void calculateWhip(){
        if(inningsPitched.get() > 0){
            whip = new SimpleDoubleProperty(((double)getWalks()+(double)getHits())/(double)inningsPitched.get());
        }
        else{
            whip = new SimpleDoubleProperty(0);
        }
    }

    public int getWins() {
        return wins.get();
    }

    public int getStrikeouts() {
        return strikeouts.get();
    }

    public int getSaves() {
        return saves.get();
    }

    public double getInningsPitched() {
        return inningsPitched.get();
    }

    public int getEarnedRuns() {
        return earnedRuns.get();
    }

    public double getEarnedRunAverage() {
        return earnedRunAverage.get();
    }

    public double getWhip() {
        return whip.get();
    } 
    
}
