package gr.adr.hermes.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import gr.adr.hermes.domain.SocialKinds;
import gr.adr.hermes.repository.SocialKindsRepository;
import gr.adr.hermes.repository.search.SocialKindsSearchRepository;
import gr.adr.hermes.service.SocialKindsService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link SocialKinds}.
 */
@Service
@Transactional
public class SocialKindsServiceImpl implements SocialKindsService {

    private final Logger log = LoggerFactory.getLogger(SocialKindsServiceImpl.class);

    private final SocialKindsRepository socialKindsRepository;

    private final SocialKindsSearchRepository socialKindsSearchRepository;

    public SocialKindsServiceImpl(SocialKindsRepository socialKindsRepository, SocialKindsSearchRepository socialKindsSearchRepository) {
        this.socialKindsRepository = socialKindsRepository;
        this.socialKindsSearchRepository = socialKindsSearchRepository;
    }

    @Override
    public SocialKinds save(SocialKinds socialKinds) {
        log.debug("Request to save SocialKinds : {}", socialKinds);
        SocialKinds result = socialKindsRepository.save(socialKinds);
        socialKindsSearchRepository.save(result);
        return result;
    }

    @Override
    public Optional<SocialKinds> partialUpdate(SocialKinds socialKinds) {
        log.debug("Request to partially update SocialKinds : {}", socialKinds);

        return socialKindsRepository
            .findById(socialKinds.getId())
            .map(existingSocialKinds -> {
                if (socialKinds.getCode() != null) {
                    existingSocialKinds.setCode(socialKinds.getCode());
                }
                if (socialKinds.getName() != null) {
                    existingSocialKinds.setName(socialKinds.getName());
                }
                if (socialKinds.getCall() != null) {
                    existingSocialKinds.setCall(socialKinds.getCall());
                }

                return existingSocialKinds;
            })
            .map(socialKindsRepository::save)
            .map(savedSocialKinds -> {
                socialKindsSearchRepository.save(savedSocialKinds);

                return savedSocialKinds;
            });
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SocialKinds> findAll(Pageable pageable) {
        log.debug("Request to get all SocialKinds");
        return socialKindsRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SocialKinds> findOne(Long id) {
        log.debug("Request to get SocialKinds : {}", id);
        return socialKindsRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SocialKinds : {}", id);
        socialKindsRepository.deleteById(id);
        socialKindsSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SocialKinds> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of SocialKinds for query {}", query);
        return socialKindsSearchRepository.search(query, pageable);
    }
}
