package cat.itacademy.barcelonactiva.VianaPerez.Nuria.s04.t02.n01.S04T02N01VianaPerezNuria.service;

import cat.itacademy.barcelonactiva.VianaPerez.Nuria.s04.t02.n01.S04T02N01VianaPerezNuria.domain.Fruit;
import cat.itacademy.barcelonactiva.VianaPerez.Nuria.s04.t02.n01.S04T02N01VianaPerezNuria.repository.FruitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FruitService {

    @Autowired
    private FruitRepository fruitRepository;

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
            return new ResponseEntity<>(newFruit, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Fruit> checkIdExist(int id) {
        //buscar si el id existe en el repository
        if (fruitRepository.existsById(id)) {
            return new ResponseEntity<>(fruitRepository.findById(id).get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    public ResponseEntity<Fruit> updateFruit(int id, Fruit fruit) {
        Fruit fruitToUpdate = fruitRepository.findById(id).get(); //buscar la fruta en el repository con el id (Optional->get()) que recibo por parámetro
        fruitToUpdate.setName(fruit.getName());
        fruitToUpdate.setQuantityKilos(fruit.getQuantityKilos());
        fruitRepository.save(fruitToUpdate);
        return new ResponseEntity<>(fruitToUpdate,HttpStatus.CREATED);
    }
}
