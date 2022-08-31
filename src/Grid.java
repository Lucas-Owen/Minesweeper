import java.util.Random;

public class Grid {
    private boolean[][] bombGrid;
    private int[][] countGrid;
    private int numRows;
    private int numColumns;
    private int numBombs;

    public Grid(){
        numRows = 10;
        numColumns = 10;
        numBombs = 25;

        createBombGrid();
        createCountGrid();
    }

    /**
     * 
     * @param rows
     * @param columns
     */
    public Grid(int rows, int columns){
        numRows = rows;
        numColumns = columns;
        numBombs = 25;

        createBombGrid();
        createCountGrid();
    }

    /**
     * 
     * @param rows
     * @param columns
     * @param numBombs
     */
    public Grid(int rows, int columns, int numBombs){
        numRows = rows;
        numColumns = columns;
        this.numBombs = numBombs;

        createBombGrid();
        createCountGrid();
    }

    private void createBombGrid(){
        int maxBombs = numRows * numColumns - 1;
        if(numBombs > maxBombs) numBombs =  maxBombs; //upper bound to prevent infinite loops

        bombGrid = new boolean[numRows][numColumns];

        for(int i = 0; i < numRows; i++){
            for(int j = 0; j < numColumns; j++){
                bombGrid[i][j] = false;
            }
        }

        Random random = new Random();
        int bomb_count = 0;
        while(bomb_count < numBombs){
            int i = random.nextInt(numRows);
            int j = random.nextInt(numColumns);
            if(!bombGrid[i][j]){
                bombGrid[i][j] = true;
                bomb_count++;
            }
        }

    }
    private void createCountGrid(){
        countGrid = new int[numRows][numColumns];

        for(int i = 0; i < numRows; i++){
            for(int j = 0; j < numColumns; j++){
                countGrid[i][j] = 0;
            }
        }

        for(int i = 0; i < numRows; i++){
            for(int j = 0; j < numColumns; j++){
                //row above
                if(i - 1 >= 0){
                    //top left
                    if(j - 1 >= 0){
                        if(bombGrid[i - 1][j - 1]) countGrid[i][j]++;
                    }
                    //top center
                    if(bombGrid[i - 1][j]) countGrid[i][j]++;
                    //top right
                    if(j + 1 < numColumns){
                        if(bombGrid[i - 1][j + 1]) countGrid[i][j]++;
                    }
                }

                //middle row
                //middle right
                if(j + 1 < numColumns){
                    if(bombGrid[i][j + 1]) countGrid[i][j]++;
                }
                //middle center
                if(bombGrid[i][j]) countGrid[i][j]++;
                //middle left
                if(j - 1 >= 0){
                    if(bombGrid[i][j - 1]) countGrid[i][j]++;
                }
                
                //row below
                if(i + 1 < numRows){
                    //bottom left
                    if(j - 1 >= 0){
                        if(bombGrid[i + 1][j - 1]) countGrid[i][j]++;
                    }
                    //bottom center
                    if(bombGrid[i + 1][j]) countGrid[i][j]++;
                    //bottom right
                    if(j + 1 < numColumns){
                        if(bombGrid[i + 1][j + 1]) countGrid[i][j]++;
                    }
                }
            }
        }
    }

    /**
     * 
     * @param row
     * @param column
     * @return
     */
    public boolean isBombAtLocation(int row, int column){
        if(row >= 0 && row < numRows && column >= 0 && column < numColumns){
            return bombGrid[row][column];
        }
        return false;
    }
    
    /**
     * 
     * @param row
     * @param column
     * @return
     */
    public int getCountAtLocation(int row, int column){
        if(row >= 0 && row < numRows && column >= 0 && column < numColumns){
            return countGrid[row][column];
        }
        return 0;
    }

     /**
     * @return int[][] return a copy of the countGrid
     */
    public int[][] getCountGrid() {
        int[][] countGridCopy = new int[numRows][numColumns];

        for(int i = 0; i < numRows; i++){
            for(int j = 0; j < numColumns; j++){
                countGridCopy[i][j] = countGrid[i][j];
            }
        }
        return countGridCopy;
    }
    /**
     * 
     * @return boolean[][] return a copy of the bombGrid
     */
    public boolean[][] getBombGrid() {
        boolean[][] bombGridCopy = new boolean[numRows][numColumns];

        for(int i = 0; i < numRows; i++){
            for(int j = 0; j < numColumns; j++){
                bombGridCopy[i][j] = bombGrid[i][j];
            }
        }
        return bombGridCopy;
    }
    
    /**
     * @return int return the numRows
     */
    public int getNumRows() {
        return numRows;
    }

    /**
     * @return int return the numColumns
     */
    public int getNumColumns() {
        return numColumns;
    }

    /**
     * @return int return the numBombs
     */
    public int getNumBombs() {
        return numBombs;
    }

    public String toString(){
        String result = "";
        String count = "\n";
        for(int i = 0; i < numRows; i++){
            for(int j = 0; j < numColumns; j++){
                if(bombGrid[i][j]) result += " T ";
                else result += " F "; 
                count += String.format(" %d ", countGrid[i][j]);
            }
            result += "\n";
            count += "\n";
        }
        return result + count;
    }
}
