import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;

public class Controller implements Serializable {

    private Model bookModel = new Model();
    private View bookView = new View();                 //mvc
    private TableView tableView = new TableView();

    String filename = "serialize.txt";                  //filename for serialization

    ArrayList<Model> books = new ArrayList<>(5) ;       //arraylist
    Model book1 = new Model("A Game of Thrones", "GRR Martin");
    Model book2 = new Model("Macbeth", "William Shakespeare");          //created books for testing
    Model book3 = new Model("The Fellowship of the Ring", "JRR Tolkien");
    Model book4 = new Model("Fire and Blood", "GRR Martin");

    public Controller()
    {
        bookView.init();
        TableView.init();
        books.add(book1);
        books.add(book2);
        books.add(book3);
        books.add(book4);               //init the controller. add all the controllers (save, reset etc) to the controller itself
        new SaveController();
        new ResetController();
        new DeserializeController();
        new TableController();
        new DeleteController();
        new RMIController();
        new RMIControllerLookup();
    }

    class SaveController implements ActionListener      //save controller. used for saving objects & serialization
    {
        public SaveController()
        {
            bookView.addSaveListener(this);
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            bookModel.setTitle(bookView.getTitle());
            bookModel.setAuthor(bookView.getAuthor());                          //get title, author from text panes
            Model m = new Model(bookView.getTitle(), bookView.getAuthor());     //add new object with values from text panes
            books.add(m);                                                       //add this to list
            System.out.println("Object: "+ books);

            System.out.println("Books in List: " +books.size());
                                                                                //print list, list size, if max limit reached
            if (books.size() >=4){
                System.out.println("Maximum ArrayList Size Reached");
            }

            try                                                                 //try/catch, used for serializing objects
            {
                FileOutputStream file = new FileOutputStream(filename);         //fileoutputsteam & objectout
                ObjectOutputStream out = new ObjectOutputStream(file);

                out.writeObject(books);                                         //method for serialization of obj

                out.close();
                file.close();

                System.out.println("Objects have been serialized");

            }

            catch(IOException ex)
            {
                System.out.println("IOException is caught");                    //catch exception
                System.out.println(ex);
            }
        }
    }
    class ResetController implements ActionListener                             //reset controller, used to reset text
    {
        public ResetController()
        {
            bookView.addResetListener(this);
        }
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            bookView.setTitle("");
            bookView.setAuthor("");                                             //set text panes to " "
            System.out.println("Inputs Reset");
        }
    }

    class DeserializeController implements ActionListener                       //deserialize controller, used to deserialize objects from txt
    {
        public DeserializeController()
        {
            bookView.addDeserializeListener(this);
        }
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            try {

                FileInputStream file = new FileInputStream(filename);
                ObjectInputStream in = new ObjectInputStream(file);

                ArrayList<Model> objList = (ArrayList<Model>) in.readObject();      //method for deserialization, add to objList

                in.close();
                file.close();

                System.out.println("Objects have been deserialized: ");
                System.out.println("Book 1: "+objList.get(0));
                System.out.println("Book 2: "+objList.get(1));
                System.out.println("Book 3: "+objList.get(2));                      //print out deserialized data with objList.get(index)
                System.out.println("Book 4: "+objList.get(3));                      //this will print data out from serialized text file
                System.out.println("Book 5: "+objList.get(4));

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    class RMIController implements ActionListener                                //rmi controller, used to start the server
    {
        public RMIController()
        {
            bookView.addRMIListener(this);
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Server started");
            try{
                LocateRegistry.createRegistry(660);     //start server on this port
                ModelD m1 = new ModelD("Ulysses", "James Joyce");
                System.out.println("New book created");
                ModelD m2 = new ModelD("To Kill a Mockingbird", "Harper Lee");      //start server, create m1, m2 objects from ModelD
                System.out.println("New book created (2)");
                Naming.rebind("rmi://localhost:660/m1", m1);
                Naming.rebind("rmi://localhost:660/m2", m2);                             //use naming.rebind to set m1, m2
            } catch (RemoteException | MalformedURLException u) {
                u.printStackTrace();
            }
        }
    }
    class RMIControllerLookup implements ActionListener                                 //rmi controller lookup, used to lookup the obj from server
    {
        public RMIControllerLookup()
        {
            bookView.addRMILookupListener(this);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String url = "rmi:///";
            System.out.println("Client has started");

            try{
                ModelInterface m1 = (ModelInterface) Naming.lookup("rmi://localhost:660/m1");
                System.out.println("Book 1 Title: " +m1.getTitle());                                    //get title, author from m1 which is in server
                System.out.println("Book 1 Author: " +m1.getAuthor());

                System.out.println("");

                ModelInterface m2 = (ModelInterface) Naming.lookup("rmi://localhost:660/m2");
                System.out.println("Book 2 Title: " +m2.getTitle());                                    //get title, author from m2 which is in server
                System.out.println("Book 2 Author: "+m2.getAuthor());

            } catch (MalformedURLException malformedURLException) {
                malformedURLException.printStackTrace();
            } catch (NotBoundException notBoundException) {
                notBoundException.printStackTrace();
            } catch (RemoteException remoteException) {
                remoteException.printStackTrace();
            }
        }
    }

    class DeleteController implements ActionListener            //delete controller, deletes latest object from list
    {
        public DeleteController()
        {
            bookView.addDeleteListener(this);
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            books.remove(books.get(books.size() -1 ));      //remove latest object from list
            System.out.println("Latest Object Deleted");
            System.out.println("Books in list: " + books.size());
        }
    }

    class TableController implements ActionListener         //table controller, creates new table gui
    {
        public TableController()
        {
            bookView.addTableListener(this);
        }
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            JButton updateButton = new JButton("Update");
            JTextField jtf = new JTextField();                  //init textfields, get title and author from textpanes
            JTextField jtf2 = new JTextField();
            bookModel.setTitle(bookView.getTitle());
            bookModel.setAuthor(bookView.getAuthor());
            TableView tableView = new TableView();
            tableView.setVisible(true);
            JTable j;
            Object[][] data = {
                    { books.get(0).getTitle(), books.get(0).getAuthor()},
                    { books.get(1).getTitle(), books.get(1).getAuthor()},
                    { books.get(2).getTitle(), books.get(2).getAuthor()},       //data to be displayed in table (1-5 in list)
                    { books.get(3).getTitle(), books.get(3).getAuthor()},
                    { books.get(4).getTitle(), books.get(4).getAuthor()}
            };

            // Column Names
            String[] columnNames = { "Book Title", "Book Author" };             //column names for table

            j = new JTable(data, columnNames);                                  //new table, set data to data, columnnames
            j.setBounds(30, 40, 200, 300);
            JScrollPane sp = new JScrollPane(j);
            tableView.add(sp);                                                  //add scrollpane
            tableView.setSize(500, 200);
            tableView.add(updateButton);
            tableView.add(jtf);                                                 //add all to view itself, set visible
            tableView.add(jtf2);
            tableView.setVisible(true);

            updateButton.addActionListener(new ActionListener() {               //update, edits textfields to update/create new obj
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("update");
                    books.remove(books.get(books.size() -1 ));
                    String updatedTitle = jtf.getText();
                    String updatedAuthor = jtf2.getText();              //remove latest obj, set variables to text from fields
                    bookView.setTitle(updatedTitle);                    //set title, author to these variables
                    bookView.setAuthor(updatedAuthor);
                    Model m = new Model(bookView.getTitle(), bookView.getAuthor());     //add as new obj, add to list
                    books.add(m);
                }
            });

        }
    }

    public static void main(String[] args) {
        new Controller();
    }
}