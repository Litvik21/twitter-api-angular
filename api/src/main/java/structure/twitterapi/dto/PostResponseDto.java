package structure.twitterapi.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class PostResponseDto {
    private String imagePath;
    private Long userId;
    private Long likeId;
    private LocalDate dateCreating;
}
