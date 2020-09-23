package co.newlabs.client.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {
    private String name;
    private String phoneNumber;
    private String address;
    private String city;
    private String state;
    private String zip;
}
