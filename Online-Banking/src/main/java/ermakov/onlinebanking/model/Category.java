package ermakov.onlinebanking.model;

import java.io.Serializable;
import java.util.Objects;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Category implements Serializable {
    private int categoryId;
    private String categoryName;

    public Category(){}

    public Category(int categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    public void setId(int id){
        this.categoryId = id;
    }
    public void setName(String name){
        this.categoryName = name;
    }
    public int getId() {
        return categoryId;
    }
    public String getName() {
        return categoryName;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return categoryId == category.categoryId &&
                Objects.equals(categoryName, category.categoryName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryId, categoryName);
    }

    @Override
    public String toString() {
        return "Category{" +
                "categoryId=" + categoryId +
                ", categoryName='" + categoryName +
                '}';
    }
}
