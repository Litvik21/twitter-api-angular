package structure.twitterapi.dto.mapper;

import org.springframework.stereotype.Component;
import structure.twitterapi.dto.PostResponseDto;
import structure.twitterapi.model.Post;

@Component
public class PostMapper {

    public PostResponseDto toDto(Post post) {
        PostResponseDto dto = new PostResponseDto();
        dto.setImagePath(post.getImagePath());
        dto.setUserId(post.getUser().getId());
        dto.setDateCreating(post.getDateCreating());

        return dto;
    }
}
