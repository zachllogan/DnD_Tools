import com.sun.deploy.util.StringUtils;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.MatteBorder;
import javax.swing.text.*;
import java.awt.*;
import java.util.ArrayList;

public class ApiMonster
{
    private String name;
    private String size;
    private String type;
    private String subtype;
    private String alignment;
    private String armor_class;
    private String armor_desc;
    private String hit_points;
    private String hit_dice;
    private Movement speed;
    private String strength;
    private String dexterity;
    private String constitution;
    private String intelligence;
    private String wisdom;
    private String charisma;
    private String strength_save;
    private String dexterity_save;
    private String constitution_save;
    private String intelligence_save;
    private String wisdom_save;
    private String charisma_save;
    private String perception;
    private Skills skills;
    private String damage_vulnerabilities;
    private String damage_resistances;
    private String damage_immunities;
    private String condition_immunities;
    private String senses;
    private String languages;
    private String challenge_rating;
    private ArrayList<Action> actions;
    private ArrayList<Action> reactions;
    private String legendary_desc;
    private ArrayList<Action> legendary_actions;
    private ArrayList<Action> special_abilities;


    private static class Movement
    {
        private String walk;
        private String swim;
        private String fly;
        private String burrow;
        private String climb;
    }

    private static class Skills
    {
        private String athletics;
        private String acrobatics;
        private String sleight_of_hand;
        private String stealth;
        private String arcana;
        private String history;
        private String investigation;
        private String nature;
        private String religion;
        private String animal_handling;
        private String insight;
        private String medicine;
        private String perception;
        private String survival;
        private String deception;
        private String intimidation;
        private String performance;
        private String persuasion;

        private boolean allNull()
        {
            return athletics == null &&
            acrobatics == null &&
            sleight_of_hand == null &&
            stealth == null &&
            arcana == null &&
            history == null &&
            investigation == null &&
            nature == null &&
            religion == null &&
            animal_handling == null &&
            insight == null &&
            medicine == null &&
            perception == null &&
            survival == null &&
            deception == null &&
            intimidation == null &&
            performance == null &&
            persuasion == null;
        }

        public String toString()
        {

            String toReturn =
                    (athletics != null ? "Athletics "+athletics+", ": "") +
                    (acrobatics != null ? "Acrobatics "+acrobatics+", ": "") +
                    (sleight_of_hand != null ? "Sleight_of_hand "+sleight_of_hand+", ": "") +
                    (stealth != null ? "Stealth "+stealth+", ": "") +
                    (arcana != null ? "Arcana "+arcana+", ": "") +
                    (history != null ? "History "+history+", ": "") +
                    (investigation != null ? "Investigation "+investigation+", ": "") +
                    (nature != null ? "Nature "+nature+", ": "") +
                    (religion != null ? "Religion "+religion+", ": "") +
                    (animal_handling != null ? "Animal_handling "+animal_handling+", ": "") +
                    (insight != null ? "Insight "+insight+", ": "") +
                    (medicine != null ? "Medicine "+medicine+", ": "") +
                    (perception != null ? "Perception "+perception+", ": "") +
                    (survival != null ? "Survival "+survival+", ": "") +
                    (deception != null ? "Deception "+deception+", ": "") +
                    (intimidation != null ? "Intimidation "+intimidation+", ": "") +
                    (performance != null ? "Performance "+performance+", ": "") +
                    (persuasion != null ? "Persuasion "+persuasion+", ": "");
            return toReturn.substring(0, toReturn.length()-2);
        }
    }

    private static class Action
    {
        private String name;
        private String desc;
    }

