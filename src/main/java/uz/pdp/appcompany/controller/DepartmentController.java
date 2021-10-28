package uz.pdp.appcompany.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appcompany.entity.Department;
import uz.pdp.appcompany.payload.DepartmentDto;
import uz.pdp.appcompany.payload.ResponseApi;
import uz.pdp.appcompany.service.DepartmentService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/department")
public class DepartmentController {
    @Autowired
    DepartmentService departmentService;

    @PostMapping
    public ResponseEntity<ResponseApi> add(@Valid @RequestBody DepartmentDto departmentDto) {
        ResponseApi add = departmentService.add(departmentDto);
        if (add.isStatus()) {
            return ResponseEntity.status(202).body(add);
        }
        return ResponseEntity.status(409).body(add);
    }

    @GetMapping
    public List<Department> getAll() {
        List<Department> all = departmentService.getAll();
        return all;
    }

    @GetMapping("/{id}")
    public Department getOneById(@PathVariable Integer id) {
        Department oneById = departmentService.getOneById(id);
        return oneById;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseApi> delete(@PathVariable Integer id) {
        ResponseApi delete = departmentService.delete(id);
        if (delete.isStatus()) {
            return ResponseEntity.status(202).body(delete);
        }
        return ResponseEntity.status(409).body(delete);

    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseApi> edit(@PathVariable Integer id, @Valid @RequestBody DepartmentDto departmentDto) {
        ResponseApi edit = departmentService.edit(id, departmentDto);
        if (edit.isStatus()) {
            return ResponseEntity.status(202).body(edit);
        }
        return ResponseEntity.status(409).body(edit);
    }



    //for displaying validation message on console
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
