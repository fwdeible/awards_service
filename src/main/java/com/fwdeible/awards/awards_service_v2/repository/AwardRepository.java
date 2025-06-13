package com.fwdeible.awards.awards_service_v2.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fwdeible.awards.awards_service_v2.model.Award;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
@Slf4j
public class AwardRepository {
    private final List<Award> awards;

    public AwardRepository() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        InputStream is = getClass().getResourceAsStream("/awardList.json");
        awards = Arrays.asList(mapper.readValue(is, Award[].class));
        log.debug("awardsList size: " + awards.size());
    }

    public List<Award> findAll() {
        return awards;
    }

    public Optional<Award> findById(Long id) {
        log.debug("AwardRepostiory.findById: {}", id);
        return awards.stream()
                .filter(a -> Objects.equals(a.getId(), id))
                .findFirst();
    }

}
