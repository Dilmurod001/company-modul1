package uz.pdp.appcompany.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appcompany.entity.Company;
import uz.pdp.appcompany.payload.CompanyDto;
import uz.pdp.appcompany.payload.ResponseApi;
import uz.pdp.appcompany.service.CompanyService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/company")
public class CompanyController {

    @Autowired
    CompanyService companyService;

    @PostMapping
    public ResponseEntity<ResponseApi> add(@Valid @RequestBody CompanyDto companyDto) {

        ResponseApi add = companyService.add(companyDto);
        if (add.isStatus()) {
            return ResponseEntity.status(201).body(add);
        }
        return ResponseEntity.status(409).body(add);
    }

    @GetMapping
    public List<Company> getAll() {
        List<Company> all = companyService.getAll();
        return all;
    }

    @GetMapping("/{id}")
    public Company getOneById(@PathVariable Integer id) {
        Company oneById = companyService.getOneById(id);
        return oneById;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseApi> delete(@PathVariable Integer id) {
        ResponseApi delete = companyService.delete(id);
        if (delete.isStatus()) {
            return ResponseEntity.status(202).body(delete);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(delete);

    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseApi> edit(@PathVariable Integer id, @Valid @RequestBody CompanyDto companyDto) {
        ResponseApi edit = companyService.edit(id, companyDto);
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
