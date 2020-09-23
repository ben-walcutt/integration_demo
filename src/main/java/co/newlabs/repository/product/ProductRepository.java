package co.newlabs.repository.product;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Repository
@AllArgsConstructor
public class ProductRepository {
    private NamedParameterJdbcTemplate template;
    private Properties queries;

    public ProductEntity getProductById(final long id) {
        String query = queries.getProperty("getProductById");
        Map<String, Object> params = new HashMap<String, Object>() {{
            put("productId", id);
        }};

        RowMapper<ProductEntity> rowMapper = new BeanPropertyRowMapper<ProductEntity>(ProductEntity.class);

        return template.queryForObject(query, params, rowMapper);
    }
}
