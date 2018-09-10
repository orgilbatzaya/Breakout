package game;

import java.util.ArrayList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.lang.Math;
import java.util.Collections;
import java.util.Stack;

public class Projectile {
    private final String PROJ_IMAGE_FILE = "brick10.gif";
    private int FALL_SPEED = 0;
    private int FIRE_SPEED = 50;
    private Image projImage;
    private ImageView myView;
    private Block myBlock;
    private boolean loaded;
    private final int numProjectiles = 6;
    private Stack<Projectile> myProjectiles;



    public Projectile(Block b) {
        projImage = new Image(this.getClass().getClassLoader().getResourceAsStream(PROJ_IMAGE_FILE));
        myView = new ImageView(projImage);
        myBlock = b;

    }

    public Stack<Projectile> makeProjectiles(ArrayList<Block> blocks){
        myProjectiles = new Stack<Projectile>();
        Collections.shuffle(blocks);
        for(int i = 0; i < numProjectiles; i++){
            Projectile p = new Projectile(blocks.get(i));
            blocks.get(i).getView().setVisible(false);
            p.myView.setX(blocks.get(i).getView().getX());
            p.myView.setY(blocks.get(i).getView().getY());
            myProjectiles.add(p);
        }
        return myProjectiles;
    }

    public void pickedUp(Paddle targetPaddle) {
        if(targetPaddle.getView().getBoundsInParent().intersects(myView.getBoundsInParent())){
            //myView.setImage(null);
            myView.setX(targetPaddle.getView().getX());
            myView.setY(targetPaddle.getView().getY());
            this.loaded = true;

            //targetPaddle.addProjectile(this);
        }
    }
    public boolean isLoaded(){
        return loaded;
    }

    public void onHit(ImageView obj) {
        if (obj.getBoundsInParent().intersects(myView.getBoundsInParent())) {
            FALL_SPEED = 100;
        }
    }

    public void move (double elapsed) {
        myView.setY(myView.getY() + FALL_SPEED * elapsed);
    }

    public void launch(double elapsed){
        myView.setY(myView.getY() - 50);
        myView.setY(myView.getY() - FIRE_SPEED * elapsed);
        System.out.println("moving");
    }

    public ImageView getView () {
        return myView;
    }

}

