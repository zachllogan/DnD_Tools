import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.ArrayList;

public class RollerDialog extends JDialog
{
    private JTextField nameField;
    private JFormattedTextField totalField;
    private JPanel dieRollersPanel;

    private boolean save;
    private ArrayList<DieRollerPanel> dieRollerPanels = new ArrayList<>();

    public RollerDialog(Frame parent)
    {
        super(parent, "Roller");

        ((JPanel)getContentPane()).setBorder(new EmptyBorder(5,5,5,5));

        setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));

        JPanel namePanel = new JPanel();
        namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.LINE_AXIS));
        add(namePanel);
        namePanel.add(new JLabel("Name: "));
        nameField = new JTextField();
        namePanel.add(nameField);
        nameField.setMaximumSize(new Dimension(nameField.getMaximumSize().width, nameField.getPreferredSize().height));

        dieRollersPanel = new JPanel();
        dieRollersPanel.setBorder(new TitledBorder(new LineBorder(new Color(75, 75, 75), 2),"Dice"));
        dieRollersPanel.setLayout(new BoxLayout(dieRollersPanel, BoxLayout.PAGE_AXIS));
        add(dieRollersPanel);
        DieRollerPanel dieRollerPanel = new DieRollerPanel();
        dieRollerPanels.add(dieRollerPanel);
        dieRollersPanel.add(dieRollerPanel.getPanel());

        JPanel totalPanel = new JPanel();
        totalPanel.setLayout(new BoxLayout(totalPanel, BoxLayout.LINE_AXIS));
        totalPanel.add(new JLabel("Total: "));
        totalField = new JFormattedTextField();
        totalField.setValue(0);
        totalField.setMaximumSize(new Dimension(totalField.getMaximumSize().width, totalField.getPreferredSize().height));
        totalField.setEditable(false);
        totalPanel.add(totalField);
        add(totalPanel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
        add(buttonPanel);

        buttonPanel.add(new JButton("OK"));
        buttonPanel.add(new JButton("Roll!"));
        buttonPanel.add(new JButton("Add"));
        buttonPanel.add(new JButton("Remove"));
        buttonPanel.add(new JButton("Cancel"));

        ActionListener listener = new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String action = e.getActionCommand();
                switch(action)
                {
                    case "OK":
                        RollerDialog.this.save = true;
                        RollerDialog.this.dispose();
                        break;
                    case "Roll!":
                        int total = 0;
                        for(DieRollerPanel p : dieRollerPanels)
                        {
                            DieRoller roller = new DieRoller(p.getDie(), p.getNum(), p.getBonus());
                            p.setResultField(roller.roll());
                            if(p.getName().toLowerCase().equals("to hit")) { continue; }
                            total += roller.getResult();
                        }
                        totalField.setValue(total);
                        break;
                    case "Add":
                        DieRollerPanel dieRollerPanel = new DieRollerPanel();
                        dieRollerPanels.add(dieRollerPanel);
                        dieRollersPanel.add(dieRollerPanel.getPanel());
                        break;
                    case "Remove":
                        if(dieRollerPanels.size() < 2) {break;}
                        dieRollerPanels.remove(dieRollerPanels.size()-1);
                        dieRollersPanel.remove(dieRollerPanels.size());
                        break;
                    case "Cancel":
                        RollerDialog.this.dispose();
                        break;
                }
                RollerDialog.this.revalidate();
                RollerDialog.this.repaint();
            }
        };

        for(Component c : buttonPanel.getComponents())
        {
            ((JButton)c).addActionListener(listener);
        }

        setModal(true);
        setPreferredSize(new Dimension(400, 500));

        save = false;
    }

    public DieRollerSet showDialog()
    {
        pack();
        super.setVisible(true);
        if(save)
        {
            DieRoller[] dieRollers = new DieRoller[dieRollerPanels.size()];
            for(int i = 0; i<dieRollers.length; i++)
            {
                DieRollerPanel p = dieRollerPanels.get(i);
                DieRoller roller = new DieRoller(p.getDie(), p.getNum(), p.getBonus());
                roller.name = p.getName();
                dieRollers[i] = roller;
            }
            DieRollerSet set = new DieRollerSet(dieRollers);
            set.name = nameField.getText();
            return set;
        }
        return null;
    }
}
