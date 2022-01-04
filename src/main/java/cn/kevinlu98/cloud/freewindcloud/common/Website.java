package cn.kevinlu98.cloud.freewindcloud.common;

import cn.kevinlu98.cloud.freewindcloud.common.enums.Site;
import cn.kevinlu98.cloud.freewindcloud.mapper.OptionMapper;
import cn.kevinlu98.cloud.freewindcloud.pojo.Option;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Author: Mr丶冷文
 * Date: 2022-01-04 20:53
 * Email: kevinlu98@qq.com
 * Description:
 */
@Slf4j
@Component
public class Website {

    @Autowired
    private OptionMapper optionMapper;

    public String title() {
        return getValue(Site.SITE_TITLE);
    }

    public String desc() {
        return getValue(Site.SITE_DESC);
    }

    public String keywords() {
        return getValue(Site.SITE_KEYWORDS);
    }

    public String icon() {
        return getValue(Site.SITE_ICON);
    }
    public String logoWhite() {
        return getValue(Site.SITE_LOGO_WHITE);
    }
    public String logoBlank() {
        return getValue(Site.SITE_LOGO_BLANK);
    }

    public String getValue(Site site) {
        Optional<Option> option = optionMapper.findById(site.getId());
        Option siteOpt = option.orElse(null);
        if (siteOpt == null) {
            siteOpt = new Option(site);
            optionMapper.save(siteOpt);
        }
        return siteOpt.getValue();
    }
}
