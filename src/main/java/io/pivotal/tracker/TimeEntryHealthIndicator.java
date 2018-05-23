package io.pivotal.tracker;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class TimeEntryHealthIndicator  implements HealthIndicator {


    private static final int MIN_TIME_ENTRIES = 2;
    private final TimeEntryRepository timeEntryRepo;

    public TimeEntryHealthIndicator(TimeEntryRepository timeEntryRepo) {
        this.timeEntryRepo = timeEntryRepo;
    }


    @Override
    public Health health() {
        Health.Builder builder = new Health.Builder();
        builder.up();
        if(timeEntryRepo.list().size()> MIN_TIME_ENTRIES) {
            builder.up();
        } else {
            builder.down();
        }
        return builder.build() ;
    }

}

