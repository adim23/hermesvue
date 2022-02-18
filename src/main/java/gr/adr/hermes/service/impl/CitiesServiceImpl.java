package gr.adr.hermes.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import gr.adr.hermes.domain.Cities;
import gr.adr.hermes.repository.CitiesRepository;
import gr.adr.hermes.repository.search.CitiesSearchRepository;
import gr.adr.hermes.service.CitiesService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Cities}.
 */
@Service
@Transactional
public class CitiesServiceImpl implements CitiesService {

    private final Logger log = LoggerFactory.getLogger(CitiesServiceImpl.class);

    private final CitiesRepository citiesRepository;

    private final CitiesSearchRepository citiesSearchRepository;

    public CitiesServiceImpl(CitiesRepository citiesRepository, CitiesSearchRepository citiesSearchRepository) {
        this.citiesRepository = citiesRepository;
        this.citiesSearchRepository = citiesSearchRepository;
    }

    @Override
    public Cities save(Cities cities) {
        log.debug("Request to save Cities : {}", cities);
        Cities result = citiesRepository.save(cities);
        citiesSearchRepository.save(result);
        return result;
    }

    @Override
    public Optional<Cities> partialUpdate(Cities cities) {
        log.debug("Request to partially update Cities : {}", cities);

        return citiesRepository
            .findById(cities.getId())
            .map(existingCities -> {
                if (cities.getName() != null) {
                    existingCities.setName(cities.getName());
                }
                if (cities.getPresident() != null) {
                    existingCities.setPresident(cities.getPresident());
                }
                if (cities.getPresidentsPhone() != null) {
                    existingCities.setPresidentsPhone(cities.getPresidentsPhone());
                }
                if (cities.getSecretary() != null) {
                    existingCities.setSecretary(cities.getSecretary());
                }
                if (cities.getSecretarysPhone() != null) {
                    existingCities.setSecretarysPhone(cities.getSecretarysPhone());
                }
                if (cities.getPolice() != null) {
                    existingCities.setPolice(cities.getPolice());
                }
                if (cities.getPolicesPhone() != null) {
                    existingCities.setPolicesPhone(cities.getPolicesPhone());
                }
                if (cities.getDoctor() != null) {
                    existingCities.setDoctor(cities.getDoctor());
                }
                if (cities.getDoctorsPhone() != null) {
                    existingCities.setDoctorsPhone(cities.getDoctorsPhone());
                }
                if (cities.getTeacher() != null) {
                    existingCities.setTeacher(cities.getTeacher());
                }
                if (cities.getTeachersPhone() != null) {
                    existingCities.setTeachersPhone(cities.getTeachersPhone());
                }
                if (cities.getPriest() != null) {
                    existingCities.setPriest(cities.getPriest());
                }
                if (cities.getPriestsPhone() != null) {
                    existingCities.setPriestsPhone(cities.getPriestsPhone());
                }

                return existingCities;
            })
            .map(citiesRepository::save)
            .map(savedCities -> {
                citiesSearchRepository.save(savedCities);

                return savedCities;
            });
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Cities> findAll(Pageable pageable) {
        log.debug("Request to get all Cities");
        return citiesRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Cities> findOne(Long id) {
        log.debug("Request to get Cities : {}", id);
        return citiesRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Cities : {}", id);
        citiesRepository.deleteById(id);
        citiesSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Cities> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Cities for query {}", query);
        return citiesSearchRepository.search(query, pageable);
    }
}