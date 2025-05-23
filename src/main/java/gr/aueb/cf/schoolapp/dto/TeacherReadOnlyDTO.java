package gr.aueb.cf.schoolapp.dto;

import lombok.*;

//@NoArgsConstructor
//@AllArgsConstructor
//@Getter
//@Setter
//@Builder
//public class TeacherReadOnlyDTO {
//    private Long id;
//    private String vat;
//    private String firstname;
//    private String lastname;
//}

public record TeacherReadOnlyDTO(Long id, String vat, String firstname, String lastname) {}
