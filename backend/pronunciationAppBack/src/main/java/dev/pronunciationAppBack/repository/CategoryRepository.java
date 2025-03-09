package dev.pronunciationAppBack.repository;

import dev.pronunciationAppBack.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,String> {
    Category getCategoryByCategoryName (String categoryName);
    Category getCategoryBySubCategoryName (String subCategoryName);
}
