package g-alot{basePackage}.service;

import g-alot{basePackage}.model.g-alot{Entity};
import java.util.List;
import java.util.Optional;

public interface g-alot{Entity}Service {

    public g-alot{Entity} create(g-alot{Entity} g-alot{entity});
    public List<g-alot{Entity}> findAll();
    public List<g-alot{Entity}> findAll(int pageIndex, int size);
    public Optional<g-alot{Entity}> findById(Long id);
    public g-alot{Entity} save(g-alot{Entity} g-alot{entity});
    public void delete(Long id);
}
