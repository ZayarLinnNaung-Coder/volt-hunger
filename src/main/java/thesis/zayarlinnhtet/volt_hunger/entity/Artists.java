package thesis.zayarlinnhtet.volt_hunger.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@Setter
public class Artists {

    @Id
    private String id;
    private String name;
    private String image;
    private String description;
}
