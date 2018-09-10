package game;

import java.util.ArrayList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.lang.Math;
import java.util.Collections;

public class PowerUp {
    private final String PU1_IMAGE = "brick4.gif";
    private int FALL_SPEED = 0;
    private Image PU1;
    private ImageView myView;
    private Block myBlock;
    private int numPowerUps = 4;
    private ArrayList<PowerUp> myPowerUps;



    public PowerUp(Block b) {
        PU1 = new Image(this.getClass().getClassLoader().getResourceAsStream(PU1_IMAGE));
        myView = new ImageView(PU1);
        myBlock = b;

    }

    public ArrayList<PowerUp> makePowerUps(ArrayList<Block> blocks){
        myPowerUps = new ArrayList<PowerUp>();
        Collections.shuffle(blocks);
        for(int i = 0; i < numPowerUps; i++){
            PowerUp p = new PowerUp(blocks.get(i));
            blocks.get(i).getView().setVisible(false);
            p.myView.setX(blocks.get(i).getView().getX());
            p.myView.setY(blocks.get(i).getView().getY());
            p.myView.setFitWidth(blocks.get(i).getView().getFitWidth());
            //p.myView.setVisible(true);
            myPowerUps.add(p);
        }
        return myPowerUps;
    }

    public void pickedUp(Paddle targetPaddle) {
        if(targetPaddle.getView().getBoundsInParent().intersects(myView.getBoundsInParent())){
            myView.setImage(null);
            int choice = (int) (Math.random() * 3) + 1;
            if(choice == 1) {
                System.out.println("lengthen" + targetPaddle.getPaddleWidth());
                lengthenPaddle(targetPaddle);
            }
            else if(choice == 2) {
                addLife(targetPaddle);
            }
            else{
                targetPaddle.setSkipLevel(true);
            }
        }
    }


    public void onHit(ImageView obj) {
        if (obj.getBoundsInParent().intersects(myView.getBoundsInParent())) {
            FALL_SPEED = 100;
        }
    }

    public void addLife(Paddle targetPaddle){
        targetPaddle.addLives(1);
    }
    public void lengthenPaddle(Paddle targetPaddle){
        //targetPaddle.setPaddleWidth(targetPaddle.getPaddleWidth() * 2);
        targetPaddle.getView().setFitWidth(150);
    }
    public void move (double elapsed) {
        myView.setY(myView.getY() + FALL_SPEED * elapsed);
        //System.out.println(myView.getBoundsInParent());
    }
    public ImageView getView () {
        return myView;
    }

}
