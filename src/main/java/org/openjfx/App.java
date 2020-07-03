package org.openjfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Financial Analysis");
        primaryStage.setWidth(500);
        primaryStage.setHeight(500);

        primaryStage.setScene(StockTable.tableRefresh());
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch();
    }

    static void setRoot(String fxml) throws IOException {
        App scene = null;
        scene.setRoot(loadFXML(fxml));
    }

    private static String loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

}