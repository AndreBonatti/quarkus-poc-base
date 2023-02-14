package br.com.project.utils.cache;

import br.com.project.model.external.rest.out.BrasilApiBanksV1Dto;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

public class BrasilApiBanksCacheImpl implements SimpleCache<Integer, BrasilApiBanksV1Dto> {

    private HashMap<Integer, BrasilApiBanksV1Dto> map = new HashMap();
    private Timer timer = new Timer();
    private long timeout;

    public BrasilApiBanksCacheImpl() {
        this.timeout = Duration.ofMinutes(10).toMillis();
    }

    public BrasilApiBanksCacheImpl(Integer minutos) {
        this.timeout = Duration.ofMinutes(minutos).toMillis();
    }

    @Override
    public void addEntry(Integer key, BrasilApiBanksV1Dto value) {
        map.put(key, value);

        timer.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        actionAfterTimeout(key);
                    }
                }, timeout);
    }

    @Override
    public boolean hasObjects() {
        return !map.isEmpty();
    }

    @Override
    public List<BrasilApiBanksV1Dto> getAll() {
        return map.values().stream().collect(Collectors.toList());
    }

    protected void actionAfterTimeout(Integer key) {
        map.remove(key);
    }
}
