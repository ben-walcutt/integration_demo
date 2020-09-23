package co.newlabs.repository.lineitem;

import co.newlabs.dto.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LineItemEntity {
    private long lineItemId;
    private long orderId;
    private long productId;
    private int quantity;
}
