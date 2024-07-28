package sync.oauth2.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Setter
@Getter
@Builder
public class GetMemberFromTaskResponseDto {
    private List<Long> userIds;

}
