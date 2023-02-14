package br.com.project.config.rest;

import br.com.project.config.exception.BusinessException;
import br.com.project.config.exception.EnumBusinessExceptionMessage;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.faulttolerance.Retry;

import javax.enterprise.context.ApplicationScoped;
import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Slf4j
@ApplicationScoped
public class ExternalRest {

    @ConfigProperty(name = "external.timeout.connect")
    private Integer timeout;

    public HttpResponse sendExternal(HttpRequest request) {

        HttpClient httpClient =
                HttpClient.newBuilder()
                        .connectTimeout(Duration.ofSeconds(timeout))
                        .followRedirects(HttpClient.Redirect.NORMAL)
                        .build();

        try {
            return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            log.error("Problem request external ", e);
            Thread.currentThread().interrupt();
            throw new BusinessException(EnumBusinessExceptionMessage.SEVICE_UNAVAILABLE);
        }
    }

    @Retry(maxRetries = 2, delay = 5, delayUnit = ChronoUnit.SECONDS)
    public HttpResponse sendExternalBackend(HttpRequest request) throws IOException, InterruptedException {
        HttpClient httpClient =
                HttpClient.newBuilder()
                        .connectTimeout(Duration.ofSeconds(timeout))
                        .followRedirects(HttpClient.Redirect.NORMAL)
                        .build();

        return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }
}
