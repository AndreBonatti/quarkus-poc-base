package br.com.project.config.health;

import com.sun.management.OperatingSystemMXBean;
import io.agroal.api.AgroalDataSource;
import io.quarkus.scheduler.Scheduled;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryUsage;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
@ApplicationScoped
public class HealthCheckService {

    private static final MemoryUsage heapMemoryUsage = ManagementFactory.getMemoryMXBean().getHeapMemoryUsage();

    private static final OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);

    @ConfigProperty(name = "health.cpu.warn")
    Integer cpuWarn;

    @ConfigProperty(name = "health.memory.warn")
    Integer memoryWarn;

    @Inject
    AgroalDataSource defaultDataSource;

    private AtomicBoolean statusDB = new AtomicBoolean(true);

    public boolean testCpu() {
        var cpuUsed = this.cpuUsed();
        log.debug("cpu_user {}% - cpu_warn {}%", this.cpuUsed(), cpuWarn);
        return cpuUsed < cpuWarn;
    }

    public boolean testMemory() {
        double memoryUsed = this.memoryUsed();
        log.debug("memory_used {}% - memory_warn {}%", memoryUsed, memoryWarn);
        return memoryUsed < memoryWarn;
    }

    public boolean statusDatabase() {
        return statusDB.get();
    }

    public Integer cpuUsed() {
        return ((int) (osBean.getSystemCpuLoad() * 100));
    }

    public Double memoryUsed() {
        return ((double) heapMemoryUsage.getUsed() / (double) heapMemoryUsage.getMax()) * 100;
    }

    @Scheduled(every = "{health.database.timeout}")
    void testDatabase() {
        try {
            defaultDataSource.isHealthy(Boolean.FALSE);
            statusDB.set(true);
        } catch (Exception e) {
            log.error("Database error ", e);
            statusDB.set(false);
        }
    }
}
