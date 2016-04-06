package wolfieballdraftkit.entitystructures;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Nelnel33
 */
public class League {
    private String name;
    private ArrayList<Team> teams;
    
    public League(String name){
        this.name = name;
        teams = new ArrayList();
    }

    public String getName() {
        return name;
    }

    public ArrayList<Team> getTeams() {
        return teams;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public Team getTeamByName(String name){
        for(Team t: teams){
            if(t.getName().equals(name)){
                return t;
            }
        }
        if(name.equals("UNDRAFTED")){return null;}
        throw new IllegalArgumentException("Team "+name+" does not exist!");
    }
    
    public Team getTeamByOwner(String owner){
        for(Team t: teams){
            if(t.getOwner().equals(owner)){
                return t;
            }
        }
        throw new IllegalArgumentException("TEAM DOES NOT EXIST");
    }
    
    public League copyLeague(League toCopy){
        League newLeague = new League(toCopy.getName());
        for(Team t: teams){
            newLeague.getTeams().add(t);
        }
        
        return newLeague;
    }
    
    public void assignPointsToTeams(){
        //SORTS FOR TOTALRUNS THEN ASSIGNS EACH THE CORRESPONDING POINT VALUE
        Collections.sort(this.teams, new Comparator<Object>() {
            @Override
            public int compare(final Object object1, final Object object2) {
                Team one = (Team)object1;
                Team two = (Team)object2;
                
                if(one.getTotalRuns() < two.getTotalRuns()){
                    return 1;
                }
                else{
                    return -1;
                }
            }
        });
        
        for(int i=0;i<teams.size();i++){
            int setvalue = 12-i;
            if(setvalue<0){
                setvalue = 0;
            }
            Team t = teams.get(i);
            t.setPointsRuns(setvalue);
        }
        
        //SORTS FOR TOTALHOMERUNS THEN ASSIGNS EACH THE CORRESPONDING POINT VALUE
        Collections.sort(this.teams, new Comparator<Object>() {
            @Override
            public int compare(final Object object1, final Object object2) {
                Team one = (Team)object1;
                Team two = (Team)object2;
                
                if(one.getTotalHomeRuns() < two.getTotalHomeRuns()){
                    return 1;
                }
                else{
                    return -1;
                }
            }
        });
        
        for(int i=0;i<teams.size();i++){
            int setvalue = 12-i;
            if(setvalue<0){
                setvalue = 0;
            }
            Team t = teams.get(i);
            t.setPointsHomeRuns(setvalue);
        }
        
        //SORTS FOR RunsBattedIn THEN ASSIGNS EACH THE CORRESPONDING POINT VALUE
        Collections.sort(this.teams, new Comparator<Object>() {
            @Override
            public int compare(final Object object1, final Object object2) {
                Team one = (Team)object1;
                Team two = (Team)object2;
                
                if(one.getTotalRunsBattedIn() < two.getTotalRunsBattedIn()){
                    return 1;
                }
                else{
                    return -1;
                }
            }
        });
        
        for(int i=0;i<teams.size();i++){
            int setvalue = 12-i;
            if(setvalue<0){
                setvalue = 0;
            }
            Team t = teams.get(i);
            t.setPointsRunsBattedIn(setvalue);
        }
        
        //SORTS FOR STOLENBASES THEN ASSIGNS EACH THE CORRESPONDING POINT VALUE
        Collections.sort(this.teams, new Comparator<Object>() {
            @Override
            public int compare(final Object object1, final Object object2) {
                Team one = (Team)object1;
                Team two = (Team)object2;
                
                if(one.getTotalStolenBases() < two.getTotalStolenBases()){
                    return 1;
                }
                else{
                    return -1;
                }
            }
        });
        
        for(int i=0;i<teams.size();i++){
            int setvalue = 12-i;
            if(setvalue<0){
                setvalue = 0;
            }
            Team t = teams.get(i);
            t.setPointsStolenBases(setvalue);
        }
        
        //SORTS FOR BATTINGAVERAGE THEN ASSIGNS EACH THE CORRESPONDING POINT VALUE
        Collections.sort(this.teams, new Comparator<Object>() {
            @Override
            public int compare(final Object object1, final Object object2) {
                Team one = (Team)object1;
                Team two = (Team)object2;
                
                if(one.getBattingAverage() < two.getBattingAverage()){
                    return 1;
                }
                else{
                    return -1;
                }
            }
        });
        
        for(int i=0;i<teams.size();i++){
            int setvalue = 12-i;
            if(setvalue<0){
                setvalue = 0;
            }
            Team t = teams.get(i);
            t.setPointsBattingAverage(setvalue);
        }
        
        //SORTS FOR Wins THEN ASSIGNS EACH THE CORRESPONDING POINT VALUE
        Collections.sort(this.teams, new Comparator<Object>() {
            @Override
            public int compare(final Object object1, final Object object2) {
                Team one = (Team)object1;
                Team two = (Team)object2;
                
                if(one.getWins() < two.getWins()){
                    return 1;
                }
                else{
                    return -1;
                }
            }
        });
        
        for(int i=0;i<teams.size();i++){
            int setvalue = 12-i;
            if(setvalue<0){
                setvalue = 0;
            }
            Team t = teams.get(i);
            t.setPointsWins(setvalue);
        }
        
        //SORTS FOR Strikeouts THEN ASSIGNS EACH THE CORRESPONDING POINT VALUE
        Collections.sort(this.teams, new Comparator<Object>() {
            @Override
            public int compare(final Object object1, final Object object2) {
                Team one = (Team)object1;
                Team two = (Team)object2;
                
                if(one.getStrikeouts() < two.getStrikeouts()){
                    return 1;
                }
                else{
                    return -1;
                }
            }
        });
        
        for(int i=0;i<teams.size();i++){
            int setvalue = 12-i;
            if(setvalue<0){
                setvalue = 0;
            }
            Team t = teams.get(i);
            t.setPointsStrikeouts(setvalue);
        }
        
        //SORTS FOR Saves THEN ASSIGNS EACH THE CORRESPONDING POINT VALUE
        Collections.sort(this.teams, new Comparator<Object>() {
            @Override
            public int compare(final Object object1, final Object object2) {
                Team one = (Team)object1;
                Team two = (Team)object2;
                
                if(one.getSaves() < two.getSaves()){
                    return 1;
                }
                else{
                    return -1;
                }
            }
        });
        
        for(int i=0;i<teams.size();i++){
            int setvalue = 12-i;
            if(setvalue<0){
                setvalue = 0;
            }
            Team t = teams.get(i);
            t.setPointsSaves(setvalue);
        }
        
        //SORTS FOR ERA THEN ASSIGNS EACH THE CORRESPONDING POINT VALUE
        Collections.sort(this.teams, new Comparator<Object>() {
            @Override
            public int compare(final Object object1, final Object object2) {
                Team one = (Team)object1;
                Team two = (Team)object2;
                
                if(one.getEarnedRunAverage() < two.getEarnedRunAverage()){
                    return 1;
                }
                else{
                    return -1;
                }
            }
        });
        
        for(int i=0;i<teams.size();i++){
            int setvalue = 12-i;
            if(setvalue<0){
                setvalue = 0;
            }
            Team t = teams.get(i);
            t.setPointsERA(setvalue);
        }
        
        //SORTS FOR WHIP THEN ASSIGNS EACH THE CORRESPONDING POINT VALUE
        Collections.sort(this.teams, new Comparator<Object>() {
            @Override
            public int compare(final Object object1, final Object object2) {
                Team one = (Team)object1;
                Team two = (Team)object2;
                
                if(one.getWhip() < two.getWhip()){
                    return 1;
                }
                else{
                    return -1;
                }
            }
        });
        
        for(int i=0;i<teams.size();i++){
            int setvalue = 12-i;
            if(setvalue<0){
                setvalue = 0;
            }
            Team t = teams.get(i);
            t.setPointsWHIP(setvalue);
        }        
    }
    
    public void recalculateStatsForTeams(){
        for(int i=0;i<teams.size();i++){
            Team t = teams.get(i);
            t.calculateStats();
        }
    }
}
