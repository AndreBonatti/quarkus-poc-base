package br.com.project.service.imp;

import br.com.project.config.exception.BusinessException;
import br.com.project.config.exception.EnumBusinessExceptionMessage;
import br.com.project.config.rest.ExternalRest;
import br.com.project.model.external.rest.out.BrasilApiBanksV1Dto;
import br.com.project.service.BrasilApiService;
import br.com.project.utils.cache.SimpleCache;
import br.com.project.utils.cache.BrasilApiBanksCacheImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.logging.Log;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.MediaType;
import java.net.URI;
import java.net.http.HttpRequest;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@ApplicationScoped
public class BrasilApiServiceImpl implements BrasilApiService {

    ObjectMapper objectMapper = new ObjectMapper();

    SimpleCache<Integer, BrasilApiBanksV1Dto> cacheBanks = new BrasilApiBanksCacheImpl();

    @ConfigProperty(name = "external.timeout.request")
    Integer timeout;

    @ConfigProperty(name = "external.endpoint1.public")
    String endpoint;

    @Inject
    ExternalRest externalRest;

    @Override
    public List<BrasilApiBanksV1Dto> banksV1() {
        findBanks();

//        return cacheBanks.getAll().stream().sorted((b1, b2) -> b1.getName().compareTo(b2.getName())).collect(Collectors.toList());
        return cacheBanks.getAll().stream().collect(Collectors.toList());
    }


    @Override
    public BrasilApiBanksV1Dto banksV1FindbyId(Integer code) {
        findBanks();

        var lista = cacheBanks.getAll();
        for (BrasilApiBanksV1Dto bank : lista) {
            if ((bank.getCode() != null) && (bank.getCode().equals(code))) {
                return bank;
            }
        }
        throw new BusinessException(EnumBusinessExceptionMessage.NOT_FOUND);
    }

    private void findBanks() {

        if (cacheBanks.hasObjects()) {
            return;
        }

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(endpoint))
                .headers("Accept", MediaType.APPLICATION_JSON)
                .timeout(Duration.ofSeconds(timeout))
                .build();

        var retorno = externalRest.sendExternal(request);

        List<BrasilApiBanksV1Dto> lista = new ArrayList<>();
        try {
            lista = objectMapper.readValue(retorno.body().toString(),
                    new TypeReference<List<BrasilApiBanksV1Dto>>() {
                    });
        } catch (JsonProcessingException e) {
            Log.error("Converter json ", e);
            throw new BusinessException(EnumBusinessExceptionMessage.INTERNAL_SERVER_ERROR);
        }

        lista.forEach(o -> cacheBanks.addEntry(o.hashCode(), o));
    }

}
