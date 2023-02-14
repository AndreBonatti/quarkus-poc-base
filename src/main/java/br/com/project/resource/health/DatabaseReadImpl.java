package br.com.project.resource.health;

import br.com.project.config.arquitetura.HealthCheckService;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@Slf4j
@Readiness
@ApplicationScoped
public class DatabaseReadImpl implements HealthCheck {

    @Inject
    HealthCheckService healthCheckService;

    @Override
    public HealthCheckResponse call() {
        log.debug("Database");

        return HealthCheckResponse.named("database")
                .status(healthCheckService.statusDatabase())
                .build();
    }

}
