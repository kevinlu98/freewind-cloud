package cn.kevinlu98.cloud.freewindcloud.service;

import cn.kevinlu98.cloud.freewindcloud.common.enums.Site;
import cn.kevinlu98.cloud.freewindcloud.mapper.OptionMapper;
import cn.kevinlu98.cloud.freewindcloud.pojo.Option;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Author: Mr丶冷文
 * Date: 2022-01-06 01:06
 * Email: kevinlu98@qq.com
 * Description:
 */
@Service
public class OptionService {

    @Autowired
    private OptionMapper optionMapper;

    public void update(String name, String value) {
        Option option = optionMapper.findOptionByName(name);
        if (option == null) {
            return;
        }
        option.setValue(value);
        optionMapper.save(option);
    }
}
