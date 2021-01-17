package gl2;

import javax.swing.*;
import java.io.*;
import java.util.*;

public class brokerageFirm {

    public static void main(String[] args) {
        stockExchange BSE = new stockExchange("BSE");
        stockExchange NSE = new stockExchange("NSE");

        System.out.println(" Reading from file1");

        readFile1(BSE,NSE);

        System.out.println("Reading from file 2");
         readFile2();
    }

    public static void readFile1(stockExchange BSE, stockExchange NSE) {
        JFileChooser op = new JFileChooser(" ");
        op.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        if (op.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File f1 = op.getSelectedFile();

            try {

                Scanner file = new Scanner(f1);
                ArrayList<order> o2 = new ArrayList<>();
                ArrayList<trader> tr = new ArrayList<>();
                orderBook oBook = new orderBook();


                try {
                    BufferedWriter out = new BufferedWriter(new FileWriter("outfilename.txt"));

                    int a =0;
                    while (file.hasNextLine()) {
                        String line = file.nextLine();
                        if (line.startsWith("Add scrip:")) {
                            String[] pos = line.split("[;:, ]", 0);
                            int na = (int) (Math.random() * 2);
                            String className;
                            if (na == 0)
                            {
                                BSE.addComp(new company("BSE", pos[3], pos[7], Double.parseDouble(pos[10]), Double.parseDouble(pos[13]), Double.parseDouble(pos[16]), Double.parseDouble(pos[19])));
                                className = "BSE";
                            }
                            else
                            {
                                NSE.addComp(new company("NSE", pos[3], pos[7], Double.parseDouble(pos[10]), Double.parseDouble(pos[13]), Double.parseDouble(pos[16]), Double.parseDouble(pos[19])));
                                className = "NSE";
                            }

                            out.write("Added scrip: "+pos[3]+ " with a new instantiation of "+ className + " stockExchange");
                            out.write("\n");

                        } else if (line.startsWith("Add user:")) {
                            String[] pos = line.split("[;:, {}]", 0);
                            ArrayList<order> o1 = new ArrayList<>();
                            for (int i = 0; i < pos.length; i++) {
                                if (i >= 9) {
                                    if (pos.length > 10) {
                                        o1.add(new order(BSE, NSE, tr, oBook, pos[3], "none", pos[i + 1], Integer.parseInt(pos[i + 2]), 0));
                                    }
                                    i = i + 2;

                                }

                            }
                            tr.add(new trader(pos[3], Integer.parseInt(pos[6]), o1));
                            out.write("Added user: "+pos[3]+ " with a new instantiation of Trader");
                            out.write("\n");

                        } else if (line.startsWith("Place order,")) {
                            String[] pos = line.split("[;:, ]", 0);

                         if(a==0) {
                             out.write("\n");
                             out.write("Market opens");
                             out.write("\n");
                         }

                            order o1 = new order(BSE, NSE, tr, oBook, pos[5], pos[9], pos[13], Integer.parseInt(pos[16]), Double.parseDouble(pos[20]));
                             o2.add(o1);
                            if(o1.getStatus() == 1)
                                out.write("Order rejected for user: "+pos[5]+ ", type: "+pos[9]+", scrip: "+pos[13]+", qty: "+pos[16]+", rate: "+pos[20]);
                            else
                                out.write("Order placed for user: "+pos[5]+ ", type: "+pos[9]+", scrip: "+pos[13]+", qty: "+pos[16]+", rate: "+pos[20]);

                            out.write("\n");
                            a++;
                        }

                        else if (line.startsWith("Show Orderbook"))
                        {
                            out.write("\n");
                            out.write("Orderbook: \n");
                            oBook.printBook(out);
                        }

                        else if (line.startsWith("Execute"))
                        {
                            out.write("\n");
                            out.write("Executed transactions:\n");
                            oBook.transaction(tr,out);
                        }

                        else if (line.startsWith("Show sector:"))
                        {
                            out.write("\n");
                            String[] pos = line.split("[;:, ]", 0);
                            out.write("Scrips listed in sector: "+pos[3]+"\n");
                            //  System.out.println(pos[3]);
                            BSE.showSector(pos[3],out);
                            NSE.showSector(pos[3],out);
                        }

                        else if (line.startsWith("Delete scrip:")) {
                            out.write("\n");
                            String[] pos = line.split("[;:, ]", 0);
                            //  System.out.println(pos[3]);
                            for (int i = 0; i < BSE.getComp().size(); i++) {
                                if ((BSE.getComp()).get(i).ticker.equals(pos[3])) {
                                    out.write("Deleted scrip: "+(BSE.getComp()).get(i).ticker+"\n");

                                    BSE.delComp((BSE.getComp()).get(i));
                                }
                            }
                            for (int i = 0; i < NSE.getComp().size(); i++) {
                                if ((NSE.getComp()).get(i).ticker.equals(pos[3])) {
                                    out.write("Deleted scrip: "+(NSE.getComp()).get(i).ticker+"\n");

                                    NSE.delComp((NSE.getComp()).get(i));
                                }
                            }
                        }
                        else if (line.startsWith("Delete User:")) {
                            out.write("\n");
                            String[] pos = line.split("[;:, ]", 0);
                            for (int i = 0; i < tr.size(); i++) {
                                if (tr.get(i).name.equals(pos[3])) {
                                    out.write("Deleted user: "+tr.get(i).name+"\n");

                                    tr.remove(tr.get(i));
                                    break;
                                }
                            }
                        }
                        else if (line.startsWith("Show Scrips")) {
                            out.write("\n");
                            printComp(BSE,out);
                            printComp(NSE,out);
                        }
                        else if (line.startsWith("Show Users")) {
                            out.write("\n");
                            printTrader(tr,out);
                        }
                        else if (line.startsWith("Exit")) {
                            out.write("\nMarket closes");
                            break;
                        }
                    }
                    out.close();
                }
                catch (IOException e) {
                    JOptionPane.showMessageDialog(null, "File not Found");
                }

            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "File not Found");
            }
        }
    }

    public static void printComp(stockExchange SE,BufferedWriter out) throws IOException
    {
        out.write("Scrips: \n");
        for (int i = 0; i < SE.comp.size(); i++) {
            out.write(SE.comp.get(i).ticker + ", sector: "+SE.comp.get(i).cat+", O:"+SE.comp.get(i).open +", H:"+SE.comp.get(i).high+", L:"+SE.comp.get(i).lower +", C:"+SE.comp.get(i).close);
            out.write("\n");

        }
    }

    public static void printTrader(ArrayList<trader> tr,BufferedWriter out)  throws IOException{
        int j=0;
        out.write("Users: \n");
       String list=" ";

        for (int i = 0; i < tr.size(); i++)
        {
            while(j<((tr.get(i)).stock.size()))
            {
               list += ((tr.get(i).stock).get(j++).disStock());
               list += ", ";
            }

            out.write("user: "+tr.get(i).name+", funds: "+tr.get(i).balance+", holding: { "+list + " }");
            out.write("\n");
            j=0;
            list = " ";
          //  System.out.println("user: "+tr.get(i).name+", funds: "+tr.get(i).balance+", holding: { "+(tr.get(i).stock).get(j).disStock());
        }
    }

    public static void readFile2() {
        JFileChooser op = new JFileChooser(" ");
        op.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        if (op.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File f1 = op.getSelectedFile();
            String delimiter = ",";

            try
            {
                FileReader fr = new FileReader(f1);
                BufferedReader br = new BufferedReader(fr);
                String line = "";

                String[] tempArr;
                double[] open = new double[15];
                double[] close = new double[15];
                double[] last = new double[15];

                int i=0;
                double sum=0,max=0,min=1000,profit=0;
                line = br.readLine();
                while((line = br.readLine()) != null)
                {
                    tempArr = line.split(delimiter);
                  /*  for(String tempStr : tempArr)
                    {
                        System.out.println(tempArr[3]);
                    }*/

                    open[i]= Double.parseDouble(tempArr[3]);
                    last[i]= Double.parseDouble(tempArr[6]);
                    close[i]= Double.parseDouble(tempArr[7]);
                    i++;

                }
                for(i=0; i<15; i++)
                {
                    sum = sum+last[i];

                    if(max < close[i])
                        max = close[i];
                    if(min > close[i])
                        min = close[i];

                    if(open[i]<close[i])
                        profit = profit + (close[i]-open[i]);
                    else
                        profit = profit + (open[i]-close[i]);

                }

                System.out.println("Average: "+sum/15);
                System.out.println("Max drawdown: "+(max-min));
                System.out.println("Max return potential percentage: "+ (profit/open[0])*100);
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }

        }
    }
}