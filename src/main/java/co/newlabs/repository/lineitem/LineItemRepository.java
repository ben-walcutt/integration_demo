package co.newlabs.repository.lineitem;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@Repository
@AllArgsConstructor
public class LineItemRepository {
    private NamedParameterJdbcTemplate template;
    private Properties queries;

    public List<LineItemEntity> getLineItemsByOrder(final long orderId) {
        String query = queries.getProperty("getLineItemsByOrder");
        Map<String, Object> params = new HashMap<String, Object>() {{
            put("orderId", orderId);
        }};

        RowMapper<LineItemEntity> rowMapper = new BeanPropertyRowMapper<LineItemEntity>(LineItemEntity.class);

        return template.query(query, params, rowMapper);
    }
}
