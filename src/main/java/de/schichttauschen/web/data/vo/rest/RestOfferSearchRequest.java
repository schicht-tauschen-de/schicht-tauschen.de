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
    private static final int defaultPage = 1;
    private static final int defaultPageSize = 10;
    private static final int maxPageSize = 10;
    private int page = 1;
    private int pageSize = 10;

    public int getPage() {
        return Math.max(page, defaultPage);
    }

    public int getPageSize() {
        return pageSize < defaultPageSize
                || pageSize > maxPageSize
                ? defaultPageSize
                : pageSize;
    }
}
