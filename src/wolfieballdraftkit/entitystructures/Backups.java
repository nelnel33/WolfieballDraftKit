package wolfieballdraftkit.entitystructures;

/**
 *
 * @author Nelnel33
 */
public class Backups extends Subteam{
    
    public static final int MAX_PLAYERS_BACKUP_LINEUP = 8;

    public Backups(Team parentTeam) {
        super(MAX_PLAYERS_BACKUP_LINEUP, parentTeam);
    }
    
    
    
}
