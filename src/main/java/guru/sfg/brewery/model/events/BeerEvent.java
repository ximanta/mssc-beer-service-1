package guru.sfg.brewery.model.events;

import guru.sfg.brewery.model.BeerDto;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BeerEvent  implements Serializable {

    static final long serialVersionUID = 8477014659991470276L;

    private BeerDto beerDto;
}
