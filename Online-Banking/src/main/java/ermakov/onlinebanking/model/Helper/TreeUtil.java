package ermakov.onlinebanking.model.Helper;

import ermakov.onlinebanking.model.Category;
import ermakov.onlinebanking.model.Subcategory;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.util.List;
import java.util.Map;

public class TreeUtil {

    public static void fillTree(JTree tree, Map<Category, List<Subcategory>> categories) {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Categories");

        for (Map.Entry<Category, List<Subcategory>> entry : categories.entrySet()) {
            Category category = entry.getKey();
            DefaultMutableTreeNode categoryNode = new DefaultMutableTreeNode(category.getName());

            for (Subcategory subcategory : entry.getValue()) {
                DefaultMutableTreeNode subcategoryNode = new DefaultMutableTreeNode(subcategory.getName());
                categoryNode.add(subcategoryNode);
            }

            root.add(categoryNode);
        }

        DefaultTreeModel model = new DefaultTreeModel(root);
        tree.setModel(model);
    }
}