package gr.aueb.cf.schoolapp.dto;//package gr.aueb.cf.schoolapp.dto;
//
//import jakarta.validation.constraints.NotNull;
//import jakarta.validation.constraints.Size;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//public class TeacherInsertDTO {
//
//    @NotNull(message = "Firstname is required")
//    @Size(min = 2, max = 255, message = "Firstname must be between 2-255 characters")
//    private String firstname;
//
//    @NotNull(message = "Lastname is required")
//    @Size(min = 2, max = 255, message = "Lastname must be between 2-255 characters")
//    private String lastname;
//
//    @NotNull(message = "Vat is required")
//    @Size(min = 9, message = "Vat must include at least 9 digits")
//    private String vat;
//}

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TeacherInsertDTO(@NotNull(message = "Firstname is required")
                               @Size(min = 2, max = 255, message = "Firstname must be between 2-255 chars") String firstname,
                               @Size(min = 2, max = 255, message = "Lastname must be between 2-255 chars")
                               @NotNull(message = "Lastname is required") String lastname,
                               @NotNull(message = "Vat is required")
                               @Size(min = 9, message = "Vat must include at least 9 digits") String vat) {}
