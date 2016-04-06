package wolfieballdraftkit.entitystructures;
/**
 *
 * @author Nelnel33
 */
public class Team {
    public static final int DEFAULT_MONEY = 260;
    
    //lists that contain starter and taxi squad.
    private Starters starters;
    private Backups taxi;
    
    //NAME+OWNER
    private String name;
    private String owner;
    
    private int money;//set to default value
    
    //init to -1; recalculated everytime you add/remove player
    private double totalPoints;
    
    private int playersLeft;
    private int moneyPerPlayer;
    
    private int pointsRuns;
    private int pointsHomeRuns;
    private int pointsRunsBattedIn;
    private int pointsStolenBases;
    private int pointsBattingAverage;
    
    private int pointsWins;
    private int pointsStrikeouts;
    private int pointsSaves;
    private int pointsERA;
    private int pointsWHIP;
    
    private int totalRuns;
    private int totalHomeRuns;
    private int totalRunsBattedIn;
    private int totalStolenBases;
    private double battingAverage;
    
    private int wins;
    private int strikeouts;
    private int saves;
    private double earnedRunAverage;
    private double whip;

    
    //init by league manager; changes when new team is added
    private int ranking;
    
    public Team(
            String name, 
            String owner
            ){
        
        starters = new Starters(this);
        taxi = new Backups(this);
        
        this.name = name;
        this.owner = owner;
        
        money = Team.DEFAULT_MONEY;
        
        totalPoints = 0;
        
        playersLeft = Starters.MAX_PLAYERS_STARTING_LINEUP;
        moneyPerPlayer = money/playersLeft;
        
        pointsRuns = 0;
        pointsHomeRuns = 0;
        pointsRunsBattedIn = 0;
        pointsStolenBases = 0;
        pointsBattingAverage = 0;
    
        pointsWins = 0;
        pointsStrikeouts = 0;
        pointsSaves = 0;
        pointsERA = 0;
        pointsWHIP = 0;
        
        totalRuns = -1;
        totalHomeRuns = -1;
        totalRunsBattedIn = -1;
        totalStolenBases = -1;
        battingAverage = -1;
        
        wins = -1;
        strikeouts = -1;
        saves = -1;
        earnedRunAverage = -1;
        whip = -1;
    }  
    
    public void calculateTotalPoints(){
        totalPoints = pointsRuns
                + pointsHomeRuns
                + pointsRunsBattedIn
                + pointsStolenBases
                + pointsBattingAverage
                + pointsWins
                + pointsStrikeouts
                + pointsSaves
                + pointsERA
                + pointsWHIP;
    }
    
