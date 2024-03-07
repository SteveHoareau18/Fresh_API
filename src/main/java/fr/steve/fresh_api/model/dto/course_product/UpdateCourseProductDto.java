package fr.steve.fresh_api.model.dto.course_product;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCourseProductDto {

    @Size(max = 50)
    private String commentary;
}
