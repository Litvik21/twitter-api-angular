package structure.twitterapi.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import structure.twitterapi.dto.LikeResponseDto;
import structure.twitterapi.dto.mapper.LikeMapper;
import structure.twitterapi.service.LikeService;

@AllArgsConstructor
@RestController
@RequestMapping("/likes")
public class LikeController {
    private final LikeService service;
    private final LikeMapper mapper;

    @GetMapping("/{id}")
    public LikeResponseDto getById(@PathVariable(name = "id") Long id) {
        return mapper.toDto(service.get(id));
    }
}
