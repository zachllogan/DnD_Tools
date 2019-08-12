import java.io.Serializable;

public class DieRoller implements Serializable
{
    public String name;
    private int die;
    private int num;
    private int bonus;
    private int result;

    public DieRoller(int die, int num, int bonus)
    {
        this.die = die;
        this.num = num;
        this.bonus = bonus;
    }

    public int roll()
    {
        result = bonus;
        if(die == 0 || num == 0)
        {
            return result;
        }
        for(int i = 0; i<num; i++)
        {
            result += Math.random()*die+1;
        }
        return result;
    }

    public int getResult()
    {
        return result;
    }
}
