package org.openjfx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;



public class CompanyData {

    public static String readUrl(String urlString) throws IOException {
        BufferedReader reader = null;
        try {
            URL url = new URL(urlString);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read);

            return buffer.toString();
        } finally {
            if (reader != null)
                reader.close();
        }
    }

    final static String jSONObjectKeyMetric = "metric";
    final static String jSONArrayKeyData = "data";
    final static String jSONObjectKeyYear = "year";
    final static String jSONObjectKeyQuarter = "quarter";
    final static String jSONObjectKeyReport = "report";
    final static String balanceSheetKey = "bs";
    final static String cashFlowKey = "cf";
    final static String incomeStatementKey = "ic";
    final static String labelKey ="label";
    final static String valueKey =  "value";
    final static String descriptionKey = "description";
    final static String urlInitial = "https://finnhub.io/api/v1/stock/";
    final static String symbolURL = "symbol?exchange=";
    final static String peerURLPart = "peers?symbol=";
    final static String metricURL = "metric?symbol=";
    final static String metricURL2 = "&metric=all";
    final static String financialsReported = "financials-reported?symbol=";
    final static String financialsFrequency = "&freq=quarterly";
    final static String apiKey = "&token=bqsjk1frh5rc3vfds0ug";
    final static Double toPercent = 100.00;

    public static ObservableList<Map> metrics(ArrayList<String> tickers) throws IOException {

//Returned observable list
        ObservableList<Map> metricList = FXCollections.observableArrayList();

//Iterating through the urls by ticker
            for (String ticker : tickers) {
                String earningsURL = urlInitial + metricURL + ticker + metricURL2 + apiKey;
                JSONObject metricOBJ = new JSONObject(readUrl(earningsURL));
//                String quarterlyURL = urlInitial + financialsReported + ticker + financialsFrequency + apiKey;
//                JSONObject quarterlyOBJ = new JSONObject(readUrl(quarterlyURL));

//Creating the data row for the ticker data
 //               Map<String, String> stringRow = new HashMap<>();
//                adding the first column in the row; ticker

//Retrieving the metric object
                JSONObject metric = metricOBJ.getJSONObject(jSONObjectKeyMetric);
//                JSONArray dataArray = quarterlyOBJ.getJSONArray(jSONArrayKeyData);

////Iterating through the keys and assigning values to them in the map

                String companySymbol = metricOBJ.getString(StockTable.symbolKey);
                Map doubleData = new HashMap<>();
                doubleData.put(StockTable.symbolKey, companySymbol);
                doubleData.put(StockTable.pEKey, getMetric(StockTable.pEKey, metric));
                doubleData.put(StockTable.currentRatioKey, getMetric(StockTable.currentRatioKey, metric));
                doubleData.put(StockTable.fCFKey, getMetric(StockTable.fCFKey, metric));
//                doubleData.put(StockTable.fCFKey, getMetric(StockTable.fCFKey, metric));
//                doubleData.put(StockTable.fCFKey, getMetric(StockTable.fCFKey, metric));
//                doubleData.put(StockTable.fCFKey, getMetric(StockTable.fCFKey, metric));
//                doubleData.put(StockTable.fCFKey, getMetric(StockTable.fCFKey, metric));
//                doubleData.put(StockTable.fCFKey, getMetric(StockTable.fCFKey, metric));
//                doubleData.put(StockTable.fCFKey, getMetric(StockTable.fCFKey, metric));

                doubleData.put(StockTable.deltaKey, deltaOfTheDeltaMetric(metric));
                doubleData.put(StockTable.divCoverKey, dividendCoverTTM(metric));



//                    int year = dataArray.optInt(Integer.parseInt(jSONObjectKeyYear));
//                    int quarter = dataArray.getInt(Integer.parseInt(jSONObjectKeyQuarter));
//                    JSONObject reportOBJ = dataArray.getJSONObject(Integer.parseInt(jSONObjectKeyReport));/*Possibly an issue*/
//
//                    JSONArray balanceSheet = reportOBJ.getJSONArray(balanceSheetKey);
//                    for (Object element : balanceSheet){
//                        element.toString()
//
//                        String metricVal = metric.optString(String.valueOf(element));
//                        stringRow.put(key, String.valueOf(metricVal));
//                    }

//                    JSONArray cashFlow = reportOBJ.getJSONArray(cashFlowKey);
//                    JSONArray incomeStatement = reportOBJ.getJSONArray(incomeStatementKey);


//                }


//Adding a row to the observable list
                metricList.add(doubleData);
            }
//        }
        return metricList;
    }
//    public static JSONArray getPeers(ArrayList<String> peersRequest) throws IOException {
//        String peersURL = urlInitial + peerURLPart + peersRequest + apiKey;
//        JSONArray peersArray = new JSONArray(readUrl(peersURL));
//        return peersArray;
//    }
//    public static boolean tickerChecker (String ticker) {
//        if
//        String tickersURL = urlInitial + symbolURL + exchange + apiKey;
//        JSONArray tickerArray = new JSONArray(readUrl(tickersURL));
//        Map<String, String> dataRow = new HashMap<>();
//        for (int i = 0; i < tickerArray.length(); i++) {
//            JSONObject item = tickerArray.getJSONObject(i);
//            String symbol = item.optString(symbolKey);
//            String description = item.optString(descriptionKey);
//            dataRow.put(description, symbol);
//            metricList.add(dataRow);
//        }
//        return metricList
//    }

    public static Double getMetric(String key, JSONObject metric){
        Double metricVal = metric.optDouble(key);
//        metricVal = rounding(metricVal);
        return metricVal;
    }

public static Double dividendCoverTTM(JSONObject metric){
    Double cashPerShare = metric.optDouble("freeCashFlowPerShareTTM");
    Double divPerShare = metric.optDouble("dividendsPerShareTTM");
    return cashPerShare / divPerShare;
}
    public static Double deltaOfTheDeltaMetric(JSONObject metric){
        Double epsNormalizedAnnual = metric.optDouble("epsNormalizedAnnual");
        Double epsGrowthTTMYoy = metric.optDouble("epsGrowthTTMYoy");
        Double peNormalizedAnnual = metric.optDouble(" peNormalizedAnnual");

        Double oneYearPredictedEPS = (epsNormalizedAnnual * (epsGrowthTTMYoy / toPercent)) + (epsNormalizedAnnual);
        Double twoYearPredictedEPS = (oneYearPredictedEPS * (epsGrowthTTMYoy / toPercent)) + (oneYearPredictedEPS);
        Double threeYearPredictedEPS = (twoYearPredictedEPS * (epsGrowthTTMYoy / toPercent)) + (twoYearPredictedEPS);
        Double fourYearPredictedEPS = (threeYearPredictedEPS * (epsGrowthTTMYoy / toPercent)) + (threeYearPredictedEPS);
        Double fiveYearPredictedEPS = (fourYearPredictedEPS * (epsGrowthTTMYoy / toPercent)) + (fourYearPredictedEPS);

        return oneYearPredictedEPS * peNormalizedAnnual;
    }


public static Double rounding(Double metric){
    BigDecimal bd = BigDecimal.valueOf(metric);
    bd = bd.setScale(2, RoundingMode.HALF_UP);
    return bd.doubleValue();
}
}