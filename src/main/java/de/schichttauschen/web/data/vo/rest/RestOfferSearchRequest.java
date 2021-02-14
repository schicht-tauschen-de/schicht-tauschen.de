package de.schichttauschen.web.data.vo.rest;

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
public class RestOfferSearchRequest {
    private OfferType type;
    private UUID companyId;
    private UUID departmentId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
