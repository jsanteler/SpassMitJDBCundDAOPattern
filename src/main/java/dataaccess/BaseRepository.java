package dataaccess;

import java.util.List;
import java.util.Optional;

//CRUD (Create, Read, Update, Delete) - Interface

// T- Typ, I - Identifier

public interface BaseRepository<T,I> {


    Optional<T> insert(T entity);
    Optional<T> getByID(I id);
    List<T> getAll();
    Optional<T> update(T entity);
    void deleteById(I id);
}
