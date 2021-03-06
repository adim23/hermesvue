package gr.adr.hermes.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import gr.adr.hermes.domain.Teams;
import gr.adr.hermes.repository.TeamsRepository;
import gr.adr.hermes.repository.search.TeamsSearchRepository;
import gr.adr.hermes.service.TeamsService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Teams}.
 */
@Service
@Transactional
public class TeamsServiceImpl implements TeamsService {

    private final Logger log = LoggerFactory.getLogger(TeamsServiceImpl.class);

    private final TeamsRepository teamsRepository;

    private final TeamsSearchRepository teamsSearchRepository;

    public TeamsServiceImpl(TeamsRepository teamsRepository, TeamsSearchRepository teamsSearchRepository) {
        this.teamsRepository = teamsRepository;
        this.teamsSearchRepository = teamsSearchRepository;
    }

    @Override
    public Teams save(Teams teams) {
        log.debug("Request to save Teams : {}", teams);
        Teams result = teamsRepository.save(teams);
        teamsSearchRepository.save(result);
        return result;
    }

    @Override
    public Optional<Teams> partialUpdate(Teams teams) {
        log.debug("Request to partially update Teams : {}", teams);

        return teamsRepository
            .findById(teams.getId())
            .map(existingTeams -> {
                if (teams.getName() != null) {
                    existingTeams.setName(teams.getName());
                }

                return existingTeams;
            })
            .map(teamsRepository::save)
            .map(savedTeams -> {
                teamsSearchRepository.save(savedTeams);

                return savedTeams;
            });
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Teams> findAll(Pageable pageable) {
        log.debug("Request to get all Teams");
        return teamsRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Teams> findOne(Long id) {
        log.debug("Request to get Teams : {}", id);
        return teamsRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Teams : {}", id);
        teamsRepository.deleteById(id);
        teamsSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Teams> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Teams for query {}", query);
        return teamsSearchRepository.search(query, pageable);
    }
}
