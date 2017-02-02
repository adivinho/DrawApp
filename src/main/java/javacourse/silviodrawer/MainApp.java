package javacourse.silviodrawer;

import java.io.File;
import java.util.LinkedList;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MainApp extends Application {
    Group root = new Group();
    private LinkedList<Shapes> AllShapes = new LinkedList<>();
    Class<?> c =  null, s = null;
    MControl MControl = new MControl();
    
    @Override
    public void start(Stage arg0) throws Exception {
    //    Parent root = FXMLLoader.load(getClass().getResource("/fxml/Scene.fxml"));
        
    //    Scene scene = new Scene(root, 800, 600);
     //   scene.getStylesheets().add("/styles/Styles.css");
        
        final Stage window;
        window = arg0;
        
        window.setTitle("JavaFX");
        BorderPane layout = new BorderPane();
        Scene scene = new Scene(layout, 900, 650);
        scene.setFill(Color.OLDLACE);
        window.setScene(scene);
        
        // --- Add Menu bar
        MenuBar menuBar = new MenuBar();

        // --- Menu File
        Menu menuFile = new Menu("File");
        MenuItem New = new MenuItem("New ... ");
        
        New.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                System.out.println("Button New is pressed");
        //        CheckBeforeOpen();
            }
        });
        
        MenuItem Open = new MenuItem("Open ...");
        
        Open.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                System.out.println("Button Open is pressed");
                FileChooser fileChooser = new FileChooser();
                fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("TXT Files", "*.txt"),
                        new FileChooser.ExtensionFilter("JSON Files", "*.json"));
                File file = fileChooser.showOpenDialog(window);
                try {
                    root = MControl.fileOpen(file, root, AllShapes, c, s);
                } catch (Exception e) {
                    
                }
            }
        });        
        
        MenuItem Save = new MenuItem("Save");
        
        Save.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                System.out.println("Button Save is pressed");
//                save();
            }
        });
                
        MenuItem Exit = new MenuItem("Exit");
        
        Exit.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent arg0) {
                System.out.println("Button Exit is pressed");
                System.exit(0);
            }
        });
        
        menuFile.getItems().addAll(New, Open, Save, new SeparatorMenuItem(), Exit);
        
        menuBar.getMenus().addAll(menuFile);
        layout.setTop(menuBar);
        
        window.show();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
