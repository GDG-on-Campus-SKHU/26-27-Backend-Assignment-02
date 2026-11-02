MenuControllerpackage com.example.jpaexample.controller;

import com.example.jpaexample.dto.CategoryInfoResponseDto;
import com.example.jpaexample.dto.CategorySaveRequestDto;
import com.example.jpaexample.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryInfoResponseDto> saveCategory(
            @RequestBody CategorySaveRequestDto categorySaveRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(categoryService.saveCategory(categorySaveRequestDto));
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long categoryId) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping
    public ResponseEntity<List<CategoryInfoResponseDto>> getAllCategory() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(categoryService.getAllCategory());
    }
}
