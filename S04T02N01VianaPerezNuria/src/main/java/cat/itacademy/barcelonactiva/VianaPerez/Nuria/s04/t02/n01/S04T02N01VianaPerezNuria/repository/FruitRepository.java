package cat.itacademy.barcelonactiva.VianaPerez.Nuria.s04.t02.n01.S04T02N01VianaPerezNuria.repository;

import cat.itacademy.barcelonactiva.VianaPerez.Nuria.s04.t02.n01.S04T02N01VianaPerezNuria.domain.Fruit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FruitRepository extends JpaRepository<Fruit, Integer> {


    Optional<Fruit> findByNameIgnoreCase(String name);
}
