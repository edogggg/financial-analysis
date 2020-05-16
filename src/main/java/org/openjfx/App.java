package org.openjfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.io.IOException;

import static org.openjfx.getApi.finnhubMetrics;


/**
 * JavaFX App
 */
public class App extends javafx.application.Application{

    private static Scene scene;

    @Override public void start(Stage primaryStage) throws Exception {

        String metDescription = finnhubMetrics("description");
        String metDisplaySymbol = finnhubMetrics("displaySymbol");
        String metSymbol = finnhubMetrics("symbol");
//
//            Label variableLabel = new Label();
//            variableLabel.setFont(new Font(10));
//            variableLabel.setText(metDescription + " " + metDisplaySymbol + " " + metSymbol);
//            variableLabel.setLayoutX(0);
//            variableLabel.setLayoutY(0);
//
//        Group group = new Group(variableLabel);
//        Scene scene = new Scene(group, 400, 300);
//        primaryStage.setScene(scene);
//        primaryStage.show();
//
//    }


        TableView tableView = new TableView();

        TableColumn<String, Stock> column1 = new TableColumn<>( "Description");
        column1.setCellValueFactory(new PropertyValueFactory<String, Stock>("description"));
        column1.setVisible(true);


        tableView.getColumns().add(column1);

                while (){
                    tableView.getItems().add(metDescription);
        }


        VBox vbox = new VBox(tableView);
        Scene scene = new Scene(vbox);
        scene = new Scene(vbox,640, 480);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static class Stock {

        private String displaySymbol = null;
        private String marketCap = null;

        public Stock() {
        }


        public Stock(String displaySymbol, String marketCap) {
            this.displaySymbol = displaySymbol;
            this.marketCap = marketCap;
        }

        public String getDisplaySymbol() {
            return displaySymbol;
        }

        public void setDisplaySymbol(String displaySymbol) {
            this.displaySymbol = displaySymbol;
        }

        public String getMarketCap() {
            return marketCap;
        }

        public void setMarketCap(String marketCap) {
            this.marketCap = marketCap;
        }
    }




    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}