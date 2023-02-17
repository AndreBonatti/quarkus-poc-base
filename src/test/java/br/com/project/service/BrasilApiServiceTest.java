package br.com.project.service;

import br.com.project.config.exception.BusinessException;
import br.com.project.config.rest.ExternalRest;
import br.com.project.model.external.rest.out.BrasilApiBanksV1Dto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.net.ssl.SSLSession;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@Slf4j
@QuarkusTest
class BrasilApiServiceTest {

//    https://github.com/quarkusio/quarkus/issues/12761

    @InjectMock
    ExternalRest externalRest;

    @Inject
    BrasilApiService brasilApiService;

    private ObjectMapper mapper = new ObjectMapper();

    private String pathResource = Paths.get("src", "test", "resources").toFile().getAbsolutePath();

    public List<BrasilApiBanksV1Dto> getBanks() {
        try {
            return mapper.readValue(Paths.get(pathResource, "model.external.out/banksv1.json").toFile(), new TypeReference<List<BrasilApiBanksV1Dto>>() {
            });
        } catch (IOException ex) {
            log.error("{}", ex);
        }
        return null;
    }

    private HttpResponse forceHttpResponse() {
        HttpResponse httpResponse = new HttpResponse() {
            @Override
            public int statusCode() {
                return 200;
            }

            @Override
            public HttpRequest request() {
                return null;
            }

            @Override
            public Optional<HttpResponse> previousResponse() {
                return Optional.empty();
            }

            @Override
            public HttpHeaders headers() {
                return null;
            }

            @SneakyThrows
            @Override
            public Object body() {
                return mapper.writeValueAsString(getBanks());
            }

            @Override
            public Optional<SSLSession> sslSession() {
                return Optional.empty();
            }

            @Override
            public URI uri() {
                return null;
            }

            @Override
            public HttpClient.Version version() {
                return null;
            }
        };
        return httpResponse;
    }

    @Test
    void banksV1Success() {

        when(externalRest.sendExternal(any())).thenReturn(forceHttpResponse());

        var banks = brasilApiService.banksV1();

        assertEquals(20L, banks.size());

    }

    @Test
    void banksV1FindbyIdSuccess() {

        when(externalRest.sendExternal(any())).thenReturn(forceHttpResponse());

        var bank = brasilApiService.banksV1FindbyId(1);

        assertNotEquals(null, bank);
        assertEquals("BCO DO BRASIL S.A.", bank.getName());
    }

    @Test
    void banksV1FindbyIdNotFound() {

        when(externalRest.sendExternal(any())).thenReturn(forceHttpResponse());

        assertThrows(BusinessException.class, () ->  brasilApiService.banksV1FindbyId(Integer.MAX_VALUE));

    }
}