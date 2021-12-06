package com.iscas.base.biz.config.i18n;

import cn.hutool.core.util.StrUtil;
import com.iscas.common.tools.constant.HeaderKey;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.util.Locale;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/12/6 13:31
 * @since jdk1.8
 */
@Configuration
public class I18nConfig {

    @Bean
    public LocaleResolver localeResolver() {
        return new I18nLocaleResolver();
    }

    /**
     * 获取请求头国际化信息
     */
    static class I18nLocaleResolver implements LocaleResolver {

        @NotNull
        @Override
        public Locale resolveLocale(HttpServletRequest httpServletRequest) {
            String language = httpServletRequest.getHeader(HeaderKey.CONTENT_LANGUAGE);
            Locale locale = Locale.getDefault();
            if (StrUtil.isNotBlank(language)) {
                String[] split = language.split("_");
                locale = new Locale(split[0], split[1]);
            }
            return locale;
        }

        @Override
        public void setLocale(@NotNull HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Locale locale) {

        }
    }
}
