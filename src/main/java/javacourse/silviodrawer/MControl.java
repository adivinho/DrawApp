/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javacourse.silviodrawer;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Stack;
import javafx.scene.Group;

/**
 *
 * @author silvo
 */
class MControl {
    @SuppressWarnings("rawtypes")
    public Group fileOpen(File file, Group root, LinkedList<Shapes> AllShapes, 
            Class c, Class s) throws Exception {
        // Checked extension
        int i = file.getAbsolutePath().lastIndexOf('.');
        if (i > 0) {
            String extension = file.getAbsolutePath().substring(i + 1);
            System.out.println(file);
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
    
    public void fileSave(final LinkedList<Shapes> allShapes, final File file) throws IOException {
        String extension = "";
        int i = file.getAbsolutePath().lastIndexOf('.');
        if (i > 0) {
            extension = file.getAbsolutePath().substring(i + 1);
        }
            JSON jsonFile = new JSON();
            jsonFile.saveJSON(allShapes, file);
    }
}
