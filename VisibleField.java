

/**
  VisibleField class
  This is the data that's being displayed at any one point in the game, Client can call getStatus(row, col) for any square.
  It actually has data about the whole current state of the game, including  
  the underlying mine field (getMineField()).  Other accessors related to game status: numMinesLeft(), isGameOver().
  It also has mutators related to moves the player could do (resetGameDisplay(), cycleGuess(), uncover()),
  and changes the game state accordingly.
  
 */
public class VisibleField {
   // ----------------------------------------------------------   
   // The following public constants (plus numbers mentioned in comments below) are the possible states of one
   // location (a "square") in the visible field (all are values that can be returned by public method 
   // getStatus(row, col)).
   
   // Covered states (all negative values):
   public static final int COVERED = -1;   // initial value of all squares
   public static final int MINE_GUESS = -2;
   public static final int QUESTION = -3;

   // Uncovered states (all non-negative values):
   public static final int ZERO = 0;
  
   // values in the range [0,8] corresponds to number of mines adjacent to this square
   
   public static final int MINE = 9;      // this loc is a mine that hasn't been guessed already (end of losing game)
   public static final int INCORRECT_GUESS = 10;  // is displayed a specific way at the end of losing game
   public static final int EXPLODED_MINE = 11;   // the one you uncovered by mistake (that caused you to lose)
   // ----------------------------------------------------------   
  
   
   private MineField mineField;
   private int[][] visibleField;
   private int numMinesGuess;

   /**
      Create a visible field that has the given underlying mineField.
      The initial state will have all the mines covered up, no mines guessed, and the game
      not over.
      @param mineField  the minefield to use for for this VisibleField
    */
   public VisibleField(MineField mineField) {
      this.mineField = mineField;
      visibleField = new int[getMineField().numRows()][getMineField().numCols()];
      for(int i = 0; i < visibleField.length; i++)
      {
         for(int j = 0; j < visibleField[0].length; j++)
         {
            visibleField[i][j] = COVERED; 
         }
      }
   }
   
   
   /**
      Reset the object to its initial state, using the same underlying MineField. 
   */     
   public void resetGameDisplay() {
      for(int i = 0; i < visibleField.length; i++)
      {
         for(int j = 0; j < visibleField[0].length; j++)
         {
            visibleField[i][j] = COVERED; 
         }
      }
      numMinesGuess = 0;
   }
   
  
   
