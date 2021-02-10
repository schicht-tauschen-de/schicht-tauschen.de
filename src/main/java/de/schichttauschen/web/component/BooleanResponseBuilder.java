package de.schichttauschen.web.component;

import de.schichttauschen.web.data.vo.rest.RestBooleanResponse;
import de.schichttauschen.web.service.TranslationService;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Autowired;

@Setter
@Accessors(fluent = true)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BooleanResponseBuilder {
    private final TranslationService translationService;
    private Boolean status;
    private String trueMessage;
    private String falseMessage;
    private Object[] trueParameters;
    private Object[] falseParameters;

    public boolean getStatus() {
        return status != null ? status : false;
    }

    public RestBooleanResponse build() {
        return RestBooleanResponse.builder()
                .status(getStatus())
                .message(getStatus()
                        ? translationService.get(trueMessage, trueParameters)
                        : translationService.get(falseMessage, falseParameters))
                .build();
    }
}
