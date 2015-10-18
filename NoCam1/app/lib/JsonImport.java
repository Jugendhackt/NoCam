import java.io.FileReader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class JsonImport {
  public static void main(String[] args){
    JSONParser parser = new JSONParser();
    try 
    {
      Object obj = parser.parse(new FileReader("cctvs.json"));
      JSONObject jsonObject = (JSONObject) obj;
      
      jsonObject.get("cctvs");
      JSONArray cctvlist = (JSONArray) jsonObject.get("cctvs");
      
      for (int i = 0;i < cctvlist.size();i++) {
    	  JSONArray tuple = (JSONArray) cctvlist.get(i); 
    	  double latitude = (double) tuple.get(0);
    	  double longitude = (double) tuple.get(1);
    	  System.out.println(latitude + " ; " + longitude);
      }
      
      
    } catch(Exception e) {
      System.out.println(e);
    } finally {
      
    } // end of try
  }
}