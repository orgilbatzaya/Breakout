package game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.ArrayList;

public class Block {
    private int WEAK = 1;
    private int STRONG = 2;
    private int SCREEN_WIDTH;
    private int SCREEN_HEIGHT;

    private ImageView myView;

    public Block(Image image){
        myView = new ImageView(image);
    }

    public void onHit(ImageView obj){
        if(obj.getBoundsInParent().intersects(myView.getBoundsInParent())){
            myView.setImage(null);
        }
    }
    public ImageView getView () {
        return myView;
    }

}
