import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.MatteBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;

public class ApiSpell
{
    private String name;
    private String desc;
    private String higher_level;
    private String range;
    private String components;
    private String material;
    private String ritual;
    private String duration;
    private String concentration;
    private String casting_time;
    private String level;
    private String school;
    private String dnd_class;

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
            doc.insertString(doc.getLength(), "\n " + level + " " + school + (ritual.equals("yes") ? " (ritual)\n" : "\n") , italic);
            doc.insertString(doc.getLength(), "\nCasting Time: ", header);
            doc.insertString(doc.getLength(), casting_time, standard);
            doc.insertString(doc.getLength(), "\nRange: ", header);
            doc.insertString(doc.getLength(), range, standard);
            doc.insertString(doc.getLength(), "\nComponents: ", header);
            doc.insertString(doc.getLength(), components + (!material.equals("") ? " (" + material + ")" : ""), standard);
            doc.insertString(doc.getLength(), "\nDuration: ", header);
            doc.insertString(doc.getLength(), (concentration.equals("yes") ? "Concentration, " : "") + duration, standard);
            doc.insertString(doc.getLength(), "\nClasses: ", header);
            doc.insertString(doc.getLength(), dnd_class, standard);
            doc.insertString(doc.getLength(), "\n" + desc, standard);
            if(!higher_level.equals(""))
            {
                doc.insertString(doc.getLength(), "\nAt Higher Levels: ", header);
                doc.insertString(doc.getLength(), higher_level, standard);
            }
        } catch (BadLocationException e) { e.printStackTrace(); }
    }
}
