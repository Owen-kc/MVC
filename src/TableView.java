import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;


public class TableView extends JFrame {


    private Container contentPane = this.getContentPane();

    //tableview, used for extra new tables if needed, can ignore
    public TableView()
    {
        this.setSize(400,400);
        this.setTitle("Table View");
        this.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
    }

    public static void init() {
    }
}
