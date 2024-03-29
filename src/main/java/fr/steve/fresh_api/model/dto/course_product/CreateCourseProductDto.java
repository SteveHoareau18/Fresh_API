package fr.steve.fresh_api.model.dto.course_product;

import fr.steve.fresh_api.model.dto.product.CreateProductDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateCourseProductDto {

    @NotBlank
    @Size(max = 50)
    private String commentary;

    private boolean taken = false;

    private CreateProductDto product;
}
