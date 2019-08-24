import javax.swing.*;

public abstract class ApiThing
{
    protected String name;
    public abstract void fillTextPane(JTextPane textPane);

    public String getName()
    {
        return name;
    }

    @Override
    public String toString()
    {
        return name;
    }
}