    public void calculateStats(){        
        int playersleft = Starters.MAX_PLAYERS_STARTING_LINEUP - this.getStarters().getPlayers().size();
        int pp;
        if(playersLeft > 0){
            pp = money / playersLeft;        
        }
        else{
            pp = money;
        }
        
        int runs = 0;
        int hr = 0;
        int rbi = 0;
        int sb = 0;
        double ba = -1;//hits/atbat
        int hits = 0;//helper
        int atBat = 0;//helper
                
        int w = 0;
        int sv = 0;
        int k = 0;
        double era = -1;//earnedruns*9/inningsPitched
        double whip = -1;//walks+hit/inningsPitched
        int bb = 0;//helper - walks
        int er = 0;//helper - earnedruns
        int ip = 0;//helper - inningspitched
        int pitcherhits = 0;
        
        for(Player s: this.starters.getPlayers()){
            if(s instanceof Pitcher){
                Pitcher p = (Pitcher)s;
                w += p.getWins();
                sv += p.getSaves();
                k += p.getStrikeouts();
                bb += p.getWalks();
                er += p.getEarnedRuns();
                ip += p.getInningsPitched();
                pitcherhits += p.getHits();
            }
            else if(s instanceof Hitter){
                Hitter h = (Hitter)s;
                runs += h.getRuns();
                hr += h.getHomeRuns();
                rbi += h.getRunsBattedIn();
                sb += h.getStolenBases();
                hits += h.getHits();
                atBat += h.getAtBat();
            }
            else{
                System.err.println("Player "+s.toString()+" is not a hitter or pitcher!");
            }
        }
        for(Player t: this.taxi.getPlayers()){
            if(t instanceof Pitcher){
                Pitcher p = (Pitcher)t;
                w += p.getWins();
                sv += p.getSaves();
                k += p.getStrikeouts();
                bb += p.getWalks();
                er += p.getEarnedRuns();
                ip += p.getInningsPitched();
            }
            else if(t instanceof Hitter){
                Hitter h = (Hitter)t;
                runs += h.getRuns();
                hr += h.getHomeRuns();
                rbi += h.getRunsBattedIn();
                sb += h.getStolenBases();
                hits += h.getHits();
                atBat += h.getAtBat();
            }
            else{
                System.err.println("Player "+t.toString()+" is not a hitter or pitcher!");
            }
        }
        
        if(atBat > 0){
            ba = ((double)hits)/((double)atBat);
        }
        if(ip > 0){
            era = ((double)er*9)/((double)ip);
            whip = (((double)bb)+((double)pitcherhits))/((double)ip);
        }
        
        this.playersLeft = playersleft;
        this.moneyPerPlayer = pp;
        
        totalRuns = runs;
        totalHomeRuns = hr;
        totalRunsBattedIn = rbi;
        totalStolenBases = sb;
        battingAverage = ba;
        wins = w;
        strikeouts = k;
        saves = sv;
        earnedRunAverage = era;
        this.whip = whip;
        
        calculateTotalPoints();
        
    }

    public int getPointsRuns() {
        return pointsRuns;
    }

    public void setPointsRuns(int pointsRuns) {
        this.pointsRuns = pointsRuns;
    }

    public int getPointsHomeRuns() {
        return pointsHomeRuns;
    }

    public void setPointsHomeRuns(int pointsHomeRuns) {
        this.pointsHomeRuns = pointsHomeRuns;
    }

    public int getPointsRunsBattedIn() {
        return pointsRunsBattedIn;
    }

    public void setPointsRunsBattedIn(int pointsRunsBattedIn) {
        this.pointsRunsBattedIn = pointsRunsBattedIn;
    }

    public int getPointsStolenBases() {
        return pointsStolenBases;
    }

    public void setPointsStolenBases(int pointsStolenBases) {
        this.pointsStolenBases = pointsStolenBases;
    }

    public int getPointsBattingAverage() {
        return pointsBattingAverage;
    }

    public void setPointsBattingAverage(int pointsBattingAverage) {
        this.pointsBattingAverage = pointsBattingAverage;
    }

    public int getPointsWins() {
        return pointsWins;
    }

    public void setPointsWins(int pointsWins) {
        this.pointsWins = pointsWins;
    }

    public int getPointsStrikeouts() {
        return pointsStrikeouts;
    }

    public void setPointsStrikeouts(int pointsStrikeouts) {
        this.pointsStrikeouts = pointsStrikeouts;
    }

    public int getPointsSaves() {
        return pointsSaves;
    }

    public void setPointsSaves(int pointsSaves) {
        this.pointsSaves = pointsSaves;
    }

    public int getPointsERA() {
        return pointsERA;
    }

    public void setPointsERA(int pointsERA) {
        this.pointsERA = pointsERA;
    }

    public int getPointsWHIP() {
        return pointsWHIP;
    }

    public void setPointsWHIP(int pointsWHIP) {
        this.pointsWHIP = pointsWHIP;
    }

    public int getPlayersLeft() {
        return playersLeft;
    }

    public int getMoneyPerPlayer() {
        return moneyPerPlayer;
    }

    public double getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(double totalPoints) {
        this.totalPoints = totalPoints;
    }

    public int getTotalRuns() {
        return totalRuns;
    }

