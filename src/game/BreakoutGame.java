package game;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.ArrayList;

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
    private final String BOUNCER_IMAGE = "ball.gif";
    private final String PADDLE_IMAGE = "paddle.gif";
    private final int PADDLE_SPEED = 20;
    private final String BLOCK1_IMAGE = "brick1.gif";
    private final String BLOCK2_IMAGE = "brick2.gif";
    private final int  NUM_BLOCKS = 10;



    // some things we need to remember during our game
    private Scene myScene;
    private Bouncer myBouncer;
    private ImageView myPaddle;
    private ArrayList<Block> myBlocks = new ArrayList<Block>();
    private int dir = 1;


    /**
     * Initialize what will be displayed and how it will be updated.
     */
    @Override
    public void start (Stage stage) {
        // attach scene to the stage and display it

        myScene = setupGame(SIZE, SIZE, BACKGROUND);
        stage.setScene(myScene);
        stage.setTitle(TITLE);
        stage.show();
        // attach "game loop" to timeline to play it

        var frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(SECOND_DELAY));
        var animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();
    }

    // Create the game's "scene": what shapes will be in the game and their starting properties
    private Scene setupGame (int width, int height, Paint background) {
        // create one top level collection to organize the things in the scene
        var root = new Group();
        // create a place to see the shapes
        var scene = new Scene(root, width, height, background);
        // make some shapes and set their properties
        var imageBouncer = new Image(this.getClass().getClassLoader().getResourceAsStream(BOUNCER_IMAGE));
        myBouncer = new Bouncer(imageBouncer, SIZE, SIZE);

        var imagePaddle = new Image(this.getClass().getClassLoader().getResourceAsStream(PADDLE_IMAGE));
        myPaddle = new ImageView(imagePaddle);
        myPaddle.setX(width / 2 - myPaddle.getBoundsInLocal().getWidth() / 2);
        myPaddle.setY(height / 2 - myPaddle.getBoundsInLocal().getHeight() / 2);

        var imageBrick1 = new Image(this.getClass().getClassLoader().getResourceAsStream(BLOCK1_IMAGE));
        for (int k = 0; k < NUM_BLOCKS; k++) {
            var bl = new Block(imageBrick1);
            myBlocks.add(bl);
            root.getChildren().add(bl.getView());
        }
        arrangeBlocks(myBlocks);


        // order added to the group is the order in which they are drawn
        root.getChildren().add(myPaddle);
        root.getChildren().add(myBouncer.getView());
        // respond to input
        scene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
        return scene;
    }

    // Change properties of shapes to animate them
    // Note, there are more sophisticated ways to animate shapes, but these simple ways work fine to start.
    private void step (double elapsedTime) {
        // update attributes

        for(Block b:myBlocks){
            myBouncer.checkDirection(myScene.getWidth(), myScene.getHeight(), b.getView());
            b.onHit(myBouncer.getView());
        }
        myBouncer.checkDirection(myScene.getWidth(), myScene.getHeight(), myPaddle);
        myBouncer.move(elapsedTime);

    }

    // What to do each time a key is pressed
    private void handleKeyInput (KeyCode code) {
        if (code == KeyCode.RIGHT) {
            myPaddle.setX(myPaddle.getX() + PADDLE_SPEED);
        }
        else if (code == KeyCode.LEFT) {
            myPaddle.setX(myPaddle.getX() - PADDLE_SPEED);
        }
        else if (code == KeyCode.UP) {
            myPaddle.setY(myPaddle.getY() - PADDLE_SPEED);
        }
        else if (code == KeyCode.DOWN) {
            myPaddle.setY(myPaddle.getY() + PADDLE_SPEED);
        }
    }
    private void arrangeBlocks(ArrayList<Block> blocks){
        double blockWidth = SIZE / NUM_BLOCKS;
        for(int i = 0; i < NUM_BLOCKS; i++){
            blocks.get(i).getView().setFitWidth(blockWidth);
            blocks.get(i).getView().setX(i * blockWidth);
        }
    }



    /**
     * Start the program.
     */
    public static void main (String[] args) {
        launch(args);
    }
}


