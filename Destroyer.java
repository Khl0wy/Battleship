// DEVELOPED BY: CHLOE
public class Destroyer extends Ship {
  
  //=============== Constructor =================
  public Destroyer () {
    super ("Destroyer", 2, 5);
  }
  
  //============= Private Methods =============== 
  /** 
   * Helper Method: Attacks all the cells in a specific row
   * @param opponentOwnGrid - The opponent's actual grid where the ship objects are stored
   * @param myEnemyGrid     - The player's tracking grid to record hits and misses
   * @param opponent        - opponent player object, for decreasing ships if hit and sunk
   * @param row             - The intended row to destroy (Attack)
   */
  private void attackRow(Grid opponentOwnGrid, Grid myEnemyGrid, Player opponent, int row) {
    // Runs the entire row and attack
    for (int col = 0; col < 10; col++) {
      String result = opponentOwnGrid.ReceiveAttack(row, col);
      // Updates enemyGrid (Based on the output)
      if (result.equals("HIT!")) { // HIT!
        myEnemyGrid.MarkHit(row, col);
      }
      else if (result.length() >= 3 && result.substring(0, 3).equals("HIT")) { // HIT AND SUNK, decreases 1 ship of opponent
        myEnemyGrid.MarkHit(row, col);
        opponent.DecrementShips();
      }
      else if (result.equals("MISS!")) { // MISS
        myEnemyGrid.MarkMiss(row, col);
      }
      // Prints the result 
      System.out.println("Shot at ( " + (row + 1) + ", " + (col + 1) + " ): " + result);
    }
  }
  
  /** 
   * Helper Method: Attacks all the cells in a specific column
   * @param opponentOwnGrid - The opponent's actual grid where the ship objects are stored
   * @param myEnemyGrid     - The player's tracking grid to record hits and misses
   * @param opponent        - opponent player object, for decreasing ships if hit and sunk
   * @param col             - The intended column to destroy (Attack)
   */
  private void attackCol(Grid opponentOwnGrid, Grid myEnemyGrid, Player opponent, int col) {
    // Runs the entire row and attack
    for (int row = 0; row < 10; row++) {
      String result = opponentOwnGrid.ReceiveAttack(row, col);
      // Updates enemyGrid (Based on the output)
      if (result.equals("HIT!")) { // HIT!
        myEnemyGrid.MarkHit(row, col);
      }
      else if (result.length() >= 3 && result.substring(0, 3).equals("HIT")) { // HIT AND SUNK, decreases 1 ship of opponent
        myEnemyGrid.MarkHit(row, col);
        opponent.DecrementShips();
      }
      else if (result.equals("MISS!")) { // MISS
        myEnemyGrid.MarkMiss(row, col);
      }
      // Prints the result 
      System.out.println("Shot at ( " + (row + 1) + ", " + (col + 1) + " ): " + result);
    }
  }
  
  //============= Public Methods =============== 
  /* Activates Destroyer's superpower: Attacks an entire row or column
   * @param opponentOwnGrid - The opponent's actual grid where the ship objects are stored
   * @param myEnemyGrid     - The player's tracking grid to record hits and misses
   * @param opponent        - opponent player object, for decreasing ships if hit and sunk
   * @param row             - The row to attack (Ignore if column is chosen, set to -1)
   * @param col             - The column to attack (Ignore if row is chosen, set to -1)
   * @param row2            - Not used (Automatically set to -1) (For overring purposes only - Cruiser)
   * @param col2            - Not used (Automatically set to -1) (For overring purposes only - Cruiser)
   */
  public void ActivatePower (Grid opponentOwnGrid, Grid myEnemyGrid, Player opponent, int row, int col, int row2, int col2) {
    System.out.println ("Destroyer's superpower activated: Destroys the selected row or column!");
    
    // Checks the input and determine whether to destroy a row or a column
    if (row >= 0) {
      attackRow(opponentOwnGrid, myEnemyGrid, opponent, row);
    } 
    else if (col >= 0) {
      attackCol(opponentOwnGrid, myEnemyGrid, opponent, col);
    } 
    
    // Superpower becomes unavailable 
    MarkPowerUsed();
    System.out.println("Destroyer's superpower has been used!");
  }
  
  /* Contains and returns Destroyer ship power name for displayer
   * @return - returns power name of ship */
  public String PowerName(){
    return " (Destroy one whole row or column) ";
  }
}