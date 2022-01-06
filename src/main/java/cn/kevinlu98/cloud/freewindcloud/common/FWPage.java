package cn.kevinlu98.cloud.freewindcloud.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Author: Mr丶冷文
 * Date: 2022-01-06 23:53
 * Email: kevinlu98@qq.com
 * Description:
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FWPage<T> {
    private int page;
    private int totalPage;
    private long total;
    List<T> data;
}
