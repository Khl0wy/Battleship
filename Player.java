// Written by : Mohaimen Ibn Reza Shuvo

public class Player {
  private String name;        // Stores player name
  private Grid ownGrid;       // Stores player grid
  private Grid enemyGrid;     // Stores tracking enemy board of player
  private Ship[] ships;       // Array stores player ships
  private int shipsRemaining; // Keeps count of remaining ships of player
  //=============Constructors===============
  /* Constructor that creates player with given name and initializes grids and ships.
   * @param name The player's display name */
  public Player(String name){
    this.name = name;
    this.ownGrid = new Grid();
    this.enemyGrid = new Grid();
    this.ships = new Ship[5];
    this.ships[0] = new Carrier();
    this.ships[1] = new Battleship();
    this.ships[2] = new Cruiser();
    this.ships[3] = new Submarine();
    this.ships[4] = new Destroyer();
    this.shipsRemaining = 5;
  }
  
    //=============Public Methods================
    /* Gets name of player
     * @return - returns name of player */
    public String GetName() {
      return name;
    }
    
    /* Gets Grid of player
     * @return - returns player's grid object */
    public Grid GetOwnGrid() {
      return ownGrid;
    }
    /* Gets enemy grid
     * @return - returns grid of opponent */
    public Grid GetEnemyGrid() {
      return enemyGrid;
    }
    /* Gets ships array of player
     * @return - returns array of ships */
    public Ship[] GetShips() {
      return ships;
    }
    /* Gets chosen ship of player
     * @param index - index of that specific ship in the ships array
     * @return - returns selected ship from the ship array */
    public Ship GetShip(int index){
      return ships[index];
    }
    public void DecrementShips() {
      shipsRemaining--;
    }
    
    /* Executes normal attack on opponent's grid at specified coordinates.
     * @param row - Row coordinate to attack (0-9)
     * @param col - Column coordinate to attack (0-9)
     * @param opponent - The opponent player object
     * @return - false if already attacked, true otherwise */
    public String Attack(int row, int col, Player opponent){
      String attackResult=opponent.GetOwnGrid().ReceiveAttack(row,col);
      if(attackResult.equals("ALREADY ATTACKED!")||attackResult.equals("INVALID INPUT!")){  // For these outcomes just returns as it is
        return attackResult;
      }
      if(attackResult.equals("HIT!")){   // If hit, marks tracking board of player with X
        enemyGrid.MarkHit(row,col);
      }
      else if(attackResult.equals("MISS!")){  // If miss, marks tracking board of player with O
        enemyGrid.MarkMiss(row,col);
      }
      else {                                 // Hit and sunk condition. Marks player tracking board with X and calls DecrementShips for opponent player
        enemyGrid.MarkHit(row,col);
        opponent.DecrementShips();
      }
      return attackResult;
    }
    
    /* Places ships in the grid
     * @param shipIndex - index of ship places in the ships array
     * @param row - selected row for ship placement
     * @param col - selected column for ship placement
     * @param isHorizontal - condition of ship, horizontal or not
     * @return - true if ship placed successfully  */
    public boolean PlaceShip(int shipIndex, int row, int col, boolean isHorizontal) {
      Ship ship = ships[shipIndex];
      boolean placed = ownGrid.PlaceShip(ship, row, col, isHorizontal);  // Calls PlaceShip method in grid class for ship placement
      return placed;
    }
    
    /* Checks if player has lost
     * @return - returns true if players remaining ships become 0, otherwise false  */
    public boolean HasLost(){
      return this.shipsRemaining==0;
    }
    
    /* Activates ships super power
     * @param shipIndex - index of ship the power of which player wants to use
     * @param currPlayer - current player object
     * @param opponent - opponent player object
     * @param system - BattleShipSys object
     * @param row1 - 1st row selected by player (set to -1 if not needed)
     * @param col1 - 1st Column selected by player (set to -1 if not needed)
     * @param row2 - 2nd row selected by player (set to -1 if not needed)
     * @param col2 - 2nd Column selected by player (set to -1 if not needed)  */
    public void ActivateShipPower(int shipIndex,Player currPlayer, Player opponent, BattleShipSys system, int row1, int col1, int row2, int col2) {
      Ship selectedShip = ships[shipIndex];
      
      if (!selectedShip.CanUsePower()) {   // If player tries to force use a super power even if it is not available, diplays unavailable message
        System.out.println("This superpower is not available!");
      }
      else{                                // If power available, then activates power
      selectedShip.ActivatePower(opponent.GetOwnGrid(),currPlayer.GetEnemyGrid(), opponent, row1,col1,row2,col2);
      system.SetCooldown(currPlayer, selectedShip.GetCooldownPeriod());
      }
    }
 }