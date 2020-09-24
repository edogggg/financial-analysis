package org.openjfx;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class StockTable {

    public static ArrayList<String> tickers = new ArrayList<>();

    public static final String symbolKey = "symbol";
    public static final String pEKey = "peExclExtraTTM";
    public static final String divCoverKey = "dividendCover";
    public static final String currentRatioKey = "currentRatioAnnual";
    public static final String fCFKey = "freeCashFlowPerShareTTM";

    public static final String deltaKey = "deltaOfTheDelta";

//    public static final String bookValKey = "bookValuePerShareQuarterly";
//    public static final String bookValueShareGrowth5YKey = "bookValueShareGrowth5Y";
//    public static final String cashFlowPerShareTTM = "cashFlowPerShareTTM";
//    public static final String costToCashReturn = "currentEv/freeCashFlowTTM";
//    public static final String dividendGrowthRate5Y = "dividendGrowthRate5Y";
//    public static final String
//    public static final String
//    public static final String
//    public static final String
//    public static final String

    public static TableView<String> stockTable() throws IOException {

        tickers.add("AAPL");
        tickers.add("uber");

//        tickers.add("AMZN");
//        tickers.add("III");
//        tickers.add("FB");
//        tickers.add("MSFT");
//        tickers.add("GOOGl");
//        tickers.add("tsla");
//        tickers.add("NFLX");



        TableView table_view = new TableView<>(CompanyData.metrics(tickers));
        TableColumn<Map, String> symbol = new TableColumn<>("Symbol");
        symbol.setCellValueFactory(
                new MapValueFactory<>(symbolKey)
        );
        table_view.getColumns().add(symbol);

        TableColumn<Map, Double> pERatio = new TableColumn<>("P/E");
        pERatio.setCellValueFactory(
                new MapValueFactory<>(pEKey)
        );
        table_view.getColumns().add(pERatio);

        TableColumn<Map, Double> dividendCover = new TableColumn<>("Dividend cover(TTM)");
        dividendCover.setCellValueFactory(
                new MapValueFactory<>(divCoverKey)
        );
        table_view.getColumns().add(dividendCover);

        TableColumn<Map, Double> deltaOfDelta = new TableColumn<>("Delta of the Delta");
        deltaOfDelta.setCellValueFactory(
                new MapValueFactory<>(deltaKey)
        );
        table_view.getColumns().add(deltaOfDelta);

        TableColumn<Map, Double> currentRatio = new TableColumn<>("Current Ratio (Annual)");
        currentRatio.setCellValueFactory(
                new MapValueFactory<>(currentRatioKey)
        );
        table_view.getColumns().add(currentRatio);

        TableColumn<Map, Double> freeCashFlow = new TableColumn<>("FCFlow per share (TTM)");
        freeCashFlow.setCellValueFactory(
                new MapValueFactory<>(fCFKey)
        );
        table_view.getColumns().add(freeCashFlow);


//          Adds columns##### possibly remove all and add items to refresh page??
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
            tickers.add(tickerInput.getText());
            try {
                stockTable().refresh();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        HBox hbox = new HBox(tickerInput, getMetrics);
        VBox vbox = new VBox(stockTable(), hbox);
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10,0,0,10));
        return new Scene(vbox);
    }
}