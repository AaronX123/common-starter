package aaron.common.data.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author xiaoyouming
 * @version 1.0
 * @since 2020-03-03
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonRequest<T> {
    private String version;
    private String token;
    @Valid
    @NotNull
    private T data;

}
