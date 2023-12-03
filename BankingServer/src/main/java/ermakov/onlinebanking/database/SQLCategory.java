package ermakov.onlinebanking.database;

import ermakov.onlinebanking.model.Category;
import ermakov.onlinebanking.model.Subcategory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SQLCategory implements ICategory {
    private static SQLCategory instance;
    private ConnectionDB dbConnection = ConnectionDB.getInstance();

    public SQLCategory(){

    }

    public static synchronized SQLCategory getInstance() {
        if (instance == null) {
            instance = new SQLCategory();
        }
        return instance;
    }

    public Map<Category, List<Subcategory>> getCategotyes() {
        Map<Category, List<Subcategory>> categoriesWithSubcategories = new HashMap<>();
        String queryCategories = "SELECT * FROM categories";

        ArrayList<String[]> result = this.dbConnection.getArrayResult(queryCategories);

        for (String[] items : result) {
            Category category = new Category();
            category.setId(Integer.parseInt(items[0]));
            category.setName(items[1]);

            String querySubcategories = "SELECT * FROM subcategories WHERE categoryid = " + category.getId();
            ArrayList<String[]> resultSubcategories = this.dbConnection.getArrayResult(querySubcategories);

            List<Subcategory> subcategories = new ArrayList<>();
            for (String[] subItems : resultSubcategories) {
                Subcategory subcategory = new Subcategory();
                subcategory.setId(Integer.parseInt(subItems[0]));
                subcategory.setName(subItems[1]);
                subcategory.setCategoryId(Integer.parseInt(subItems[2]));
                subcategories.add(subcategory);
            }

            categoriesWithSubcategories.put(category, subcategories);
        }
        //System.out.println(categoriesWithSubcategories);
        return categoriesWithSubcategories;
    }

    public boolean deleteCategoryOrSubcategory(String name) {
        String queryCategory = "SELECT * FROM categories WHERE categoryname = '" + name + "'";
        ArrayList<String[]> resultCategory = this.dbConnection.getArrayResult(queryCategory);
        if (!resultCategory.isEmpty()) {
            String categoryId = resultCategory.get(0)[0];
            String deleteCategoryQuery = "DELETE FROM categories WHERE categoryid = " + categoryId;
            this.dbConnection.execute(deleteCategoryQuery);
            String deleteSubcategoriesQuery = "DELETE FROM subcategories WHERE categoryid = " + categoryId;
            this.dbConnection.execute(deleteSubcategoriesQuery);
            return true;
        } else {
            String querySubcategory = "SELECT * FROM subcategories WHERE subcategoryname = '" + name + "'";
            ArrayList<String[]> resultSubcategory = this.dbConnection.getArrayResult(querySubcategory);
            if (!resultSubcategory.isEmpty()) {
                String subcategoryId = resultSubcategory.get(0)[0];
                String deleteSubcategoryQuery = "DELETE FROM subcategories WHERE subcategoryid = " + subcategoryId;
                this.dbConnection.execute(deleteSubcategoryQuery);
                return true;
            } else {
                System.out.println("Категория или подкатегория с именем '" + name + "' не найдена.");
                return false;
            }
        }
    }

    public boolean editCategoryOrSubcategory(String oldName, String newName) {
        String queryCategory = "SELECT * FROM categories WHERE categoryname = '" + oldName + "'";
        ArrayList<String[]> resultCategory = this.dbConnection.getArrayResult(queryCategory);

        if (!resultCategory.isEmpty()) {
            String categoryId = resultCategory.get(0)[0];
            String updateCategoryQuery = "UPDATE categories SET categoryname = '" + newName + "' WHERE categoryid = " + categoryId;
            this.dbConnection.execute(updateCategoryQuery);
            return true;
        } else {
            String querySubcategory = "SELECT * FROM subcategories WHERE subcategoryname = '" + oldName + "'";
            ArrayList<String[]> resultSubcategory = this.dbConnection.getArrayResult(querySubcategory);
            if (!resultSubcategory.isEmpty()) {
                String subcategoryId = resultSubcategory.get(0)[0];
                String updateSubcategoryQuery = "UPDATE subcategories SET subcategoryname = '" + newName + "' WHERE subcategoryid = " + subcategoryId;
                this.dbConnection.execute(updateSubcategoryQuery);
                return true;
            } else {
                System.out.println("Категория или подкатегория с именем '" + oldName + "' не найдена.");
                return false;
            }
        }
    }

    public boolean createCategory(String name) {
        String getMaxCategoryIDQuery = "SELECT MAX(categoryid) FROM categories";
        ArrayList<String[]> resultMaxCategoryID = this.dbConnection.getArrayResult(getMaxCategoryIDQuery);
        int nextCategoryID = 1;
        if (!resultMaxCategoryID.isEmpty()) {
            String maxCategoryIDString = resultMaxCategoryID.get(0)[0];
            if (maxCategoryIDString != null) {
                nextCategoryID = Integer.parseInt(maxCategoryIDString) + 1;
            }
        }
        String insertCategoryQuery = "INSERT INTO categories (categoryid, categoryname) VALUES (" + nextCategoryID + ", '" + name + "')";
        this.dbConnection.execute(insertCategoryQuery);
        return true;
    }

    public boolean createSubcategory(String name, String categoryName) {
        String getMaxSubcategoryIDQuery = "SELECT MAX(subcategoryid) FROM subcategories";
        ArrayList<String[]> resultMaxSubcategoryID = this.dbConnection.getArrayResult(getMaxSubcategoryIDQuery);
        int nextSubcategoryID = 1;
        if (!resultMaxSubcategoryID.isEmpty()) {
            String maxSubcategoryIDString = resultMaxSubcategoryID.get(0)[0];
            if (maxSubcategoryIDString != null) {
                nextSubcategoryID = Integer.parseInt(maxSubcategoryIDString) + 1;
            }
        }
        String getCategoryIDQuery = "SELECT categoryid FROM categories WHERE categoryname = '" + categoryName + "'";
        ArrayList<String[]> resultCategoryID = this.dbConnection.getArrayResult(getCategoryIDQuery);
        if (!resultCategoryID.isEmpty()) {
            String categoryID = resultCategoryID.get(0)[0];
            String insertSubcategoryQuery = "INSERT INTO subcategories (subcategoryid, subcategoryname, categoryid) VALUES (" + nextSubcategoryID + ", '" + name + "', " + categoryID + ")";
            this.dbConnection.execute(insertSubcategoryQuery);
            String checkSubcategoryQuery = "SELECT * FROM subcategories WHERE subcategoryname = '" + name + "' AND categoryid = " + categoryID;
            ArrayList<String[]> resultSubcategory = this.dbConnection.getArrayResult(checkSubcategoryQuery);
            return !resultSubcategory.isEmpty();
        } else {
            System.out.println("Категория с именем '" + categoryName + "' не найдена.");
            return false;
        }
    }
}
