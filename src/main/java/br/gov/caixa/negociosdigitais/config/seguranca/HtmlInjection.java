package br.gov.caixa.negociosdigitais.config.seguranca;

import br.gov.caixa.negociosdigitais.config.exception.BusinessException;
import br.gov.caixa.negociosdigitais.config.exception.EnumBusinessExceptionMessage;
import lombok.extern.slf4j.Slf4j;
import org.owasp.html.Encoding;
import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.PolicyFactory;

import javax.ws.rs.container.ContainerRequestContext;

@Slf4j
public class HtmlInjection {

    private static final PolicyFactory POLICY_FACTORY = new HtmlPolicyBuilder().toFactory();

    private HtmlInjection() {
    }

    /*
    https://github.com/OWASP/java-html-sanitizer
    https://medium.com/@adson.oliveira_93027/prevenindo-ataques-de-html-injection-em-apis-rest-com-spring-boot-40c2ea2e49e6
    https://www.web2generators.com/html-based-tools/online-html-entities-encoder-and-decoder

     <!-- ==== OWASP ===== -->
        <dependency>
            <groupId>com.googlecode.owasp-java-html-sanitizer</groupId>
            <artifactId>owasp-java-html-sanitizer</artifactId>
            <version>20220608.1</version>
        </dependency>
     */

    public static void validation(ContainerRequestContext request) {

        var query = request.getUriInfo().getRequestUri().getQuery();
        log.debug("QUERY: {}", query);
        if (query != null) validContent(query);

        var queryPath = request.getUriInfo().getRequestUri().getPath();
        log.debug("Path: {}", queryPath);
        if (queryPath != null) validContent(queryPath);

        String body = (String) request.getProperty("body");
        log.debug("BODY: {}", body);
        if (body != null) validContent(body);
    }

    private static void validContent(String value) throws BusinessException {
        var sanitize = POLICY_FACTORY.sanitize(value);
        var decodeHtml = Encoding.decodeHtml(sanitize, false);

        if (!value.equals(decodeHtml)) {
            throw new BusinessException(EnumBusinessExceptionMessage.BAD_REQUEST_HTML_INJECTION);
        }
    }
}
