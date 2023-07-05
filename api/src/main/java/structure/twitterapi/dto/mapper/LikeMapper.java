package structure.twitterapi.dto.mapper;

import org.springframework.stereotype.Component;
import structure.twitterapi.dto.LikeResponseDto;
import structure.twitterapi.model.Like;

@Component
public class LikeMapper {
    public LikeResponseDto toDto(Like like) {
        return new LikeResponseDto(
                like.getId(),
                like.getUser().getId(),
                like.getPost().getId()
        );
    }
}
