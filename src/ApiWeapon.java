import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.MatteBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;

public class ApiWeapon extends ApiThing
{
    private String category;
    private String cost;
    private String damage_dice;
    private String damage_type;
    private String weight;
    private String[] properties;

    public void fillTextPane(JTextPane textPane)
    {
        Color paperColor = new Color(241, 228, 197);
        Color paperEndsColor = new Color(217, 159, 43);

        textPane.setEditable(false);
        textPane.setBackground(paperColor);
        textPane.setBorder(new CompoundBorder(new MatteBorder(5, 0, 5, 0, paperEndsColor), new MatteBorder(0, 5, 0, 5, paperColor)));
        textPane.setFont(new Font("Georgia",0,12));

        StyledDocument doc = textPane.getStyledDocument();
        final Color textColor =  new Color(88, 23, 13);

        Style title = doc.addStyle("title", null);
        StyleConstants.setForeground(title, textColor);
        StyleConstants.setFontSize(title, 30);
        StyleConstants.setFontFamily(title, "Times New Roman");
        StyleConstants.setBold(title, true);

        Style header = doc.addStyle("header", null);
        StyleConstants.setForeground(header, textColor);
        StyleConstants.setFontSize(header, 15);
        StyleConstants.setBold(header, true);

        Style standard = doc.addStyle("standard", null);
        StyleConstants.setForeground(standard, textColor);
        StyleConstants.setFontSize(standard, 15);

        Style italic = doc.addStyle("italic", null);
        StyleConstants.setForeground(italic, textColor);
        StyleConstants.setFontSize(italic, 15);
        StyleConstants.setItalic(italic, true);

        try
        {
            doc.insertString(doc.getLength(), name.toUpperCase(), title);
            doc.insertString(doc.getLength(), "\n " + category.substring(0, category.length()-1), italic);
            doc.insertString(doc.getLength(), "\n\nDamage: ", header);
            doc.insertString(doc.getLength(), damage_dice, standard);
            doc.insertString(doc.getLength(), "\nDamage Type: ", header);
            doc.insertString(doc.getLength(), damage_type.substring(0,1).toUpperCase() + damage_type.substring(1), standard);
            if(properties.length != 0)
            {
                doc.insertString(doc.getLength(), "\nProperties: ", header);
                for (int i = 0; i < properties.length; i++)
                {
                    doc.insertString(doc.getLength(), properties[i] + (i!=properties.length-1 ? ", " : ""), standard);
                }
            }
            doc.insertString(doc.getLength(), "\nWeight: ", header);
            doc.insertString(doc.getLength(), weight, standard);
            doc.insertString(doc.getLength(), "\nCost: ", header);
            doc.insertString(doc.getLength(), cost, standard);
        } catch (BadLocationException e) { e.printStackTrace(); }
    }
}
