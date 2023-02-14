package br.com.project.service;

import br.com.project.model.external.rest.out.BrasilApiBanksV1Dto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@Slf4j
@QuarkusTest
class ExternalServiceTest {

    @Inject
    ExternalService externalService;

    @InjectMock
    BrasilApiService brasilApiService;

    private ObjectMapper mapper = new ObjectMapper();

    private String pathResource = Paths.get("src", "test", "resources").toFile().getAbsolutePath();

    public List<BrasilApiBanksV1Dto> getBanks() {
        try {
            return mapper.readValue(Paths.get(pathResource, "model.external.out/banksv1.json").toFile(), new TypeReference<List<BrasilApiBanksV1Dto>>() {
            });
        } catch (IOException ex) {
            log.error("", ex);
        }
        return null;
    }


    @Test
    void findAllSuccess() {

        when(brasilApiService.banksV1()).thenReturn(getBanks());

        var banks = externalService.findAll();

        assertEquals(20L, banks.size());
    }

    @Test
    void findByIdSuccess() {

        when(brasilApiService.banksV1FindbyId(any())).thenReturn(getBanks().get(0));

        var bank = externalService.findById(1);

        assertNotEquals(null, bank);
    }
}