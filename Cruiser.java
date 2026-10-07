// Witten by : Mohaimen Ibn Reza Shuvo

public class Cruiser extends Ship {
    public Cruiser() {
        super("Cruiser", 3, 2);
    }
    
    //===============Private Method==============
   /* Helper method for follow-up attack
    * @param opponentOwnGrid - The opponent's actual grid where the ship objects are stored
    * @param myEnemyGrid     - The player's tracking grid to record hits and misses
    * @param opponent        - opponent player object, for decreasing ships if hit and sunk
    * @param row2            - Follow-up attack row
    * @param col2            - Follow-up attack column
    */ 
    private void hitCondition(Grid opponentOwnGrid,Grid enemyGrid, Player opponent, int row2, int col2){
      System.out.println("HIT! Follow-up shot activated!");
      String attack2 = opponentOwnGrid.ReceiveAttack(row2,col2);
      if(attack2.equals("HIT!")){   // 2nd shot Hit condition
        opponentOwnGrid.MarkHit(row2,col2);
        enemyGrid.MarkHit(row2,col2);
        System.out.println("Follow-up shot at (" + (row2 + 1) + "," + (col2 + 1) + "): " + attack2);
      }
      else if(attack2.equals("MISS!")){   // 2nd shot Miss condition 
        opponentOwnGrid.MarkMiss(row2, col2);
        enemyGrid.MarkMiss(row2, col2);
        System.out.println("Follow-up shot at (" + (row2 + 1) + "," + (col2 + 1) + "): " + attack2);
      }
      else if(attack2.equals("ALREADY ATTACKED!") || attack2.equals("INVALID INPUT!")){  // If attack was done on a preselected spot, or invalid spot, ends attack and declares waste
          System.out.println("Follow-up shot wasted, as input attack was already done or invalid.");
        }
      else{  // 2nd shot, Hit and sink condition
        opponentOwnGrid.MarkHit(row2,col2);
        enemyGrid.MarkHit(row2,col2);
        opponent.DecrementShips();
        System.out.println("Follow-up shot at (" + (row2 + 1) + "," + (col2 + 1) + "): " + attack2);
      }
    }
    
    //=============Public Methods==============
    /* Activates Cruiser's superpower: Follow-up attack if first attack is a hit
    * @param opponentOwnGrid - The opponent's actual grid where the ship objects are stored
    * @param myEnemyGrid     - The player's tracking grid to record hits and misses
    * @param opponent        - opponent player object, for decreasing ships if hit and sunk
    * @param row             - The first row to attack
    * @param col             - The first column to attack
    * @param row2            - The second row to attack (Only if first attack was a hit, otherwise not used)
    * @param col2            - The second column to attack (Only if first attack was a hit, otherwise not used)
    */
    public void ActivatePower(Grid opponentOwnGrid, Grid enemyGrid, Player opponent, int row1, int col1, int row2, int col2) {
        System.out.println("Cruiser Superpower: Shot + Follow-up!");
        String attack1 = opponentOwnGrid.ReceiveAttack(row1, col1);
        System.out.println("Initial shot at (" + (row1 + 1) + "," + (col1 + 1) + "): " + attack1);

        if (attack1.equals("HIT!")) {         // Checks if first attack was a hit, then marks both grids with hit, and calls HitCondition Helper method for follow-up attack
            opponentOwnGrid.MarkHit(row1,col1);
            enemyGrid.MarkHit(row1,col1);
            hitCondition(opponentOwnGrid, enemyGrid,opponent,row2,col2);
        }
        else if(attack1.equals("MISS!")) {   // If miss, marks both grids as miss and ends power
            opponentOwnGrid.MarkMiss(row1, col1);
            enemyGrid.MarkMiss(row1, col1);
            System.out.println("MISS! No follow-up shot.");
        }
        else if(attack1.equals("ALREADY ATTACKED!") || attack1.equals("INVALID INPUT!")){  // If attack was done on a preselected spot, or invalid spot, ends attack and declares waste
          System.out.println("Super power wasted, as input attack was already done or invalid.");
        }
        else{                               // Hit and sunk condition, marks both grids hit, decreases one ship of opponent, and calls helper method for follow-up
            opponentOwnGrid.MarkHit(row1,col1);
            enemyGrid.MarkHit(row1,col1);
            hitCondition(opponentOwnGrid, enemyGrid, opponent,row2,col2);
            opponent.DecrementShips();
        }

        MarkPowerUsed();                    // Marks power as used after attack is done.
    }
    
    /* Contains and returns Cruiser ship power name for displayer
     * @return - returns power name of ship */
    public String PowerName(){
    return " (Make one follow-up shot, if first shot is HIT) ";
  }
}