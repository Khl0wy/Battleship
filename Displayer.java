// DEVELOPED BY: CHLOE
public class Displayer {
  // Global Variables
  public static final int WATER = 0;
  public static final int SHIP = 1;
  public static final int HIT = 2;
  public static final int MISS = 3;
  
  //=============== Constructor =================
  public Displayer () {
    
  }
  
  //====================== Private Methods =======================//
  /** 
   * Helper Method: Displays the symbol according to the content of the cell
   * @param cell      - The value of the cell in the grid (WATER/SHIP/HIT/MISS)
   * @param hideShips - Whether ships should be hidden (Just in case because tracking grid doesn't store any actual ship objects)
   * @return          - Character symbol to print
   */
  private char getSymbolStatus(int cell, boolean hideShips) {
    if (cell == SHIP && hideShips) {
      return '~';  // Hide ships
    }
    char symbol;
    
    switch (cell) {
      case WATER: 
        symbol = '~'; 
        break; // Water
      case SHIP:
        symbol = 'S'; 
        break; // Ship
      case HIT:
        symbol = 'X'; 
        break; // Hit
      case MISS:
        symbol = 'O'; 
        break; // Miss
      default:
        symbol = '?'; // Error
    }
    return symbol;
  }
  
  /**
   * Helper Method: Prints the column numbers (1–10) for grid display */
  private void printColNum() {
    System.out.print("   ");
    for (int col = 1; col <= 10; col++) {
      System.out.print(col + " ");
    }
    System.out.println();
  }
  
  //============= Public Methods =============== 
  /* Displays the game title screen */
  public void DisplayWelcome() {
    System.out.println ("=============================================================");
    System.out.printf ("%51s\n", "WELCOME TO BATTLESHIP BY REZA AND CHLOE :)");
    System.out.println ("=============================================================");
  }
  
  /* Displays the list of available attack options */
  public void DisplayMenu() {
    System.out.println("\n1. Normal Attack");
    System.out.println("2. Use Superpower");
  }
  
  /* Displays the grid (Both own and tracking grid) 
   * @param grid      - The grid object containing 2D information to display
   * @param hideShips - Prevents showing enemy's ships on the tracking grid (But not used - just for safety) 
   */
  public void DisplayGrid(Grid grid, boolean hideShips) {
    int[][] board = grid.GetBoard();
    
    // Prints column numbers
    printColNum();
    
    // Prints row numbers
    for (int row = 0; row < 10; row++) {
      System.out.printf("%2d ", row + 1);  // Alignment of row numbers
      
      // Prints the symbols on the grid depending on the different conditions of the respective cells
      for (int col = 0; col < 10; col++) {
        System.out.print(getSymbolStatus(board[row][col], hideShips) + " ");
      }
      System.out.println();
    }
  }
  
  /* Displays the overall cooldown status (Period)(How many more rounds until the next use of superpower)
   * @param cooldown - Number of turns before another superpower can be used again
   */
  public void DisplayCooldown(int cooldown) {
    System.out.println("\n========== SUPERPOWER STATUS ==========");
    // Checks the cooldown period
    if (cooldown > 0) {
      System.out.println("All superpowers are blocked for " + cooldown + " more turn(s)!");
    } 
    else {
      System.out.println("The next superpower is ready to be used!");
    }
  }
  
  /* Displays the player's turn 
   * @param playerName - Player's name (Player 1 and Player 2)
   * @param turnNum    - The current round/turn 
   */
  public void DisplayPlayerTurn(String playerName, int turnNum) {
    System.out.println("\n========== " + playerName + "'s Turn ==========");
    System.out.println("Turn: " + turnNum);
  }
  
  /* Displays the result after every move
   * @param result   - Display the result for each turn 
   * @param opponent - The opponent's details 
   */
  public void DisplayResult(String result, Player opponent) {
    System.out.println("\n========== RESULT ==========");
    System.out.println(result); 
    
    System.out.print("Ships left: ");  // Which ships are left (Not sunk)
    Ship[] ships = opponent.GetShips();
    // Loops through all the ships (Checks each ship's health)
    for (int i = 0; i < ships.length; i++) { 
      if (ships[i].GetHealth() > 0) {
        System.out.print(ships[i].GetShipName());
        // For nicer UI
        if (i < ships.length - 1) {  
          System.out.print(", "); 
        }
      }
    }
    System.out.println();
  }
  
  /* Displays the winner of the game
   * @param playerName -  The player's name who won the game
   */
  public void DisplayWinner(String playerName) {
    System.out.println ("==================================");
    System.out.printf ("%22s\n", "GAME OVER!");
    System.out.println ("==================================");
    System.out.println (playerName + " WINS!");
  }
  
  /* Displays the superpower's menu (Which superpowers are still available and can be used)
   * @param ships - The array of ships (5)
   */
  public void DisplaySuperpowerMenu(Ship[] ships) {
    System.out.println("\n========== SUPERPOWER MENU ==========");
    System.out.println("Available superpowers:");
    
    // Automatically set to false
    // Checks whether the superpower has been used (Through looping)
    boolean hasAvailable = false;
    for (int i = 0; i < ships.length; i++) {
      // If yes, prints the superpower's individual cooldown (Details)
      if (ships[i].CanUsePower()) {
        System.out.println((i + 1) + ". " + ships[i].GetShipName() + " " + ships[i].PowerName() + "(Cooldown after use: " + ships[i].GetCooldownPeriod() + " turns (Including this turn))");
        hasAvailable = true;
      }
    }
    // If none, prints message
    if (!hasAvailable) {
      System.out.println("No ships have available superpowers!");
    }
  }
  
  /* Displays the process of ship placing 
   * @param shipName - The name of the ship
   * @param size     - The size of the ship 
   */
  public void DisplayShipPlacement(String shipName, int size) {
    System.out.println("\nPlacing " + shipName + " (Size: " + size + ")"); 
    System.out.println("-----------------------------");
  }
  
  /* Displays a blank screen to avoid cheating/looking at opponent's grid */
  public void ClearScreen() {
    for (int i = 0; i < 50; i++) {
      System.out.println();
    }
  }
}