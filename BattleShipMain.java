// Written by: Mohaimen Ibn Reza Shuvo
import java.util.Scanner;

public class BattleShipMain {
  public static void main(String[] args) {
    Scanner input = new Scanner(System.in);
    BattleShipSys battleSystem = new BattleShipSys();
    Displayer displayer = new Displayer();
    
    // Display welcome screen
    displayer.DisplayWelcome();
    
    // Create players
    System.out.print("\nEnter Player 1 name: ");
    String name1 = input.nextLine();
    
    System.out.print("\nEnter Player 2 name: ");
    String name2 = input.nextLine();
    
    // Instantiate and Register players with battle system
    battleSystem.SetPlayerRef(battleSystem.InstantiatePlayer(name1), battleSystem.InstantiatePlayer(name2));
    
    // ========== Player 1 ship placement phase ==========
    System.out.println("\n" + name1 + ", Place your ships!");
    for (int i = 0; i < 5; i++) {   // loops through all the ships for placement
      Ship ship = battleSystem.GetCurrentPlayer().GetShip(i);
      boolean placed = false;       // Variable to check if placement was successful
      
      while (!placed) {             // Loop for continuous prompting until ship is placed correctly by player
        displayer.DisplayGrid(battleSystem.GetCurrentPlayer().GetOwnGrid(), false);
        displayer.DisplayShipPlacement(ship.GetShipName(), ship.GetSize());               // Calls for display placement menu
        System.out.println("Note: When placing ships, select cell that contains the head of the ship. ( Like 10 x 10 will go out of bounds ).");
        
        System.out.printf("%-31s", "Enter starting row (1-10): ");
        int row = input.nextInt() - 1;
        System.out.printf("%-31s", "Enter starting column (1-10): ");
        int col = input.nextInt() - 1;
        System.out.printf("%-31s", "Horizontal? (1 = Yes, 0 = No): ");
        int horizontal = input.nextInt();
        System.out.println("");
        
        int result = battleSystem.ValidateAndPlaceShip(battleSystem.GetCurrentPlayer(), i, row, col, horizontal);  // battleSystem verifies placement, and places ship
        
        if (result == -1) {   // All error conditions, except the else statement
          System.out.println("Invalid placement of row! Please input again!\n");
        } 
        else if (result == -2) {
          System.out.println("Invalid placement of column! Please input again!\n");
        } 
        else if (result == -3) {
          System.out.println("Invalid horizontal input! Please input again!\n");
        } 
        else if (result == -4) {
          System.out.println("Cell already occupied, or cell out of bounds. Please select a different cell.\n");
        } 
        else {
          placed = true;
        }
      }
    }
    System.out.println("\nAll ships placed!");
    displayer.DisplayGrid(battleSystem.GetCurrentPlayer().GetOwnGrid(), false);          // Shows own grid with ships
    displayer.ClearScreen();                                                             // Clear screen for privacy
    
    // ========== Player 2 ship placement phase ==========
    System.out.println("\n" + name2 + ", place your ships!");
    for (int i = 0; i < 5; i++) {         // Loops through all ships for placement
      Ship ship = battleSystem.GetOpponent().GetShip(i);
      boolean placed = false;             // Variable to check if placement was successful
      
      while (!placed) {                   // loop for continuous prompting until ship is placed successfully
        displayer.DisplayGrid(battleSystem.GetOpponent().GetOwnGrid(), false);
        displayer.DisplayShipPlacement(ship.GetShipName(), ship.GetSize());     // Calls for display placement menu
        System.out.println("Note: When placing ships, select cell that contains the head of the ship. ( Like 10 x 10 will go out of bounds ).");
        
        System.out.printf("%-31s", "Enter starting row (1-10): ");
        int row = input.nextInt() - 1;
        System.out.printf("%-31s", "Enter starting column (1-10): ");
        int col = input.nextInt() - 1;
        System.out.printf("%-31s", "Horizontal? (1 = Yes, 0 = No): ");
        int horizontal = input.nextInt();
        System.out.println("");
        
        int result = battleSystem.ValidateAndPlaceShip(battleSystem.GetOpponent(), i, row, col, horizontal);  // battleSystem verifies placement, and places ship
        
        if (result == -1) {  // All error conditions except else statement
          System.out.println("Invalid placement of row! Please input again!\n");
        } 
        else if (result == -2) {
          System.out.println("Invalid placement of column! Please input again!\n");
        } 
        else if (result == -3) {
          System.out.println("Invalid horizontal input! Please input again!\n");
        } 
        else if (result == -4) {
          System.out.println("Cell already occupied, or cell out of bounds. Please select a different cell.\n");
        } 
        else {
          placed = true;
        }
      }
    }
    System.out.println("\nAll ships placed!");
    displayer.DisplayGrid(battleSystem.GetOpponent().GetOwnGrid(), false);   // Displayes opponents own grid
    displayer.ClearScreen();                                                 // Clear screen for privacy
    
    // ========== Main game loop ==========
    while (!battleSystem.IsGameOver()) {           // This loop runs the whole whole game until someone wins
      battleSystem.StartTurn();     // Reduces cooldown by 1 at the start of each turn
      Player currentPlayer = battleSystem.GetCurrentPlayer();
      Player opponent = battleSystem.GetOpponent();
      
      // Display turn information and grids
      displayer.DisplayPlayerTurn(currentPlayer.GetName(), battleSystem.GetTotalTurns() + 1);
      displayer.DisplayCooldown(battleSystem.GetCooldown(currentPlayer));
      System.out.println("\nYour Ships Board:");
      displayer.DisplayGrid(currentPlayer.GetOwnGrid(), false);
      System.out.println("\nYour Tracking Board (Attacks on enemy's grid):");
      displayer.DisplayGrid(currentPlayer.GetEnemyGrid(), false);
      
      // Check if superpowers are blocked, if yes, forces to use normal attack
      if (battleSystem.GetCooldown(currentPlayer) > 0) {
        System.out.println("\nSuperpowers blocked! You must perform a normal attack!");
        boolean validAttack = false;   // Variable to check if attack is valid
        
        while (!validAttack) {         // Runs loop again and again until valid attack done
          System.out.printf("%-28s", "Enter target row (1-10): ");
          int row = input.nextInt() - 1;
          System.out.printf("%-28s", "Enter target column (1-10): ");
          int col = input.nextInt() - 1;
          
          String attackResult = battleSystem.PerformNormalAttack(row, col);   // Method to execute normal attack
          
          if (attackResult.equals("ALREADY ATTACKED!") || attackResult.equals("INVALID INPUT!")) {
            System.out.println("Invalid attack or already attacked! Please try again!\n");
          } else {
            validAttack = true;
            displayer.DisplayResult(attackResult, opponent);     // Displays result
          }
          if (battleSystem.IsGameOver()) break;                 // Checks if game is over or not
        }
      } 
      else {   // If cooldown is 0, goes for a usual player attck selection
        // Superpowers available
        boolean turnComplete = false;   // Variable to check if turn is complete
        
        while (!turnComplete) {         // Loops until turn is complete
          displayer.DisplayMenu();
          System.out.print("Choose action: ");
          int action = input.nextInt();
          System.out.println("");
          
          if (action == 1) {   // Normal attack choosen
            // Normal attack
            boolean validAttack = false;   // Variable to check for valid attack
            while (!validAttack) {         // loops until valid attack is performed
              System.out.printf("%-28s", "Enter target row (1-10): ");
              int row = input.nextInt() - 1;
              System.out.printf("%-28s", "Enter target column (1-10): ");
              int col = input.nextInt() - 1;
              
              String attackResult = battleSystem.PerformNormalAttack(row, col);  // battleSystem performs normal attack
              
              if (attackResult.equals("ALREADY ATTACKED!") || attackResult.equals("INVALID INPUT!")) {  // Invalid attack condition
                System.out.println("Invalid attack or already attacked! Please try again!\n");
              } 
              else {          // Valid attack condition
                validAttack = true;
                displayer.DisplayResult(attackResult, opponent);
              }
              if (battleSystem.IsGameOver()) break;
            }
            turnComplete = true;
          } 
          else if (action == 2) {  // Super attack choosen
            // Superpower
              displayer.DisplaySuperpowerMenu(currentPlayer.GetShips());  // Shows available super powers
              
              if (!battleSystem.HasAnySuperpowerAvailable()) {   // Checks if any power is available, returns true even if one power is available
                System.out.println("All super powers used!\n");
              } 
              else {   // If powers available, lets player choose
                System.out.print("Choose ship (1-5) : ");
                int choice = input.nextInt();
                System.out.println("");
                
                if (choice < 1 || choice > 5) {   // Invalid input
                  System.out.println("Invalid choice! Please try again!\n");
                } 
                else if (!battleSystem.IsSuperpowerAvailable(choice - 1)) {
                  System.out.println("This superpower is not available!");
                } 
                else {
                  if (choice==Ship.CARRIER) {
                      // Carrier - no coordinates needed, all coordinates set to -1 automatically
                      // Activate the power, result will be printed based on the condition of the attacked cell
                      currentPlayer.ActivateShipPower(choice - 1, currentPlayer, opponent, battleSystem, -1, -1, -1, -1);
                    }
                    else if (choice==Ship.BATTLESHIP) {
                      // Battleship - needs center coordinate, row2 and col2 set to -1 automatically
                      // Lets player select the center cell to drop the attack on
                      System.out.printf("%-31s", "Enter center row (1-10): ");
                      int row = input.nextInt() - 1;
                      System.out.printf("%-31s", "Enter center column (1-10): ");
                      int col = input.nextInt() - 1;
                      System.out.println("");
                      // Activate the power, result will be printed based on the condition of the attacked cell
                      currentPlayer.ActivateShipPower(choice - 1, currentPlayer, opponent, battleSystem, row, col, -1, -1);
                    }
                    else if (choice==Ship.CRUISER) {
                      // Cruiser - needs all coordinates, set by player
                      // Lets player select 1st cell and follow-up cell to attck before activating power
                      System.out.printf("%-35s", "Enter initial shot row (1-10): ");
                      int row1 = input.nextInt() - 1;
                      System.out.printf("%-35s", "Enter initial shot column (1-10): ");
                      int col1 = input.nextInt() - 1;
                      System.out.printf("%-35s", "Enter follow-up row (1-10): ");
                      int row2 = input.nextInt() - 1;
                      System.out.printf("%-35s", "Enter follow-up column (1-10): ");
                      int col2 = input.nextInt() - 1;
                      System.out.println("");
                      // Activate the power, result will be printed based on the condition of the attacked cell
                      currentPlayer.ActivateShipPower(choice - 1, currentPlayer, opponent, battleSystem, row1, col1, row2, col2);
                    }
                    else if (choice==Ship.SUBMARINE) {
                      // Submarine - no coordinates needed, all set to -1
                      // Activate the power, result will be printed based on the condition of the attacked cell
                      currentPlayer.ActivateShipPower(choice - 1, currentPlayer, opponent, battleSystem, -1, -1, -1, -1);
                    }
                    else if (choice==Ship.DESTROYER) {
                      // Destroyer - need either one row or one column depending on player's choice
                      // Asks user for choosing attack on row, or attack on column
                      System.out.printf("%-35s", "Attack (1) Row or (2) Column? ");
                      int choice2 = input.nextInt();
                      System.out.printf("%-35s", "Enter row/column number (1-10): ");
                      int index = input.nextInt() - 1;
                      if (choice2 == 1) {
                        // Activate the power (Result will be printed based on the condition of the attacked cell)
                        // Attack row, make all other coordinates -1
                        currentPlayer.ActivateShipPower(choice - 1, currentPlayer, opponent, battleSystem, index, -1, -1, -1);
                      } 
                      else {
                        // Activate the power (Result will be printed based on the condition of the attacked cell)
                        // Attack column, make all other coordinates -1
                        currentPlayer.ActivateShipPower(choice - 1, currentPlayer, opponent, battleSystem, -1, index, -1, -1);
                      }
                    }
                    displayer.DisplayResult("Superpower activated!", opponent); // Displays result of attack
                    turnComplete = true;
                }
              }
          } 
          else {   // Condition for invalid input in the attack selection menu
            System.out.println("Invalid input! Please try again!\n");
          }
          
          if (battleSystem.IsGameOver()) break;   // Checks if game is over i.e opponent remaining ships is 0 or not, then breaks out of the turn loop
        }
      }
      
      battleSystem.EndTurn();   // Increses turn count by 1
      
      if (battleSystem.IsGameOver()) {   // If games over, displayes winner name, and breaks out of game loop
        displayer.DisplayWinner(currentPlayer.GetName());
        break;
      }
      
      System.out.println("\nPress Enter to end turn...");  // Prompts to press enter at the end of each turn
      input.nextLine();
      input.nextLine();
      displayer.ClearScreen();                            // 50 new lines to shift from one player screen to another for privaacy
      battleSystem.SwitchPlayers();                       // Switchs player after each turn
    }
    
    input.close();
  }
}