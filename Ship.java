// Written by : Mohaimen Ibn Reza Shuvo

// Abstract class
public abstract class Ship {
    // Global constants for ships
    public static final int CARRIER = 1;
    public static final int BATTLESHIP = 2;
    public static final int CRUISER = 3;
    public static final int SUBMARINE = 4;
    public static final int DESTROYER = 5;

    private String shipName;      // Stores ship name
    private int size;             // Stores ship size
    private int health;           // Stores ship health
    private boolean isSunk;       // Checks if ship sank or not
    private int[][] coordinates;  // 2D array storing the grid positions occupied by the ship
    private boolean powerUsed;    // Variable to chack if ship's power has already been used or not
    private int cooldownPeriod;   // After using this ships super power, the cooldown time

    //============Constructors================
    /* Creates a ship objects and instantiate variables
     * @param name - name of ship
     * @param size - size of ship
     * @param cooldownPeriod - cooldown period of ship after using power  */
    public Ship(String name, int size, int cooldownPeriod){
        this.shipName = name;
        this.size = size;
        this.health = size;
        this.isSunk = false;
        this.coordinates = new int[size][2];
        this.powerUsed = false;
        this.cooldownPeriod = cooldownPeriod;
    }
    
    //==============Public Methods==============
    /* Used to make a ship take damage and reduce health, or sink if health is 0
     * @return - true if ship can take damage and reduce health or sink, otherwise false  */
    public boolean TakeDamage() {
        if (health > 0) {
            health--;
            if (health == 0) {  // If health reaches 0, sets isSunk to true
                isSunk = true;
            }
            return true;
        }
        return false;
    }
    
    /* Gets ship's is sunk condition
     * @return - returns isSunk  */
    public boolean IsSunk() {
        return isSunk;
    }
    
    /* Checks if ship's power can be used or not
     * @return - returns true if power is not used, and ship is not sunk  */
    public boolean CanUsePower() {
        return !powerUsed && !isSunk;
    }
    
    /* Gets ship size
     * @return - returns ship size  */
    public int GetSize() {
        return size;
    }
    
    /* Gets ship name
     * @return - returns ship name  */
    public String GetShipName() {
        return shipName;
    }
    
    /* Gets ship cooldown period
     * @return - returns ship cooldown period  */
    public int GetCooldownPeriod() {
        return cooldownPeriod;
    }

    /* Gets ship health
     * @return - returns ship health  */
    public int GetHealth() {
        return health;
    }

    /* Gets power used condition of ship
     * @return - returns true if power used, otherwise returns false */
    public boolean GetPowerUsed() {
        return powerUsed;
    }

    /* Gets ship coordinates array
     * @return - returns ship coordinates array  */
    public int[][] GetCoordinates() {
        return coordinates;
    }

    /* Sets ship Coordinates in coordinates array
     * @param coords - 2D array of ships coordinates  */
    public void SetCoordinates(int[][] coords) {
        this.coordinates = coords;
    }

    // Abstract method with all parameters - must be overridden by all subclasses
    public abstract void ActivatePower(Grid opponentOwnGrid, Grid enemyGrid, Player opponent, int row, int col, int row2, int col2);

    /* Abstract method (Must be overriden), Contains and returns ship power name for displayer
     * @return - returns power name of ship */ 
    public abstract String PowerName();
    
    // Makrs ship power as used
    public void MarkPowerUsed() {
        this.powerUsed = true;
    }
}