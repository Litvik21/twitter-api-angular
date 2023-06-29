package structure.twitterapi.dto;

import java.time.LocalDateTime;
import java.util.List;

public record PostResponseDto (String imagePath,
                               Long userId,
                               List<Long> likeIds,
                               LocalDateTime dateCreating) {

}
