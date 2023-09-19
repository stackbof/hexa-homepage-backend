package pro.hexa.backend.main.api.domain.news.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AdminNewsDto {

    @Schema(description = "뉴스 id")
    private Long newsId;

    @Schema(description = "뉴스 종류")
    private String newsType;

    @Schema(description = "뉴스 제목")
    private String title;

    @Schema(description = "뉴스 날짜")
    private Date date;
}