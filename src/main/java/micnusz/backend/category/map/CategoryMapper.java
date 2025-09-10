package micnusz.backend.category.map;

import micnusz.backend.category.Category;
import micnusz.backend.category.dto.CategoryApiDto;
import micnusz.backend.category.dto.CategoryResponseDto;

public class CategoryMapper {

    public static Category toDomain(CategoryApiDto dto) {
        return new Category(
                dto.id(),
                dto.name(),
                dto.image(),
                dto.slug());
    }

    public static CategoryResponseDto toResponse(Category category) {
        return new CategoryResponseDto(category.id(), category.name(), category.image(), category.slug());
    }
}
