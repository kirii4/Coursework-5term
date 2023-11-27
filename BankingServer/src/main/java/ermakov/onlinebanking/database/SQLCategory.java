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
}
