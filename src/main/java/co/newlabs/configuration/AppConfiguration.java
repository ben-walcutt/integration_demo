package co.newlabs.configuration;

import co.newlabs.dto.LineItemDTO;
import co.newlabs.dto.OrderDTO;
import co.newlabs.dto.ProductDTO;
import co.newlabs.repository.lineitem.LineItemEntity;
import co.newlabs.repository.order.OrderEntity;
import co.newlabs.repository.product.ProductEntity;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.client.RestTemplate;

@Configuration
@ImportResource("classpath:queries.xml")
public class AppConfiguration {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public MapperFactory mapperFactory() {
        DefaultMapperFactory mapperFactory = new DefaultMapperFactory.Builder()
                .dumpStateOnException(false)
                .useBuiltinConverters(true)
                .build();

        mapperFactory.classMap(OrderDTO.class, OrderEntity.class)
                .mapNulls(true)
                .field("orderNumber", "orderId")
                .byDefault()
                .register();

        mapperFactory.classMap(ProductDTO.class, ProductEntity.class)
                .mapNulls(true)
                .byDefault()
                .register();

        mapperFactory.classMap(LineItemDTO.class, LineItemEntity.class)
                .mapNulls(true)
                .byDefault()
                .register();

        return mapperFactory;
    }

    @Bean
    public MapperFacade mapper(MapperFactory mapperFactory) {
        return mapperFactory.getMapperFacade();
    }
}
