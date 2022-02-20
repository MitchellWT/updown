package com.mitchelltsutsulis.updown.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix="api")
data class ApiConfig(val fileStoragePath: String = "/opt/updown/storage/")
