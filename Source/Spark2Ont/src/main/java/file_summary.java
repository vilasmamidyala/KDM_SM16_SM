/**
 * Created by vilas on 7/24/2016.
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;

public class file_summary {
    public static void main(String[] args) throws ClientProtocolException, IOException {
        FileReader f = new FileReader("data/Categories/Clinton/102");//103
        BufferedReader b = new BufferedReader(f);
        String output = "";
        while(b.readLine() != null) {
            output = output + b.readLine();
        }
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost("http://api.intellexer.com/clusterizeText?apikey=bbbfb162-ee2e-4c5b-8945-45ded0377a98&conceptsRestriction=10&fullTextTrees=true&loadSentences=true&wrapConcepts=true&textStreamLength=2222222222");
        StringEntity input = new StringEntity(output);
        post.setEntity(input);
        HttpResponse response = client.execute(post);
        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        StringBuilder s = new StringBuilder();
        String line =" ";
        while ((line = rd.readLine()) != null) {
            //System.out.println(line);
            s.append(line);
        }
        String output2 = s.toString();
        org.json.JSONObject j = new org.json.JSONObject(output2);
        JSONArray jArray = j.getJSONObject("conceptTree").getJSONArray("children");
        String result = "";

        for (int i = 0;i < jArray.length();i++) {
            result = result + jArray.optJSONObject(i).getString("text")+"\n";
        }
        System.out.println("Summarized words:");
        System.out.println( result);

    }
}