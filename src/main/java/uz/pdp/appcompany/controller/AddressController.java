package uz.pdp.appcompany.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appcompany.entity.Address;
import uz.pdp.appcompany.payload.AddressDto;
import uz.pdp.appcompany.payload.ResponseApi;
import uz.pdp.appcompany.service.AddressService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/address")
public class AddressController {

    @Autowired
    AddressService addressService;

    @PostMapping
    public ResponseEntity<ResponseApi> addAddress(@Valid @RequestBody AddressDto addressDto) {
        ResponseApi add = addressService.add(addressDto);
        if (!add.isStatus()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(add);
        }
        return ResponseEntity.status(201).body(add);

    }

    @GetMapping
    public ResponseEntity<List<Address>> getAll() {
        List<Address> all = addressService.getAll();
        return ResponseEntity.ok(all);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Address> getOneById(@PathVariable Integer id) {
        Address oneById = addressService.getOneById(id);
        return ResponseEntity.ok(oneById);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseApi> delete(@PathVariable Integer id) {
        ResponseApi delete = addressService.delete(id);
        if (delete.isStatus()) {
            return ResponseEntity.status(202).body(delete);
        }
        return ResponseEntity.status(409).body(delete);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseApi> edit(@PathVariable Integer id, @RequestBody AddressDto addressDto) {
        ResponseApi edit = addressService.edit(id, addressDto);
        if (edit.isStatus()) {
            return ResponseEntity.status(202).body(edit);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(edit);
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
