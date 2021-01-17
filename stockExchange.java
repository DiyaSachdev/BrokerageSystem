package gl2;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;


public class stockExchange {
    ArrayList<company> comp = new ArrayList<>();
    String name;

    stockExchange(String na1)
    {this.name = na1;}

    public void addComp(company comp1)
    {
        (this.comp).add(comp1);
    }

    public ArrayList<company> getComp()
    {
        return (this.comp);
    }

    public void showSector(String sec, BufferedWriter out) throws IOException
    {
        for(int i=0; i<comp.size(); i++)
        {
            if((comp.get(i).cat).equals(sec))
            {
                out.write(comp.get(i).ticker +", OHLC<> = "+ comp.get(i).open+" , " + comp.get(i).high+" , " + comp.get(i).lower+" , " + comp.get(i).close);
                out.write("\n");

            }
        }
    }

    public void delComp(company comp1)
    {
        for(company c1: comp)
        {
            if (c1 == comp1) {

                comp.remove(c1);
                break;
            }

        }
    }

}
