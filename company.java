package gl2;

public class company {
    String exchange;
    String ticker;
    String cat;
    double open;
    double high;
    double lower;
    double close;

    double lowcirc;
    double uppcirc;

    company(String ex,String tick1, String cat1, double o1, double h1, double l1, double c1)
    {
        this.exchange = ex;
        this.ticker = tick1;
        this.cat = cat1;
        this.open = o1;
        this.high = h1;
        this.lower = l1;
        this.close = c1;

        this.lowcirc = this.close - 0.1*this.close;
        this.uppcirc = this.close + 0.1*this.close;
    }
}
