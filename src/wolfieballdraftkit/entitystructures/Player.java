/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wolfieballdraftkit.entitystructures;

import java.util.ArrayList;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import wolfieballdraftkit.enums.Contract;
import wolfieballdraftkit.enums.Position;

/**
 *
 * @author Nelnel33
 */
public class Player {        
    //loaded on startup
    private StringProperty firstName;//can get
    private StringProperty lastName;//can get
    private StringProperty proTeam;//Professional MLB Team can only get
    private StringProperty qualifyingPositions;//can get+set
    private IntegerProperty yearOfBirth;//can get
    private StringProperty notes;//can get+set
    private IntegerProperty hits;//can get
    private IntegerProperty walks;//can get
    
    private IntegerProperty estimatedValue;//can get+set
    
    private String nation;//can get+set(must be set, not made in constructor)
    
    //will be inputed after draft **MUST ALL BE SET TOGETHER!!!
    private StringProperty position;//can get+set
    private ObjectProperty<Contract> contract;//can get+set 
    private IntegerProperty salary;//can get+set 
    private ObjectProperty<Team> team;//can get+set
    
    private double averageRanking;

    public Player(
            String firstName, 
            String lastName, 
            String proTeam, 
            String qualifyingPositions, 
            int yearOfBirth, 
            String notes,
            int hits,
            int walks) {
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.proTeam = new SimpleStringProperty(proTeam);
        this.qualifyingPositions = new SimpleStringProperty(qualifyingPositions);
        this.yearOfBirth = new SimpleIntegerProperty(yearOfBirth);
        this.notes = new SimpleStringProperty(notes);
        this.hits = new SimpleIntegerProperty(hits);
        this.walks = new SimpleIntegerProperty(walks);
        this.estimatedValue = new SimpleIntegerProperty(0);
        this.position = new SimpleStringProperty();
        this.contract = new SimpleObjectProperty();
        this.salary = new SimpleIntegerProperty();
        this.team = new SimpleObjectProperty(); 
    }
    
    public void setDraftAttributes(String position, Contract contract, int salary, Team team){      
        this.position.set(position);
        this.contract.set(contract);
        this.salary.set(salary);
        this.team.set(team);  
    }
    
    public boolean isPitcher(){
        return this.getQualifyingPositions().contains("P");
    }
    
    public boolean equals(Player p){
        return p.getFirstName().equals(this.getFirstName()) 
                && p.getLastName().equals(this.getLastName());
    }

    public String getFirstName() {
        return firstName.get();
    }

    public String getLastName() {
        return lastName.get();
    }

    public String getProTeam() {
        return proTeam.get();
    }

    public String getQualifyingPositions() {
        return qualifyingPositions.get();
    }

    public int getYearOfBirth() {
        return yearOfBirth.get();
    }

    public String getNotes() {
        return notes.get();
    }

    public String getPosition() {
        return position.get();
    }

    public Contract getContract() {
        return contract.get();
    }

    public int getSalary() {
        return salary.get();
    }

    public Team getTeam() {
        return team.get();
    }

    public void setQualifyingPositions(String qualifyingPositions) {
        this.qualifyingPositions.set(qualifyingPositions);
    }

    public void setNotes(String notes) {
        this.notes.set(notes);
    }

    public int getHits() {
        return hits.get();
    }

    public int getWalks() {
        return walks.get();
    }
    
    public void setNation(String nation){
        this.nation = nation;
    }
    
    public String getNation(){
        return nation;
    }

    public double getEstimatedValue() {
        return estimatedValue.get();
    }

    public void setEstimatedValue(int estimatedValue) {
        this.estimatedValue.set(estimatedValue);
    }
    
    public double calculateAverageRank(){
        if(this instanceof Pitcher){
            Pitcher p = (Pitcher)this;
            double avg = ((double)p.rankWins+
            (double)p.rankStrikeouts+
            (double)p.rankSaves+
            (double)p.rankEarnedRunAverage+
            (double)p.rankWhip)/5;
            this.averageRanking = avg;
            
            System.out.println("Rank stats pitcher"+
                    (double)p.rankWins+"-"+
            (double)p.rankStrikeouts+"-"+
            (double)p.rankSaves+"-"+
            (double)p.rankEarnedRunAverage+"-"+
            (double)p.rankWhip+"@"+
                    avg);
            
            return avg;
        }
        else if(this instanceof Hitter){
            Hitter h = (Hitter)this;
            double avg = ((double)h.rankRuns+
                (double)h.rankHomeRuns+
                (double)h.rankRunsBattedIn+
                (double)h.rankStolenBases+
                (double)h.rankBattingAverage)/5;
            this.averageRanking = avg;
            
            System.out.println("Rank stats hitter"+
                    h.rankRuns+"-"+
                h.rankHomeRuns+"-"+
                h.rankRunsBattedIn+"-"+
                h.rankStolenBases+"-"+
                h.rankBattingAverage+"@"+avg);
            return avg;
        }
        else{
            System.err.println("cannot calculate average rank, not hitter or pitcher");
            this.averageRanking = 0;
        }
        
        return averageRanking;
    }
    
    
    public ArrayList<String> qualifyingPositionsInStringArray(){
        ArrayList<String> positions = new ArrayList();
        String qp = qualifyingPositions.get();
        
        if(this.getQualifyingPositions().contains("1B")||
                this.getQualifyingPositions().contains("3B")){
            positions.add("CI");
        }
        if(this.getQualifyingPositions().contains("2B")||
                this.getQualifyingPositions().contains("SS")){
            positions.add("MI");
        }
        if(this instanceof Hitter){
            positions.add("U");
        }
        
        int begin = 0;
        for (int i = 0; i < qp.length(); i++) {
            char p = qp.charAt(i);
            if (p == '_') {
                positions.add(qp.substring(begin, i));
                begin = i + 1;
            }
        }
        
        positions.add(qp.substring(begin,qp.length()));
        
        for(String s:positions){
            //System.out.println("qualifyingPositionsInStringArray: "+s);
        }
        
        return positions;
    }
    
    @Override
    public String toString(){
        return "|"+firstName+"-"+lastName+"|";
    }
    
}
