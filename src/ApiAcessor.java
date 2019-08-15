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

    public static ApiMonster getMonster(String slug)
    {
        String json = request("monsters/" + slug + "?format=json");
        json = json.replaceFirst("\"actions\":\"\",", "");
        json = json.replaceFirst("\"reactions\":\"\",", "");
        json = json.replaceFirst("\"lengendary_actions\":\"\",", "");
        json = json.replaceFirst("\"special_abilities\":\"\",", "");
        ApiMonster monster = new Gson().fromJson(json, ApiMonster.class);
        return monster;
    }

    public static ApiSpell getSpell(String slug)
    {
        ApiSpell spell = new Gson().fromJson(request("spells/" + slug + "?format=json"), ApiSpell.class);
        return spell;
    }

    public static ApiWeapon getWeapon(String slug)
    {
        String json = request("weapons/" + slug + "?format=json");
        json = json.replaceFirst("\"properties\":\"\",", "");
        ApiWeapon weapon = new Gson().fromJson(json, ApiWeapon.class);
        return weapon;
    }

    public static ApiMagicItem getMagicItem(String slug)
    {
        ApiMagicItem magicItem = new Gson().fromJson(request("magicitems/" + slug + "?format=json"), ApiMagicItem.class);
        return magicItem;
    }
}
