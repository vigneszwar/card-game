package cardgame.services.backendervices;

import cardgame.services.dealerservices.Dealer;
import org.springframework.stereotype.Component;
import java.util.HashMap;

@Component
public class UserDealerMap {
    HashMap<Long, Dealer> map;

    public UserDealerMap() {
        this.map = new HashMap<>();
    }

    public void mapUserDealer(long id, Dealer dealer) {
        this.map.put(id, dealer);
    }

    public Dealer getDealer(long id) {
        return this.map.get(id);
    }

}
