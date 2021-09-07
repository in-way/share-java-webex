

package com.gbps.webex;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gbps.webex.helper.JacksonHelper;
import com.gbps.webex.core.ExceptionHandlerManager;
import com.gbps.webex.core.ExceptionHandlerRegistry;
import com.gbps.webex.core.ValidHandlerManager;
import com.gbps.webex.core.ValidHandlerRegistry;
import com.gbps.webex.log.DefaultWebExLogHandler;
import com.gbps.webex.log.WebExLogHandler;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.util.List;

@Configuration
public class GbpsWebExConfigure implements ApplicationContextAware, ApplicationListener<ContextRefreshedEvent> {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (applicationContext == contextRefreshedEvent.getApplicationContext()) {
            try {
                RequestMappingHandlerAdapter adapter = applicationContext.getBean(RequestMappingHandlerAdapter.class);
                ObjectMapper objectMapper = applicationContext.getBean(ObjectMapper.class);
                changeObjectMapperInRequestMappingHandlerAdapter(adapter, objectMapper);
            } catch (Exception ex) {
                // expected
                //... do thing
                // ex.printStackTrace();
            }
        }
    }

    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = JacksonHelper.buildJsr310ObjectMapper();
        return objectMapper;
    }

    @Bean
    public ValidHandlerManager validMessageManager() {
        ValidHandlerRegistry registry = new ValidHandlerRegistry();
        registerValid(registry);

        ValidHandlerManager validMessageManager = new ValidHandlerManager();
        validMessageManager.init(registry);
        return validMessageManager;
    }

    @Bean
    public ExceptionHandlerManager exceptionHandlerManager() {
        ExceptionHandlerRegistry registry = new ExceptionHandlerRegistry();
        registerExceptions(registry);
        ExceptionHandlerManager exceptionHandlerManager = new ExceptionHandlerManager();
        exceptionHandlerManager.init(registry);
        return exceptionHandlerManager;
    }

    /**
     * 默认返回webex的log处理器，如需改变日志输出内容，请重写这个方法。
     * @return WebExLogHandler 的实现。
     */
    @Bean
    public WebExLogHandler webExLogHandler() {
        return new DefaultWebExLogHandler();
    }

    /**
     * 判定是否是开发模式。
     * 开发模式时，会将内部异常返回至浏览器前台。
     * @return true开发模式/false非开发模式。
     */
    public boolean isDev() {
        return false;
    }

    /**
     * 注册额外的Exception处理时，重写此方法。
     * @param registry 异常注册对象，参见registry的registerException方法。
     */
    protected void registerExceptions(ExceptionHandlerRegistry registry) {
        // default nothing...
    }

    /**
     * 注册额外的valid处理时，重写此方法。
     * @param registry 校验规则注册对象，参见registry的registerValidTrans方法。
     */
    protected void registerValid(ValidHandlerRegistry registry) {
        // default nothing...
    }

    /**
     * 设置ObjectMapper 到 RequestMappingHandlerAdapter。
     * Spring4 兼容写法，SpringBoot 可以直接通过生成Bean即可。
     */
    private void changeObjectMapperInRequestMappingHandlerAdapter(RequestMappingHandlerAdapter adapter, ObjectMapper objectMapper) {
        if (adapter == null) {
            return;
        }
        List<HttpMessageConverter<?>> messageConverters = adapter.getMessageConverters();
        if (messageConverters == null) {
            return;
        }
        for (HttpMessageConverter<?> converter : messageConverters) {
            if (converter instanceof MappingJackson2HttpMessageConverter) {
                ((MappingJackson2HttpMessageConverter) converter).setObjectMapper(objectMapper);
            }
        }
    }
}

