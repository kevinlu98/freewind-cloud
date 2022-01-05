package cn.kevinlu98.cloud.freewindcloud.mapper;

import cn.kevinlu98.cloud.freewindcloud.pojo.Option;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Author: Mr丶冷文
 * Date: 2022-01-04 20:54
 * Email: kevinlu98@qq.com
 * Description:
 */

public interface OptionMapper extends JpaRepository<Option, Integer> {
    Option findOptionByName(String name);
}
