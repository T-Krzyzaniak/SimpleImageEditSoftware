package models;

import controllers.ImageController;
import views.MainFrame;

import javax.swing.*;
import java.awt.*;

public class LineModel {

    private final Integer startX;

    private final Integer startY;

    private final Integer width;

    private final Integer height;

    private final Color color;

    public LineModel(){
        this.color = Color.black;
        this.height = 0;
        this.startX = 0;
        this.startY = 0;
        this.width = 0;
    }
    public LineModel(int startX, int startY,int width,Color color){
        this.color = color;
        this.startX = startX;
        this.startY = startY;
        this.height = 5;
        if(width ==this.height){
            this.width = width+1;
        }else {
            this.width = width;
        }
    }
    public Color getColor(){return color;}
    public Integer getStartX(){return startX;}
    public Integer getStartY(){return startY;}
    public Integer getWidth(){return width;}
    public Integer getHeight(){return height;}

}
