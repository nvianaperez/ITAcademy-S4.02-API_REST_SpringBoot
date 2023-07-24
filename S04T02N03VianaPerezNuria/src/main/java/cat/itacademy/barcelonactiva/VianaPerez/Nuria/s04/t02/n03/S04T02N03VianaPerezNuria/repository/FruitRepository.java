package cat.itacademy.barcelonactiva.VianaPerez.Nuria.s04.t02.n03.S04T02N03VianaPerezNuria.repository;

import cat.itacademy.barcelonactiva.VianaPerez.Nuria.s04.t02.n03.S04T02N03VianaPerezNuria.domain.Fruit;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FruitRepository extends MongoRepository<Fruit, ObjectId> {
    Optional<Fruit> findByNameIgnoreCase(String name);
}
