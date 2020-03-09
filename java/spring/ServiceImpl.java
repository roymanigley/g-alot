import java.util.List;
import java.util.Optional;

@Service
public class g-alot{Entity}ServiceImpl implements g-alot{Entity}Service {

    private final g-alot{Entity}Repository repository;

    public g-alot{Entity}ServiceImpl(g-alot{Entity}Repository repository) {
        this.repository = repository;
    }   

    @Override
    public g-alot{Entity} create(g-alot{Entity} g-alot{entity}) {
        if (g-alot{entity}.getId() != null)
            throw new IllegalArgumentException("can not create record with existing id");
        return save(g-alot{entity});
    }

    @Override
    public List<g-alot{Entity}> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<g-alot{Entity}> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public g-alot{Entity} save(g-alot{Entity} g-alot{entity}) {
        return repository.save(g-alot{entity});
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
