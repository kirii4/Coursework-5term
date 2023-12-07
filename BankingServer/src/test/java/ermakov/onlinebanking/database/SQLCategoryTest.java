package ermakov.onlinebanking.database;

import ermakov.onlinebanking.model.Category;
import ermakov.onlinebanking.model.Subcategory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class SQLCategoryTest {
    private SQLCategory sqlCategory;

    @Before
    public void setUp() {
        sqlCategory = SQLCategory.getInstance();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testGetCategories() {
        Map<Category, List<Subcategory>> categoriesWithSubcategories = sqlCategory.getCategotyes();
        assertNotNull(categoriesWithSubcategories);
        if (!categoriesWithSubcategories.isEmpty()) {
            for (Map.Entry<Category, List<Subcategory>> entry : categoriesWithSubcategories.entrySet()) {
                Category category = entry.getKey();
                List<Subcategory> subcategories = entry.getValue();
                System.out.println("Category: " + category.getName());
                for (Subcategory subcategory : subcategories) {
                    System.out.println("  Subcategory: " + subcategory.getName());
                }
            }
        } else {
            System.out.println("No categories found.");
        }
    }

    @Test
    public void testDeleteCategoryOrSubcategory() {
        String categoryNameToDelete = "Благотворительный сбор на лечение Савончику Вячеславу";
        assertTrue(!sqlCategory.deleteCategoryOrSubcategory(categoryNameToDelete));
    }

    @Test
    public void testEditCategoryOrSubcategory() {
        String oldName = "А1";
        String newName = "А2";
        assertTrue(!sqlCategory.editCategoryOrSubcategory(oldName, newName));
    }

    @Test
    public void testCreateCategory() {
        String categoryName = "NewTestCategory";
        assertTrue(sqlCategory.createCategory(categoryName));
    }

    @Test
    public void testCreateSubcategory() {
        String subcategoryName = "NewTestSubcategory";
        String categoryName = "TestCategory";
        assertTrue(!sqlCategory.createSubcategory(subcategoryName, categoryName));
    }
}
