package edu.jsu.mcis.cs310;

import com.github.cliftonlabs.json_simple.*;
import com.opencsv.*;
import com.opencsv.CSVParser.*;
import com.github.cliftonlabs.json_simple.JsonArray.*;
import com.github.cliftonlabs.json_simple.JsonObject.*;
import com.github.cliftonlabs.json_simple.JsonException.*;
import com.opencsv.CSVReader.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class Converter {
    
    /*
        
        Consider the following CSV data, a portion of a database of episodes of
        the classic "Star Trek" television series:
        
        "ProdNum","Title","Season","Episode","Stardate","OriginalAirdate","RemasteredAirdate"
        "6149-02","Where No Man Has Gone Before","1","01","1312.4 - 1313.8","9/22/1966","1/20/2007"
        "6149-03","The Corbomite Maneuver","1","02","1512.2 - 1514.1","11/10/1966","12/9/2006"
        
        (For brevity, only the header row plus the first two episodes are shown
        in this sample.)
    
        The corresponding JSON data would be similar to the following; tabs and
        other whitespace have been added for clarity.  Note the curly braces,
        square brackets, and double-quotes!  These indicate which values should
        be encoded as strings and which values should be encoded as integers, as
        well as the overall structure of the data:
        
        {
            "ProdNums": [
                "6149-02",
                "6149-03"
            ],
            "ColHeadings": [
                "ProdNum",
                "Title",
                "Season",
                "Episode",
                "Stardate",
                "OriginalAirdate",
                "RemasteredAirdate"
            ],
            "Data": [
                [
                    "Where No Man Has Gone Before",
                    1,
                    1,
                    "1312.4 - 1313.8",
                    "9/22/1966",
                    "1/20/2007"
                ],
                [
                    "The Corbomite Maneuver",
                    1,
                    2,
                    "1512.2 - 1514.1",
                    "11/10/1966",
                    "12/9/2006"
                ]
            ]
        }
        
        Your task for this program is to complete the two conversion methods in
        this class, "csvToJson()" and "jsonToCsv()", so that the CSV data shown
        above can be converted to JSON format, and vice-versa.  Both methods
        should return the converted data as strings, but the strings do not need
        to include the newlines and whitespace shown in the examples; again,
        this whitespace has been added only for clarity.
        
        NOTE: YOU SHOULD NOT WRITE ANY CODE WHICH MANUALLY COMPOSES THE OUTPUT
        STRINGS!!!  Leave ALL string conversion to the two data conversion
        libraries we have discussed, OpenCSV and json-simple.  See the "Data
        Exchange" lecture notes for more details, including examples.
        
    */
    
    @SuppressWarnings("unchecked")
    public static String csvToJson(String csvString) {
        
        JsonObject jsonResult = new JsonObject();
        
        try {
            CSVParser csvParser = new CSVParser();
            List<String[]> csvData = csvParser.parseLineMulti(csvString);
            
            String[] headers = csvData.get(0);
            
            JsonArray prodNums = new JsonArray();
            JsonArray colHeadings = new JsonArray();
            JsonArray data = new JsonArray();
            
            for(int i = 1; i < csvData.size(); i++){
                String[] row = csvData.get(i);
                JsonObject rowData = new JsonObject();
                
                for (int j = 0; j < headers.length; j++){
                    if (j == 0){
                        prodNums.add(row[j]);
                    } else if (j == 2 || j == 3){
                        rowData.put(headers[j], Integer.valueOf(row[j]));
                    } else {
                        rowData.put(headers[j], row[j]);
                    }
                }
                data.add(rowData);
            }
            
            jsonResult.put("prodNums", prodNums);
            jsonResult.put("colHeadings", colHeadings);
            jsonResult.put("Data", data);
     
          
            
        } catch (IOException | JsonException e) {
            e.printStackTrace();
        }
        
        return jsonResult.toString();
        
    }
    
    @SuppressWarnings("unchecked")
    public static String jsonToCsv(String jsonString) {
        
        JsonParser JsonParser = new JsonParser();
        StringBuilder csvBuilder = new StringBuilder();
        // default return value; replace later!
        
        try {
            JsonObject jsonObject = (JsonObject) jsonParser.parse(jsonString);
            
            JsonArray prodNums = (JsonArray) jsonObject.get("ProdNums");
            JsonArray colHeadings = (JsonArray) jsonObject.get("colHeadings");
            JsonArray data = (JsonArray) jsonObject.get("Data");
            
            for (int i = 0; i < colHeadings.size(); i++){
                csvBuilder.append(colHeadings.get(i));
                if (i < colHeadings.size() - 1){
                    csvBuilder.append(",");
                }
            }
            csvBuilder.append("\n");
            
            
            for (int i = 0; i < data.size(); i++){
                JsonObject rowData = (JsonObject) data.get(i);
                for (int j = 0; j < colHeadings.size(); j++){
                    csvBuilder.append(rowData.get(colHeadings.get(j)));
                    if (j < colHeadings.size() - 1){
                        csvBuilder.append(",");
                    }
                }
                csvBuilder.append("\n");
            }
            
             
           
            
        } catch (JsonException e) {
            e.printStackTrace();
        }
        
        return csvBuilder.toString();
        
    }
    
}
