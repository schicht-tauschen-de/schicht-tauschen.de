package de.schichttauschen.web.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TranslationService {
    private final MessageSource messageSource;

    public String get(String key) {
        return get(key, null);
    }

    public String get(String key, Object[] parameters) {
        try {
            return messageSource.getMessage(key, parameters, LocaleContextHolder.getLocale());
        } catch (NoSuchMessageException e) {
            return "===" + key + "===";
        }
    }

}
