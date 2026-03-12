package org.springframework.samples.petclinic.system;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@EnableCaching
public class CacheConfiguration {
    // Deixamos vazio para não dar erro de compilação com pacotes que não existem
}
