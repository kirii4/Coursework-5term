package ermakov.onlinebanking.database;

import ermakov.onlinebanking.model.Category;
import ermakov.onlinebanking.model.Subcategory;

import java.util.List;
import java.util.Map;

public interface ICategory {
    Map<Category, List<Subcategory>> getCategotyes();
    boolean deleteCategoryOrSubcategory(String name);
    boolean editCategoryOrSubcategory(String oldName, String newName);
    boolean createCategory(String name);
    boolean createSubcategory(String name, String category);
}
