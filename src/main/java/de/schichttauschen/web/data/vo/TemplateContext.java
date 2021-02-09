package de.schichttauschen.web.data.vo;

import lombok.*;

import java.util.HashMap;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TemplateContext {
    @Singular
    private Map<String, Object> parameters = new HashMap<>();
}
