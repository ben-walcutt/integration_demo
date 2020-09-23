package co.newlabs.dto;

import co.newlabs.client.account.AccountDTO;
import co.newlabs.client.tracking.TrackingDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private long orderNumber;
    private List<LineItemDTO> lineItems;
    private AccountDTO account;
    private TrackingDTO tracking;
}
