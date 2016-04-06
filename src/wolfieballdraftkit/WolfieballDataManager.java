package wolfieballdraftkit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import wolfieballdraftkit.entitystructures.DraftablePlayers;
import wolfieballdraftkit.entitystructures.Hitter;
import wolfieballdraftkit.entitystructures.League;
import wolfieballdraftkit.entitystructures.Pitcher;
import wolfieballdraftkit.entitystructures.Player;
import wolfieballdraftkit.entitystructures.Subteam;
import wolfieballdraftkit.entitystructures.Team;
import wolfieballdraftkit.file.LeagueFileManager;
import wolfieballdraftkit.gui.MainPane;

/**
 *
 * @author Nelnel33
 */
public class WolfieballDataManager {
        
    public static final String[] PRO_TEAMS = {
        "ATL", "AZ", "CHC", "CIN", "COL", "LAD", "MIA", "MIL",
        "NYM", "PHI", "PIT", "SD", "SF", "STL", "WAS"
    };
    
    public static final DraftablePlayers MLBTeamList = MLBTeamList();
    
    //LIST that corresponds with the draftable players on the TableView in PlayersPane
    DraftablePlayers shownOnTableDraftablePlayers;
    DraftablePlayers fullDraftablePlayers;
    
    //THE GUI
    MainPane mainPane;
    
    //WILL HAVE LEAGUE DATA STRUCTURE BUILT
    League league;

    public WolfieballDataManager(DraftablePlayers draftablePlayers, MainPane mainPane) {
        this.shownOnTableDraftablePlayers = draftablePlayers;
        this.fullDraftablePlayers = DraftablePlayers.copy(draftablePlayers);
        this.mainPane = mainPane;
        league = new League("Wolfie-ball League");
    }
    
    public static final DraftablePlayers MLBTeamList(){
        try {
            return LeagueFileManager.loadDraftablePlayers(Wolfieball_StartupConstant.JSON_FILE_PATH_PITCHERS,Wolfieball_StartupConstant.JSON_FILE_PATH_HITTERS);
        } catch (IOException ex) {
            System.err.println("MLB Team List is missing hitter.json and pitchers.json");
            return null;
        }
    }
    
    public void resetAllLists(){
        fullDraftablePlayers = new DraftablePlayers();
        shownOnTableDraftablePlayers = new DraftablePlayers();
        league = new League("Wolfieball League");
        
    }

    public DraftablePlayers getShownOnTableDraftablePlayers() {
        return shownOnTableDraftablePlayers;
    }

    public DraftablePlayers getFullDraftablePlayers() {
        return fullDraftablePlayers;
    }
    
    public League getLeague(){
        return league;
    }
    
    public ArrayList<Team> getTeamsInLeague(){
        return league.getTeams();
    }
    
    public void reset(){
        shownOnTableDraftablePlayers = new DraftablePlayers();
    }

    public MainPane getMainPane() {
        return mainPane;
    }
    
    /**
     * Restores shownOnTableDraftablePlayers to its full form.
     */
    public void restorePlayerList(){
        shownOnTableDraftablePlayers = DraftablePlayers.copy(fullDraftablePlayers);
    }
    