   /**
      Returns a reference to the mineField that this VisibleField "covers"
      @return the minefield
    */
   public MineField getMineField() {
      return mineField;       // DUMMY CODE so skeleton compiles
   }
   
   
   /**
      get the visible status of the square indicated.
      @param row  row of the square
      @param col  col of the square
      @return the status of the square at location (row, col).  
      PRE: getMineField().inRange(row, col)
    */
   public int getStatus(int row, int col) {
      assert(getMineField().inRange(row, col));
      return visibleField[row][col];      
   }

   
   /**
      Return the the number of mines left to guess.  This has nothing to do with whether the mines guessed are correct
      or not.  Just gives the user an indication of how many more mines the user might want to guess.  So the value can
      be negative, if they have guessed more than the number of mines in the minefield.     
      @return the number of mines left to guess.
    */
   public int numMinesLeft() {
      return getMineField().numMines() - numMinesGuess;       

   }
 
   
   /**
      Cycles through covered states for a square, updating number of guesses as necessary.  Call on a COVERED square
      changes its status to MINE_GUESS; call on a MINE_GUESS square changes it to QUESTION;  call on a QUESTION square
      changes it to COVERED again; call on an uncovered square has no effect.  
      @param row  row of the square
      @param col  col of the square
      PRE: getMineField().inRange(row, col)
    */
   public void cycleGuess(int row, int col) {
      if(getMineField().inRange(row, col))
      {
         if (getStatus(row, col) == COVERED)
         {
            visibleField[row][col] = MINE_GUESS;
            numMinesGuess++;
         }
         else if (getStatus(row, col) == MINE_GUESS)
         {
            visibleField[row][col] = QUESTION;
            numMinesGuess--;
         }
         else if(getStatus(row, col) == QUESTION)
         {
            visibleField[row][col] = COVERED;
         }
      }
      
   }

   
   /**
      Uncovers this square and returns false iff you uncover a mine here.
      If the square wasn't a mine or adjacent to a mine it also uncovers all the squares in 
      the neighboring area that are also not next to any mines, possibly uncovering a large region.
      Any mine-adjacent squares you reach will also be uncovered, and form 
      (possibly along with parts of the edge of the whole field) the boundary of this region.
      Does not uncover, or keep searching through, squares that have the status MINE_GUESS. 
      @param row  of the square
      @param col  of the square
      @return false   iff you uncover a mine at (row, col)
      PRE: getMineField().inRange(row, col)
    */
   public boolean uncover(int row, int col) {
      if(!getMineField().inRange(row, col))
      {
         return true;
      }
      if( !(getStatus(row, col) == COVERED || getStatus(row, col) == QUESTION ))
      {
         return true;
      }
      else if(getMineField().hasMine(row, col))
      {
         visibleField[row][col] = EXPLODED_MINE;
         return false;
         
      }
      else if( getMineField().numAdjacentMines(row, col) > ZERO )
      {
         visibleField[row][col] = getMineField().numAdjacentMines(row, col); 
         return true;
      }
      else 
         {
            visibleField[row][col] = ZERO;
            if(getMineField().inRange(row - 1, col - 1))
            {
               uncover(row - 1, col - 1);
            }
            if(getMineField().inRange(row - 1, col))
            {
               uncover(row - 1, col);
            }
            if(getMineField().inRange(row - 1, col + 1))
            {
               uncover(row - 1, col + 1);
            }
            if(getMineField().inRange(row, col - 1))
            {
               uncover(row, col - 1);
            }
            
            if(getMineField().inRange(row, col + 1))
            {
               uncover(row, col + 1);
            }
            if(getMineField().inRange(row + 1, col - 1))
            {
               uncover(row + 1, col - 1);
            }
            if(getMineField().inRange(row + 1, col))
            {
               uncover(row + 1, col);
            }
            if(getMineField().inRange(row + 1, col + 1))
            {
               uncover(row + 1, col + 1);
            }
            return true;
         }
               
   }
 
   
   /**
      Returns whether the game is over.
      @return whether game over
    */
   public boolean isGameOver() {
      int countUncover = 0;
     
      for (int i = 0; i < getMineField().numRows(); i++ )
      {
         for(int j = 0; j < getMineField().numCols(); j++)
         {
            
            if( getStatus(i, j) >= ZERO && getStatus(i, j) < MINE )
            {
               countUncover++;
            }
            if ( getStatus(i, j) == EXPLODED_MINE)
            {
              
               for(int m = 0; m < getMineField().numRows(); m++ )
               {
                  for(int n = 0; n < getMineField().numCols(); n++ )
                  {
                     if(getStatus(m, n) == MINE_GUESS && !getMineField().hasMine(m, n))
                     {
                        visibleField[m][n] = INCORRECT_GUESS;   
                     }
                     if(getStatus(m, n) != MINE_GUESS && getMineField().hasMine(m, n))
                     {
                        visibleField[m][n] = MINE; 
                     }
                     visibleField[i][j]= EXPLODED_MINE; 
                  }
               }
               
               return true;
            }
         }
      }
      if(countUncover == getMineField().numRows()* getMineField().numCols() - getMineField().numMines() )
      {
         for(int m = 0; m < getMineField().numRows(); m++ )
         {
            for(int n = 0; n < getMineField().numCols(); n++ )
            {
               if(getStatus(m, n) == COVERED )
               {
                  visibleField[m][n] = MINE_GUESS;
               }
               
            }
         }
         return true;
      }
      return false;       
   }
 
   
   /**
      Return whether this square has been uncovered. 
      @param row of the square
      @param col of the square
      @return whether the square is uncovered
      PRE: getMineField().inRange(row, col)
    */
   public boolean isUncovered(int row, int col) {
      if(getMineField().inRange(row, col))
      {
         if( visibleField[row][col] >= 0)
         {
            return true;
         }
      }
      return false;       
   }
   
 
   
}
