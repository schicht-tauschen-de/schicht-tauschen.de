package de.schichttauschen.web.service;

import de.schichttauschen.web.component.BooleanResponseBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BooleanResponseBuilderFactory {
    private final TranslationService translationService;

    public BooleanResponseBuilder get() {
        return new BooleanResponseBuilder(translationService);
    }
}
