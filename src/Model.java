import java.io.Serializable;

public class Model implements Serializable {        //model implements serializeable

    private String title, author;       //init

    public Model()      //default constructor
    {

    }
    public Model(String title, String author) {     //full constructor
        this.title = title;
        this.author = author;
    }

    //getters & setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }


    //toString to print data
    @Override
    public String toString() {
        return "Book: " +
                "Title: '" + title + '\'' +
                ", Author: '" + author + '\'' + "||";
    }
}

