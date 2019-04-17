   import java.util.Random;
   

   
   /** 
      MineField
         class with locations of mines for a game.
    */
   public class MineField {
      private boolean[][] mineData;
      private int numMine;
   
      
      
      /**
         Create a minefield with same dimensions as the given array, and populate it with the mines in the array
         such that if mineData[row][col] is true, then hasMine(row,col) will be true and vice versa. 
       * @param mineData  the data for the mines; must have at least one row and one col.
       */
      public MineField(boolean[][] mineData) {
   
         this.mineData = new boolean[mineData.length][mineData[0].length];
         for(int i = 0; i < mineData.length; i++)
         {
            for(int j = 0 ; j < mineData[0].length;j++)
            {
               this.mineData[i][j] = mineData[i][j];
               if (mineData[i][j])
               {
                  numMine++;
               }
            }
         }
         
         
      }
      
      
      /**
         Create an empty minefield that may later have numMines mines (once 
         populateMineField is called on this object).  Until populateMineField is called on such a MineField, 
         numMines() will not correspond to the number of mines currently in the MineField.
         @param numRows  number of rows this minefield will have, must be positive
         @param numCols  number of columns this minefield will have, must be positive
         @param numMines   number of mines this minefield will have,  once we populate it.
         PRE: numRows > 0 and numCols > 0 and 0 <= numMines < (1/3 of total number of field locations). 
       */
      public MineField(int numRows, int numCols, int numMines) {
         assert(numRows > 0 && numCols > 0 && 0 <= numMines && (numRows * numCols ) > 3 * numMines);
         mineData = new boolean[numRows][numCols];
         numMine = numMines;
         
        
      }
      
   
      /**
         Removes any current mines on the minefield, and puts numMines() mines in random locations on the minefield,
         ensuring that no mine is placed at (row, col).
         @param row the row of the location to avoid placing a mine
         @param col the column of the location to avoid placing a mine
         PRE: inRange(row, col)
       */
      public void populateMineField(int row, int col) {
       
         assert(inRange(row, col));
         resetEmpty();
         Random generator = new Random();
         int count = 0;
         int loc;
         while ( count < numMines())
         {
            loc = generator.nextInt(numRows() * numCols());
            if(hasMine(loc / numCols() , loc % numCols()) || (loc /numCols() == row && loc % numCols() == col))
            {
            }
            else {
               mineData[loc / numCols()][loc % numCols() ] = true;
               count++;
            }
         }
        
      }
      
      
      /**
         Reset the minefield to all empty squares.  This does not affect numMines(), numRows() or numCols()
         Thus, after this call, the actual number of mines in the minefield does not match numMines().  
         Note: This is the state the minefield is in at the beginning of a game.
       */
      public void resetEmpty() {
         for(int i = 0; i < numRows(); i++)
         {
            for(int j = 0; j < numCols(); j++)
            {
               mineData[i][j] = false; 
            }
         }
         
      }
   
      
     /**
        Returns the number of mines adjacent to the specified mine location (not counting a possible 
        mine at (row, col) itself).
        Diagonals are also considered adjacent, so the return value will be in the range [0,8]
        @param row  row of the location to check
        @param col  column of the location to check
        @return  the number of mines adjacent to the square at (row, col)
        PRE: inRange(row, col)
      */
      public int numAdjacentMines(int row, int col) {
         assert(inRange(row, col));
         int count = 0;
         for(int i = -1; i <= 1; i++)
         {
            for(int j = -1; j <= 1; j++)
            {
               if(i == 0 && j== 0)
               {
               }
               else if (inRange(row + i, col + j) && hasMine(row + i, col + j) )
               {
                  count++;
               }
               
            }
            
         }    
         return count;       
      }
      
      
      /**
         Returns true iff (row,col) is a valid field location.  Row numbers and column numbers
         start from 0.
         @param row  row of the location to consider
         @param col  column of the location to consider
         @return whether (row, col) is a valid field location
      */
      public boolean inRange(int row, int col) {
         if(0 <= row && row < numRows() && 0<=col && col < numCols())
         {
            return true;
         }
         return false;       
      }
      
      
      /**
         Returns the number of rows in the field.
         @return number of rows in the field
      */  
      public int numRows() {
         return mineData.length;       
      }
      
      
      /**
         Returns the number of rows in the field.
         @return number of rows in the field
      */    
      public int numCols() {
         return mineData[0].length;       
      }
      
      
      /**
         Returns whether there is a mine in this square
         @param row  row of the location to check
         @param col  column of the location to check
         @return whether there is a mine in this square
         PRE: inRange(row, col)   
      */    
      public boolean hasMine(int row, int col) {
         assert(inRange(row, col));
         if(mineData[row][col])
         {
            return true;
         }
    
            return false;       
      }
      
      
      /**
         Returns the number of mines you can have in this minefield.  
       * @return
       */
      public int numMines() {
         return numMine;       
      }
   
      
      
            
   }
   
