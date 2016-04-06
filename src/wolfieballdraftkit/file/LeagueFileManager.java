/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wolfieballdraftkit.file;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonWriter;
import wolfieballdraftkit.WolfieballDataManager;
import wolfieballdraftkit.Wolfieball_StartupConstant;
import wolfieballdraftkit.entitystructures.DraftablePlayers;
import wolfieballdraftkit.entitystructures.Hitter;
import wolfieballdraftkit.entitystructures.League;
import wolfieballdraftkit.entitystructures.Pitcher;
import wolfieballdraftkit.entitystructures.Player;
import wolfieballdraftkit.entitystructures.Subteam;
import wolfieballdraftkit.entitystructures.Team;
import wolfieballdraftkit.enums.Contract;

/**
 *
 * @author Nelnel33
 */
public class LeagueFileManager {
    //Constants for both type of players
    public static final String JSON_PRO_TEAM = "TEAM";
    public static final String JSON_LAST_NAME = "LAST_NAME";
    public static final String JSON_FIRST_NAME = "FIRST_NAME";
    public static final String JSON_YOB = "YEAR_OF_BIRTH";
    public static final String JSON_NOTES = "NOTES";
    public static final String JSON_H = "H";    
    public static final String JSON_NATION = "NATION_OF_BIRTH";//NOT EVER USED!!
    
    //More player constants
    public static final String JSON_EST_VALUE = "estimated_value";
    public static final String JSON_POSITION = "position";
    public static final String JSON_CONTRACT = "contract";
    public static final String JSON_SALARY = "salary";
    public static final String JSON_TEAM = "team";
    
    //Hitters.json Constants
    public static final String JSON_HITTERS = "Hitters";
    public static final String JSON_QP = "QP";
    public static final String JSON_AB = "AB";
    public static final String JSON_R = "R";
    public static final String JSON_HR = "HR";
    public static final String JSON_RBI = "RBI";
    public static final String JSON_SB = "SB";
    public static final String JSON_BATTING_AVG = "batting_average";
    
    //Pitchers.json Constants
    public static final String JSON_PITCHERS = "Pitchers";
    public static final String JSON_IP = "IP";
    public static final String JSON_ER = "ER";
    public static final String JSON_W = "W";//wins
    public static final String JSON_SV = "SV";
    public static final String JSON_BB = "BB";//walks
    public static final String JSON_K = "K";
    public static final String JSON_EARNED_RUN_AVG = "earned_run_average";
    public static final String JSON_WHIP = "WHIP";
    
    //Team constants
    public static final String JSON_TEAM_NAME = "team_name";
    public static final String JSON_TEAM_OWNER = "team_owner";
    public static final String JSON_MONEY = "money";
    public static final String JSON_RANKING = "ranking";
    public static final String JSON_STARTERS = "starters";
    public static final String JSON_TAXI = "taxi";
    public static final String JSON_LEAGUE = "league";
    public static final String JSON_TEAMS_IN_LEAGUE = "teams_in_league";
    public static final String JSON_DRAFTABLE_PLAYERS = "draftable_players";
    public static final String JSON_DRAFT = "draft";
    
