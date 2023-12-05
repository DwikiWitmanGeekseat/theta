package au.com.geekseat.theta.service;

import au.com.geekseat.theta.error.BadRequestException;
import au.com.geekseat.theta.model.BaseModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public class BaseService<T extends BaseModel, E extends JpaRepository<T, Long>> {

    protected static final Logger LOG = LoggerFactory.getLogger(BaseService.class);

    protected final E repository;

    public BaseService(E repository) {
        this.repository = repository;
    }

    @Transactional(readOnly=true)
    public Iterable<T> findAll() {
        return repository.findAll();
    }

    @Transactional
    public T save(T entity) {
        LOG.info("saving {} to database", entity.getClass().getSimpleName());
        return repository.save(entity);
    }

    @Transactional
    public T update(T entity) {
        if(entity.getId() == null){
            throw new BadRequestException("id must not be null");
        }
        LOG.info("updating {} to database", entity.getClass().getSimpleName());
        return repository.save(entity);
    }

    @Transactional(readOnly=true)
    public T findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Transactional
    public String delete(Long id) {
        repository.deleteById(id);
        return "Success";
    }

}
