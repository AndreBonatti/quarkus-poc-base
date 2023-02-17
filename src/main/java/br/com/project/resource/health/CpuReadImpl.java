package br.com.project.resource.health;

import br.com.project.config.arquitetura.HealthCheckService;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.inject.ConfigProperty;
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

    @ConfigProperty(name = "health.cpu.warn.enable")
    Boolean cpuTestEnable;

    @Inject
    HealthCheckService healthCheckService;

    @Override
    public HealthCheckResponse call() {

        var enableCheck = (cpuTestEnable) ? healthCheckService.testCpu() : true;

        Integer cpuUsed = healthCheckService.cpuUsed();

        return HealthCheckResponse.named("cpu")
                .status(enableCheck)
                .withData("cpu-usage", cpuUsed + "%")
                .withData("cpu-core", osBean.getAvailableProcessors())
                .build();
    }

}
