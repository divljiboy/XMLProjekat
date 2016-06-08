package xml.controller;

import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * Created by Daniel on 5/30/2016.
 */
public interface IBaseController<T>{

    ResponseEntity<List<T>> getAll();
    ResponseEntity<T> getById(Long id);
    ResponseEntity post(T object);
    ResponseEntity put(T object,Long id);
    //public abstract ResponseEntity search();

}
