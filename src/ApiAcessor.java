import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.stream.Collectors;

public class ApiAcessor
{
    private static String request(String request)
    {
        System.setProperty("http.agent", "Mozilla/5.0");
        URLConnection connection = null;
        String text = "";
        try {
            connection =  new URL("https://api-beta.open5e.com/" + request).openConnection();
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            text = br.lines().collect(Collectors.joining(System.lineSeparator()));
            br.close();
        }catch ( Exception ex ) {
            ex.printStackTrace();
        }
        return text;
    }

    public static ApiSearch search(String query)
    {
        ApiSearch search = new Gson().fromJson(request("search/?format=json&name=" + query), ApiSearch.class);
        search.removeUnused();
        return search;
    }
}
