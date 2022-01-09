package cn.kevinlu98.cloud.freewindcloud.mapper;

import cn.kevinlu98.cloud.freewindcloud.pojo.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Author: Mr丶冷文
 * Date: 2022-01-08 17:37
 * Email: kevinlu98@qq.com
 * Description:
 */
public interface DriverMapper extends JpaRepository<Driver, Integer> {

    List<Driver> findAllByIsDefalut(Integer isDefault);

    Driver findByIsDefalut(Integer isDefault);

    Integer countByTypeAndPathAndName(Integer type, String path, String name);
}