    public static final String JSON_EXT = ".json";
    public static final String SLASH = "/";
    
    
    public static DraftablePlayers loadDraftablePlayers(
            String jsonPitchersFilePath, 
            String jsonHittersFilePath
            ) throws IOException{
        
        DraftablePlayers draftablePlayers = new DraftablePlayers();
        
        JsonObject jsonPitchers = loadJSONFile(jsonPitchersFilePath);
       
        //PITCHERS FILE
        JsonArray jsonPitchersArray = jsonPitchers.getJsonArray(LeagueFileManager.JSON_PITCHERS);
        
        for(int i=0;i<jsonPitchersArray.size();i++){
            JsonObject jo = jsonPitchersArray.getJsonObject(i);
            String firstName = jo.getString(LeagueFileManager.JSON_FIRST_NAME);
            String lastName = jo.getString(LeagueFileManager.JSON_LAST_NAME);
            String proTeam = jo.getString(LeagueFileManager.JSON_PRO_TEAM);
            String qualifyingPositions = "P";
            int yearOfBirth = Integer.parseInt(jo.getString(LeagueFileManager.JSON_YOB));
            String notes = jo.getString(LeagueFileManager.JSON_NOTES);
            int hits = Integer.parseInt(jo.getString(LeagueFileManager.JSON_H));
            int walks = Integer.parseInt(jo.getString(LeagueFileManager.JSON_BB));
            int wins = Integer.parseInt(jo.getString(LeagueFileManager.JSON_W));
            int strikeouts = Integer.parseInt(jo.getString(LeagueFileManager.JSON_K));
            int saves = Integer.parseInt(jo.getString(LeagueFileManager.JSON_SV));
            double inningsPitched = Double.parseDouble(jo.getString(LeagueFileManager.JSON_IP));
            int earnedRuns = Integer.parseInt(jo.getString(LeagueFileManager.JSON_ER));
            
            String nation = jo.getString(LeagueFileManager.JSON_NATION);
            
            Player p = new Pitcher(
                    firstName,
                    lastName,
                    proTeam,
                    qualifyingPositions,
                    yearOfBirth,
                    notes,
                    hits,
                    walks,
                    wins,
                    strikeouts,
                    saves,
                    inningsPitched,
                    earnedRuns
            );
            p.setNation(nation);
            
            draftablePlayers.addPlayer(p);
        }
        
        //HITTERS FILE
        JsonObject jsonHitters = loadJSONFile(jsonHittersFilePath);
        
        JsonArray jsonHittersArray = jsonHitters.getJsonArray(LeagueFileManager.JSON_HITTERS);
        
        for(int j=0;j<jsonHittersArray.size();j++){
            JsonObject jo = jsonHittersArray.getJsonObject(j);
            String firstName = jo.getString(LeagueFileManager.JSON_FIRST_NAME);
            String lastName = jo.getString(LeagueFileManager.JSON_LAST_NAME);
            String proTeam = jo.getString(LeagueFileManager.JSON_PRO_TEAM);
            String qualifyingPositions = jo.getString(LeagueFileManager.JSON_QP);
            int yearOfBirth = Integer.parseInt(jo.getString(LeagueFileManager.JSON_YOB));
            String notes = jo.getString(LeagueFileManager.JSON_NOTES);
            int hits = Integer.parseInt(jo.getString(LeagueFileManager.JSON_H));
            int walks = -1;
           
            int runs = Integer.parseInt(jo.getString(LeagueFileManager.JSON_R));
            int homeRuns = Integer.parseInt(jo.getString(LeagueFileManager.JSON_HR));
            int runsBattedIn = Integer.parseInt(jo.getString(LeagueFileManager.JSON_RBI));
            int stolenBases = Integer.parseInt(jo.getString(LeagueFileManager.JSON_SB));
            int atBat = Integer.parseInt(jo.getString(LeagueFileManager.JSON_AB));
            
            String nation = jo.getString(LeagueFileManager.JSON_NATION);
            
            Player p = new Hitter(
                    firstName,
                    lastName,
                    proTeam,
                    qualifyingPositions,
                    yearOfBirth,
                    notes,
                    hits,
                    walks,
                    runs,
                    homeRuns,
                    runsBattedIn,
                    stolenBases,
                    atBat
            );
            
            p.setNation(nation);
            
            draftablePlayers.addPlayer(p);
        }
        
        return draftablePlayers;
    }
    
        // LOADS A JSON FILE AS A SINGLE OBJECT AND RETURNS IT
    private static JsonObject loadJSONFile(String jsonFilePath) throws IOException {
        InputStream is = new FileInputStream(jsonFilePath);
        JsonReader jsonReader = Json.createReader(is);
        JsonObject json = jsonReader.readObject();
        jsonReader.close();
        is.close();
        return json;
    }  
    
    public static void saveLeague(WolfieballDataManager dataManager) throws FileNotFoundException{       
       DraftablePlayers dp  = dataManager.getFullDraftablePlayers();
       League league = dataManager.getLeague();   
       
       String fileName = "" + league.getName()+LeagueFileManager.JSON_EXT;
       String filePath = Wolfieball_StartupConstant.PATH_JSON_DRAFT+fileName;
       
       // INIT THE WRITER
        OutputStream os = new FileOutputStream(filePath);
        JsonWriter jsonWriter = Json.createWriter(os);  
        
        jsonWriter.writeObject(makeLeagueObject(league,dp));
    }
   
    private static JsonObject makeLeagueObject(League league, DraftablePlayers dp){
        JsonArrayBuilder jsb = Json.createArrayBuilder();
        for (Team t : league.getTeams()) {
            jsb.add(makeTeamObject(t));
        }
        JsonArray allTeams = jsb.build();

        JsonObject jso = Json.createObjectBuilder()
                .add(LeagueFileManager.JSON_LEAGUE, league.getName())
                .add(LeagueFileManager.JSON_TEAMS_IN_LEAGUE, allTeams)
                .add(LeagueFileManager.JSON_DRAFTABLE_PLAYERS, makeDraftablePlayersArray(dp))
                .build();

        return jso;
    }

