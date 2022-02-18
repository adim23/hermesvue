package gr.adr.hermes.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import gr.adr.hermes.domain.CompanyKinds;
import gr.adr.hermes.repository.CompanyKindsRepository;
import gr.adr.hermes.repository.search.CompanyKindsSearchRepository;
import gr.adr.hermes.service.CompanyKindsService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CompanyKinds}.
 */
@Service
@Transactional
public class CompanyKindsServiceImpl implements CompanyKindsService {

    private final Logger log = LoggerFactory.getLogger(CompanyKindsServiceImpl.class);

    private final CompanyKindsRepository companyKindsRepository;

    private final CompanyKindsSearchRepository companyKindsSearchRepository;

    public CompanyKindsServiceImpl(
        CompanyKindsRepository companyKindsRepository,
        CompanyKindsSearchRepository companyKindsSearchRepository
    ) {
        this.companyKindsRepository = companyKindsRepository;
        this.companyKindsSearchRepository = companyKindsSearchRepository;
    }

    @Override
    public CompanyKinds save(CompanyKinds companyKinds) {
        log.debug("Request to save CompanyKinds : {}", companyKinds);
        CompanyKinds result = companyKindsRepository.save(companyKinds);
        companyKindsSearchRepository.save(result);
        return result;
    }

    @Override
    public Optional<CompanyKinds> partialUpdate(CompanyKinds companyKinds) {
        log.debug("Request to partially update CompanyKinds : {}", companyKinds);

        return companyKindsRepository
            .findById(companyKinds.getId())
            .map(existingCompanyKinds -> {
                if (companyKinds.getName() != null) {
                    existingCompanyKinds.setName(companyKinds.getName());
                }

                return existingCompanyKinds;
            })
            .map(companyKindsRepository::save)
            .map(savedCompanyKinds -> {
                companyKindsSearchRepository.save(savedCompanyKinds);

                return savedCompanyKinds;
            });
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CompanyKinds> findAll(Pageable pageable) {
        log.debug("Request to get all CompanyKinds");
        return companyKindsRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CompanyKinds> findOne(Long id) {
        log.debug("Request to get CompanyKinds : {}", id);
        return companyKindsRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CompanyKinds : {}", id);
        companyKindsRepository.deleteById(id);
        companyKindsSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CompanyKinds> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of CompanyKinds for query {}", query);
        return companyKindsSearchRepository.search(query, pageable);
    }
}
