package de.schichttauschen.web.data.vo.rest;

import de.schichttauschen.web.data.entity.Offer;
import de.schichttauschen.web.data.entity.OfferType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestOffer {
    private UUID id;
    private OfferType type;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private RestAccountInfo fromAccount;
    private RestAccountInfo toAccount;
    private RestCompany company;

    public static RestOffer fromOffer(Offer offer) {
        return RestOffer.builder()
                .type(offer.getType())
                .startDate(offer.getStartDate())
                .endDate(offer.getEndDate())
                .fromAccount(offer.getFromAccount() == null ? null : RestAccountInfo.fromAccountPublic(offer.getFromAccount()))
                .toAccount(offer.getToAccount() == null ? null : RestAccountInfo.fromAccountPublic(offer.getToAccount()))
                .company(RestCompany.fromDepartment(offer.getDepartment()))
                .build();
    }
}
