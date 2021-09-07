

package com.gbps.webex;

import com.gbps.webex.advice.AbstractShareExceptionHandlerAdvice;
import com.gbps.webex.advice.AbstractShareResponseBodyAdvice;
import com.gbps.webex.annotation.GbpsRest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.ControllerAdvice;


@Configuration
public class GbpsAdviceAutoConfigure {

     @Bean
     public AbstractShareExceptionHandlerAdvice shareExceptionHandlerAdvice() {
         return new AutoShareExceptionAdvice();
     }

     @Bean
     public AbstractShareResponseBodyAdvice shareResponseBodyAdvice() {
         return new AutoShareResponseBodyAdvice();
     }

    @ControllerAdvice(annotations = {GbpsRest.class})
    public static class AutoShareExceptionAdvice extends AbstractShareExceptionHandlerAdvice {

    }

    @ControllerAdvice(annotations = {GbpsRest.class})
    public static class AutoShareResponseBodyAdvice extends AbstractShareResponseBodyAdvice {

    }

}

