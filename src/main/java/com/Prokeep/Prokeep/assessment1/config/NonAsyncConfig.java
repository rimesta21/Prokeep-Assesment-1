package com.Prokeep.Prokeep.assessment1.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
// No @EnableAsync annotation
@Profile("non-async")
public class NonAsyncConfig {
}
