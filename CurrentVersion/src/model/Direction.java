package model;
/**
 * @enum Direction for determining which way the player is moving/facing
 *
 */
public enum Direction {
    NORTH(0),SOUTH(1),EAST(2),WEST(3),NORTHEAST(4),NORTHWEST(5),SOUTHEAST(6),SOUTHWEST(7);
    
    private int rank;
    
    /**
     * @param rank set rank to this
     */
    Direction(int rank){
        this.rank = rank;
    }
    /**
     * @return rank
     */
    public int getRank(){
        return this.rank;
    }

}
