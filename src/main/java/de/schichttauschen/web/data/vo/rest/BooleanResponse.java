package de.schichttauschen.web.data.vo.rest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BooleanResponse {
    private Boolean status;
    private String message;
}