    private static JsonArray makeDraftablePlayersArray(DraftablePlayers dp) {
        JsonArrayBuilder jsb = Json.createArrayBuilder();
        for (Player p : dp.getPlayers()) {
            if (p instanceof Hitter) {
                Hitter h = (Hitter) p;
                jsb.add(makeDraftableHitterObject(h));
            } else if (p instanceof Pitcher) {
                Pitcher pi = (Pitcher) p;
                jsb.add(makeDraftablePitcherObject(pi));
            }
        }
        JsonArray jA = jsb.build();

        return jA;
    }

    private static JsonObject makeTeamObject(Team team) {
        JsonObject jso = Json.createObjectBuilder()
                .add(LeagueFileManager.JSON_TEAM_NAME, team.getName())
                .add(LeagueFileManager.JSON_TEAM_OWNER, team.getOwner())
                .add(LeagueFileManager.JSON_MONEY, team.getMoney())
                .add(LeagueFileManager.JSON_RANKING, team.getRanking())
                .add(LeagueFileManager.JSON_STARTERS, makeSubteamArray(team.getStarters()))
                .add(LeagueFileManager.JSON_TAXI, makeSubteamArray(team.getTaxi()))
                .build();

        return jso;
    }

    private static JsonArray makeSubteamArray(Subteam team) {
        JsonArrayBuilder jsb = Json.createArrayBuilder();
        for (Player p : team.getPlayers()) {
            if (p instanceof Hitter) {
                Hitter h = (Hitter) p;
                jsb.add(makeHitterObject(h));
            } else if (p instanceof Pitcher) {
                Pitcher pi = (Pitcher) p;
                jsb.add(makePitcherObject(pi));
            }
        }
        JsonArray jA = jsb.build();
        return jA;
    }

    private static JsonObject makeDraftableHitterObject(Hitter hitter) {
        JsonObject jso = Json.createObjectBuilder()
                //default stats
                .add(LeagueFileManager.JSON_FIRST_NAME, hitter.getFirstName())
                .add(LeagueFileManager.JSON_LAST_NAME, hitter.getLastName())
                .add(LeagueFileManager.JSON_PRO_TEAM, hitter.getProTeam())
                .add(LeagueFileManager.JSON_QP, hitter.getQualifyingPositions())
                .add(LeagueFileManager.JSON_YOB, hitter.getYearOfBirth())
                .add(LeagueFileManager.JSON_NOTES, hitter.getNotes())
                .add(LeagueFileManager.JSON_H, hitter.getHits())
                .add(LeagueFileManager.JSON_BB, hitter.getWalks())
                .add(LeagueFileManager.JSON_EST_VALUE, hitter.getEstimatedValue())
                .add(LeagueFileManager.JSON_NATION, hitter.getNation())
                //hitter only stats
                .add(LeagueFileManager.JSON_R, hitter.getRuns())
                .add(LeagueFileManager.JSON_HR, hitter.getHomeRuns())
                .add(LeagueFileManager.JSON_RBI, hitter.getRunsBattedIn())
                .add(LeagueFileManager.JSON_SB, hitter.getStolenBases())
                .add(LeagueFileManager.JSON_AB, hitter.getAtBat())
                //.add(LeagueFileManager.JSON_BATTING_AVG, hitter.getBattingAverage())
                .build();

        return jso;
    }

    private static JsonObject makeDraftablePitcherObject(Pitcher pitcher) {
        JsonObject jso = Json.createObjectBuilder()
                //default stats
                .add(LeagueFileManager.JSON_FIRST_NAME, pitcher.getFirstName())
                .add(LeagueFileManager.JSON_LAST_NAME, pitcher.getLastName())
                .add(LeagueFileManager.JSON_PRO_TEAM, pitcher.getProTeam())
                .add(LeagueFileManager.JSON_QP, pitcher.getQualifyingPositions())
                .add(LeagueFileManager.JSON_YOB, pitcher.getYearOfBirth())
                .add(LeagueFileManager.JSON_NOTES, pitcher.getNotes())
                .add(LeagueFileManager.JSON_H, pitcher.getHits())
                .add(LeagueFileManager.JSON_BB, pitcher.getWalks())
                .add(LeagueFileManager.JSON_EST_VALUE, pitcher.getEstimatedValue())
                .add(LeagueFileManager.JSON_NATION, pitcher.getNation())
                //pitcher only stats
                .add(LeagueFileManager.JSON_W, pitcher.getWins())
                .add(LeagueFileManager.JSON_K, pitcher.getStrikeouts())
                .add(LeagueFileManager.JSON_SV, pitcher.getStrikeouts())
                .add(LeagueFileManager.JSON_IP, pitcher.getInningsPitched())
                .add(LeagueFileManager.JSON_ER, pitcher.getEarnedRuns())
                //.add(LeagueFileManager.JSON_EARNED_RUN_AVG, pitcher.getEarnedRunAverage())
                //.add(LeagueFileManager.JSON_WHIP, pitcher.getWhip())
                .build();

        return jso;
    }

