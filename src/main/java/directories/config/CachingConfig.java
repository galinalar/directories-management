package directories.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

/**
 * Configuration for caching.
 */

@Configuration
@EnableCaching
public class CachingConfig {

    @Bean
    public CacheManager cacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(Arrays.asList(
                new ConcurrentMapCache("findById"),
                new ConcurrentMapCache("findAllRegions"),
                new ConcurrentMapCache("findByName"),
                new ConcurrentMapCache("findByShortName"),
                new ConcurrentMapCache("findByNameAndShortName")));
        return cacheManager;
    }
}
