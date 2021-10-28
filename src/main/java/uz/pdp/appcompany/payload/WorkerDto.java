package uz.pdp.appcompany.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class WorkerDto {

    private String fullName;
    private String phoneNumber;
    @NotNull(message = "Street must not  be null")
    private String street;
    private String homeNumber;
    private Integer departmentId;
}
