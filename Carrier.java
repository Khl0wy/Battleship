// DEVELOPED BY: CHLOE
import java.util.Random;
public class Carrier extends Ship {
  
  //=============== Constructor =================
  public Carrier () {
    super ("Carrier", 5, 5);
  }
  
  //============= Private Methods =============== 
  /** 
   * Helper Method: Changes numeric numbers into ordinal number
   * Eg: 1 -> 1st, 2 -> 2nd, 3 -> 3rd... 
   * @param n - The number to convert
   * @return - Returns the string representing the ordinal number
   */
  private String numberOrder(int n) {
    switch(n) {
      case 1: 
        return "1st";
      case 2: 
        return "2nd";
      case 3: 
        return "3rd";
      default:
        return n + "th";
    }
  }
  
  //============= Public Methods =============== 
  /* Activates Carrier's superpower: 4 random shots
   * @param opponentOwnGrid - The opponent's actual grid where the ship objects are stored
   * @param myEnemyGrid     - The player's tracking grid to record hits and misses 
   * @param opponent        - opponent player object, for decreasing ships if hit and sunk
   * @param row             - Not used (Automatically set to -1) (For overring purposes only - Destroyer & Cruiser)
   * @param col             - Not used (Automatically set to -1) (For overring purposes only - Destroyer & Cruiser)
   * @param row2            - Not used (Automatically set to -1) (For overring purposes only - Cruiser)
   * @param col2            - Not used (Automatically set to -1) (For overring purposes only - Cruiser)
   */
  public void ActivatePower (Grid opponentOwnGrid, Grid myEnemyGrid, Player opponent, int row, int col, int row2, int col2) {
    // Variables to control the shots
    boolean[][] hit = new boolean[10][10];
    int shots = 0;
    Random r = new Random();
    
    System.out.println ("Carrier's superpower activated: 4 random shots!");
    
    // 4 random and unique shots
    while (shots < 4) {
      int targetRow = r.nextInt(10); // Selects random row
      int targetCol = r.nextInt(10); // Selects random column
      
      // Checks if the cell is unhit
      // If yes, then attacks
      if (!hit[targetRow][targetCol]) {
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
        // Prints the result 
        System.out.println (numberOrder(shots + 1) + " shot at ( " + (targetRow + 1) + " , " + (targetCol + 1) + " ): " +  result);
        // Marks the coordinate and increment
        hit[targetRow][targetCol] = true;
        shots++;
      }
    }
    
    // Superpower becomes unavailable 
    MarkPowerUsed();
    System.out.println ("Carrier's superpower has been used!");
  }
  
  /* Contains and returns Carrier ship power name for displayer
   * @return - returns power name of ship */
  public String PowerName(){
    return " (4 Random shots on the board) ";
  }
}