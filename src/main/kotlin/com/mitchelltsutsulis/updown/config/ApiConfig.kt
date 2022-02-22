package com.mitchelltsutsulis.updown.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix="api")
data class ApiConfig(var fileStoragePath: String = "/opt/updown/storage/")