    public void setTotalRuns(int totalRuns) {
        this.totalRuns = totalRuns;
    }

    public int getTotalHomeRuns() {
        return totalHomeRuns;
    }

    public void setTotalHomeRuns(int totalHomeRuns) {
        this.totalHomeRuns = totalHomeRuns;
    }

    public int getTotalRunsBattedIn() {
        return totalRunsBattedIn;
    }

    public void setTotalRunsBattedIn(int totalRunsBattedIn) {
        this.totalRunsBattedIn = totalRunsBattedIn;
    }

    public int getTotalStolenBases() {
        return totalStolenBases;
    }

    public void setTotalStolenBases(int totalStolenBases) {
        this.totalStolenBases = totalStolenBases;
    }

    public double getBattingAverage() {
        return battingAverage;
    }

    public void setBattingAverage(double battingAverage) {
        this.battingAverage = battingAverage;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getStrikeouts() {
        return strikeouts;
    }

    public void setStrikeouts(int strikeouts) {
        this.strikeouts = strikeouts;
    }

    public int getSaves() {
        return saves;
    }

    public void setSaves(int saves) {
        this.saves = saves;
    }

    public double getEarnedRunAverage() {
        return earnedRunAverage;
    }

    public void setEarnedRunAverage(double earnedRunAverage) {
        this.earnedRunAverage = earnedRunAverage;
    }

    public double getWhip() {
        return whip;
    }

    public void setWhip(double whip) {
        this.whip = whip;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
    
    public String getName() {
        return name;
    }

    public String getOwner() {
        return owner;
    }

    public Starters getStarters() {
        return starters;
    }

    public Backups getTaxi() {
        return taxi;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }
    
    public boolean addPlayer(Player p, DraftablePlayers dp){
        if(!starters.isFull()){
            int money = p.getSalary();
            if (this.money >= money) {
                boolean flag = starters.addPlayer(p);
                if (flag) {
                    this.money -= money;
                    calculateStats();
                    if (dp != null) {
                        dp.removePlayer(p);
                    }
                }
                return flag;
            }
        }
        else{
            boolean flag = taxi.addPlayer(p);
            calculateStats();
            if(flag){
                if(dp != null){
                    dp.removePlayer(p);
                }
            }
            return flag;
        }
        
        return false;
    }
    
    public boolean removePlayer(String firstName, String lastName, DraftablePlayers dp){
        for(Player p: starters.getPlayers()){
            if(p.getFirstName().equals(firstName) && p.getLastName().equals(lastName)){
                starters.getPlayers().remove(p);
                this.money += p.getSalary();
                this.calculateStats();
                dp.addPlayer(p);
                return true;
            }
        }
        for(Player l: taxi.getPlayers()){
            if(l.getFirstName().equals(firstName) && l.getLastName().equals(lastName)){
                taxi.getPlayers().remove(l);
                dp.addPlayer(l);
                return true;
            }
        }
        return false;
    }
    
    /**
     * When removed, sends all players back to the DraftablePlayers
     * @param dp
     */
    public void reset(DraftablePlayers dp){
        for(int i=0;i<starters.getPlayers().size();i++){
            dp.addPlayer(starters.removePlayer(i));
        }
        for(int j=0;j<taxi.getPlayers().size();j++){
            dp.addPlayer(starters.removePlayer(j));
        }
        
        this.money = Team.DEFAULT_MONEY;
    }
    
    public String toString(){
        return this.name;
    }
    
    public String getAllInfo(){
        return 
                "--------------"+
                name + "\n"
                + money + "\n"
                + totalPoints + "\n"
                + totalRuns + "\n"
                + totalHomeRuns + "\n"
                + totalRunsBattedIn + "\n"
                + totalStolenBases + "\n"
                + battingAverage + "\n"
                + wins + "\n"
                + strikeouts + "\n"
                + saves + "\n"
                + earnedRunAverage + "\n"
                + whip + "\n"
                + "------------";

    }
    
    
    
}
