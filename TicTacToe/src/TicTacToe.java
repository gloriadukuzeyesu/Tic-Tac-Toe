import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class TicTacToe extends JPanel implements ActionListener {
    // logical variable
    boolean playerX;
    boolean gameDone = false;
    int winner = -1;
    int player1wins = 0, player2wins = 0;
    int[][] board = new int[3][3]; // 2D array represents the game baord

    //paint variables
    int lineWidth = 5;
    int lineLength = 270;
    int x = 15, y = 100; // location of the first line
    int offSet = 95; // square width
    int a = 0, b = 5;
    int mouseX, mouseY = 0;

    //colors of gui
    Color turtle = new Color(0x80bdab);
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
        add(jButton);
        jButton.setVisible(false);
        addMouseListener(new XOListener());
    }

    public static void main(String[] arg) {
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

    public void setPlayer1wins(int a) {
        player1wins = a;
    }

    public void setPlayer2wins(int a) {
        player2wins = a;
    }

    private void resetGame() {
        playerX = true;
        winner = -1;
        gameDone = false;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
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

    private void drawBoard(Graphics page) {
        setBackground(turtle);
        page.setColor(darkGray);
        page.fillRoundRect(x, y, lineLength, lineWidth, 5, 30); // 5  and 30 are the line radius
        page.fillRoundRect(x, y + offSet, lineLength, lineWidth, 5, 30);
        page.fillRoundRect(y, x, lineWidth, lineLength, 30, 5);
        page.fillRoundRect(y + offSet, x, lineWidth, lineLength, 30, 5);
    }

    private void drawGame(Graphics page) {
        // to draw image we use a nested for loop
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == 0) {
                    // do nothing
                } else if (board[i][j] == 1) { // draw the x
                    ImageIcon xIcon = new ImageIcon("orange.png");
                    Image xImage = xIcon.getImage();
                    page.drawImage(xImage, 30 + offSet * i, 30 + offSet * j, null);
                } else if (board[i][j] == 2) { // draw the 0
                    page.setColor(offWhite);
                    page.fillOval(30 + offSet * i, 30 + offSet * j, 50, 50);
                    page.setColor(turtle);
                    page.fillOval(40 + offSet * i, 40 + offSet * j, 30, 30);
                }
            }
        }
        repaint();
    }

    private void drawUI(Graphics page) {
        // SET COLOR AND FONT
        page.setColor(darkGray);
        page.fillRect(300, 0, 120, 300);
        Font font = new Font("Helvetica", Font.PLAIN, 20);
        page.setFont(font);

        //SET WIN COUNTER
        page.setColor(offWhite);
        page.drawString("Win Count", 310, 30);
        page.drawString(": " + player1wins, 362, 70);
        page.drawString(": " + player2wins, 362, 105);

        // DRAW SCORE X
        ImageIcon xIcon = new ImageIcon("orange.png");
        Image xImage = xIcon.getImage();
        Image newXImage = xImage.getScaledInstance(25, 25, Image.SCALE_SMOOTH);
        ImageIcon newXIcon = new ImageIcon(newXImage);
        page.drawImage(newXIcon.getImage(), 44 + offSet + 190, 47, null);

        // DRAW SCORE O
        page.setColor(offWhite);
        page.fillOval(43 + 190 + offSet, 80, 30, 30);
        page.setColor(darkGray);
        page.fillOval(49 + 190 + offSet, 85, 20, 20);

        // DRAW WHO IS TURN OR WINNER
        page.setColor(offWhite);
        Font font1 = new Font("Serif", Font.ITALIC, 18);
        page.setFont(font1);

        // GAME LOGIC TO SHOW THE WINNER OR A TIE
        if (gameDone) {
            //display the winner
            if (winner == 1) { // x
                page.drawString("The winner is", 310, 150);
                page.drawImage(xImage, 335, 160, null);
            } else if (winner == 2) { // o
                page.drawString("The winner is", 310, 150);
                page.setColor(offWhite);
                page.fillOval(332, 160, 50, 50);
                page.setColor(darkGray);
                page.fillOval(345, 170, 30, 30);
            } else if (winner == 3) { // tie
                page.drawString("It is a tie", 330, 178);
            }
        } else {
            // show whose turn
            Font font2 = new Font("Serif", Font.ITALIC, 20);
            page.setFont(font2);
            page.drawString("It is ", 350, 160);
            if (playerX) {
                page.drawString("X's turn", 325, 180);
            } else {
                page.drawString("O's turn", 325, 180);
            }
        }

        // DRAW LOGO
        Image cookie = Toolkit.getDefaultToolkit().getImage("cookie.png");
        page.drawImage(cookie, 345, 235, 30, 30, this);
        Font c = new Font("Courier", Font.ITALIC, 13);
        page.setFont(c);
        page.drawString("Just One Byte", 310, 280);
    }

    private void checkWinner() {
        if (gameDone) {
            System.out.println("Game is Over");
            return;
        }
        // vertical
        int temp = -1;
        if ((board[0][0] == board[0][1])
                && (board[0][1] == board[0][2])
                && (board[0][0] != 0)) {
            temp = board[0][0];
        } else if ((board[1][0] == board[1][1])
                && (board[1][1] == board[1][2])
                && (board[1][0] != 0)) {
            temp = board[1][1];
        } else if ((board[2][0] == board[2][1])
                && (board[2][1] == board[2][2])
                && (board[2][0] != 0)) {
            temp = board[2][1];

            // horizontal
        } else if ((board[0][0] == board[1][0])
                && (board[1][0] == board[2][0])
                && (board[0][0] != 0)) {
            temp = board[0][0];
        } else if ((board[0][1] == board[1][1])
                && (board[1][1] == board[2][1])
                && (board[0][2] != 0)) {
            temp = board[0][2];

            // diagonal
        } else if ((board[0][0] == board[1][1])
                && (board[1][1] == board[2][2])
                && (board[0][0] != 0)) {
            temp = board[0][2];
        } else {
            // check for a tie
            boolean notDone = false;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == 0) {
                        notDone = true;
                        break;
                    }
                }
            }
            if (!notDone) {
                temp = 3;
            }
        }
        if (temp > 0) {
            winner = temp;
            if (winner == 1) {
                player1wins++;
                System.out.println("Winner is X");
            } else if (winner == 2) {
                player2wins++;
                System.out.println("winner is O");
            } else if (winner == 3) {
                System.out.println("It is a tie");
            }
            gameDone = true;
            getJButton().setVisible(true);
        }
    }

    private class XOListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent event) {
            mouseX = -1;
            mouseY = -1;
            if (!gameDone) {
                a = event.getX();
                b = event.getY();
                System.out.println("CLicked => x: " + a + ", y: " + b);

                if (a > 12 && a < 99) {
                    mouseX = 0;
                } else if (a > 103 && a < 195) {
                    mouseX = 1;
                } else if (a > 200 && a < 287) {
                    mouseX = 2;
                } else {
                    mouseX = -1;
                }

                if (b > 12 && b < 99) {
                    mouseY = 0;
                } else if (b > 103 && b < 195) {
                    mouseY = 1;
                } else if (b > 200 && b < 287) {
                    mouseY = 2;
                } else {
                    mouseY = -1;
                }

                // draw x or 0 and switch the player
                if (mouseX != -1 && mouseY != -1) {
                    // open spot to play
                    if (board[mouseX][mouseY] == 0) {
                        if (playerX) {
                            board[mouseX][mouseY] = 1;
                            playerX = false;
                        } else {
                            board[mouseX][mouseY] = 2;
                            playerX = true;
                        }
                        checkWinner();
                        System.out.println("CLicked => x: " + a + ", y: " + b +
                                " board:( " + x + ", " + y + " )");
                    }
                } else {
                    System.out.println("invalid click");
                }
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }
}
