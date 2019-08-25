import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class InitiativePanel
{
    public static final DieRoller d20 = new DieRoller(20, 1, 0);


    private ArrayList<ApiThing> statBlocks;
    private LinkedHashMap<String, DieRollerSet> dieRollerSets;

    private boolean currentlyTurn;

    private JCheckBox PCCheckBox;
    private JCheckBox activeCheckBox;
    private JTextField nameField;
    private JFormattedTextField maxHPField;
    private JFormattedTextField currentHPField;
    private JFormattedTextField initiativeRollField;
    private JFormattedTextField initiativeBonusField;
    private JFormattedTextField initiativeTotalField;
    private JComboBox rollerSelector;
    private JButton rollButton;
    private JButton newRollerButton;
    private JTextArea rollResultsArea;
    private JButton rollInitiativeButton;
    private JProgressBar HPBar;
    private JPanel panel;
    private JButton deleteButton;
    private JFormattedTextField modifyHPField;
    private JButton minusHPButton;
    private JButton plusHPButton;

    public InitiativePanel()
    {
        ActionListener listener = new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String action = e.getActionCommand();
                InitiativeData data = new InitiativeData();
                getData(data);
                switch(action)
                {
                    case "+":
                        currentHPField.setValue(((Number)currentHPField.getValue()).intValue() + ((Number)modifyHPField.getValue()).intValue());
                        modifyHPField.setValue(1);
                        break;
                    case "-":
                        currentHPField.setValue(((Number)currentHPField.getValue()).intValue() - ((Number)modifyHPField.getValue()).intValue());
                        modifyHPField.setValue(1);
                        break;
                    case "Roll Initiative":
                        initiativeRollField.setValue(d20.roll());
                        break;
                    case "New":
                        DieRollerSet dieRollerSet = new RollerDialog((JFrame)SwingUtilities.getRoot(InitiativePanel.this.getPanel())).showDialog();
                        if(dieRollerSet != null)
                        {
                            dieRollerSets.put(dieRollerSet.name, dieRollerSet);
                            rollerSelector.addItem(dieRollerSet.name);
                        }
                        break;
                    case "Delete":
                        if(rollerSelector.getItemCount()>0)
                        {
                            dieRollerSets.remove(rollerSelector.getItemAt(rollerSelector.getSelectedIndex()));
                            rollerSelector.removeItemAt(rollerSelector.getSelectedIndex());
                        }
                        break;
                    case "Roll!":
                        String roller = (String) rollerSelector.getSelectedItem();
                        if(roller != null)
                        {
                            LinkedHashMap<String, Integer> rolls = dieRollerSets.get(roller).roll();
                            int total = 0;
                            String text = "";
                            for(String roll : rolls.keySet())
                            {
                                text += roll + ": " + rolls.get(roll) + "\n";
                                if(roll.toLowerCase().equals("to hit")) { continue; }
                                total += rolls.get(roll);
                            }
                            if(rolls.size() > 1)
                            {
                                text += "Total: " + total;
                            }
                            rollResultsArea.setText(text);
                        }
                        break;
                }
            }
        };
        rollInitiativeButton.addActionListener(listener);
        minusHPButton.addActionListener(listener);
        plusHPButton.addActionListener(listener);
        deleteButton.addActionListener(listener);
        newRollerButton.addActionListener(listener);
        rollButton.addActionListener(listener);

        PropertyChangeListener listener1 = new PropertyChangeListener()
        {
            @Override
            public void propertyChange(PropertyChangeEvent evt)
            {
                InitiativePanel.this.update();
            }
        };
        initiativeRollField.addPropertyChangeListener("value", listener1);
        initiativeBonusField.addPropertyChangeListener("value", listener1);
        maxHPField.addPropertyChangeListener("value", listener1);
        currentHPField.addPropertyChangeListener("value", listener1);
        activeCheckBox.addPropertyChangeListener("selected", listener1);

        nameField.setCaretColor(new Color(244, 244, 244));


        statBlocks = new ArrayList<>();
        dieRollerSets = new LinkedHashMap<>();
        currentlyTurn = false;
    }

    public JPanel getPanel()
    {
        return panel;
    }

    private void createUIComponents()
    {
        currentHPField = new JFormattedTextField(new NumberFormatter(NumberFormat.getIntegerInstance()));
        maxHPField = new JFormattedTextField(new NumberFormatter(NumberFormat.getIntegerInstance()));
        modifyHPField = new JFormattedTextField(new NumberFormatter(NumberFormat.getIntegerInstance()));
        initiativeRollField = new JFormattedTextField(new NumberFormatter(NumberFormat.getIntegerInstance()));
        initiativeBonusField = new JFormattedTextField(new NumberFormatter(NumberFormat.getIntegerInstance()));
        initiativeTotalField = new JFormattedTextField(new NumberFormatter(NumberFormat.getIntegerInstance()));

        currentHPField.setValue(0);
        maxHPField.setValue(0);
        modifyHPField.setValue(1);
        initiativeRollField.setValue(0);
        initiativeBonusField.setValue(0);
        initiativeTotalField.setValue(0);

        HPBar = new JProgressBar();

        panel = new JPanel()
        {
            @Override
            public void paint(Graphics g)
            {
                InitiativePanel.this.update();
                if(InitiativePanel.this.currentlyTurn)
                {
                    this.setBorder(new TitledBorder(new LineBorder(Color.BLACK, 3), "Turn!", 0, 0, Font.decode(null).deriveFont(Font.BOLD, 18)));
                }
                else
                {
                    panel.setBorder(new EmptyBorder( 26, 7, 7, 7));
                }
                super.paint(g);
                if(!InitiativePanel.this.activeCheckBox.isSelected())
                {
                    g.setColor(new Color(0,0,0,0.5f));
                    g.fillRect(0, 0, InitiativePanel.this.panel.getWidth(), InitiativePanel.this.panel.getHeight());
                }
            }
        };
        panel.setBorder(new EmptyBorder( 26, 7, 7, 7));
    }

    public void setData(InitiativeData data)
    {
        initiativeRollField.setValue(data.getInitiativeRoll());
        initiativeBonusField.setValue(data.getInitiativeBonus());
        initiativeTotalField.setValue(data.getInitiativeTotal());
        PCCheckBox.setSelected(data.isIsPC());
        activeCheckBox.setSelected(data.isIsActive());
        nameField.setText(data.getName());
        maxHPField.setValue(data.getMaxHP());
        currentHPField.setValue(data.getCurrentHP());
        rollResultsArea.setText(data.getRollResults());
        currentlyTurn = data.isCurrentlyTurn();
        for(DieRollerSet set : data.getDieRollerSets())
        {
            dieRollerSets.put(set.name, set);
            rollerSelector.addItem(set.name);
        }
        statBlocks = data.getStatBlocks();
        update();
    }

    public void getData(InitiativeData data)
    {
        data.setInitiativeRoll( ((Number)initiativeRollField.getValue()).intValue() );
        data.setInitiativeBonus( ((Number)initiativeBonusField.getValue()).intValue() );
        data.setInitiativeTotal( ((Number)initiativeTotalField.getValue()).intValue() );
        data.setIsPC(PCCheckBox.isSelected());
        data.setIsActive(activeCheckBox.isSelected());
        data.setName(nameField.getText());
        data.setMaxHP( ((Number)maxHPField.getValue()).intValue() );
        data.setCurrentHP( ((Number)currentHPField.getValue()).intValue() );
        data.setRollResults(rollResultsArea.getText());
        data.setCurrentlyTurn(currentlyTurn);
        data.setDieRollerSets(new ArrayList<DieRollerSet>(dieRollerSets.values()));
        data.setStatBlocks(statBlocks);
    }

    public boolean isModified(InitiativeData data)
    {
        if ((int)initiativeRollField.getValue() != data.getInitiativeRoll())
            return true;
        if ((int)initiativeBonusField.getValue() != data.getInitiativeBonus())
            return true;
        if ((int)initiativeTotalField.getValue() != data.getInitiativeTotal())
            return true;
        if (PCCheckBox.isSelected() != data.isIsPC()) return true;
        if (activeCheckBox.isSelected() != data.isIsActive()) return true;
        if (nameField.getText() != null ? !nameField.getText().equals(data.getName()) : data.getName() != null)
            return true;
        if ((int)maxHPField.getValue() != data.getMaxHP())
            return true;
        if ((int)currentHPField.getValue() != data.getCurrentHP())
            return true;
        if (rollResultsArea.getText() != null ? !rollResultsArea.getText().equals(data.getRollResults()) : data.getRollResults() != null)
            return true;
        if(!data.getDieRollerSets().equals(new ArrayList<DieRollerSet>(dieRollerSets.values())))
            return true;
        if(!data.getStatBlocks().equals(statBlocks))
            return true;
        return false;
    }

    private void update()
    {
        initiativeTotalField.setValue(((Number)initiativeRollField.getValue()).intValue() + ((Number)initiativeBonusField.getValue()).intValue());
        int percentHP = 0;
        if(((Number)maxHPField.getValue()).doubleValue() != 0)
        {
            percentHP = (int) (100 * ((Number) currentHPField.getValue()).doubleValue() / ((Number) maxHPField.getValue()).doubleValue());

        }
        HPBar.setValue(percentHP);
        HPBar.setString(percentHP + "%");
        if(percentHP <= 25)
        {
            HPBar.setForeground(Color.red);
        }
        else if (percentHP <= 50)
        {
            HPBar.setForeground(Color.yellow);
        }
        else
        {
            HPBar.setForeground(Color.green);
        }
        panel.repaint();
    }

    public void setCurrentlyTurn(boolean currentlyTurn)
    {
        this.currentlyTurn = currentlyTurn;
    }

    public boolean getCurrentlyTurn()
    {
        return currentlyTurn;
    }

    public void addStatBlock(ApiThing apiThing)
    {
        statBlocks.add(apiThing);
    }

    public void removeStatBlock(int index)
    {
        statBlocks.remove(index);
    }

    public ArrayList<ApiThing> getStatBlocks()
    {
        return statBlocks;
    }
}
