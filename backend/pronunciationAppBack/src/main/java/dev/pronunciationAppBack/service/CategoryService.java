package dev.pronunciationAppBack.service;

import dev.pronunciationAppBack.model.Category;
import dev.pronunciationAppBack.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public Category createCategory(Category category){

        return categoryRepository.save(category);
    }

    public List<Category> getAllCategories(){

        return categoryRepository.findAll();
    }

    public Optional<Category> getCategoryById(String id){

        return categoryRepository.findById(id);
    }

    public void deleteById(String id){

        categoryRepository.deleteById(id);
    }

    public void deleteAllCategories (){

        categoryRepository.deleteAll();
    }

    public Category updateCategory(Category category){

        return categoryRepository.save(category);
    }

    public boolean categoryExistsById(String id){

        return categoryRepository.existsById(id);
    }

    public Category getCategoryByName(String name){

        return categoryRepository.getCategoryByCategoryName(name);
    }

    public Category getCategoryBySubCategoryName(String subName){

        return categoryRepository.getCategoryBySubCategoryName(subName);
    }

}
