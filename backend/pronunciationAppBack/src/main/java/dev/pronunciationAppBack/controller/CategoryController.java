package dev.pronunciationAppBack.controller;

import dev.pronunciationAppBack.model.Category;
import dev.pronunciationAppBack.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();

        if (categories.isEmpty()) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(categories);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable String id){

        Optional<Category> category = categoryService.getCategoryById(id);

        return category.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());

    }

    @PostMapping("/createCategory")
    public Category createCategory(@RequestBody Category category){

        return categoryService.createCategory(category);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable String id, @RequestBody Category category){

        if(!categoryService.categoryExistsById(id)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        }

        Category updatedCategory = categoryService.updateCategory(category);

        return ResponseEntity.ok(updatedCategory);
    }

    @DeleteMapping
    public String deleteAllCategories(){

        categoryService.deleteAllCategories();

        return "All categories deleted!";
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategoryById(@PathVariable String idToDelete){

        if(!categoryService.categoryExistsById(idToDelete)){

            return  ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        categoryService.categoryExistsById(idToDelete);

        return  ResponseEntity.ok("Level deleted!");
    }




}
