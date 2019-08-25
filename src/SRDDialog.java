import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class SRDDialog extends JDialog
{
    private JComboBox statBlockSelector;
    private JTextPane statBlockPane;
    private JPanel panel;
    private JButton plusButton;
    private JButton minusButton;

    private InitiativePanel initiativePanel;

    SRDDialog(SRDDialog origin, InitiativePanel initiativePanel)
    {
        this(initiativePanel);
        if(origin!=null)
        {
            setSize(origin.getSize());
            setLocation(origin.getLocation());
            origin.dispose();
        }
    }

    SRDDialog(InitiativePanel initiativePanel)
    {
        setModal(false);
        setDefaultCloseOperation(HIDE_ON_CLOSE);

        add(panel);
        ActionListener listener = new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                switch (e.getActionCommand())
                {
                    case "+":
                        ApiThing apiThing = new SearchDialog().search();
                        if(apiThing != null)
                        {
                            initiativePanel.addStatBlock(apiThing);
                            statBlockSelector.addItem(apiThing);
                            statBlockSelector.setSelectedIndex(statBlockSelector.getItemCount()-1);
                        }
                        break;
                    case "-":
                        if(statBlockSelector.getItemCount() > 0)
                        {
                            initiativePanel.removeStatBlock(statBlockSelector.getSelectedIndex());
                            statBlockSelector.removeItemAt(statBlockSelector.getSelectedIndex());
                        }
                }
            }
        };
        plusButton.addActionListener(listener);
        minusButton.addActionListener(listener);
        statBlockSelector.addItemListener(new ItemListener()
        {
            @Override
            public void itemStateChanged(ItemEvent e)
            {
                if(e.getStateChange() == ItemEvent.SELECTED)
                {
                    ((ApiThing)e.getItem()).fillTextPane(statBlockPane);
                }
            }
        });

        this.initiativePanel = initiativePanel;
        if(initiativePanel.getStatBlocks() != null)
        {
            for (ApiThing apiThing : initiativePanel.getStatBlocks())
            {
                statBlockSelector.addItem(apiThing);
            }
        }
        statBlockSelector.setSelectedIndex(statBlockSelector.getItemCount()-1);
    }
}