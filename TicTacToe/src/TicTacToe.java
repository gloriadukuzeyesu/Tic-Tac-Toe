import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToe extends JPanel implements ActionListener {
    // logical variable
    boolean playerX;
    boolean gameDone = false;
    int winner = -1;
    int player1wins = 0, player2wins = 0;
    int [][] board = new int[3][3]; // 2D array represents the game baord

    //paint variables
    int lineWidth = 5;
    int lineLength = 270;
    int x = 15, y = 100; // location of the first line
    int offSet = 95; // square width
    int a = 0, b = 5;
    int mouseX, mouseY = 0;

    //colors of gui
    Color turtle = new Color(0x80bdab);
    Color orange = new Color(0xfdcb9e);
    Color offWhite = new Color(0xf7f7f7);
    Color darkGray = new Color(0x3f3f44);

    // Components
    JButton jButton;

    // constructor
    public TicTacToe() {
        Dimension size = new Dimension(450, 350);
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        jButton = new JButton("Play Again?");
        jButton.addActionListener(this);
//        jButton.setVisible(false);
    }

    public static void main (String[] arg) {
        JFrame frame = new JFrame("Tic Tac Toe");
        frame.getContentPane();

        TicTacToe tPanel = new TicTacToe();
        frame.add(tPanel);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // when the button is clicked, reset the game
        resetGame();
    }

    public JButton getJButton() {
        return jButton;
    }
    public void setPlayer1wins (int a) {
        player1wins = a;
    }
    public void setPlayer2wins (int a) {
        player2wins = a;
    }

    private void resetGame() {
        playerX = true;
        winner = -1;
        gameDone = false;
        for ( int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j ++) {
                board[i][j] = 0; // set all spot on the board to 0
            }
        }
        // hide the button while the game is still going
         getJButton().setVisible(false);
    }

    // focus on the graphics

    public void paintComponent(Graphics page) {
        super.paintComponent(page);
        drawBoard(page);
        drawUI(page);
        drawGame(page);
    }

    private void drawGame(Graphics page) {
        // to draw image we use a nested for loop
        for(int i = 0; i < 3; i++) {
            for ( int j = 0; j < 3; j++) {
                if( board[i][j] == 0) {
                    // do nothing
                } else if (board[i][j] == 1) {
                    ImageIcon xIcon = new ImageIcon("orange.png");
                    Image xImage = xIcon.getImage();
                    page.drawImage(xImage, 30 + offSet * i,30 + offSet * j, null);
                } else if (board[i][j] == 2){
                    page.setColor(offWhite);
                    page.fillOval(30 + offSet * i, 30 + offSet * j, 50, 50);
                    page.setColor(turtle);
                    page.fillOval(340 + offSet * i, 40 + offSet * j, 30, 30);

                }
            }
        }
    }

    private void drawUI(Graphics page) {
        // SET COLOR AND FONT
        page.setColor(darkGray);
        page.fillRect(300, 0, 120, 300);
        Font font = new Font("Helvetica", Font.PLAIN ,20);
        page.setFont(font);

        //SET WIN COUNTER
        page.setColor(offWhite);
        page.drawString("Win Count", 310, 30);
        page.drawString(": " + player1wins, 360, 70);
        page.drawString(": " + player2wins, 360, 105);

        // DRAW SCORE X
        ImageIcon xIcon = new ImageIcon("orange.png");
        Image xImage = xIcon.getImage();
        Image newXImage = xImage.getScaledInstance(25,25, Image.SCALE_SMOOTH);
        ImageIcon newXIcon = new ImageIcon(newXImage);
        page.drawImage(newXIcon.getImage(), 329, 47, null);

        // DRAW SCORE O
        page.setColor(offWhite);
        page.fillOval(233 + offSet, 80, 30, 30);
        page.setColor(darkGray);
        page.fillOval(250 + offSet,85,20,20);

        // DRAW WHO IS TURN OR WINNER
        page.setColor(offWhite);
        Font font1 = new Font ("Serif", Font.ITALIC, 18);
        page.setFont(font1);

        // GAME LOGIC TO SHOW THE WINNER OR A TIE

        if(gameDone) {
            //display the winner
            if(winner == 1) {
                page.drawString("The winner is", 310, 150);
                page.drawImage(xImage, 335, 160,null);
            }else if(winner == 2) {
                page.drawString("The winner is", 310, 150);
                page.setColor(offWhite);
                page.fillOval(335, 160,50,50);
                page.setColor(darkGray);
                page.fillOval(345,160, 30,30);
            }else if (winner == 3) {
                // tie
                page.drawString("It is a tie", 330, 178);
            }
        } else{
            // show whose turn
            Font font2 = new Font("Serif", Font.ITALIC, 20);
            page.setFont(font2);
            page.drawString("It is ", 350, 160);
            if(playerX) {
                page.drawString("X's turn", 325, 180);
            }else{
                page.drawString("O's turn", 325, 180);
            }
        }

        // DRAW LOGO
        Image cookie = Toolkit.getDefaultToolkit().getImage("cookie.png");
        page.drawImage(cookie, 345, 235, 30, 30, this);
        Font c = new Font ("Courier", Font.BOLD+Font.ITALIC, 13);
        page.setFont(c);
        page.drawString("Just One Byte", 310, 280);
    }

    private void drawBoard(Graphics page) {
        setBackground(turtle);
        page.setColor(darkGray);
        page.fillRoundRect(x, y, lineLength, lineWidth, 5,30); // 5  and 30 are the line radius
        page.fillRoundRect(x, y + offSet, lineLength, lineWidth, 5,30);
        page.fillRoundRect(y, x, lineWidth, lineLength, 30,5);
        page.fillRoundRect(y + offSet, x, lineWidth, lineLength, 30, 5);
    }

}
