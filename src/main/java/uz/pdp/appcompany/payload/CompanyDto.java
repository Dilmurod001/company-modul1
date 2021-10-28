package uz.pdp.appcompany.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyDto {
    @NotNull(message = "Corporation name must not be empty")
    private String corpName;
    @NotNull(message = "DirectorName must not be empty")
    private String directorName;

    @NotNull(message = "Street must not  be null")
    private String street;
    private String homeNumber;
}