    private static JsonObject makeHitterObject(Hitter hitter) {
        JsonObject jso = Json.createObjectBuilder()
                //default stats
                .add(LeagueFileManager.JSON_FIRST_NAME, hitter.getFirstName())
                .add(LeagueFileManager.JSON_LAST_NAME, hitter.getLastName())
                .add(LeagueFileManager.JSON_PRO_TEAM, hitter.getProTeam())
                .add(LeagueFileManager.JSON_QP, hitter.getQualifyingPositions())
                .add(LeagueFileManager.JSON_YOB, hitter.getYearOfBirth())
                .add(LeagueFileManager.JSON_NOTES, hitter.getNotes())
                .add(LeagueFileManager.JSON_H, hitter.getHits())
                .add(LeagueFileManager.JSON_BB, hitter.getWalks())
                .add(LeagueFileManager.JSON_EST_VALUE, hitter.getEstimatedValue())
                .add(LeagueFileManager.JSON_NATION, hitter.getNation())
                //hitter only stats
                .add(LeagueFileManager.JSON_R, hitter.getRuns())
                .add(LeagueFileManager.JSON_HR, hitter.getHomeRuns())
                .add(LeagueFileManager.JSON_RBI, hitter.getRunsBattedIn())
                .add(LeagueFileManager.JSON_SB, hitter.getStolenBases())
                .add(LeagueFileManager.JSON_AB, hitter.getAtBat())
                //.add(LeagueFileManager.JSON_BATTING_AVG, hitter.getBattingAverage())
                //only when a player is on a team will he have these stats
                .add(LeagueFileManager.JSON_POSITION, hitter.getPosition())
                .add(LeagueFileManager.JSON_CONTRACT, hitter.getContract().toString())
                .add(LeagueFileManager.JSON_SALARY, hitter.getSalary())
                .add(LeagueFileManager.JSON_TEAM, hitter.getTeam().getName())
                .build();
        return jso;
    }

    private static JsonObject makePitcherObject(Pitcher pitcher) {
        JsonObject jso = Json.createObjectBuilder()
                //default stats
                .add(LeagueFileManager.JSON_FIRST_NAME, pitcher.getFirstName())
                .add(LeagueFileManager.JSON_LAST_NAME, pitcher.getLastName())
                .add(LeagueFileManager.JSON_PRO_TEAM, pitcher.getProTeam())
                .add(LeagueFileManager.JSON_QP, pitcher.getQualifyingPositions())
                .add(LeagueFileManager.JSON_YOB, pitcher.getYearOfBirth())
                .add(LeagueFileManager.JSON_NOTES, pitcher.getNotes())
                .add(LeagueFileManager.JSON_H, pitcher.getHits())
                .add(LeagueFileManager.JSON_BB, pitcher.getWalks())
                .add(LeagueFileManager.JSON_EST_VALUE, pitcher.getEstimatedValue())
                .add(LeagueFileManager.JSON_NATION, pitcher.getNation())
                //pitcher only stats
                .add(LeagueFileManager.JSON_W, pitcher.getWins())
                .add(LeagueFileManager.JSON_K, pitcher.getStrikeouts())
                .add(LeagueFileManager.JSON_SV, pitcher.getStrikeouts())
                .add(LeagueFileManager.JSON_IP, pitcher.getInningsPitched())
                .add(LeagueFileManager.JSON_ER, pitcher.getEarnedRuns())
                //.add(LeagueFileManager.JSON_EARNED_RUN_AVG, pitcher.getEarnedRunAverage())
                //.add(LeagueFileManager.JSON_WHIP, pitcher.getWhip())
                //only when a player is on a team will he have these stats
                .add(LeagueFileManager.JSON_POSITION, pitcher.getPosition())
                .add(LeagueFileManager.JSON_CONTRACT, pitcher.getContract().toString())
                .add(LeagueFileManager.JSON_SALARY, pitcher.getSalary())
                .add(LeagueFileManager.JSON_TEAM, pitcher.getTeam().getName())
                .build();

        return jso;
    }
    