    public void fillTextPane(JTextPane textPane)
    {
        final Color paperColor = new Color(241, 228, 197);
        final Color paperEndsColor = new Color(217, 159, 43);
        final Font georgiaFont = new Font("Georgia",0,12);

        textPane.setEditable(false);
        textPane.setBackground(paperColor);
        textPane.setBorder(new CompoundBorder(new MatteBorder(5, 0, 5, 0, paperEndsColor), new MatteBorder(0, 5, 0, 5, paperColor)));
        textPane.setFont(georgiaFont);

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

        Style attributeHeader = doc.addStyle("attributeHeader", null);
        StyleConstants.setForeground(attributeHeader, textColor);
        StyleConstants.setFontSize(attributeHeader, 20);
        StyleConstants.setBold(attributeHeader, true);
        StyleConstants.setAlignment(attributeHeader, StyleConstants.ALIGN_CENTER);

        Style attributeNumber = doc.addStyle("attributeNumber", null);
        StyleConstants.setForeground(attributeNumber, textColor);
        StyleConstants.setFontSize(attributeNumber, 20);
        StyleConstants.setAlignment(attributeNumber, StyleConstants.ALIGN_CENTER);


        Style standard = doc.addStyle("standard", null);
        StyleConstants.setForeground(standard, textColor);
        StyleConstants.setFontSize(standard, 15);

        Style italic = doc.addStyle("italic", null);
        StyleConstants.setForeground(italic, textColor);
        StyleConstants.setFontSize(italic, 15);
        StyleConstants.setItalic(italic, true);

        Style sectionHeader = doc.addStyle("sectionHeader", null);
        StyleConstants.setFontFamily(sectionHeader, "Arial");
        StyleConstants.setForeground(sectionHeader, textColor);
        StyleConstants.setFontSize(sectionHeader, 20);

        Style headerBlack = doc.addStyle("headerBlack", null);
        StyleConstants.setForeground(headerBlack, Color.BLACK);
        StyleConstants.setFontSize(headerBlack, 15);
        StyleConstants.setBold(headerBlack, true);
        StyleConstants.setItalic(headerBlack, true);

        Style standardBlack = doc.addStyle("standardBlack", null);
        StyleConstants.setForeground(standardBlack, Color.BLACK);
        StyleConstants.setFontSize(standardBlack, 15);

        final Color separatorColor = new Color(165, 42, 42);
        class thickSeparator extends JSeparator
        {
            {setPreferredSize(new Dimension(0, 15));}
            @Override
            protected void paintComponent(Graphics g)
            {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setColor(separatorColor);
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
                g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
                g2d.fillPolygon(new int[]{0, getWidth(), getWidth(), 0}, new int[]{0, 3, 4, 7}, 4);
            }
        }
        class thinSeparator extends JSeparator
        {
            {
                setForeground(separatorColor);
                setBackground(separatorColor);
                setPreferredSize(new Dimension(0, 20));
            }
        }

        try
        {
            doc.insertString(doc.getLength(), name.toUpperCase(), title);
            doc.insertString(doc.getLength(), "\n " + size + " " + type + (!subtype.equals("") ? " (" + subtype + ")" : "")  + ", " + alignment + "\n", italic);
            textPane.setCaretPosition(doc.getLength());
            textPane.insertComponent(new thickSeparator());


            doc.insertString(doc.getLength(), "\nArmor Class ", header);
            doc.insertString(doc.getLength(), armor_class + " (" + armor_desc + ")", standard);
            doc.insertString(doc.getLength(), "\nHit Point ", header);
            doc.insertString(doc.getLength(), hit_points + " (" + hit_dice + ")", standard);
            doc.insertString(doc.getLength(), "\nSpeed ", header);
            doc.insertString(doc.getLength(), speed.walk + "ft." +
                    (speed.swim != null?", swim " + speed.swim + "ft.":"") +
                    (speed.climb != null?", climb " + speed.climb + "ft.":"") +
                    (speed.burrow != null?", burrow " + speed.burrow + "ft.":"") +
                    (speed.fly != null?", fly " + speed.fly + "ft.":"") + "\n", standard);
            textPane.setCaretPosition(doc.getLength());
            textPane.insertComponent(new thickSeparator());


            doc.insertString(doc.getLength(), "\n", standard);
            textPane.setCaretPosition(doc.getLength());

            StyledDocument wisdomDoc = new DefaultStyledDocument();
            wisdomDoc.insertString(0, "WIS\n", attributeHeader);
            String wisdomMod = Integer.toString(Math.floorDiv(Integer.parseInt(wisdom)-10, 2));
            wisdomMod = " (" + (wisdomMod.substring(0,1).equals("-") ? "" : "+") + wisdomMod + ")";
            wisdomDoc.insertString(4, wisdom + wisdomMod, attributeNumber);
            wisdomDoc.setParagraphAttributes(0,4, attributeHeader, false);
            wisdomDoc.setParagraphAttributes(4, wisdomDoc.getLength(), attributeNumber, false);
            JTextPane wisdomPane = new JTextPane(wisdomDoc);
            wisdomPane.setBackground(paperColor);
            wisdomPane.setFont(georgiaFont);
            textPane.insertComponent(wisdomPane);

            StyledDocument intelligenceDoc = new DefaultStyledDocument();
            intelligenceDoc.insertString(0, "INT\n", attributeHeader);
            String intelligenceMod = Integer.toString(Math.floorDiv(Integer.parseInt(intelligence)-10, 2));
            intelligenceMod = " (" + (intelligenceMod.substring(0,1).equals("-") ? "" : "+") + intelligenceMod + ")";
            intelligenceDoc.insertString(4, intelligence + intelligenceMod, attributeNumber);
            intelligenceDoc.setParagraphAttributes(0,4, attributeHeader, false);
            intelligenceDoc.setParagraphAttributes(4, intelligenceDoc.getLength(), attributeNumber, false);
            JTextPane intelligencePane = new JTextPane(intelligenceDoc);
            intelligencePane.setBackground(paperColor);
            intelligencePane.setFont(georgiaFont);
            textPane.insertComponent(intelligencePane);

            StyledDocument constitutionDoc = new DefaultStyledDocument();
            constitutionDoc.insertString(0, "CON\n", attributeHeader);
            String constitutionMod = Integer.toString(Math.floorDiv(Integer.parseInt(constitution)-10, 2));
            constitutionMod = " (" + (constitutionMod.substring(0,1).equals("-") ? "" : "+") + constitutionMod + ")";
            constitutionDoc.insertString(4, constitution + constitutionMod, attributeNumber);
            constitutionDoc.setParagraphAttributes(0,4, attributeHeader, false);
            constitutionDoc.setParagraphAttributes(4, constitutionDoc.getLength(), attributeNumber, false);
            JTextPane constitutionPane = new JTextPane(constitutionDoc);
            constitutionPane.setBackground(paperColor);
            constitutionPane.setFont(georgiaFont);
            textPane.insertComponent(constitutionPane);

            StyledDocument dexterityDoc = new DefaultStyledDocument();
            dexterityDoc.insertString(0, "DEX\n", attributeHeader);
            String dexterityMod = Integer.toString(Math.floorDiv(Integer.parseInt(dexterity)-10, 2));
            dexterityMod = " (" + (dexterityMod.substring(0,1).equals("-") ? "" : "+") + dexterityMod + ")";
            dexterityDoc.insertString(4, dexterity + dexterityMod, attributeNumber);
            dexterityDoc.setParagraphAttributes(0,4, attributeHeader, false);
            dexterityDoc.setParagraphAttributes(4, dexterityDoc.getLength(), attributeNumber, false);
            JTextPane dexterityPane = new JTextPane(dexterityDoc);
            dexterityPane.setBackground(paperColor);
            dexterityPane.setFont(georgiaFont);
            textPane.insertComponent(dexterityPane);

            StyledDocument strengthDoc = new DefaultStyledDocument();
            strengthDoc.insertString(0, "STR\n", attributeHeader);
            String strengthMod = Integer.toString(Math.floorDiv(Integer.parseInt(strength)-10, 2));
            strengthMod = " (" + (strengthMod.substring(0,1).equals("-") ? "" : "+") + strengthMod + ")";
            strengthDoc.insertString(4, strength + strengthMod, attributeNumber);
            strengthDoc.setParagraphAttributes(0,4, attributeHeader, false);
            strengthDoc.setParagraphAttributes(4, strengthDoc.getLength(), attributeNumber, false);
            JTextPane strengthPane = new JTextPane(strengthDoc);
            strengthPane.setBackground(paperColor);
            strengthPane.setFont(georgiaFont);
            textPane.insertComponent(strengthPane);

            doc.insertString(doc.getLength(), "\n", standard);
            textPane.setCaretPosition(doc.getLength());
            textPane.insertComponent(new thickSeparator());


            String savingThrows =
                    (strength_save != null ? "Str +"+strength_save+", " : "") +
                            (dexterity_save != null ? "Dex +"+dexterity_save+", " : "") +
                            (constitution_save != null ? "Con +"+constitution_save+", " : "") +
                            (intelligence_save != null ? "Int +"+intelligence_save+", " : "") +
                            (wisdom_save != null ? "Wis +"+wisdom_save+", " : "");
            if(!savingThrows.equals(""))
            {
                doc.insertString(doc.getLength(), "\nSaving Throws ", header);
                doc.insertString(doc.getLength(), savingThrows.substring(0, savingThrows.length() - 2), standard);
            }
            if(!skills.allNull())
            {
                doc.insertString(doc.getLength(), "\nSkills ", header);
                doc.insertString(doc.getLength(), skills.toString(), standard);
            }
            if(!damage_vulnerabilities.equals(""))
            {
                doc.insertString(doc.getLength(), "\nDamage Vulnerability ", header);
                doc.insertString(doc.getLength(), damage_vulnerabilities, standard);
            }
            if(!damage_resistances.equals(""))
            {
                doc.insertString(doc.getLength(), "\nDamage Resistance ", header);
                doc.insertString(doc.getLength(), damage_resistances, standard);
            }
            if(!damage_immunities.equals(""))
            {
                doc.insertString(doc.getLength(), "\nDamage Immunities ", header);
                doc.insertString(doc.getLength(), damage_immunities, standard);
            }
            if(!condition_immunities.equals(""))
            {
                doc.insertString(doc.getLength(), "\nCondition Immunities ", header);
                doc.insertString(doc.getLength(), condition_immunities, standard);
            }
            if(!senses.equals(""))
            {
                doc.insertString(doc.getLength(), "\nSenses ", header);
                doc.insertString(doc.getLength(), senses, standard);
            }
            if(!languages.equals(""))
            {
                doc.insertString(doc.getLength(), "\nLanguages ", header);
                doc.insertString(doc.getLength(), languages, standard);
            }
            doc.insertString(doc.getLength(), "\nChallenge ", header);
            doc.insertString(doc.getLength(), challenge_rating +"\n", standard);
            textPane.setCaretPosition(doc.getLength());
            textPane.insertComponent(new thickSeparator());


            for (Action ability : special_abilities)
            {
                doc.insertString(doc.getLength(), "\n" + ability.name +". ", headerBlack);
                doc.insertString(doc.getLength(), ability.desc + "\n", standardBlack);
            }


            if(actions != null)
            {
                doc.insertString(doc.getLength(), "\nActions \n", sectionHeader);
                textPane.setCaretPosition(doc.getLength());
                textPane.insertComponent(new thinSeparator());

                for(Action action : actions)
                {
                    doc.insertString(doc.getLength(), "\n" + action.name +". ", headerBlack);
                    doc.insertString(doc.getLength(), action.desc + "\n", standardBlack);
                }
            }


            if(reactions != null)
            {
                doc.insertString(doc.getLength(), "\nReactions \n", sectionHeader);
                textPane.setCaretPosition(doc.getLength());
                textPane.insertComponent(new thinSeparator());

                for(Action action : reactions)
                {
                    doc.insertString(doc.getLength(), "\n" + action.name +". ", headerBlack);
                    doc.insertString(doc.getLength(), action.desc + "\n", standardBlack);
                }
            }


            if(legendary_actions != null)
            {
                doc.insertString(doc.getLength(), "\nLegendary Actions \n", sectionHeader);
                textPane.setCaretPosition(doc.getLength());
                textPane.insertComponent(new thinSeparator());
                doc.insertString(doc.getLength(), "\n" + legendary_desc, standardBlack);

                for(Action action : legendary_actions)
                {
                    doc.insertString(doc.getLength(), "\n\n" + action.name +". ", headerBlack);
                    doc.insertString(doc.getLength(), action.desc, standardBlack);
                }
            }
        } catch (BadLocationException e) { e.printStackTrace(); }

    }
}
