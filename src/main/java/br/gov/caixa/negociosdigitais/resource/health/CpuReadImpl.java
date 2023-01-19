package br.gov.caixa.negociosdigitais.resource.health;

import br.gov.caixa.negociosdigitais.service.arq.HealthCheckService;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;

@Slf4j
@Readiness
@ApplicationScoped
public class CpuReadImpl implements HealthCheck {

    private static final OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();

    @Inject
    HealthCheckService healthCheckService;

    @Override
    public HealthCheckResponse call() {

        Integer cpuUsed = healthCheckService.cpuUsed();

        return HealthCheckResponse.named("cpu")
                .status(healthCheckService.testCpu())
                .withData("cpu-usage", cpuUsed + "%")
                .withData("cpu-core", osBean.getAvailableProcessors())
                .build();
    }

}
