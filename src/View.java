import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.ActionListener;

public class View extends JFrame {

    //buttons, labels, textareas, table, container init
    private JButton saveButton = new JButton("Save & Serialize");
    private JButton resetButton = new JButton("Reset Text Fields");
    private JButton deserializeButton = new JButton("Deserialize & Print");
    private JButton tableButton = new JButton("Show Table View");
    private JButton deleteButton = new JButton("Delete Latest Object");
    private JButton rmiButton = new JButton("Start RMI server");
    private JButton rmiLookupButton = new JButton("Lookup Default Book (RMI)");

    private JLabel titleLabel = new JLabel("Enter Book Title:");
    private JLabel authorLabel = new JLabel("Enter Author:");

    private JTextArea titleArea =  new JTextArea();
    private JTextArea authorArea =  new JTextArea();
    private JTable table = new JTable();

    private Container contentPane = this.getContentPane();

    //view, set size, title, layout
    public View()
    {
        this.setSize(400,400);
        this.setTitle("MVC");
        this.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));

    }

    //init of view, add previously defined buttons, labels, textfields etc to the view itself
    public void init()
    {
        contentPane.add(titleLabel);
        contentPane.add(titleArea);

        contentPane.add(authorLabel);
        contentPane.add(authorArea);

        contentPane.add(saveButton);
        contentPane.add(resetButton);
        contentPane.add(deserializeButton);
        contentPane.add(tableButton);
        contentPane.add(deleteButton);
        contentPane.add(rmiButton);
        contentPane.add(rmiLookupButton);

        contentPane.add(table);

        this.setVisible(true);
    }

    //action listeners for the various buttons
    public void addResetListener(ActionListener l)
    {
        resetButton.addActionListener(l);
    }

    public void addSaveListener(ActionListener l)
    {
        saveButton.addActionListener(l);
    }

    public void addRMIListener(ActionListener l)
    {
        rmiButton.addActionListener(l);
    }

    public void addRMILookupListener(ActionListener l)
    {
        rmiLookupButton.addActionListener(l);
    }

    public void addDeserializeListener(ActionListener l)
    {
        deserializeButton.addActionListener(l);
    }

    public void addTableListener(ActionListener l)
    {
        tableButton.addActionListener(l);
    }

    public void addDeleteListener(ActionListener l)
    {
        deleteButton.addActionListener(l);
    }

    //getters & setters for the text areas
    public String getTitle()
    {
        return titleArea.getText();
    }

    public String getAuthor()
    {
        return authorArea.getText();
    }

    public void setTitle(String s)
    {
        titleArea.setText(s);
    }

    public void setAuthor(String s)
    {
        authorArea.setText(s);
    }



    //main
    public static void main(String[] args) {
        new View().init();
    }
}