import java.io.*;
import java.net.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Set;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

public class Url_summary {
    public static void main(String[] args) throws ClientProtocolException, IOException {
        HttpClient client = new DefaultHttpClient();

        //HttpGet request = new HttpGet("http://api.intellexer.com/summarize?apikey=bbbfb162-ee2e-4c5b-8945-45ded0377a98&url=http%3A%2F%2Fwww.hillaryclinton.com/issues/&summaryRestriction=7&returnedTopicsCount=2&loadConceptsTree=true&loadNamedEntityTree=true&usePercentRestriction=true&conceptsRestriction=7&structure=general&fullTextTrees=true&textStreamLength=1000&useCache=false&wrapConcepts=true");
        HttpGet request = new HttpGet("http://api.intellexer.com/summarize?apikey=bbbfb162-ee2e-4c5b-8945-45ded0377a98&url=http%3A%2F%2Ftwitter.com/HillaryClinton/&summaryRestriction=7&returnedTopicsCount=2&loadConceptsTree=true&loadNamedEntityTree=true&usePercentRestriction=true&conceptsRestriction=7&structure=general&fullTextTrees=true&textStreamLength=1000&useCache=false&wrapConcepts=true");
        HttpResponse response = client.execute(request);
        BufferedReader rd = new BufferedReader (new InputStreamReader(response.getEntity().getContent()));
        String line = " ";
        StringBuilder s = new StringBuilder();
        while ((line = rd.readLine()) != null) {
            //System.out.println(line);
            s.append(line);
        }
        String output = s.toString();
        JSONObject j = new JSONObject(output);
        JSONArray jArray = j.getJSONArray("items");

        String result = "";

        for (int i = 0;i < jArray.length();i++) {
            result = result + jArray.optJSONObject(i).getString("text")+"\n";
        }
        System.out.println("Important summary the URL:"+"\n");
        System.out.println( result);
        /*
        System.out.println("Important  words collected from the URL:"+"\n");
        JSONArray jArray2 = j.getJSONObject("conceptTree").getJSONArray("children");
        String result2 = "";

        for (int i = 0;i < jArray2.length();i++) {
            result2 = result2 + jArray2.optJSONObject(i).getString("text")+"\n";

        }
        System.out.println( result2);
*/
    }
}