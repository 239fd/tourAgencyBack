package by.bsuir.touragency.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseDto {
    private String accessToken;
    private Long userId;
    private String role;
}