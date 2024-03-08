package fr.steve.fresh_api.model.dto.course_product;

import com.fasterxml.jackson.annotation.JsonProperty;

import fr.steve.fresh_api.model.dto.product.CreateProductDto;

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
    @JsonProperty("product_id")
    private Integer productId;
    private CreateProductDto product;
}
