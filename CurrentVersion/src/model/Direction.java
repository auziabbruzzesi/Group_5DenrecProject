package model;

public enum Direction {
    NORTH(0),SOUTH(1),EAST(2),WEST(3),NORTHEAST(4),NORTHWEST(5),SOUTHEAST(6),SOUTHWEST(7);
    
    private int rank;
    
    Direction(int rank){
        this.rank = rank;
    }
    public int getRank(){
        return this.rank;
    }

}
