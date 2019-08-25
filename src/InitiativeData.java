import java.io.Serializable;
import java.util.ArrayList;

public class InitiativeData implements Serializable
{
    private static final long serialVersionUID = 12645L;



    private ArrayList<DieRollerSet> dieRollerSets;

    public ArrayList<DieRollerSet> getDieRollerSets()
    {
        return dieRollerSets;
    }

    public void setDieRollerSets(ArrayList<DieRollerSet> dieRollerSets)
    {
        this.dieRollerSets = dieRollerSets;
    }


    private ArrayList<ApiThing> statBlocks;

    public ArrayList<ApiThing> getStatBlocks()
    {
        return statBlocks;
    }

    public void setStatBlocks(ArrayList<ApiThing> statBlocks)
    {
        this.statBlocks = statBlocks;
    }


    private boolean currentlyTurn;

    public boolean isCurrentlyTurn()
    {
        return currentlyTurn;
    }

    public void setCurrentlyTurn(boolean currentlyTurn)
    {
        this.currentlyTurn = currentlyTurn;
    }



    private int initiativeRoll;
    private int initiativeBonus;
    private int initiativeTotal;
    private boolean isPC;
    private boolean isActive;
    private String name;
    private int maxHP;
    private int currentHP;
    private String rollResults;

    public InitiativeData()
    {
    }

    public int getInitiativeRoll()
    {
        return initiativeRoll;
    }

    public void setInitiativeRoll(final int initiativeRoll)
    {
        this.initiativeRoll = initiativeRoll;
    }

    public int getInitiativeBonus()
    {
        return initiativeBonus;
    }

    public void setInitiativeBonus(final int initiativeBonus)
    {
        this.initiativeBonus = initiativeBonus;
    }

    public int getInitiativeTotal()
    {
        return initiativeTotal;
    }

    public void setInitiativeTotal(final int initiativeTotal)
    {
        this.initiativeTotal = initiativeTotal;
    }

    public boolean isIsPC()
    {
        return isPC;
    }

    public void setIsPC(final boolean isPC)
    {
        this.isPC = isPC;
    }

    public boolean isIsActive()
    {
        return isActive;
    }

    public void setIsActive(final boolean isActive)
    {
        this.isActive = isActive;
    }

    public String getName()
    {
        return name;
    }

    public void setName(final String name)
    {
        this.name = name;
    }

    public int getMaxHP()
    {
        return maxHP;
    }

    public void setMaxHP(final int maxHP)
    {
        this.maxHP = maxHP;
    }

    public int getCurrentHP()
    {
        return currentHP;
    }

    public void setCurrentHP(int currentHP)
    {
        this.currentHP = currentHP;
    }

    public String getRollResults()
    {
        return rollResults;
    }

    public void setRollResults(final String rollResults)
    {
        this.rollResults = rollResults;
    }
}