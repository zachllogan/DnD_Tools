import java.util.ArrayList;

public class ApiSearch
{
    private ArrayList<SearchResult> results;

    public String[] getNames()
    {
        String[] names = new String[results.size()];
        SearchResult result;
        for(int i = 0; i<results.size(); i++)
        {
            result = results.get(i);
            names[i] = result.name;
        }
        return names;
    }

    public String getRoute(String name)
    {
        name = name.toLowerCase();
        for (int i = 0; i < results.size(); i++)
        {
            if(results.get(i).name.toLowerCase().equals(name))
            {
                return results.get(i).route;
            }
        }
        return null;
    }

    public String getSlug(String name)
    {
        name = name.toLowerCase();
        for (int i = 0; i < results.size(); i++)
        {
            if(results.get(i).name.toLowerCase().equals(name))
            {
                return results.get(i).slug;
            }
        }
        return null;
    }

    public void removeUnused()
    {
        String route;
        for(int i = 0; i<results.size(); i++)
        {
            route = results.get(i).route;
            if(!(route.equals("spells/") || route.equals("monsters/") || route.equals("magicitems/") || route.equals("weapons/")))
            {
                results.remove(i);
                i--;
            }
        }
    }

    private static class SearchResult
    {
        private String name;
        private String route;
        private String slug;
    }
}
