package br.com.project.config.health;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;

import javax.enterprise.context.ApplicationScoped;
import java.util.Date;

@Slf4j
@Readiness
@ApplicationScoped
public class InfoReadImpl implements HealthCheck {

    @ConfigProperty(name = "quarkus.application.version")
    String aplicationVersion;

    @Override
    public HealthCheckResponse call() {
        log.debug("Info-sytem");

        var osName = System.getProperty("os.name");
        var osVersion = System.getProperty("os.version");
        var userCountry = System.getProperty("user.country");
        var userTimerZone = System.getProperty("user.timezone");
        var jvmDate = System.getProperty("java.version.date");
        var jvmVersion = System.getProperty("java.vm.version");

        return HealthCheckResponse.named("info-servise")
                .status(true)
                .withData("APP_VERSION", aplicationVersion)
                .withData("OS_NAME", osName)
                .withData("OS_VERSION", osVersion)
                .withData("USER_COUNTRY", userCountry)
                .withData("USER_TIMEZ", userTimerZone)
                .withData("JVM_Version", jvmVersion)
                .withData("JVM_DATE", jvmDate)
                .withData("TIME_STAMP", new Date().toString())
                .build();
    }

}
