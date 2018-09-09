package game;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.Iterator;
import javafx.application.Platform;
import javafx.scene.text.Text;
import javafx.scene.layout.StackPane;


import javafx.scene.layout.Pane;


/**
 * A breakout variant
 *
 * @author Orgil Batzaya
 */
public class BreakoutGame extends Application {
    private final String TITLE = "Breakout";
    private final int SIZE = 400;
    private final int FRAMES_PER_SECOND = 60;
    private final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    private final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    private final Paint BACKGROUND = Color.AZURE;
    private final Paint HIGHLIGHT = Color.OLIVEDRAB;
    private final int NUM_BLOCKS = 16;
    private boolean skipLevel = false;
    private int currentLevel;
    private int scoreValue;


    // some things we need to remember during our game
    private Stage primaryStage;
    private Timeline animation;
    private Scene splashScreen;
    private Scene levelOne;
    private Scene levelTwo;
    private Scene levelThree;
    private Scene endScreen;
    private PowerUp startPowerUp;
    private Bouncer myBouncer;
    private Paddle myPaddle;
    private Text LivesLeft;
    private Text Level;
    private Text Score;
    private Text EndGame;
    private String gameResult;
    private ArrayList<Block> myBlocks = new ArrayList<Block>();
    private ArrayList<PowerUp> myPowerUps = new ArrayList<>();


    /**
     * Initialize what will be displayed and how it will be updated.
     */
    @Override
    public void start(Stage stage) {
        // attach scene to the stage and display it
        primaryStage = stage;
        currentLevel = 0;
        scoreValue = 0;
        setScene(currentLevel);

        // attach "game loop" to timeline to play it

        var frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(SECOND_DELAY, myPaddle));

        animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();

    }

    private void setScene(int currentLvl){
        Scene scene;
        myBlocks.clear();
        myPowerUps.clear();
        if(currentLvl == 0){
            scene = splashScreen;
        }
        else if(currentLvl == 1){
            scene = levelOne;
        }
        else if(currentLvl == 2){
            scene = levelTwo;
        }
        else if(currentLvl == 3){
            scene = levelThree;
        }
        scene = setupGame(SIZE, SIZE, BACKGROUND, currentLvl);
        primaryStage.setScene(scene);
        primaryStage.setTitle(TITLE);
        primaryStage.show();
    }

    // Create the game's "scene": what shapes will be in the game and their starting properties
    private Scene setupGame(int width, int height, Paint background, int currentLv) {
        var root = new Group();
        var scene = new Scene(root, width, height, background);

        if(currentLv == 0){
            StackPane splashRoot = new StackPane();
            Text startInfo = new Text(100, 200,
            "Hi! Welcome to my Breakout Game! \n \n" +
                    "Hit the Blocks to gain points and beat levels! \n" +
                    "As levels progress, blocks will increase in quantity\n"+
                    "and strength. Don't let the ball fall under you! \n \n" +
                    "There are 3 power ups that appear randomly: \n" +
                    " - paddle widen \n " +
                    " - add life \n"+
                    " - enable a level skip \n \n" +
                    "The cheat keys are: \n" +
                    " - (0, 1, 2, 3) to navigate to the this screen and any level.\n" +
                    " - \"I\" to gain a very large number of lives\n" +
                    " - \"R\" to reset ball and paddle position in the middle \n" + "\n" +
                    "PRESS S TO START");
            startInfo.setTextAlignment(TextAlignment.CENTER);
            splashRoot.getChildren().add(startInfo);
            StackPane.setAlignment(startInfo, Pos.CENTER);
            scene = new Scene(splashRoot, width, height, background);

        } else {
            myBouncer = new Bouncer(SIZE, SIZE);
            myPaddle = new Paddle(SIZE, SIZE);

            root.getChildren().add(myPaddle.getView());
            root.getChildren().add(myBouncer.getView());
            for (int k = 0; k < NUM_BLOCKS; k++) {
                var bl = new Block(2);
                myBlocks.add(bl);
                root.getChildren().add(bl.getView());
            }
            arrangeBlocks(myBlocks);


            startPowerUp = new PowerUp(null);
            myPowerUps = startPowerUp.makePowerUps(myBlocks);
            for (int k = 0; k < myPowerUps.size(); k++) {
                root.getChildren().add(myPowerUps.get(k).getView());
            }

            LivesLeft = new Text(10, 10, "LIVES LEFT: " + myPaddle.getLivesLeft());
            Level = new Text(110, 10, "LEVEL: " + currentLv);
            Score = new Text(210, 10, "SCORE: " + scoreValue);
            EndGame = new Text(SIZE / 2, SIZE / 2, "");


            root.getChildren().add(LivesLeft);
            root.getChildren().add(Level);
            root.getChildren().add(Score);
            root.getChildren().add(EndGame);
        }

        scene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
        return scene;
    }

    private void checkGameProgress(){
        if(myBlocks.size() == 0 && currentLevel!= 0){
            currentLevel ++;
            setScene(currentLevel);
        }
        if(currentLevel == 4){
           gameResult = "won";
        }
    }
    private void step(double elapsedTime, Paddle target) {
        // update attributes
        System.out.println(myBlocks.size());
        if(currentLevel != 0) {
            checkGameProgress();
            if (gameResult == "won") {
                animation.pause();
                EndGame.setText("You " + gameResult + "! Your score was " + scoreValue);
            }
            if (myPaddle.checkLives(animation) == false) {
                gameResult = "lost";
                EndGame.setText("You " + gameResult + "! Your score was " + scoreValue);
            }


            myBouncer.move(elapsedTime, target);
            LivesLeft.setText("Lives: " + myPaddle.getLivesLeft());
            Score.setText("SCORE: " + scoreValue);

            for (int i = 0; i < myBlocks.size(); i++) {

                if (myBlocks.get(i).onHit(myBouncer.getView())) {
                    myBlocks.remove(i);
                    scoreValue += 10;
                }
                myBouncer.checkDirection(myBlocks.get(i).getView(), myPaddle);

            }
            myBouncer.checkDirection(myPaddle.getView(), myPaddle);
            for (int i = 0; i < myPowerUps.size(); i++) {
                myPowerUps.get(i).onHit(myBouncer.getView());
                myPowerUps.get(i).pickedUp(myPaddle);
                myPowerUps.get(i).move(elapsedTime);

            }
        }
    }


    // What to do each time a key is pressed
    private void handleKeyInput(KeyCode code) {
        if (code == KeyCode.RIGHT) {
            myPaddle.getView().setX(myPaddle.getView().getX() + myPaddle.getSpeed());
        } else if (code == KeyCode.LEFT) {
            myPaddle.getView().setX(myPaddle.getView().getX() - myPaddle.getSpeed());
        } else if (code == KeyCode.ESCAPE) {
            Platform.exit();
            System.exit(0);
        } else if (code == KeyCode.SPACE) {
            animation.play();
        } else if (code == KeyCode.DIGIT2) {
            currentLevel = 2;
            setScene(currentLevel);
        } else if (code == KeyCode.DIGIT3) {
            currentLevel = 3;
            setScene(currentLevel);
        } else if (code == KeyCode.DIGIT1 || code == KeyCode.S) {
            currentLevel = 1;
            setScene(currentLevel);
        } else if (code == KeyCode.DIGIT0) {
            currentLevel = 0;
            setScene(currentLevel);
        } else if (code == KeyCode.I) {
            myPaddle.addLives(1000);
        } else if (code == KeyCode.R) {
            currentLevel = 0;
            setScene(currentLevel);
        }
    }

        private void arrangeBlocks (ArrayList < Block > blocks) {
            int rows = 2;
            int blocksPerRow = NUM_BLOCKS / rows;


            double blockWidth = SIZE / blocksPerRow;
            for (int i = 0; i < blocksPerRow; i++) {
                blocks.get(i).getView().setFitWidth(blockWidth);
                blocks.get(i).getView().setX(i * blockWidth);
                blocks.get(i).getView().setY(20);

            }
            for (int i = blocksPerRow; i < NUM_BLOCKS; i++) {
                blocks.get(i).getView().setFitWidth(blockWidth);
                blocks.get(i).getView().setX((i - 8) * blockWidth);
                blocks.get(i).getView().setY(50);


            }
        }





    /**
     * Start the program.
     */
    public static void main (String[] args) {
        launch(args);
    }
}


