package edu.jsu.mcis.cs310;

import com.github.cliftonlabs.json_simple.*;
import com.opencsv.*;
import com.opencsv.CSVParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
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
        
        JSONObject jsonResult = new JSONObject();
        
       // default return value; replace later!
        
        try {
            CSVParser csvParser = new CSVParser();
            List<String[]> csvData = csvParser.parseAll(csvString);
            
            String[] headers = csvData.get(0);
            
            JSONArray prodnums = new JSONArray();
            JSONArray colheadings = new JSONArray();
            JSONArray data = new JSONArray();
            
            for(int i = 1; i < csvData.size(); i++){
                String[] row = csvData.get(i);
                JSONObject rowData = new JSONObject();
                
                for (int j = 0; j < headers.length; j++){
                    if (j == 0){
                        prodnums.add(row[j]);
                    } else if (j == 2 || j == 3){
                        rowData.put(headers[j], Integer.parseInt(row[j]));
                    } else {
                        rowData.put(headers[j], row[j]);
                    }
                }
                data.add(rowData);
            }
            
            jsonResult.put("prodnums", prodnums);
            jsonResult.put("colheadings", colheadings);
            jsonResult.put("Data", data);
     
            // INSERT YOUR CODE HERE
            
        }
        catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        
        return jsonResult.toJSONString();
        
    }
    
    @SuppressWarnings("unchecked")
    public static String jsonToCsv(String jsonString) {
        
        JSONParser jsonParser = new JSONParser();
        StringBuilder csvBuilder = new StringBuilder();
        // default return value; replace later!
        
        try {
            JSONObject jsonObject = (JSONOject) jsonParser.parse(jsonString);
            
            JSONArray prodnums = (JSONArray) jsonObject.get("Prodnums");
            JSONArray colheadings = (JSONArray) jsonObject.get("colheadings");
            JSONArray data = (JSONArray) jsonObject.get("Data");
            
            for (int i = 0; i < colheadings.size(); i++){
                csvBuilder.append(colheadings.get(i));
                if (i < colheadings.size() - 1){
                    csvBuilder.append(",");
                }
            }
            csvBuilder.append("\n");
            
            
            for (int i = 0; i < data.size(); i++){
                JSONObject rowData = (JSONObject) data.get(i);
                for (int j = 0; j < colheadings.size(); j++){
                    csvBuilder.append(rowData.get(colheadings.get(j)));
                    if (j < colheadings.size() - 1){
                        csvBuilder.append(",");
                    }
                }
                csvBuilder.append("\n");
            }
            
             
            // INSERT YOUR CODE HERE
            
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        
        return csvBuilder.toString();
        
    }
    
}
