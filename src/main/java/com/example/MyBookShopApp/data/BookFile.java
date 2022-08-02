package com.example.MyBookShopApp.data;

import javax.persistence.*;

@Entity
@Table(name = "book_file")
public class BookFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//    @NotNull
    private String hash;
//    @NotNull
    @Column(name = "type_id")
    private Integer typeId;
//    @NotNull

    private String path;

    @ManyToOne
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private Book book;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer type_id) {
        this.typeId = type_id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "BookFile{" +
                "id=" + id +
                ", hash='" + hash + '\'' +
                ", typeId=" + typeId +
                ", path='" + path + '\'' +
                ", book=" + book +
                '}';
    }

    public String getBookFileExtensionString(){
        return BookFileType.getExtensionStringByTypeId(typeId);
    }
}
