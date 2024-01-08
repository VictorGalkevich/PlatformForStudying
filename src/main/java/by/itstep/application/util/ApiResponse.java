package by.itstep.application.util;

import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
public class ApiResponse<T> {
    private T result;
    private boolean error;
    public ApiResponse(T result, boolean error) {
        this.result = result;
        this.error = error;
    }
    public static <T> ApiResponse<T> success(T result) {
        return new ApiResponse<>(result, false);
    }
    public static <T> ApiResponse<T> error(T result) {
        return new ApiResponse<>(result, true);
    }
}
