package br.gov.caixa.negociosdigitais.resource.health;

import com.sun.management.OperatingSystemMXBean;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;

import javax.enterprise.context.ApplicationScoped;
import java.lang.management.ManagementFactory;

import static br.gov.caixa.negociosdigitais.utils.FormatByte.formatSize;

@Slf4j
@Readiness
@ApplicationScoped
public class InfoReadImpl implements HealthCheck {

    private static final OperatingSystemMXBean osBean =
            ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);

    @Override
    public HealthCheckResponse call() {
        log.debug("Info-sytem");

        var osName = System.getProperty("os.name");
        var osVersion = System.getProperty("os.version");
        var userCountry = System.getProperty("user.country");
        var userTimerZone = System.getProperty("user.timezone");
        var jvmDate = System.getProperty("java.version.date");
        var jvmVersion = System.getProperty("java.vm.version");

        var total = osBean.getTotalPhysicalMemorySize();
        var free = osBean.getFreePhysicalMemorySize();

        return HealthCheckResponse.named("info-servise")
                .status(true)
                .withData("OS_NAME", osName)
                .withData("OS_VERSION", osVersion)
                .withData("USER_COUNTRY", userCountry)
                .withData("USER_TIMEZ", userTimerZone)
                .withData("JVM_Version", jvmVersion)
                .withData("JVM_DATE", jvmDate)
                .withData("MEMORY_MAX", formatSize(total))
                .withData("MEMORY_USED", formatSize(total - free))
                .withData("MEMORY_FREE", formatSize(free))
                .build();
    }

}
