import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Arrays;

public class SearchDialog extends JDialog
{
    boolean confirmed = false;

    JComboBox<String> searchBox = new JComboBox<String>();
    {
        {
            searchBox.setBorder(new LineBorder(new Color(171, 173, 179), 1));

            searchBox.setEditable(true);

            searchBox.setUI(new BasicComboBoxUI()
            {
                @Override
                protected JButton createArrowButton()
                {
                    return new JButton()
                    {
                        @Override
                        public int getWidth()
                        {
                            return 0;
                        }
                    };
                }
            });
            searchBox.remove(searchBox.getComponent(0));

            Component c = searchBox.getEditor().getEditorComponent();
            if ( c instanceof JTextComponent )
            {
                final JTextComponent tc = (JTextComponent)c;
                tc.getDocument().addDocumentListener(new DocumentListener()
                {
                    @Override
                    public void changedUpdate(DocumentEvent arg0) {}

                    @Override
                    public void insertUpdate(DocumentEvent arg0)
                    {
                        update();
                    }

                    @Override
                    public void removeUpdate(DocumentEvent arg0)
                    {
                        update();
                    }

                    public void update()
                    {
                        SwingUtilities.invokeLater(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                ApiSearch search = null;
                                search = ApiAcessor.search(tc.getText().replace(" ", "_"));
                                searchBox.setEditable(false);
                                searchBox.removeAllItems();
                                String[] names = search.getNames();
                                if(!Arrays.asList(names).contains(tc.getText()))
                                {
                                    searchBox.addItem(tc.getText());
                                }
                                for(int i = 0; i<names.length && i<5; i++)
                                {
                                    searchBox.addItem(names[i]);
                                }
                                searchBox.setEditable(true);
                                searchBox.setPopupVisible(true);
                                tc.requestFocus();
                            }
                        });
                    }
                });
                tc.addFocusListener(new FocusListener()
                {
                    @Override
                    public void focusGained(FocusEvent arg0)
                    {
                        if ( tc.getText().length() > 0 )
                        {
                            searchBox.setPopupVisible(true);
                        }
                    }

                    @Override
                    public void focusLost(FocusEvent arg0) {}
                });

            }
            else
            {
                throw new IllegalStateException("Editing component is not a JTextComponent!");
            }
        }
    }


    public SearchDialog()
    {
        super();

        setModal(true);
        setLayout(new FlowLayout());

        add(searchBox);
        add(new JButton("Confirm")
        {
            {
                addActionListener(e ->
                {
                    confirmed = true;
                    SearchDialog.this.dispose();
                });
            }
        });
    }


    public ApiThing search()
    {
        confirmed = false;
        pack();
        setVisible(true);
        ApiThing result = null;
        if(confirmed)
        {
            String name  = (String)searchBox.getSelectedItem();
            ApiSearch search = ApiAcessor.search(name);
            String route = search.getRoute(name);
            if(route != null)
            {
                switch (route)
                {
                    case "monsters/":
                        result = ApiAcessor.getMonster(search.getSlug(name));
                        break;
                    case "spells/":
                        result = ApiAcessor.getSpell(search.getSlug(name));
                        break;
                    case "magicitems/":
                        result = ApiAcessor.getMagicItem(search.getSlug(name));
                        break;
                    case "weapons/":
                        result = ApiAcessor.getWeapon(search.getSlug(name));
                }
            }
        }
        return result;
    }
}
