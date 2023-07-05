package structure.twitterapi.dto;

import java.time.LocalDateTime;
import java.util.List;

public record PostResponseDto (Long id,
                               String imagePath,
                               Long userId,
                               List<Long> likeIds,
                               LocalDateTime dateCreating) {

}
