package ermakov.onlinebanking.model;

import java.io.Serializable;
import java.util.Objects;

public class Subcategory implements Serializable {
    private int subcategoryId;
    private String subcategoryName;

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    private int categoryId;

    public Subcategory(){}

    public Subcategory(int subcategoryId, String subcategoryName, int categoryId) {
        this.subcategoryId = subcategoryId;
        this.subcategoryName = subcategoryName;
        this.categoryId = categoryId;
    }

    public int getId() {
        return subcategoryId;
    }

    public void setId(int subcategoryId) {
        this.subcategoryId = subcategoryId;
    }

    public String getName() {
        return subcategoryName;
    }

    public void setName(String subcategoryName) {
        this.subcategoryName = subcategoryName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subcategory that = (Subcategory) o;
        return subcategoryId == that.subcategoryId &&
                categoryId == that.categoryId &&
                Objects.equals(subcategoryName, that.subcategoryName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subcategoryId, subcategoryName, categoryId);
    }

    @Override
    public String toString() {
        return "Subcategory{" +
                "subcategoryId=" + subcategoryId +
                ", subcategoryName='" + subcategoryName + '\'' +
                ", categoryId=" + categoryId +
                '}';
    }
}
