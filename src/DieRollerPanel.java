import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.text.NumberFormat;

public class DieRollerPanel
{
    private JTextField nameField;
    private JFormattedTextField dieField;
    private JFormattedTextField numField;
    private JFormattedTextField bonusField;
    private JFormattedTextField resultField;
    private JPanel panel;

    public JPanel getPanel()
    {
        return panel;
    }

    public DieRollerPanel()
    {
        panel.setMaximumSize(new Dimension(panel.getMaximumSize().width, panel.getPreferredSize().height));
    }

    private void createUIComponents()
    {
        dieField = new JFormattedTextField(new NumberFormatter(NumberFormat.getIntegerInstance()));
        numField = new JFormattedTextField(new NumberFormatter(NumberFormat.getIntegerInstance()));
        bonusField = new JFormattedTextField(new NumberFormatter(NumberFormat.getIntegerInstance()));
        resultField = new JFormattedTextField(new NumberFormatter(NumberFormat.getIntegerInstance()));

        dieField.setValue(0);
        numField.setValue(0);
        bonusField.setValue(0);
        resultField.setValue(0);
    }

    public String getName()
    {
        return nameField.getText();
    }

    public int getDie()
    {
        return ((Number)dieField.getValue()).intValue();
    }

    public int getNum()
    {
        return ((Number)numField.getValue()).intValue();
    }

    public int getBonus()
    {
        return ((Number)bonusField.getValue()).intValue();
    }

    public void setResultField(int value)
    {
        resultField.setValue(value);
    }
}
