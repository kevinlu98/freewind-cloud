package cn.kevinlu98.cloud.freewindcloud.service;

import cn.kevinlu98.cloud.freewindcloud.common.PojoUtils;
import cn.kevinlu98.cloud.freewindcloud.mapper.DriverMapper;
import cn.kevinlu98.cloud.freewindcloud.pojo.Driver;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Author: Mr丶冷文
 * Date: 2022-01-08 17:38
 * Email: kevinlu98@qq.com
 * Description:
 */
@Service
public class DriverService {
    @Autowired
    private DriverMapper driverMapper;

    public List<Driver> find() {
        return driverMapper.findAll();
    }

    public Boolean exist(Driver driver) {
        int type = driver.getType();
        String path = driver.getPath().trim();
        String name = driver.getName().trim();
        return driverMapper.countByTypeAndPathAndName(type, path, name) > 0;
    }

    public Driver activeDriver() {
        return driverMapper.findByIsDefalut(1);
    }

    public void save(Driver driver) {
        driver.setName(driver.getName().trim());
        driver.setMaxSize(driver.getMaxSize().trim());
        String path = driver.getPath().trim();
        if (path.endsWith("/")) {
            path.substring(0, path.length() - 1);
        }
        driver.setPath(driver.getPath().trim());
        if (driver.getId() == null) {
            driverMapper.save(driver);
        }
        if (CollectionUtils.isEmpty(driverMapper.findAllByIsDefalut(1))) {
            driver.setIsDefalut(1);
        } else {
            driver.setIsDefalut(2);
        }
        Optional<Driver> driverOpt = driverMapper.findById(driver.getId());
        if (driverOpt.isPresent()) {
            Driver driverDb = driverOpt.get();
            PojoUtils.beanCopyWithIngore(driver, driverDb);
            driverMapper.save(driverDb);
        }
    }
}