    public static void loadDraft(String jsonFilePath, WolfieballDataManager dataManager) throws IOException{
        //need to clear both before loading draft!
        DraftablePlayers dp = dataManager.getFullDraftablePlayers();
        League league = dataManager.getLeague();
        
        JsonObject json = loadJSONFile(jsonFilePath);
        league.setName(json.getString(LeagueFileManager.JSON_LEAGUE));
        
        //LOAD ALL PLAYERS BACK INTO DRAFTABLE PLAYERS
        JsonArray jsonDraftablePlayersArray = json.getJsonArray(LeagueFileManager.JSON_DRAFTABLE_PLAYERS);
        for(int i=0;i<jsonDraftablePlayersArray.size();i++){
            JsonObject jsonPlayer = jsonDraftablePlayersArray.getJsonObject(i);
            if(jsonPlayer.getString(LeagueFileManager.JSON_QP).contains("P")){
                dp.addPlayer(buildDraftablePitcherFromJsonObject(jsonPlayer));
            }
            else{
                dp.addPlayer(buildDraftableHitterFromJsonObject(jsonPlayer));
            }
        }
        
        //LOAD ALL EMPTY TEAMS IN WITH NAME AND OWNER
        JsonArray jsonTeamsInLeague = json.getJsonArray(LeagueFileManager.JSON_TEAMS_IN_LEAGUE);
        for(int j=0;j<jsonTeamsInLeague.size();j++){
            JsonObject jsonTeam = jsonTeamsInLeague.getJsonObject(j);
            String teamName = jsonTeam.getString(LeagueFileManager.JSON_TEAM_NAME);
            String teamOwner = jsonTeam.getString(LeagueFileManager.JSON_TEAM_OWNER);
            int money = jsonTeam.getInt(LeagueFileManager.JSON_MONEY);
            int ranking = jsonTeam.getInt(LeagueFileManager.JSON_RANKING);
            
            Team t = new Team(teamName, teamOwner);
            t.setMoney(money);
            t.setRanking(ranking);
            league.getTeams().add(t);
        }      
        
        //ADD ALL PLAYERS TO TEAMS
        for(int k=0;k<league.getTeams().size();k++){
            Team t = league.getTeams().get(k);
            JsonObject jsonTeam = jsonTeamsInLeague.getJsonObject(k);
            JsonArray jsonStarters = jsonTeam.getJsonArray(JSON_STARTERS);
            JsonArray jsonTaxi = jsonTeam.getJsonArray(JSON_TAXI);
            
            for(int s=0;s<jsonStarters.size();s++){
                JsonObject jsonPlayer = jsonStarters.getJsonObject(s);
                if(jsonPlayer.getString(LeagueFileManager.JSON_QP).contains("P")){
                    Pitcher p = LeagueFileManager.buildPitcherFromJsonObject(jsonPlayer, league);
                    t.addPlayer(p, null);
                }
                else{
                    Hitter h = LeagueFileManager.buildHitterFromJsonObject(jsonPlayer, league);
                    t.addPlayer(h, null);
                }
            }
            
            for(int ta=0;ta<jsonTaxi.size();ta++){
                JsonObject jsonPlayer = jsonTaxi.getJsonObject(ta);
                if(jsonPlayer.getString(LeagueFileManager.JSON_QP).contains("P")){
                    Pitcher p = LeagueFileManager.buildPitcherFromJsonObject(jsonPlayer, league);
                    t.addPlayer(p, null);
                }
                else{
                    Hitter h = LeagueFileManager.buildHitterFromJsonObject(jsonPlayer, league);
                    t.addPlayer(h, null);
                }
            }
        }
    }
    
