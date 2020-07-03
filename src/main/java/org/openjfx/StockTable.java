package org.openjfx;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javafx.util.StringConverter;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;



public class StockTable {

    public static ArrayList<String> tickers = new ArrayList<>();
    public static ArrayList<String> metric = new ArrayList<>();

    public static TableView<String> stockTable() throws IOException {

//        These are the column names and where the keys are passed into the map
        //for ('keys'  iterate keys) { add table column }
//        This is where the keys are passed into the map
        tickers.add("AAPL");
        tickers.add("AMZN");
        tickers.add("III");

        TableColumn<Map, String> symbol = new TableColumn<>("Symbol");
        String sym = "symbol";
        symbol.setCellValueFactory(
                new MapValueFactory<>(sym)
        );
        TableColumn<Map, String> pERatio = new TableColumn<>("P/E");
        String pEKey = "peNormalizedAnnual";
        metric.add(pEKey);
        pERatio.setCellValueFactory(
                new MapValueFactory<>(pEKey)
        );
        TableColumn<Map, String> dividendCover = new TableColumn<>("Dividend cover(TTM)");
        //        This generates the table and calls the generate data method
        String divCover = "dividendCover";
        metric.add(divCover);
        dividendCover.setCellValueFactory(
                new MapValueFactory<>(divCover)
        );
        TableColumn<Map, String> currentRatio = new TableColumn<>("Current Ratio (Annual)");
        String currentR = "currentRatioAnnual";
        metric.add(currentR);
        currentRatio.setCellValueFactory(
                new MapValueFactory<>(currentR)
        );
//        TableColumn<Map, String> currentRatio = new TableColumn<>("Current Ratio (Annual)");
//        String currentR = "currentRatioAnnual";
//        metric.add(currentR);
//        currentRatio.setCellValueFactory(
//                new MapValueFactory<>(currentR)
//        );





        TableView table_view = new TableView<>(CompanyData.metrics(tickers, metric));

//          Adds columns
        table_view.getColumns().addAll(symbol, pERatio, dividendCover, currentRatio );

        return table_view;
    }


//        Callback method
//        Callback<TableColumn<Map, String>, TableCell<Map, String>>
//                cellFactoryForMap = p -> new TextFieldTableCell(new StringConverter() {
//            @Override
//            public String toString(Object t) {
//                return t.toString();
//            }
//
//            @Override
//            public Object fromString(String string) {
//                return string;
//            }
//        });
//        actual.setCellFactory(cellFactoryForMap);
//        estimate.setCellFactory(cellFactoryForMap);
//        period.setCellFactory(cellFactoryForMap);
//        symbol.setCellFactory(cellFactoryForMap);

    public static Scene tableRefresh() throws IOException {

        TextField tickerInput = new TextField();
        tickerInput.setPromptText("e.g \"AAPL\", \"AMZN\"");

        Button getMetrics = new Button("Get metrics");
        getMetrics.setOnAction(action -> {
            metric.add(tickerInput.getText());

        });
        HBox hbox = new HBox(tickerInput, getMetrics);
        VBox vbox = new VBox(stockTable(), hbox);
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10,0,0,10));
        return new Scene(vbox);
    }
}