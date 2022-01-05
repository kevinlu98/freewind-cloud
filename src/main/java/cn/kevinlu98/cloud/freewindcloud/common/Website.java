package cn.kevinlu98.cloud.freewindcloud.common;

import cn.kevinlu98.cloud.freewindcloud.common.enums.Site;
import cn.kevinlu98.cloud.freewindcloud.mapper.OptionMapper;
import cn.kevinlu98.cloud.freewindcloud.pojo.Option;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
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
@CacheConfig(cacheNames = {"site_config"})
public class Website {

    @Autowired
    private OptionMapper optionMapper;

    @Cacheable(key = "targetClass + methodName")
    public String title() {
        return getValue(Site.SITE_TITLE);
    }

    @Cacheable(key = "targetClass + methodName")
    public String desc() {
        return getValue(Site.SITE_DESC);
    }

    @Cacheable(key = "targetClass + methodName")
    public String keywords() {
        return getValue(Site.SITE_KEYWORDS);
    }

    @Cacheable(key = "targetClass + methodName")
    public String icon() {
        return getValue(Site.SITE_ICON);
    }

    @Cacheable(key = "targetClass + methodName")
    public String logoWhite() {
        return getValue(Site.SITE_LOGO_WHITE);
    }

    @Cacheable(key = "targetClass + methodName")
    public String logoBlank() {
        return getValue(Site.SITE_LOGO_BLANK);
    }

    @Cacheable(key = "targetClass + methodName")
    public String avatarSize() {
        return getValue(Site.SITE_AVATAR_SIZE);
    }

    @Cacheable(key = "targetClass + methodName")
    public String defaultMaxSize() {
        return getValue(Site.SITE_DEFAULT_MAX_SIZE);
    }

    @Cacheable(key = "targetClass + methodName")
    public String uploadLimit() {
        return getValue(Site.SITE_UPLOAD_LIMIT);
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
