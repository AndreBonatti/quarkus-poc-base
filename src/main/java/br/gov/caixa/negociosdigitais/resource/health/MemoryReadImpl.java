package br.gov.caixa.negociosdigitais.resource.health;

import br.gov.caixa.negociosdigitais.service.arq.HealthCheckService;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryUsage;

import static br.gov.caixa.negociosdigitais.utils.FormatByte.formatSize;

@Slf4j
@Readiness
@ApplicationScoped
public class MemoryReadImpl implements HealthCheck {

    private static final MemoryUsage heapMemoryUsage = ManagementFactory.getMemoryMXBean().getHeapMemoryUsage();

    @Inject
    HealthCheckService healthCheckService;

    @Override
    public HealthCheckResponse call() {

        return HealthCheckResponse.named("heap-memory")
                .status(healthCheckService.testMemory())
                .withData("max-heap", formatSize(heapMemoryUsage.getMax()))
                .withData("used-heap", formatSize(heapMemoryUsage.getUsed()))
                .withData("free-heap", formatSize(heapMemoryUsage.getMax() - heapMemoryUsage.getUsed()))
                .build();
    }
}
