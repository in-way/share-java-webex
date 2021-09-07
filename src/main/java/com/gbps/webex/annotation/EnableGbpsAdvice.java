

package com.gbps.webex.annotation;

import com.gbps.webex.GbpsAdviceAutoConfigure;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(GbpsAdviceAutoConfigure.class)
public @interface EnableGbpsAdvice {
}
