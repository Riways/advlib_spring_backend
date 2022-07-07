package api.entity;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Table(name = "authors")
public class Author {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "Field 'Author' should not be empty")
    @Column(name = "full_name")
    private String fullName;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "author" )
    transient private List<BookFromLibrary> authorBooks;

    public Author() {
    }


    public Author( String fullName) {
        this.fullName = fullName;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public List<BookFromLibrary> getAuthorBooks() {
        return authorBooks;
    }

    public void setAuthorBooks(List<BookFromLibrary> authorBooks) {
        this.authorBooks = authorBooks;
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", authorBooks=" + authorBooks +
                '}';
    }
}
