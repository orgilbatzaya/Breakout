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
import java.util.Iterator;

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
    private final String BOUNCER_IMAGE = "ball.gif";
    private final String BLOCK1_IMAGE = "brick1.gif";
    private final String BLOCK2_IMAGE = "brick2.gif";
    private final int  NUM_BLOCKS = 16;
    private boolean ingame = true;



    // some things we need to remember during our game
    private Scene levelOne;
    private Scene levelTwo;
    private Scene levelThree;
    private Bouncer myBouncer;
    private Paddle myPaddle;
    private ArrayList<Block> myBlocks = new ArrayList<Block>();


    /**
     * Initialize what will be displayed and how it will be updated.
     */
    @Override
    public void start (Stage stage) {
        // attach scene to the stage and display it

        levelOne = setupGame(SIZE, SIZE, BACKGROUND);
        //levelTwo = setupGame(SIZE, SIZE, BACKGROUND);

        stage.setScene(levelOne);
        //levelOne.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
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
        myBouncer = new Bouncer(SIZE, SIZE);
        myPaddle = new Paddle(SIZE,SIZE);
        // order added to the group is the order in which they are drawn

        root.getChildren().add(myPaddle.getView());
        root.getChildren().add(myBouncer.getView());

        //var blockRegion = new Pane();
        for (int k = 0; k < NUM_BLOCKS; k++) {
            var bl = new Block(2);
            myBlocks.add(bl);
            root.getChildren().add(bl.getView());
        }
        //arrangeBlocks(myBlocks);
        //root.getChildren().add(blockRegion);
        arrangeBlocks(myBlocks);



        // respond to input
        scene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
        return scene;
    }

    // Change properties of shapes to animate them
    // Note, there are more sophisticated ways to animate shapes, but these simple ways work fine to start.
    private void step (double elapsedTime) {
        // update attributes
        System.out.println(myBlocks.size());
        myBouncer.move(levelOne.getWidth(), levelOne.getHeight(), elapsedTime);

        for(int i = 0; i < myBlocks.size(); i++){
            //myBouncer.checkDirection(levelOne.getWidth(), levelOne.getHeight(), myBlocks.get(i).getView());
            //updateMyBlocks();
            if(myBlocks.get(i).onHit(myBouncer.getView())){
                myBlocks.remove(i);
            }
            myBouncer.checkDirection(levelOne.getWidth(), levelOne.getHeight(), myBlocks.get(i).getView());

        }
        myBouncer.checkDirection(levelOne.getWidth(), levelOne.getHeight(), myPaddle.getView());
        //myBouncer.move(elapsedTime);



    }
    /*private void updateMyBlocks(){
        if (myBlocks.isEmpty()) {

            ingame = false;
            return;
        }

        for (int i = 0; i < myBlocks.size(); i++) {

            Block b = myBlocks.get(i);

            if (b != null) {
                continue;
            } else {
                myBlocks.remove(i);
            }
        }
    }*/

    // What to do each time a key is pressed
    private void handleKeyInput (KeyCode code) {
        if (code == KeyCode.RIGHT) {
            myPaddle.getView().setX(myPaddle.getView().getX() + myPaddle.getSpeed());
        }
        else if (code == KeyCode.LEFT) {
            myPaddle.getView().setX(myPaddle.getView().getX() - myPaddle.getSpeed());
        }
        else if (code == KeyCode.UP) {
            myPaddle.getView().setY(myPaddle.getView().getY() - myPaddle.getSpeed());
        }
        else if (code == KeyCode.DOWN) {
            myPaddle.getView().setY(myPaddle.getView().getY() + myPaddle.getSpeed());
        }

    }
    private void arrangeBlocks(ArrayList<Block> blocks) {
        int rows = 2;
        int blocksPerRow = NUM_BLOCKS / rows;



        double blockWidth = SIZE / blocksPerRow;
        for (int i = 0; i < blocksPerRow; i++) {
            blocks.get(i).getView().setFitWidth(blockWidth);
            blocks.get(i).getView().setX(i *blockWidth);
            blocks.get(i).getView().setY(20);


        }
        for (int i = blocksPerRow; i < NUM_BLOCKS; i++) {
            blocks.get(i).getView().setFitWidth(blockWidth);
            blocks.get(i).getView().setX((i - 8) *blockWidth);
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


