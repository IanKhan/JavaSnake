import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.*;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.util.*;
import java.awt.event.*;
import java.awt.*;


public class ProjectSnake extends JPanel implements ActionListener {

    private final int B_WIDTH = 300;
    private final int B_HEIGHT = 300;
    private final int DOT_SIZE = 10;
    private final int ALL_DOTS = 900;
    private final int RAND_POS = 29;
    private final int DELAY = 140;

    private int score=0;

    private final int x[] = new int[ALL_DOTS];
    private final int y[] = new int[ALL_DOTS];

    private int dots;
    private int Dots = 0;
    private int food_x;
    private int food_y;

    private int GameState=0;

    private boolean leftDirection = false;
    private boolean rightDirection = true;
    private boolean upDirection = false;
    private boolean downDirection = false;
    private boolean inGame = true;

    private Timer timer;
    private Image ball;
    private Image apple;
    private Image head;

    private enum State{
        MENU,
        GAME

    };
    private State STATE=State.MENU;

    boolean[] keys = new boolean[256];

    public ProjectSnake() {

        addKeyListener(new MAdap());
        setBackground(Color.black);
        setFocusable(true);

        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        loadImages();
        initGame();
    }

   /* void init() {
        Container cn = getContentPane();
        cn.setLayout(null);
    }*/

    private void loadImages() {

        ImageIcon i = new ImageIcon("pic.jpg");
        ball = i.getImage();

        ImageIcon ia = new ImageIcon("apple.png");
        apple = ia.getImage();

        ImageIcon ian = new ImageIcon(".png");
        head = ian.getImage();
    }


    private void initGame() {

        dots = 2;


        for (int khan = 0; khan < dots; khan++) {
            x[khan] = 50 - khan * 10;
            y[khan] = 50;
        }


        locateApple();

        timer = new Timer(DELAY, this);
        timer.start();
    }

   /* boolean spacedown = false;
    void getInput() {
        //Spacebar.
        if (keys[32]) {
            if (GameState == 0) {
                if (spacedown == false) GameState = 1;
            }
        }
    }*/
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }



    private void doDrawing(Graphics g) {


       /* if(GameState == 0){
            Font fnt = new Font("Arial", Font.BOLD, 20);
            g.setFont(fnt);
            g.setColor(Color.BLACK);
            g.drawString("TAP TO START", getWidth() / 2 - 60, getHeight() / 2);
        }*/


        if (inGame) {

            if(STATE==State.GAME){

            }

            g.drawImage(apple, food_x, food_y, this);

            for (int ian = 0; ian < dots; ian++) {
                if (ian == 0) {
                    g.drawImage(head, x[ian], y[ian], this);
                } else {
                    g.drawImage(ball, x[ian], y[ian], this);
                }
            }

            Toolkit.getDefaultToolkit().sync();

        } else {

            gameOver(g);
        }
    }

    JButton Try = new JButton("Try Again");
    JButton Ex = new JButton("Exit");
    private void gameOver(Graphics g) {

        String msg = "Game Over";
        String msg1 = ("Score: " + Dots);
        Font small = new Font("Arial", Font.BOLD, 14);
        FontMetrics font = getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (B_WIDTH - font.stringWidth(msg)) / 2, B_HEIGHT / 2);
        g.drawString(msg1, (B_HEIGHT - font.stringWidth(msg1)) /2,B_WIDTH / 3);


        Try.setBounds(getWidth(),getHeight(),150,200);
        Try.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent a){
                new ProjectSnake();}
        });
    }

    private void Apple() {

        if ((x[0] == food_x) && (y[0] == food_y)) {

            dots++;

            if(Dots<dots){
                Dots++;
            }
            locateApple();
        }
    }

    private void move() {

        for (int btn = dots; btn > 0; btn--) {
            x[btn] = x[(btn - 1)];
            y[btn] = y[(btn - 1)];
        }

        if (leftDirection) {
            x[0] -= DOT_SIZE;
        }

        if (rightDirection) {
            x[0] += DOT_SIZE;
        }

        if (upDirection) {
            y[0] -= DOT_SIZE;
        }

        if (downDirection) {
            y[0] += DOT_SIZE;
        }
    }

    private void checkCollision() {

        for (int wade = dots; wade > 0; wade--) {

            if ((wade > 4) && (x[0] == x[wade]) && (y[0] == y[wade])) {
                inGame = false;
            }

        }

        if (y[0] >= B_HEIGHT) {
            inGame = false;
        }

        if (y[0] < 0) {
            inGame = false;
        }

        if (x[0] >= B_WIDTH) {
            inGame = false;
        }

        if (x[0] < 0) {
            inGame = false;
        }

        if(!inGame) {
            timer.stop();
        }
    }

    private void locateApple() {

        int location = (int) (Math.random() * RAND_POS);
        food_x = ((location * DOT_SIZE));
        //score++;

        location = (int) (Math.random() * RAND_POS);
        food_y = ((location * DOT_SIZE));
        //score++;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (inGame) {
            Apple();
            checkCollision();
            //score++;
            move();
        }

        repaint();
    }


    private class MAdap extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();
            if(STATE==State.GAME){}

            if ((key == KeyEvent.VK_LEFT) && (!rightDirection)) {
                leftDirection = true;
                upDirection = false;
                downDirection = false;
            }

            if ((key == KeyEvent.VK_RIGHT) && (!leftDirection)) {
                rightDirection = true;
                upDirection = false;
                downDirection = false;
            }

            if ((key == KeyEvent.VK_UP) && (!downDirection)) {
                upDirection = true;
                rightDirection = false;
                leftDirection = false;
            }

            if ((key == KeyEvent.VK_DOWN) && (!upDirection)) {
                downDirection = true;
                rightDirection = false;
                leftDirection = false;
            }
        }
    }
    //.add(Try);
}