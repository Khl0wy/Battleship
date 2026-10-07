// DEVELOPED BY: CHLOE
import java.util.Random;
public class Grid {
  
  // Private Variables
  private int[][] board; // Stores the status of each cell
  private Ship[][] shipRef;  // Stores actual ship objects
  
  //=============== Constructor =================
  public Grid() {
    this.board = new int [10][10];     // Create size of the board
    this.shipRef = new Ship [10][10];  // Stores actual ship objects
    
    // Default set everything as water at the start of the game
    for (int i = 0; i < 10; i++) {
      for (int j = 0; j < 10; j++) {
        board[i][j] = Displayer.WATER;
      }
    }
  }
  
  //====================== Private Methods =======================//
  /* Helper Method: Checks whether the specific cell is free (Water)
   * @param row          - Intended row to be placed
   * @param col          - Intended column to be placed
   * @param isHorizontal - Is the ship placed horizontally?
   * @return             - True if the cell is available, otherwise false
   */
  private boolean isCellAvailable (int row, int col, int size, boolean isHorizontal) {
    // Loops through the size of the ship
    for (int i = 0; i < size; i++) {
      int checkRow;
      int checkCol;
      
      // Gets the coordinates to be filled with ship objects
      if (isHorizontal) {
        checkRow = row;
        checkCol = col + i;
        // Checks whether it's out of bound
        if(checkRow >= 10 || checkCol >= 10 || checkRow < 0 || checkCol < 0){
          return false;
        }
      } 
      else {
        checkRow = row + i;
        checkCol = col;
        // Checks whether it's out of bound
        if(checkRow >= 10 || checkCol >= 10 || checkRow < 0 || checkCol < 0){
          return false;
        }
      }
      // Checks whether the coordinates are water (Free)
      if (board[checkRow][checkCol] != Displayer.WATER) {
        return false;
      }
    }
    return true; 
  }
  
  /* Helper Method: Checks whether the ship is within the grid 
   * And does not overlap with other ships
   * @param ship         - Actual ship object: Which ship (Different sizes for each ship)
   * @param row          - Intended row to be placed
   * @param col          - Intended column to be placed
   * @param isHorizontal - Is the ship placed horizontally?
   * @return             - True is the placement is valid, otherwise false
   */
  private boolean isValidPlacement (Ship ship, int row, int col, boolean isHorizontal) {
    int size = ship.GetSize();
    
    // Checks whether it's horizontally placed
    // And whether it's out of the grid's size
    if (isHorizontal) {  // Horizontal 
      if (col + size > 10) { //Out of bound
        return false; 
      }
    }
    // Ships are placed vertically
    else {
      if (row + size > 10) {  //Out of bound
        return false; 
      }
    }
    return isCellAvailable (row, col, size, isHorizontal); // True or false based on the placement's validity
  }
  
  //============= Public Methods =============== 
  /* Get the board. (Size is fixed)
   * @return - The board. 
   */
  public int[][] GetBoard() {
    return this.board;
  }
  
  /* Process player's attack
   * Whenever players attack, it will call this method
   * @param row - The intended attack row
   * @param col - The intended attack column
   * @return    -  A message result (Based on the outcome of the attack)
   */
  public String ReceiveAttack(int row, int col) {  
    // Checks whether it's within the grid's range
    if (row < 0 || row >= 10 || col < 0 || col >= 10) {
      return "INVALID INPUT!"; 
    }
    
    int coordinate = board[row][col];
    
    // Checks whether the targeted cell has been attacked before
    if (coordinate == Displayer.HIT || coordinate == Displayer.MISS) {
      return "ALREADY ATTACKED!"; 
    }
    // If it's a ship, will attack, change the status of the cell, and damage the ship
    if (coordinate == Displayer.SHIP) {
      board[row][col] = Displayer.HIT;
      Ship hitShip = shipRef[row][col];
      hitShip.TakeDamage();
      
      // Checks the status of the ship after it has been damaged
      if (hitShip.IsSunk()) {
        return "HIT AND SUNK " + hitShip.GetShipName() + " !"; // Hit and sunk
      }
      else {
        return "HIT!"; // Just damaged
      }
    }
    board[row][col] = Displayer.MISS;
    return "MISS!"; // Missed
  }
  
  /* Controls the ship placement
   * @param ship         - Actual ship object: Which ship (Different sizes for each ship)
   * @param row          - Intended row to be placed
   * @param col          - Intended column to be placed
   * @param isHorizontal - Is the ship placed horizontally?
   * @return             - True if ship is placed successfully, otherwise return false
   */
  public boolean PlaceShip(Ship ship, int row, int col, boolean isHorizontal) {
    // Checks the validity of the ship placement 
    if (!isValidPlacement(ship, row, col, isHorizontal)) {
      return false; 
    }
    int size = ship.GetSize();
    int [][] coordinates = new int [size][2]; // The exact coordinates of each ship 
    
    // Loops through the ship's size
    for (int i = 0; i < size; i++) {
      int actualRow;
      int actualCol;
      
      // Horizontally placed: Row stays the same; column increases by 1 every loop
      if (isHorizontal) {
        actualRow = row;
        actualCol = col + i;
      }
      // Vertically placed: Column stays the same; row increases by 1 every loop
      else {
        actualRow = row + i;
        actualCol = col;
      }
      
      // Marks the placed coordinates as actual ships
      board[actualRow][actualCol] = Displayer.SHIP;
      shipRef[actualRow][actualCol] = ship;
      coordinates[i][0] = actualRow;
      coordinates[i][1] = actualCol;
    }
    ship.SetCoordinates(coordinates);
    return true;
  }
  
  /* Marks the cell as hit
   * @param row  - Attacked row
   * @param col  - Attacked column
   */
  public void MarkHit (int row, int col) {
    board[row][col] = Displayer.HIT;
  }
  
  /* Marks the cell as miss
   * @param row  - Attacked row
   * @param col  - Attacked column
   */
  public void MarkMiss (int row, int col) {
    board[row][col] = Displayer.MISS;
  }
  
  /* Gets a random unhit ship coordinate
   * @return - One random ship coordinate 
   *         - Or null if no unhit ships
   */
  public int[] RandomShipCoordinate () {
    Random r = new Random();
    int[] shipCoord = new int[100];  // Stores ship coordinates (In pairs: row and col)
    int count = 0;  // Number of unhit ship positions
    
    // Loops through the entire 10x10 grid
    for (int row = 0; row < 10; row++) {
      for (int col = 0; col < 10; col++) {
        // Checks if the cell is a ship (Unhit)
        if (board[row][col] == Displayer.SHIP) {
          // Gets the exact coordinates of unhit ships
          shipCoord[count * 2 ] = row;
          shipCoord[count * 2 + 1] = col;
          count++; 
        }
      }
    }
    // All ships have sunk
    // Shouldn't run this as game end
    if (count == 0) {
      return null; 
    }
    // Get a random ship index from all the unhit ships
    int randIndex = r.nextInt(count);
    // Gets the row and column that belongs to the random ship index
    int attackRow = shipCoord[randIndex * 2];
    int attackCol = shipCoord[randIndex * 2 + 1];
    return new int[]{attackRow, attackCol};   // The random ship coordinate
  }
}