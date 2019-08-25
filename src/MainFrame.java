import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;

public class MainFrame extends JFrame
{

    private JPanel rootPane;
    private JButton loadButton;
    private JButton saveButton;
    private JButton sortButton;
    private JButton rollButton;
    private JPanel initiativesPanel;
    private JButton addButton;
    private JButton removeButton;
    private JButton nextButton;
    private JButton previousButton;
    private JButton resetButton;
    private JButton duplicateButton;
    private JButton SRDButton;

    private ArrayList<InitiativePanel> initiativePanels;
    private int turnPanel;
    //private boolean showSRD;
    private SRDDialog srdDialog;

    public MainFrame()
    {
        initiativePanels = new ArrayList<>();


        initiativesPanel.setLayout(new FlowLayout(FlowLayout.LEADING, 0, 0)
        {
            public Dimension preferredLayoutSize(Container target)
            {
                Dimension dimension = super.preferredLayoutSize(target);
                dimension.width = Math.min(dimension.width, target.getParent().getWidth());
                if(target.getComponentCount() != 0)
                {
                    Dimension component = target.getComponent(0).getPreferredSize();
                    dimension.height = (int) ((component.getHeight()+5) * Math.ceil(target.getComponentCount() / Math.floor(dimension.width / component.getWidth())));
                }
                return dimension;
            }
        });

        ActionListener listener = new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String action = e.getActionCommand();
                switch(action)
                {
                    case "Next":
                        advanceTurn(1);
                        break;
                    case "Previous":
                        advanceTurn(-1);
                        break;
                    case "Reset":
                        advanceTurn(initiativePanels.size() - turnPanel);
                        break;
                    case "Roll!":
                        InitiativeData d = new InitiativeData();
                        for(InitiativePanel p : initiativePanels)
                        {
                            p.getData(d);
                            if(!d.isIsPC())
                            {
                                d.setInitiativeRoll(InitiativePanel.d20.roll());
                                p.setData(d);
                            }
                        }
                        break;

                    case "Sort":
                        MainFrame.this.initiativesPanel.removeAll();
                        MainFrame.this.initiativePanels.sort(new Comparator<InitiativePanel>()
                        {
                            @Override
                            public int compare(InitiativePanel o1, InitiativePanel o2)
                            {
                                InitiativeData d1 = new InitiativeData();
                                InitiativeData d2 = new InitiativeData();
                                o1.getData(d1);
                                o2.getData(d2);
                                if( (d1.isIsActive() && d2.isIsActive()) || (!d1.isIsActive() && !d2.isIsActive()) )
                                {
                                    int diffrence = d2.getInitiativeTotal() - d1.getInitiativeTotal();
                                    if(diffrence == 0)
                                    {
                                        diffrence = d2.getInitiativeBonus() - d1.getInitiativeBonus();
                                        if(diffrence == 0)
                                        {
                                            return ((int)Math.random()*2) - 1;
                                        }
                                        return diffrence;
                                    }
                                    return diffrence;
                                }
                                return d1.isIsActive() ? -1 : 1;
                            }
                        });
                        for(InitiativePanel p : initiativePanels)
                        {
                            initiativesPanel.add(p.getPanel());
                            if(p.getCurrentlyTurn())
                            {
                                turnPanel = initiativesPanel.getComponentCount()-1;
                            }
                        }
                        break;

                    case "Add":
                        InitiativePanel addPanel = new InitiativePanel();
                        MainFrame.this.initiativePanels.add(addPanel);
                        MainFrame.this.initiativesPanel.add(addPanel.getPanel());
                        if(turnPanel == -1)
                        {
                            addPanel.setCurrentlyTurn(true);
                            turnPanel = 0;
                        }
                        break;

                    case "Duplicate":
                        InitiativeData d2 = new InitiativeData();
                        initiativePanels.get(turnPanel).getData(d2);
                        InitiativePanel p2 = new InitiativePanel();
                        p2.setData(d2);
                        p2.setCurrentlyTurn(false);
                        initiativePanels.add(turnPanel+1, p2);
                        initiativesPanel.add(p2.getPanel(), turnPanel+1);
                        break;

                    case "Remove":
                        if(MainFrame.this.initiativePanels.size() >= 1)
                        {
                            MainFrame.this.initiativesPanel.remove(turnPanel);
                            initiativePanels.remove(turnPanel);
                            if(initiativePanels.size() == 0)
                            {
                                turnPanel = -1;
                                break;
                            }
                            turnPanel %= initiativePanels.size();
                            initiativePanels.get(turnPanel).setCurrentlyTurn(true);
                        }
                        break;

                    case "Save":
                        FileDialog fileDialog = new FileDialog(MainFrame.this, "Save", FileDialog.SAVE);
                        fileDialog.setVisible(true);
                        String file = fileDialog.getFile();
                        if(file == null)
                        {
                            break;
                        }
                        InitiativeData[] data = new InitiativeData[initiativesPanel.getComponentCount()];
                        for(int i = 0; i<data.length; i++)
                        {
                            InitiativeData datum = new InitiativeData();
                            initiativePanels.get(i).getData(datum);
                            data[i] = datum;
                        }
                        try
                        {
                            FileOutputStream fileOutputStream = new FileOutputStream(file);
                            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
                            objectOutputStream.writeObject(data);
                            objectOutputStream.close();
                            fileOutputStream.close();
                        } catch(Exception ex)   { ex.printStackTrace(); }
                        break;

                    case "Load":
                        FileDialog fileD = new FileDialog(MainFrame.this, "Save", FileDialog.LOAD);
                        fileD.setVisible(true);
                        String f = fileD.getFile();
                        if(f == null)
                        {
                            break;
                        }
                        InitiativeData[] encounter = new InitiativeData[0];
                        try
                        {
                            FileInputStream fileInputStream = new FileInputStream(f);
                            ObjectInputStream objectInputStream = new ObjectInputStream((fileInputStream));
                            encounter = (InitiativeData[]) objectInputStream.readObject();
                            objectInputStream.close();
                            fileInputStream.close();
                        } catch (Exception ex)  { ex.printStackTrace(); }
                        for(InitiativeData datum : encounter)
                        {
                            InitiativePanel p = new InitiativePanel();
                            p.setData(datum);
                            initiativePanels.add(p);
                            initiativesPanel.add(p.getPanel());
                            if(p.getCurrentlyTurn())
                            {
                                initiativePanels.get(turnPanel).setCurrentlyTurn(false);
                                turnPanel = initiativePanels.size() - 1;
                            }
                        }
                        break;
                    case "SRD":
                        //showSRD = true;
                        srdDialog = new SRDDialog(srdDialog, initiativePanels.get(turnPanel));
                        srdDialog.pack();
                        srdDialog.setVisible(true);
                }
                MainFrame.this.initiativesPanel.revalidate();
                MainFrame.this.initiativesPanel.repaint();
            }
        };
        nextButton.addActionListener(listener);
        previousButton.addActionListener(listener);
        resetButton.addActionListener(listener);
        rollButton.addActionListener(listener);
        sortButton.addActionListener(listener);
        loadButton.addActionListener(listener);
        saveButton.addActionListener(listener);
        addButton.addActionListener(listener);
        duplicateButton.addActionListener(listener);
        removeButton.addActionListener(listener);
        SRDButton.addActionListener(listener);



        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            UIManager.put("ProgressBar.selectionForeground", Color.BLACK);
            UIManager.put("ProgressBar.selectionBackground", Color.BLACK);
        } catch (Exception e){e.printStackTrace();}

        setTitle("Initiative Tracker");
        setContentPane(rootPane);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1360, 690));

        InitiativePanel addPanel = new InitiativePanel();
        addPanel.setCurrentlyTurn(true);
        MainFrame.this.initiativePanels.add(addPanel);
        MainFrame.this.initiativesPanel.add(addPanel.getPanel());
        turnPanel = 0;

        getGlassPane().setVisible(true);
    }

    private void advanceTurn(int advancement)
    {
        if(turnPanel == -1)
        {
            return;
        }
        initiativePanels.get(turnPanel).setCurrentlyTurn(false);
        turnPanel = ((turnPanel + advancement) % initiativePanels.size() + initiativePanels.size()) % initiativePanels.size();
        InitiativeData data = new InitiativeData();
        for(int i = 0; i<initiativePanels.size(); i++)
        {
            initiativePanels.get(((turnPanel + ((advancement > 0 ? 1 : -1) * i)) % initiativePanels.size() + initiativePanels.size()) % initiativePanels.size()).getData(data);
            if(data.isIsActive())
            {
                turnPanel = ((turnPanel + (advancement > 0 ? 1 : -1) * i) % initiativePanels.size() + initiativePanels.size()) % initiativePanels.size();
                initiativePanels.get(turnPanel).setCurrentlyTurn(true);
                initiativesPanel.scrollRectToVisible(initiativePanels.get(turnPanel).getPanel().getBounds());
                break;
            }
        }
        if(srdDialog != null && srdDialog.isVisible())
        {
            srdDialog = new SRDDialog(srdDialog, initiativePanels.get(turnPanel));
            srdDialog.pack();
            srdDialog.setVisible(true);
        }
    }
}
