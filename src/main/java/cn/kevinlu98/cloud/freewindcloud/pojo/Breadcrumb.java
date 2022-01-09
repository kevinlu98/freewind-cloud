package cn.kevinlu98.cloud.freewindcloud.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Author: Mr丶冷文
 * Date: 2022-01-09 00:43
 * Email: kevinlu98@qq.com
 * Description:
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Breadcrumb {
    private String show;
    private String path;
}
