import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class GridGui extends JFrame implements ActionListener{
    private int height;
    private int width;
    private int bombCount;
    private int clicksRemaining;
    private int[][] countGrid;
    private boolean[][] bombGrid;
    private JButton[][] buttons;
    final int scale = 30;

    public GridGui(){
        initialize();
    }
    
    private void initialize(){
        Grid grid = new Grid();
        System.out.println(grid);
        height = grid.getNumRows();
        width = grid.getNumColumns();
        countGrid = grid.getCountGrid();
        bombGrid = grid.getBombGrid();
        bombCount = grid.getNumBombs();
        clicksRemaining = width * height - bombCount;
        buttons = new JButton[height][width];

        this.setSize(400, 400);
        this.setTitle("Minesweeper");
        this.setLayout(null);
        addBombsAndButtons();
        this.setResizable(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
    private void addBombsAndButtons(){
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                JButton button = new JButton();
                JTextArea text;
                buttons[i][j] = button;
                if(bombGrid[i][j]) text = new JTextArea("T");
                else text = new JTextArea(String.format("%d", countGrid[i][j]));

                text.setBounds(scale * j, scale * i, scale, scale);
                text.setEditable(false);

                button.addActionListener(this);
                button.setBounds(scale * j, scale * i, scale, scale);

                this.add(button);
                this.add(text);
            }
        }
    }
    @Override
    public void actionPerformed(ActionEvent e){
        JButton button = (JButton) e.getSource();
        Rectangle rectangle = button.getBounds();
        int col = rectangle.x / scale;
        int row = rectangle.y / scale;
        button.setEnabled(false); 
        button.setVisible(false);

        if(bombGrid[row][col]){
            hideButtons();
            displayMessage("You Lose!");
        }
        
        else{
            clicksRemaining--;
            if(clicksRemaining == 0){
                displayMessage("You Win!");
            }
        }
    }
    private void displayMessage(String message){
        int response = JOptionPane.showConfirmDialog(this, message + " Start new game?", "Game Over!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if(response == JOptionPane.NO_OPTION){
            this.dispose();
        }
        else{
            this.dispose();
            new GridGui();
        }
    }
    private void hideButtons(){
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                buttons[i][j].setVisible(false);
            }
        }
    }
}
