package gr.aueb.cf.schoolapp.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.UniqueElements;

public record StudentInsertDTO (@NotNull(message = "Firstname is required")
                                @Size(min = 5, max = 255, message = "Firstname must be between 5-255 chars") String firstname,
                                @NotNull(message = "Lastname is required")
                                @Size(min = 5, max = 255, message = "Lastname must be between 2-255 chars") String lastname,
                                String email,
                                String vat) {}
