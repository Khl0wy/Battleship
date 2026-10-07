// Written by : Mohaimen Ibn Reza Shuvo

public class BattleShipSys {
    
    private Player player1Ref;       // Stores reference of first player
    private Player player2Ref;       // Stores reference of second player
    private Player currentPlayer;    // Tracks which player's turn it is
    private Player opponent;         // Tracks the opponent of current player
    private int player1Cooldown;     // Cooldown count of player 1
    private int player2Cooldown;     // Cooldown count of player 2
    private int totalTurns;          // Keeps count of total number of turns
    private boolean gameOver;        // Tracks if the game has ended
    
    //===============Constructor================
    /* Constructor that instantiates BattleShipSys */
    public BattleShipSys() {
        this.player1Cooldown = 0;
        this.player2Cooldown = 0;
        this.totalTurns = 0;
        this.gameOver = false;
        this.currentPlayer = null;
        this.opponent = null;
    }
    // =============Private Methods=============    
    /* Helper method - Reduces cooldown after each turn by 1
     * @param player - player whose turn it is  */
    private void reduceCooldown(Player player){
        if(player == player1Ref){   // Compares to player 1 reference and reduces player1Cooldown by 1
            if(player1Cooldown > 0){
                player1Cooldown--;
            }
        }
        if(player == player2Ref){  // Compares to player 2 reference and reduces player2Cooldown by 1
            if(player2Cooldown > 0){
                player2Cooldown--;
            }
        }
    }

    // =============Public Methods==============   
    /* Gets cooldown of player
     * @param player - player whose cooldown needs to be returned
     * @return - returns cooldown of selected player  */
    public int GetCooldown(Player player){
        if(player == player1Ref){  // Matches reference to determine player
            return this.player1Cooldown;
        }
        else{
            return this.player2Cooldown;
        }
    }
    
    /* Gets total turns of player
     * @return - totalTurns  */
    public int GetTotalTurns(){
        return this.totalTurns;
    }
    
    /* Gets current player
     * @return - current player object */
    public Player GetCurrentPlayer() {
        return this.currentPlayer;
    }
    
    /* Gets opponent player
     * @return - opponent player object */
    public Player GetOpponent() {
        return this.opponent;
    }
    
    /* Gets game over status
     * @return - true if game is over, false otherwise */
    public boolean IsGameOver() {
        return this.gameOver;
    }
    
    /* Instantiates player objects
     * @param name - name of instantiating player
     * @return - player object  */
    public Player InstantiatePlayer(String name){
        Player player = new Player(name);
        return player;
    }
    
    /* Sets player references for comparing later and initializes current player
     * @param p1 - player 1 reference
     * @param p2 - player 2 reference  */
    public void SetPlayerRef(Player p1, Player p2){
        this.player1Ref = p1;
        this.player2Ref = p2;
        this.currentPlayer = p1;  // Player 1 starts first
        this.opponent = p2;       // Player 2 initially set as opponent
    }
    
    /* Sets player cooldown
     * @param currentPlayer - current player object
     * @param turns - number of turns to block super power */
    public void SetCooldown(Player currentPlayer, int turns){
        if(currentPlayer == player1Ref){  // Compares to player 1 reference and sets player1Cooldown
            player1Cooldown = turns;
        }
        else if(currentPlayer == player2Ref){  // Compares to player 2 reference and sets player2Cooldown
            player2Cooldown = turns;
        }
    }
    
    /* Switches players after a turn is complete */
    public void SwitchPlayers() {
        Player temp = currentPlayer;
        currentPlayer = opponent;
        opponent = temp;
    }
    
    /* Performs a normal attack for the current player
     * @param row - target row coordinate (0-9)
     * @param col - target column coordinate (0-9)
     * @return - attack result string */
    public String PerformNormalAttack(int row, int col) {
        String attackResult = currentPlayer.Attack(row, col, opponent);
        
        // Check if opponent has lost after this attack
        if (opponent.HasLost()) {
            gameOver = true;
        }
        
        return attackResult;
    }
    
    /* Checks if a ship's superpower is available
     * @param shipIndex - index of ship to check (0-4)
     * @return - true if power is available, false otherwise */
    public boolean IsSuperpowerAvailable(int shipIndex) {
        return currentPlayer.GetShip(shipIndex).CanUsePower();
    }
    
    /* Checks if any superpower is available for current player
     * @return - true if at least one power is available, false otherwise */
    public boolean HasAnySuperpowerAvailable() {
        Ship[] ships = currentPlayer.GetShips();
        for (int i = 0; i < ships.length; i++) {
            if (ships[i].CanUsePower()) {
                return true;
            }
        }
        return false;
    }
    
    /* Starts a new turn by reducing cooldown
     * Should be called at the beginning of each turn */
    public void StartTurn() {
        reduceCooldown(currentPlayer);
    }
    
    /* Ends the current turn by incrementing turn counter
     * Should be called at the end of each turn */
    public void EndTurn() {
        this.totalTurns++;
    }
    
    /* Handles ship placement for a player with input validation
     * @param player - the player placing ships
     * @param shipIndex - index of ship to place (0-4)
     * @param row - starting row coordinate (0-9)
     * @param col - starting column coordinate (0-9)
     * @param horizontal - 1 for horizontal, 0 for vertical
     * @return - status code: 0 = success, -1 = invalid row, -2 = invalid col, -3 = invalid horizontal, -4 = placement failed */
    public int ValidateAndPlaceShip(Player player, int shipIndex, int row, int col, int horizontal) {
        // Validate row
        if (row < 0 || row >= 10) {
            return -1;  // Invalid row
        }
        
        // Validate column
        if (col < 0 || col >= 10) {
            return -2;  // Invalid column
        }
        
        // Validate horizontal input
        if (horizontal != 0 && horizontal != 1) {
            return -3;  // Invalid horizontal
        }
        
        // Attempt placement
        boolean isHorizontal = (horizontal == 1);
        boolean placed = player.PlaceShip(shipIndex, row, col, isHorizontal);
        
        if (!placed) {
            return -4;  // Placement failed (occupied or out of bounds)
        }
        
        return 0;  // Success
    }
}