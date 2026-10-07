// DEVELOPED BY: CHLOE
public class Submarine extends Ship {
  
  //=============== Constructor =================
  public Submarine () {
    super ("Submarine", 3, 5);
  }
  
  //============= Public Methods =============== 
  /* Activates Submarine's superpower: Guaranteed hit
   * @param opponentOwnGrid - The opponent's actual grid where the ship objects are stored
   * @param myEnemyGrid     - The player's tracking grid to record hits and misses 
   * @param opponent        - opponent player object, for decreasing ships if hit and sunk
   * @param row             - Not used (Automatically set to -1) (For overring purposes only - Destroyer & Cruiser)
   * @param col             - Not used (Automatically set to -1) (For overring purposes only - Destroyer & Cruiser)
   * @param row2            - Not used (Automatically set to -1) (For overring purposes only - Cruiser)
   * @param col2            - Not used (Automatically set to -1) (For overring purposes only - Cruiser)
   */
  public void ActivatePower (Grid opponentOwnGrid, Grid myEnemyGrid, Player opponent, int row, int col, int row2, int col2) {
    System.out.println ("Submarine's superpower activated: Guarantee ship hit!");
    
    // Random unhit ship coordinate
    int[] shipCoor = opponentOwnGrid.RandomShipCoordinate();
    
    // Prevent crash but should never run this
    if (shipCoor == null) {
      System.out.println("No available ship cells to hit. (Game Ended)");
    }
    else {
      // Attacks (Health actually decreases)
      String result = opponentOwnGrid.ReceiveAttack (shipCoor[0], shipCoor[1]);
      
      // Updates enemyGrid (Based on the output)
      if (result.equals("HIT!")) { // HIT!
        myEnemyGrid.MarkHit(shipCoor[0], shipCoor[1]);
      }
      else if (result.length() >= 3 && result.substring(0, 3).equals("HIT")) { // HIT AND SUNK, decreases 1 ship of opponent
        myEnemyGrid.MarkHit(shipCoor[0], shipCoor[1]);
        opponent.DecrementShips();
      }
      else if (result.equals("MISS!")) { // MISS
        myEnemyGrid.MarkMiss(shipCoor[0], shipCoor[1]);
      }
      // Prints the result 
      System.out.println ("Enemy ship hit at ( " + (shipCoor[0] + 1) + ", " + (shipCoor[1] + 1) + " ): " + result);
      
      // Superpower becomes unavailable 
      MarkPowerUsed();
      System.out.println ("Submarine's superpower has been used!");
    }
  }
  
  /* Contains and returns Submarine ship power name for displayer
   * @return - returns power name of ship */
  public String PowerName(){
    return " (Random 1 shot at an enemy's ship) ";
  }
}
