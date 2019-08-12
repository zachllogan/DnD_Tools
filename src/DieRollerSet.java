import java.io.Serializable;
import java.util.LinkedHashMap;

public class DieRollerSet implements Serializable
{
    public String name;
    private DieRoller[] dieRollers;

    public DieRollerSet(DieRoller[] dieRollers)
    {
        this.dieRollers = dieRollers;
    }

    public LinkedHashMap<String, Integer> roll()
    {
        LinkedHashMap<String, Integer> results = new LinkedHashMap<>();
        for(int i = 0; i<dieRollers.length; i++)
        {
            results.put(dieRollers[i].name, dieRollers[i].roll());
        }

        return  results;
    }
}
