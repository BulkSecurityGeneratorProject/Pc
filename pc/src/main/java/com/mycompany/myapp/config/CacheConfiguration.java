package com.mycompany.myapp.config;

import io.github.jhipster.config.JHipsterProperties;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.ehcache.jsr107.Eh107Configuration;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
@AutoConfigureAfter(value = { MetricsConfiguration.class })
@AutoConfigureBefore(value = { WebConfigurer.class, DatabaseConfiguration.class })
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(Expirations.timeToLiveExpiration(Duration.of(ehcache.getTimeToLiveSeconds(), TimeUnit.SECONDS)))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(com.mycompany.myapp.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Ordenador.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Procesador.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Procesador.class.getName() + ".ordenadors", jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.DiscoDuro.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.DiscoDuro.class.getName() + ".ordenadors", jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Ram.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Ram.class.getName() + ".ordenadors", jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Ssd.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Ssd.class.getName() + ".ordenadors", jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Optico.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Optico.class.getName() + ".ordenadors", jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Graficas.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Graficas.class.getName() + ".ordenadors", jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Alimentacion.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Alimentacion.class.getName() + ".ordenadors", jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Placa.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Placa.class.getName() + ".ordenadors", jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Fabricante.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Fabricante.class.getName() + ".discoDuros", jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Fabricante.class.getName() + ".rams", jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Fabricante.class.getName() + ".graficas", jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Fabricante.class.getName() + ".alimentacions", jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Fabricante.class.getName() + ".ssds", jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Fabricante.class.getName() + ".opticos", jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Fabricante.class.getName() + ".placas", jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Fabricante.class.getName() + ".procesadors", jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
