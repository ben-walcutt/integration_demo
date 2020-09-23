package co.newlabs.repository.order;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Repository
@AllArgsConstructor
public class OrderRepository {
    private final NamedParameterJdbcTemplate template;
    private final @Qualifier("queries") Properties queries;

    public OrderEntity getOrderById(final long id) {
        String query = queries.getProperty("getOrderById");
        Map<String, Object> params = new HashMap<String, Object>() {{
            put("orderId", id);
        }};

        RowMapper<OrderEntity> rowMapper = new BeanPropertyRowMapper<OrderEntity>(OrderEntity.class);

        return template.queryForObject(query, params, rowMapper);
    }
}