    public void calculateEstimatedValue(){
        System.err.println("HELLO@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        ArrayList<Pitcher> pitchers = new ArrayList();
        ArrayList<Hitter> hitters = new ArrayList();
        
        double totalMoneyRemaining = 0;
        double totalRemainingPlayers = 0;
        double totalHitters = 0;
        double totalPitchers = 0;
        double medianSalaryHitter = 0;
        double medianSalaryPitcher = 0;
        
        for(Team t: this.league.getTeams()){
            totalMoneyRemaining += t.getMoney();
            totalHitters += t.getStarters().getHittersLeft();
            totalPitchers += t.getStarters().getPitchersLeft();
        }   
        
        for(int z=0;z<shownOnTableDraftablePlayers.getPlayers().size();z++){
            Player p = shownOnTableDraftablePlayers.getPlayer(z);
            if(p instanceof Hitter){
                Hitter hit = (Hitter)p;
                hitters.add(hit);
            }
            else if(p instanceof Pitcher){
                Pitcher pitch = (Pitcher)p;
                pitchers.add(pitch);                
            }
        }
        
        if(pitchers.size() <= 0 || hitters.size() <= 0){
            return;
        }
       
        totalRemainingPlayers = this.shownOnTableDraftablePlayers.getPlayers().size();
        //totalHitters = hitters.size();
        //totalPitchers = pitchers.size();
        medianSalaryHitter = totalMoneyRemaining/(2*totalHitters);
        medianSalaryPitcher = totalMoneyRemaining/(2*totalPitchers);
        
        sortAndAssignRankingsToPitchers(pitchers);
        sortAndAssignRankingsToHitters(hitters);  
        
        System.out.println("----Estimated Values of Players in Draftable Players----");
        for(int i=0;i<this.shownOnTableDraftablePlayers.getPlayers().size();i++){
            Player p = this.shownOnTableDraftablePlayers.getPlayers().get(i);
            double avgRank = p.calculateAverageRank();
            if(p instanceof Hitter){
                Hitter h = (Hitter)p;
                
                double estimatedValue = 
                        (((double)medianSalaryHitter)*((double)totalHitters)*2)/
                        avgRank;
                
                h.setEstimatedValue((int)estimatedValue);
                        
            }
            else if(p instanceof Pitcher){
                Pitcher pitch = (Pitcher)p;
                
                double estimatedValue = 
                        (((double)medianSalaryPitcher)*((double)totalHitters)*2)/
                        avgRank;
                
                pitch.setEstimatedValue((int)estimatedValue);
            }
            
            System.out.print(p.getEstimatedValue());
        }
        
        System.out.println("------------------------------");
    }
    
    public void sortAndAssignRankingsToPitchers(List<Pitcher> pitchers){
        //SORT BY WINS
        Collections.sort(pitchers, new Comparator<Object>() {
            @Override
            public int compare(final Object object1, final Object object2) {
                Pitcher one = (Pitcher)object1;
                Pitcher two = (Pitcher)object2;
                
                if(one.getWins() > two.getWins()){
                    return 1;
                }
                else if(one.getWins() < two.getWins()){
                    return -1;
                }
                else{
                    return 0;
                }
            }
        });
        
        for(int i=pitchers.size()-1;i>=0;i--){
            Pitcher p = pitchers.get(i);
            p.rankWins = i;
        } 
        
        //SORT BY Strikeouts
        Collections.sort(pitchers, new Comparator<Object>() {
            @Override
            public int compare(final Object object1, final Object object2) {
                Pitcher one = (Pitcher)object1;
                Pitcher two = (Pitcher)object2;
                
                if(one.getStrikeouts() > two.getStrikeouts()){
                    return 1;
                }
                else if(one.getStrikeouts() < two.getStrikeouts()){
                    return -1;
                }
                else{
                    return 0;
                }
            }
        });
        
        for(int i=pitchers.size()-1;i>=0;i--){
            Pitcher p = pitchers.get(i);
            p.rankStrikeouts = i;
        } 
        
        //SORT BY Saves
        Collections.sort(pitchers, new Comparator<Object>() {
            @Override
            public int compare(final Object object1, final Object object2) {
                Pitcher one = (Pitcher)object1;
                Pitcher two = (Pitcher)object2;
                
                if(one.getSaves() > two.getSaves()){
                    return 1;
                }
                else if(one.getSaves() < two.getSaves()){
                    return -1;
                }
                else{
                    return 0;
                }
            }
        });
        
        for(int i=pitchers.size()-1;i>=0;i--){
            Pitcher p = pitchers.get(i);
            p.rankSaves = i;
        }
        
        //SORT BY ERA
        Collections.sort(pitchers, new Comparator<Object>() {
            @Override
            public int compare(final Object object1, final Object object2) {
                Pitcher one = (Pitcher)object1;
                Pitcher two = (Pitcher)object2;
                
                if(one.getEarnedRunAverage() > two.getEarnedRunAverage()){
                    return 1;
                }
                else if(one.getEarnedRunAverage() < two.getEarnedRunAverage()){
                    return -1;
                }
                else{
                    return 0;
                }
            }
        });
        
        for(int i=pitchers.size()-1;i>=0;i--){
            Pitcher p = pitchers.get(i);
            p.rankEarnedRunAverage = i;
        }
        
        //SORT BY WHIP
        Collections.sort(pitchers, new Comparator<Object>() {
            @Override
            public int compare(final Object object1, final Object object2) {
                Pitcher one = (Pitcher)object1;
                Pitcher two = (Pitcher)object2;
                
                if(one.getWhip() > two.getWhip()){
                    return 1;
                }
                else if(one.getWhip() < two.getWhip()){
                    return -1;
                }
                else{
                    return 0;
                }
            }
        });
        
        for(int i=pitchers.size()-1;i>=0;i--){
            Pitcher p = pitchers.get(i);
            p.rankWhip = i;
        }
    }
    
    public void sortAndAssignRankingsToHitters(List<Hitter> hitters){
        //SORT BY RUNS
        Collections.sort(hitters, new Comparator<Object>() {
            @Override
            public int compare(final Object object1, final Object object2) {
                Hitter one = (Hitter)object1;
                Hitter two = (Hitter)object2;
                
                if(one.getRuns() > two.getRuns()){
                    return 1;
                }
                else if(one.getRuns() < two.getRuns()){
                    return -1;
                }
                else{
                    return 0;
                }
            }
        });
        
        for(int i=hitters.size()-1;i>=0;i--){
            Hitter p = hitters.get(i);
            p.rankRuns = i;
        }
        
        //SORT BY HOME RUNS
        Collections.sort(hitters, new Comparator<Object>() {
            @Override
            public int compare(final Object object1, final Object object2) {
                Hitter one = (Hitter)object1;
                Hitter two = (Hitter)object2;
                
                if(one.getHomeRuns() > two.getHomeRuns()){
                    return 1;
                }
                else if(one.getHomeRuns() < two.getHomeRuns()){
                    return -1;
                }
                else{
                    return 0;
                }
            }
        });
        
        for(int i=hitters.size()-1;i>=0;i--){
            Hitter p = hitters.get(i);
            p.rankHomeRuns = i;
        }
        
        //SORT BY RBI
        Collections.sort(hitters, new Comparator<Object>() {
            @Override
            public int compare(final Object object1, final Object object2) {
                Hitter one = (Hitter)object1;
                Hitter two = (Hitter)object2;
                
                if(one.getRunsBattedIn() > two.getRunsBattedIn()){
                    return 1;
                }
                else if(one.getRunsBattedIn() < two.getRunsBattedIn()){
                    return -1;
                }
                else{
                    return 0;
                }
            }
        });
        
        for(int i=hitters.size()-1;i>=0;i--){
            Hitter p = hitters.get(i);
            p.rankRunsBattedIn = i;
        }
        
        //SORT BY STOLENBASES
        Collections.sort(hitters, new Comparator<Object>() {
            @Override
            public int compare(final Object object1, final Object object2) {
                Hitter one = (Hitter)object1;
                Hitter two = (Hitter)object2;
                
                if(one.getStolenBases() > two.getStolenBases()){
                    return 1;
                }
                else if(one.getStolenBases() < two.getStolenBases()){
                    return -1;
                }
                else{
                    return 0;
                }
            }
        });
        
        for(int i=hitters.size()-1;i>=0;i--){
            Hitter p = hitters.get(i);
            p.rankStolenBases = i;
        }
        
        //SORT BY BATTINGAVERAGE
        Collections.sort(hitters, new Comparator<Object>() {
            @Override
            public int compare(final Object object1, final Object object2) {
                Hitter one = (Hitter)object1;
                Hitter two = (Hitter)object2;
                
                if(one.getBattingAverage() > two.getBattingAverage()){
                    return 1;
                }
                else if(one.getBattingAverage() < two.getBattingAverage()){
                    return -1;
                }
                else{
                    return 0;
                }
            }
        });
        
        for(int i=hitters.size()-1;i>=0;i--){
            Hitter p = hitters.get(i);
            p.rankBattingAverage = i;
        }
    }
    
    
    
    
    
    
    
    
    
}
