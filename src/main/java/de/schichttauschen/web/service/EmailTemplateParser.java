package de.schichttauschen.web.service;

import de.schichttauschen.web.data.vo.TemplateContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EmailTemplateParser {
    private final SpringTemplateEngine templateEngine;

    public String parse(String name, TemplateContext templateContext) {
        final Context ctx = new Context();
        templateContext.getParameters().forEach(ctx::setVariable);
        return templateEngine.process("mail/" + name + ".txt", ctx);
    }

    public String parse(String name) {
        return parse(name, new TemplateContext());
    }
}
