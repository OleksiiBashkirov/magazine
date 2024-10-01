package bashkirov.store.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "product.page")
public class ProductPageConfig {
    @Getter
    @Setter
    private int size;
}
