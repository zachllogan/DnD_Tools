import javax.swing.*;
import java.io.Serializable;

public abstract class ApiThing implements Serializable
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
