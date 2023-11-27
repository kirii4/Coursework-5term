package ermakov.onlinebanking.database;

import ermakov.onlinebanking.model.Category;
import ermakov.onlinebanking.model.Subcategory;

import java.util.List;
import java.util.Map;

public interface ICategory {
    public abstract Map<Category, List<Subcategory>> getCategotyes();
}
