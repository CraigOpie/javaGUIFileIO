/**
 * <h1>GUI File IO</h1>
 * This class demonstrates in depth understanding of creating a JavaFX GUI that handles File IO.
 *
 * @author  Craig Opie
 * @version 1.0, 11/02/19
 * @class   GUIFileIO
 * @concept The core concept for this lesson is the ability to perform file I/O using JavaFX.
 *
 */
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class GUIFileIO extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) {

        TextArea text_ = new TextArea();
        text_.setWrapText(true);

        MenuBar menuBar = new MenuBar();
        Menu file = new Menu("File");

        MenuItem open = new MenuItem("Open");
        open.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                System.out.println("Open Command");
                text_.setText(OpenFile());
            }
        });

        MenuItem save = new MenuItem("Save");
        save.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                System.out.println("Save Command");
                SaveFile(text_.getText());
            }
        });

        file.getItems().addAll(open, save);

        menuBar.getMenus().add(file);
        final String os = System.getProperty("os.name");
        if (os != null && os.startsWith("Win")) {
            menuBar.setUseSystemMenuBar(true);;
        }

        VBox layout = new VBox();
        layout.getChildren().addAll(menuBar, text_);
        layout.setFillWidth(true);

        Scene scene = new Scene(layout, 800, 600);
        text_.prefWidthProperty().bind(scene.widthProperty());
        text_.prefHeightProperty().bind(scene.heightProperty());

        stage.setTitle("Text Editor");
        stage.setScene(scene);
        stage.show();
    }

    private String OpenFile() {
        String content = "";
        FileChooser fc = new FileChooser();
        File fileToOpen = fc.showOpenDialog(null);
        if ( fileToOpen != null ) {
            StringBuffer sb = new StringBuffer();
            try ( FileInputStream fis = new FileInputStream(fileToOpen);
                  BufferedInputStream bis = new BufferedInputStream(fis) ) {
                while ( bis.available() > 0 ) {
                    sb.append((char)bis.read());
                }
            }

            catch ( Exception e ) {
                System.out.println("File failed to open. ERROR: " + e.getLocalizedMessage());
                e.printStackTrace();
            }

            content = sb.toString();
        }

        return content;
    }

    private void SaveFile(String content) {
        FileChooser fc = new FileChooser();
        File fileToSave = fc.showSaveDialog(null);
        if ( fileToSave != null ) {
            try ( FileOutputStream fos = new FileOutputStream(fileToSave);
                  BufferedOutputStream bos = new BufferedOutputStream(fos) ) {
                bos.write(content.getBytes());
                bos.flush();
                System.out.println("File saved successfully.");
            }

            catch ( Exception e ) {
                System.out.println("File failed to save. ERROR: " + e.getLocalizedMessage());
                e.printStackTrace();
            }
        }
    }
}
