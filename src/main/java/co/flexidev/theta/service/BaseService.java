package co.flexidev.theta.service;

import co.flexidev.theta.error.BadRequestException;
import co.flexidev.theta.model.BaseModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.repository.CrudRepository;
import java.util.Optional;

public class BaseService<T extends BaseModel, R extends CrudRepository<T, Long>> {

    private static final Logger LOG = LoggerFactory.getLogger(BaseService.class);

    private final R repository;

    public BaseService(R repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public Iterable<T> findAll() {
        return repository.findAll();
    }

    @Transactional
    public T save(T entity) {
        LOG.info("Saving {} to database", entity.getClass().getSimpleName());
        return repository.save(entity);
    }

    @Transactional
    public T update(T entity) {
        if (entity.getId() == null) {
            throw new BadRequestException("ID must not be null");
        }
        LOG.info("Updating {} to database", entity.getClass().getSimpleName());
        return repository.save(entity);
    }

    @Transactional(readOnly = true)
    public T findById(Long id) {
        Optional<T> optionalEntity = repository.findById(id);
        return optionalEntity.orElse(null);
    }

    @Transactional
    public String delete(Long id) {
        repository.deleteById(id);
        return "Success";
    }
}

