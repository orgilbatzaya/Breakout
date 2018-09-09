package game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import java.util.Iterator;



import java.util.ArrayList;

public class Block {

    private int myStrength;
    private final String BLOCK1_IMAGE = "brick1.gif";
    private final String BLOCK2_IMAGE = "brick2.gif";
    private Image weakBrick;
    private Image strongBrick;
    protected ImageView myView;

    public Block(int strength){

        myStrength = strength;
        weakBrick = new Image(this.getClass().getClassLoader().getResourceAsStream(BLOCK1_IMAGE));
        strongBrick = new Image(this.getClass().getClassLoader().getResourceAsStream(BLOCK2_IMAGE));
        myView = new ImageView(weakBrick);
        if(myStrength == 2){
            myView.setImage(strongBrick);
        }

    }

    public boolean onHit(ImageView obj) {
        Group group = (Group) myView.getParent();
        //System.out.println(group.getChildren().size());
        boolean remove = false;
        if (obj.getBoundsInParent().intersects(myView.getBoundsInParent())) {
            myStrength = myStrength -1;
            if(myStrength <= 0){
                myView.setImage(null);
                group.getChildren().remove(myView);
                remove = true;
            } else {
                myView.setImage(weakBrick);
            }
        }
        return remove;


    }



    public ImageView getView () {
        return myView;
    }

}
