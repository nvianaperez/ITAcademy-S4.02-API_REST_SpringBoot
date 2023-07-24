package cat.itacademy.barcelonactiva.VianaPerez.Nuria.s04.t02.n03.S04T02N03VianaPerezNuria.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "fruits")
@Data
@NoArgsConstructor
public class Fruit {
    @Id
    @Field("id")
    private ObjectId id;

    private String name;

    private int quantityKilos;

    public Fruit(String name, int quantity) {
        this.name = name;
        this.quantityKilos = quantity;
    }
    public Fruit(String name) {
        this.name = name;
        this.quantityKilos = 0;
    }
}
