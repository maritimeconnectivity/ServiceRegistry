package net.maritimeconnectivity.serviceregistry.components;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.maritimeconnectivity.serviceregistry.models.domain.Instance;
import net.maritimeconnectivity.serviceregistry.services.InstanceService;
import net.maritimeconnectivity.serviceregistry.utils.SearchAreaCalculator;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

@Component
@Slf4j
@RequiredArgsConstructor
public class TmpMigrator implements ApplicationRunner {

    private static final String ERROR_LOG_FILE = "search-area-backfill-errors.log";

    private final InstanceService instanceService;
    private final SearchAreaCalculator searchAreaCalculator;

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        Pageable pageable = PageRequest.of(0, 100);
        Page<Instance> page;

        int updated = 0;
        int skipped = 0;

        do {
            page = instanceService.findAll(pageable);

            for (Instance instance : page.getContent()) {
                if (instance.getGeometry() == null || instance.getGeometry().isEmpty()) {
                    skipped++;
                    continue;
                }

                var areas = searchAreaCalculator.findIntersectingSearchAreas(instance.getGeometry());
                instance.updateSearchAreas(areas);
                instanceService.saveFromMigration(instance);

                updated++;

                log.info(
                        "Updated instance name='{}' with {} search areas",
                        instance.getName(),
                        areas.size()
                );
            }

            pageable = page.nextPageable();

        } while (page.hasNext());

        log.info("DONE ADDING SEARCH AREAS. Updated={}, skipped={}", updated, skipped);
    }
}