package structure.twitterapi.dto.mapper;

import org.springframework.stereotype.Component;
import structure.twitterapi.dto.PostResponseDto;
import structure.twitterapi.model.Like;
import structure.twitterapi.model.Post;

@Component
public class PostMapper {

    public PostResponseDto toDto(Post post) {
        return new PostResponseDto(
                post.getId(),
                post.getImagePath(),
                post.getDescription(),
                post.getUser().getId(),
                post.getLikes().stream().map(Like::getId).toList(),
                post.getDateCreating()
        );
    }
}
