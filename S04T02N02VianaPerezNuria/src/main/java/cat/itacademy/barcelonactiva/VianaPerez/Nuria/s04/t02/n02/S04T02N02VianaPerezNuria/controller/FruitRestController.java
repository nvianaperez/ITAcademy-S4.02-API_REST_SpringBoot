package cat.itacademy.barcelonactiva.VianaPerez.Nuria.s04.t02.n02.S04T02N02VianaPerezNuria.controller;

import cat.itacademy.barcelonactiva.VianaPerez.Nuria.s04.t02.n02.S04T02N02VianaPerezNuria.domain.Fruit;
import cat.itacademy.barcelonactiva.VianaPerez.Nuria.s04.t02.n02.S04T02N02VianaPerezNuria.repository.FruitRepository;
import cat.itacademy.barcelonactiva.VianaPerez.Nuria.s04.t02.n02.S04T02N02VianaPerezNuria.service.FruitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:8080") //Política de seguridad CORS --> los navegadores por defecto bloquearán las solicitudes realizadas desde un dominio diferente al dominio del servidor que sirve la aplicación
@RequestMapping("/api/v1/fruits")
public class FruitRestController {


    @Autowired
    private FruitService fruitService;
    @Autowired
    private FruitRepository fruitRepository;

    //http://localhost:8080/fruita/add
    //POST /api/v1/fruits/add
    @PostMapping("/add")
    public ResponseEntity<Fruit> addFruit(@RequestBody Fruit fruit) {
        return fruitService.checkAddFruit(fruit);
    }

    //http://localhost:8080/fruita/update
    //PUT /api/v1/fruits/{id}} complete update
    //PATCH /api/v1/fruits/{id}} partial update
    @PutMapping("/{id}")
    public ResponseEntity<Fruit> updateOneFruit(@PathVariable("id") int id, @RequestBody Fruit fruit) {
        ResponseEntity<Fruit> fruitResponse = fruitService.checkIdExist(id);
        if (fruitResponse.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
            return fruitResponse;
        } else {
            ResponseEntity<Fruit> updatedFruit = fruitService.updateFruit(id, fruit);
            return updatedFruit;
        }
    }


    //http://localhost:8080/fruita/delete/{id}
    //DELETE /api/v1/fruits/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Fruit> deleteOneFruit(@PathVariable("id") int id) {
        ResponseEntity<Fruit> fruitResponse = fruitService.checkIdExist(id);

        if (fruitResponse.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
            return fruitResponse;
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            fruitRepository.delete(fruitResponse.getBody());
            return fruitResponse;
//            Fruit fruitToDelete = fruitResponse.getBody();
//            fruitRepository.delete(fruitToDelete);
//            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    //http://localhost:8080/fruita/getOne/{id}
    //GET /api/v1/fruits/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Fruit> getOneFruit(@PathVariable("id") int id) {
        return fruitService.checkIdExist(id);
    }

    //http://localhost:8080/fruita/getAll
    //GET /api/v1/fruits
    @GetMapping()
    public ResponseEntity<List<Fruit>> getAllFruits() {
        return fruitService.checkGetFruits();
    }
}
