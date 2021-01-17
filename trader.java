package gl2;

import java.util.ArrayList;

public class trader {
    String name;
    Double balance;
    ArrayList<order> stock = new ArrayList<>();

    trader(String na1, double bal, ArrayList<order> stock1)
    {
        this.name = na1;
        this.balance = bal;
        this.stock = stock1;
    }

    public String getName() {
        return name;
    }


}
