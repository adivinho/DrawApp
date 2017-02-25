/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javacourse.silviodrawer;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


/**
 *
 * @author silvo
 */
class MMenuItem extends MenuItem{
    Class<?> c =  null, s = null;
    public MMenuItem(String name, final Stage window, final Group root, final LinkedList<Shapes> AllShapes){
        switch (name){
        case "New":
            this.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                System.out.println("Menu New has choosen");
                CheckBeforeOpen(window, root, AllShapes);
            }
        });
            break;
        case "Open":            
            this.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                CheckBeforeOpen(window, root, AllShapes);
                System.out.println("Menu Open has choosen");
                FileChooser fileChooser = new FileChooser();
                fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JSON Files", "*.json"));
                File file = fileChooser.showOpenDialog(window);
                try {
                    root.getChildren().addAll(fileOpen(file, root, AllShapes, c, s));
                } catch (Exception e) {
                    System.out.println("Got an Exception during opening a file "+e);
                }
            }
        });
            break;
        case "Save":
            this.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                System.out.println("Menu Save has choosen");
                save(window, AllShapes);
                }
            });
            break;    
        case "Exit":
            this.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent arg0) {
                System.out.println("Menu Exit has choosen");
                System.exit(0);
            }
            });
            break;
        }
        this.setText(name);
    }
    
    public void save(Stage window, LinkedList<Shapes> AllShapes) {        
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName("Simple.json");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json"));

        // Show save file dialog
        File file = fileChooser.showSaveDialog(window);
        try { fileSave(AllShapes, file);
        } catch (IOException e) {
        System.out.println("Got an Exception during writing a file "+e);
        }
    }
    
    public void fileSave(final LinkedList<Shapes> allShapes, final File file) throws IOException {
        String extension = "";
        int i = file.getAbsolutePath().lastIndexOf('.');
        if (i > 0) {
            extension = file.getAbsolutePath().substring(i + 1);
        }
        JSON jsonFile = new JSON();
        jsonFile.saveJSON(allShapes, file);
    }
    
    @SuppressWarnings("rawtypes")
    public Group fileOpen(File file, Group root, LinkedList<Shapes> AllShapes, Class c, Class s) throws Exception {
        // Checked extension
        int i = file.getAbsolutePath().lastIndexOf('.');
        if (i > 0) {
            String extension = file.getAbsolutePath().substring(i + 1);
            System.out.println(file+" type: "+extension);
        }
        JSON jsonFile = new JSON();
        try {
                return jsonFile.loadJSON(file, root, AllShapes, c, s);
            } 
        catch (Exception e) {
                System.out.println("Tried to open file "+file);
                throw e;
            }
    }
    
    public void CheckBeforeOpen(final Stage window, final Group root, final LinkedList<Shapes>AllShapes) {
        final Stage dialog = new Stage();
        dialog.setTitle("To save or not to save");
        dialog.setMinWidth(200);
        final Label x = new Label("Do you want to save your current picture?");
                
                final Button Yes = new Button("Yes");
                Yes.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent arg0) {
                    save(window, AllShapes);
                    dialog.close();
                    dropCurrentShapes(root, AllShapes);
                }
                });
        
                final Button No = new Button("No");
                No.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent arg0) {
                        dialog.close();     
                        dropCurrentShapes(root, AllShapes);
                    }
                });
                
                VBox sP = new VBox(8);
                sP.setAlignment(Pos.CENTER);
                sP.getChildren().addAll(x, Yes, No);
                Scene s = new Scene(sP, 400, 100);
                dialog.setScene(s);
                dialog.showAndWait();
    }
    
    public void dropCurrentShapes(Group root, LinkedList<Shapes> AllShapes){
        for (Shapes s:AllShapes){
            s.notSelected(root);
        }                        
        root.getChildren().remove(1,AllShapes.size()+1);
        AllShapes.clear();
    }
       
}