    private static Pitcher buildDraftablePitcherFromJsonObject(JsonObject pitcher){
        String firstName = pitcher.getString(LeagueFileManager.JSON_FIRST_NAME);
        String lastName = pitcher.getString(LeagueFileManager.JSON_LAST_NAME);
        String proTeam = pitcher.getString(LeagueFileManager.JSON_PRO_TEAM);
        String qp = pitcher.getString(LeagueFileManager.JSON_QP);
        int yob = pitcher.getInt(LeagueFileManager.JSON_YOB);
        String notes = pitcher.getString(LeagueFileManager.JSON_NOTES);
        int hits = pitcher.getInt(LeagueFileManager.JSON_H);
        int walks = pitcher.getInt(LeagueFileManager.JSON_BB);
        //double estimatedValue = Double.parseDouble(pitcher.getString(LeagueFileManager.JSON_EST_VALUE));
        String nation = pitcher.getString(LeagueFileManager.JSON_NATION);
        //pitcher only stats
        int wins = pitcher.getInt(LeagueFileManager.JSON_W);
        int strikeouts = pitcher.getInt(LeagueFileManager.JSON_K);
        int saves = pitcher.getInt(LeagueFileManager.JSON_SV);
        int inningsPitched = pitcher.getInt(LeagueFileManager.JSON_IP);
        int earnedRuns = pitcher.getInt(LeagueFileManager.JSON_ER);
        
        Pitcher p = new Pitcher(
                firstName,
                lastName,
                proTeam,
                qp,
                yob,
                notes,
                hits,
                walks,
                wins,
                strikeouts,
                saves,
                inningsPitched,
                earnedRuns
        );
        p.setNation(nation);
        //p.setEstimatedValue(estimatedValue);
        
        return p;
    }
    
    private static Hitter buildDraftableHitterFromJsonObject(JsonObject hitter){
        String firstName = hitter.getString(LeagueFileManager.JSON_FIRST_NAME);
        String lastName = hitter.getString(LeagueFileManager.JSON_LAST_NAME);
        String proTeam = hitter.getString(LeagueFileManager.JSON_PRO_TEAM);
        String qp = hitter.getString(LeagueFileManager.JSON_QP);
        int yob = hitter.getInt(LeagueFileManager.JSON_YOB);
        String notes = hitter.getString(LeagueFileManager.JSON_NOTES);
        int hits = hitter.getInt(LeagueFileManager.JSON_H);
        int walks = hitter.getInt(LeagueFileManager.JSON_BB);
        //double estimatedValue = Double.parseDouble(hitter.getString(LeagueFileManager.JSON_EST_VALUE));
        String nation = hitter.getString(LeagueFileManager.JSON_NATION);
        //hitter only stats
        int runs = hitter.getInt(LeagueFileManager.JSON_R);
        int homeRuns = hitter.getInt(LeagueFileManager.JSON_HR);
        int runsBattedIn = hitter.getInt(LeagueFileManager.JSON_RBI);
        int stolenBases = hitter.getInt(LeagueFileManager.JSON_SB);
        int atBat = hitter.getInt(LeagueFileManager.JSON_AB);
        
        Hitter h = new Hitter(
                firstName,
                lastName,
                proTeam,
                qp,
                yob,
                notes,
                hits,
                walks,
                runs,
                homeRuns,
                runsBattedIn,
                stolenBases,
                atBat
        );
        h.setNation(nation);
        //h.setEstimatedValue(estimatedValue);
        
        return h;                
    }
    
    private static Pitcher buildPitcherFromJsonObject(JsonObject pitcher, League league){
        Pitcher p = LeagueFileManager.buildDraftablePitcherFromJsonObject(pitcher);
        
        String position = pitcher.getString(LeagueFileManager.JSON_POSITION);
        Contract contract = Contract.valueOf(pitcher.getString(LeagueFileManager.JSON_CONTRACT));
        int salary = pitcher.getInt(LeagueFileManager.JSON_SALARY);
        String teamString = pitcher.getString(LeagueFileManager.JSON_TEAM);
        Team team = league.getTeamByName(pitcher.getString(JSON_TEAM));
        
        p.setDraftAttributes(position, contract, salary, team);
        
        return p;
    }
    
    private static Hitter buildHitterFromJsonObject(JsonObject hitter, League league){
        Hitter h = LeagueFileManager.buildDraftableHitterFromJsonObject(hitter);
        
        String position = hitter.getString(LeagueFileManager.JSON_POSITION);
        Contract contract = Contract.valueOf(hitter.getString(LeagueFileManager.JSON_CONTRACT));
        int salary = hitter.getInt(LeagueFileManager.JSON_SALARY);
        String teamString = hitter.getString(LeagueFileManager.JSON_TEAM);
        Team team = league.getTeamByName(hitter.getString(JSON_TEAM));
        
        h.setDraftAttributes(position, contract, salary, team);
        
        return h;
    }
    
    
}
