package SAPAdvertisements.models;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "categories")
public class Categories {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int category_id;

    @Column(name = "category_name", nullable = false)
    private String categoryName;

    @OneToMany(mappedBy = "category_id", fetch = FetchType.EAGER)
    private Set<Announcements> announcements;

    public Categories() {
    }

    public Categories(int category_id, String categoryName) {
        this.category_id = category_id;
        this.categoryName = categoryName;
    }

    public Categories(int category_id) {
        this.category_id = category_id;
    }

    public Categories(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public String toString() {
        return "Category_id=" + category_id +
                ", categoryName='" + categoryName ;
    }
}
