package cat.itacademy.barcelonactiva.VianaPerez.Nuria.s04.t02.n02.S04T02N02VianaPerezNuria.repository;

import cat.itacademy.barcelonactiva.VianaPerez.Nuria.s04.t02.n02.S04T02N02VianaPerezNuria.domain.Fruit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
//JpaRepository --> todos los métodos del CRUD de JPA + ordenación + paginación del lado del servidor
public interface FruitRepository extends JpaRepository<Fruit, Integer> {

    Optional<Fruit> findByNameIgnoreCase(String name);
}
