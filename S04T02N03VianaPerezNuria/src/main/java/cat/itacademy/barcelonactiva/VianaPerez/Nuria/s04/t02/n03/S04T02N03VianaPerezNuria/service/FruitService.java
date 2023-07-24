package cat.itacademy.barcelonactiva.VianaPerez.Nuria.s04.t02.n03.S04T02N03VianaPerezNuria.service;

import cat.itacademy.barcelonactiva.VianaPerez.Nuria.s04.t02.n03.S04T02N03VianaPerezNuria.domain.Fruit;
import cat.itacademy.barcelonactiva.VianaPerez.Nuria.s04.t02.n03.S04T02N03VianaPerezNuria.repository.FruitRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Logical business layer that allows making the application more robust
 *
 * @author Nuria Viana
 * @version 1.0
 * @see <a href="https://jarroba.com/annotations-anotaciones-en-java/">annotations</a>
 *
 */
@Service
public class FruitService {

    @Autowired
    private FruitRepository fruitRepository;

    /**
     * Check if List<Fruit> is null or empty
     * @param
     * @return ResponseEntity<List<Fruit>> with body and HttpStatus
     */
    public ResponseEntity<List<Fruit>> checkGetFruits() {
        try {
            List<Fruit> fruits = fruitRepository.findAll();
            if (fruits == null) return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            if (fruits.isEmpty()) return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            return ResponseEntity.ok(fruits);
//            return new ResponseEntity<>(fruits, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Check if fruit added by user has the minimum necessary information to be created
     * @param fruit
     * @return ResponseEntity<Fruit> with body, @Nullable headers and HttpStatus
     */
    public ResponseEntity<Fruit> checkAddFruit(Fruit fruit) {
        try {
            //buscar si la fruta ya existe por nombre
            Optional<Fruit> fruitOptional = fruitRepository.findByNameIgnoreCase(fruit.getName());
            //si ya existe Error
            if (fruitOptional.isPresent()) return new ResponseEntity<>(fruit, HttpStatus.FOUND);
            //si viene sin nombre o la cantidad es negativa
            if (fruit.getName().isEmpty() || fruit.getQuantityKilos() < 0) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            //si no existe la instancio, la grabo en bbdd y retorno la respuesta
            Fruit newFruit = new Fruit(fruit.getName(), fruit.getQuantityKilos());
            fruitRepository.save(newFruit);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "application/json");
            headers.add("X-Custom-Message", "The product is created successfully");
            return new ResponseEntity<>(newFruit, headers, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Check if id enter by user exist in data base
     * @param id
     * @return ResponseEntity<Fruit> with @Nullable body and HttpStatus
     */
    public ResponseEntity<Fruit> checkIdExist(ObjectId id){
        //buscar si el id existe en el repository
        if (fruitRepository.existsById(id)) {
            return new ResponseEntity<>(fruitRepository.findById(id).get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Check if id enter by user exist in data  and update the information enter by user
     * @param id
     * @param fruit
     * @return ResponseEntity<Fruit> with @Nullable body and HttpStatus
     */
    public ResponseEntity<Fruit> updateFruit(ObjectId id, Fruit fruit) {
        Fruit fruitToUpdate = fruitRepository.findById(id).get(); //buscar la fruta en el repository con el id (Optional->get()) que recibo por parámetro
        fruitToUpdate.setName(fruit.getName());
        fruitToUpdate.setQuantityKilos(fruit.getQuantityKilos());
        fruitRepository.save(fruitToUpdate);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("X-Custom-Message", "The product is updated successfully");
        return new ResponseEntity<>(fruitToUpdate, headers, HttpStatus.CREATED);
    }
}
