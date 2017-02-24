/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javacourse.silviodrawer;

import java.io.File;
import java.util.LinkedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 *
 * @author silvo
 */
class MButton extends Button{
    private File file;
    private Image image;
    private final String STYLE_NORMAL = "-fx-background-color: lightgray; -fx-effect: dropshadow( one-pass-box , black , 8 , 0.0 , 2 , 0 ); -fx-border-radius: 4;";
    private final String STYLE_PRESSED = "-fx-background-color: darkgray; -fx-border-color: rgb(49, 89, 23); -fx-border-radius: 4;";

    MButton(final String newstate, String filename, final String state, final VBox toolsArea, final Group root, final LinkedList<Shapes> AllShapes){
        final DropShadow shadow = new DropShadow();
        this.setStyle(STYLE_NORMAL);
        this.file = new File(filename);
        this.image = new Image(file.toURI().toString());
        System.out.println("MButton() "+file.toURI().toString());
        ImageView picture = new ImageView(image);
        this.setGraphic(picture);
        this.setOnAction(new EventHandler<ActionEvent>() {
        @Override public void handle(ActionEvent e) {
                setStyle(STYLE_PRESSED);
                for (Shapes crnt : AllShapes) {
                    crnt.notSelected(root);
                }
                if (javacourse.silviodrawer.MainApp.state.equals(newstate)) {
                        javacourse.silviodrawer.MainApp.state = "blank";
                        setStyle(STYLE_NORMAL);
                    }                    
                else { 
                        javacourse.silviodrawer.MainApp.state = newstate;
                        for (Node bb:toolsArea.getChildren()){
                            if(bb instanceof MButton){
                                ((MButton) bb).Release();
                            }
                        }
                        setStyle(STYLE_PRESSED);
                }                    
                System.out.println("The "+newstate+" is set.");
        }
        });
        this.addEventHandler(MouseEvent.MOUSE_ENTERED, 
        new EventHandler<MouseEvent>() {
        @Override 
        public void handle(MouseEvent e) {
           shadow.setOffsetY(0f);
           shadow.setOffsetX(0f);
           shadow.setWidth(40);
           shadow.setHeight(40);
           shadow.setColor(Color.BLACK);
           setEffect(shadow);
        }
        });
        this.addEventHandler(MouseEvent.MOUSE_EXITED, 
        new EventHandler<MouseEvent>() {
        @Override public void handle(MouseEvent e) {
            setEffect(null);
        }
        });
    }
    
    public void Release(){
        this.setStyle(STYLE_NORMAL);
    }
}
