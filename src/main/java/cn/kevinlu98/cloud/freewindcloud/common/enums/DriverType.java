package cn.kevinlu98.cloud.freewindcloud.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Author: Mr丶冷文
 * Date: 2022-01-08 17:21
 * Email: kevinlu98@qq.com
 * Description:
 */
@Getter
@AllArgsConstructor
public enum DriverType {
    DRIVER_LOCAL(1, "本地云盘", "/static/images/driver/local.png"),
    ;
    private final Integer value;
    private final String desc;
    private final String icon;

    public static DriverType find(Integer value) {
        List<DriverType> drivers = Arrays.stream(DriverType.values()).filter(x -> x.value.equals(value)).collect(Collectors.toList());
        return drivers.size() > 0 ? drivers.get(0) : DriverType.DRIVER_LOCAL;
    }
}
