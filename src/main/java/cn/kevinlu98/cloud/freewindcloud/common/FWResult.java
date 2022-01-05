package cn.kevinlu98.cloud.freewindcloud.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * Author: 鲁恺文
 * Date: 2022-01-04 16:32
 * Email: lukaiwen@xiaomi.com
 * Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
public class FWResult<T> {
    private boolean success;
    private String message;
    private T data;

    public static <T> FWResult<T> success(String message) {
        return FWResult.<T>builder().success(true).message(message).build();
    }

    public static <T> FWResult<T> success(String message, T data) {
        return FWResult.<T>builder().success(true).message(message).data(data).build();
    }

    public static <T> FWResult<T> fail(String message) {
        return FWResult.<T>builder().success(false).message(message).build();
    }
}
