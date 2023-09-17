package edu.jsu.mcis.cs310;

import com.github.cliftonlabs.json_simple.*;
import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVParserWriter;
import com.opencsv.RFC4180Parser;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;



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
    public static String csvToJson(String csvString) throws Exception {
        
        JsonArray jsonArray = new JsonArray();
        
        CSVReader csvReader = new CSVReader(new StringReader(csvString));
        
        String[] header = csvReader.readNext();
       
        if (header != null) {
        String[] line;
        while ((line = csvReader.readNext()) != null) {
            JsonObject jsonObject = new JsonObject();
            for (int i = 0; i < header.length; i++) {
                jsonObject.put(header[i], line[i]);
            }
            jsonArray.add(jsonObject);
        }
    }
    
    return jsonArray.getString(0);
  }
    @SuppressWarnings("unchecked")
    public static String jsonToCsv(String jsonString) throws Exception {
    JsonArray jsonArray = (JsonArray) Jsoner.deserialize(jsonString);
    
    StringWriter writer = new StringWriter();
        try (CSVWriter csvWriter = new CSVWriter(writer)) {
            JsonObject firstObject = (JsonObject) jsonArray.get(0);
            String[] header = new String[firstObject.size()];
            int index = 0;
            for (Object key : firstObject.keySet()) {
                header[index++] = key.toString();
            }
            csvWriter.writeNext(header);
            
            
            for (Object obj : jsonArray) {
                JsonObject jsonObject = (JsonObject) obj;
                String[] data = new String[header.length];
                for (int i = 0; i < header.length; i++) {
                    data[i] = jsonObject.get(header[i]).toString();
                }
                csvWriter.writeNext(data);
            }   } 
    
    return writer.toString();
}
}
        