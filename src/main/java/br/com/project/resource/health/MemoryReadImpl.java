package br.com.project.resource.health;

import br.com.project.utils.FormatByte;
import br.com.project.config.arquitetura.HealthCheckService;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryUsage;

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
                .withData("max-heap", FormatByte.formatSize(heapMemoryUsage.getMax()))
                .withData("used-heap", FormatByte.formatSize(heapMemoryUsage.getUsed()))
                .withData("free-heap", FormatByte.formatSize(heapMemoryUsage.getMax() - heapMemoryUsage.getUsed()))
                .build();
    }
}
