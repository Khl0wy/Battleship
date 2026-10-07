// DEVELOPED BY: CHLOE
public class Battleship extends Ship {
  
  //=============== Constructor =================
  public Battleship () {
    super ("Battleship", 4, 5);
  }
  
  //============= Public Methods =============== 
  /* Activates BattleShip's superpower: Destroys 3x3 box/area
   * Chooses the middle of the 3x3 box
   * @param opponentOwnGrid - The opponent's actual grid where the ship objects are stored
   * @param myEnemyGrid     - The player's tracking grid to record hits and misses 
   * @param opponent        - opponent player object, for decreasing ships if hit and sunk
   * @param row             - The row to attack
   * @param col             - The column to attack 
   * @param row2            - Not used (Automatically set to -1) (For overring purposes only - Cruiser)
   * @param col2            - Not used (Automatically set to -1) (For overring purposes only - Cruiser)
   */
  public void ActivatePower (Grid opponentOwnGrid, Grid myEnemyGrid, Player opponent, int row, int col, int row2, int col2) {    
    System.out.println ("Battleship's superpower activated: Destroys 3x3 area!");
    
    // Loops through the 3x3 area/box 
    for (int i = -1; i <= 1; i++) {
      for (int j = -1; j <= 1; j++) {
        // Move from the centre to left, right, up or down
        int targetRow = row + i; 
        int targetCol = col + j;
        
        // Checks whether all the coordinates are in the grid
        if (targetRow >= 0 && targetRow < 10 && targetCol >= 0 && targetCol < 10) {
          String result = opponentOwnGrid.ReceiveAttack(targetRow, targetCol);
          
          // Updates enemyGrid (Based on the output)
          if (result.equals("HIT!")) { // HIT!
            myEnemyGrid.MarkHit(targetRow, targetCol);
          }
          else if (result.length() >= 3 && result.substring(0, 3).equals("HIT")) { // HIT AND SUNK, decreases 1 ship of opponent
            myEnemyGrid.MarkHit(targetRow, targetCol);
            opponent.DecrementShips();
          }
          else if (result.equals("MISS!")) { // MISS
            myEnemyGrid.MarkMiss(targetRow, targetCol);
          }
          // Prints the result (Hit/Miss coordinates)
          System.out.println("Shot at ( " + (targetRow + 1) + ", " + (targetCol + 1) + " ): " + result);
        }
        else {
          // Prints the result (Out of bound)  
          System.out.println("Ignored out-of-bounds shot at (" + (targetRow + 1) + ", " + (targetCol + 1) + ")");
        }
      }
    }
    
    // Superpower becomes unavailable 
    MarkPowerUsed();
    System.out.println ("Battleship's superpower has been used!");
  }
  
  /* Contains and returns Battleship ship power name for displayer
   * @return - returns power name of ship */
  public String PowerName(){
    return " (3x3 Bomb drop on the board) ";
  }
}