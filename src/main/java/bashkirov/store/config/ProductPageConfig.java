package bashkirov.store.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "product.page")
@Getter
@Setter
public class ProductPageConfig {
    private int size;
}
