package gl2;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

public class order {
    String user;
    String type;
    String scrip;
    int status;
    int qty;
    double rate;

    order(stockExchange BSE,stockExchange NSE,ArrayList<trader> tr,orderBook oBook,String user1, String type1, String scrip1, int qty1, double rate1)
    {
        this.user = user1;
        this.type = type1;

        this.scrip = scrip1;
        this.qty = qty1;
        this.rate = rate1;
        this.status = 0;

        if(rate1!=0)
        {
            valid(BSE,NSE,tr,oBook);
        }

    }
    order(String user1, String type1, String scrip1, int qty1, double rate1){
        this.user = user1;
        this.type = type1;

        this.scrip = scrip1;
        this.qty = qty1;
        this.rate = rate1;
    }

    public void valid(stockExchange BSE, stockExchange NSE,ArrayList<trader> tr,orderBook oBook)
    {
        ArrayList<company> Bcomp = BSE.getComp();
        ArrayList<company> Ncomp = NSE.getComp();

        //System.out.println(this.scrip);
        for(int i=0; i<Bcomp.size(); i++)
        {
            if((Bcomp.get(i)).ticker.equals(this.scrip))
            {
                if(this.rate < (Bcomp.get(i)).lowcirc)
                    this.status =1;

                else if(this.qty*this.rate > gettrader(tr,this.user).balance && this.type.equals("buy"))
                    this.status =1;

                else if(this.rate > (Bcomp.get(i)).uppcirc)
                    this.status =1;

                else {
                    oBook.addOrder(new order(this.user,this.type,this.scrip,this.qty,this.rate));
                }
            }
        }

        for(int i=0; i<Ncomp.size(); i++)
        {
            if((Ncomp.get(i)).ticker.equals(this.scrip))
            {

                if(this.rate < (Ncomp.get(i)).lowcirc)
                    this.status =1;

                else if(this.qty*this.rate > gettrader(tr,this.user).balance && this.type.equals("buy"))
                    this.status =1;

                else if(this.rate > (Ncomp.get(i)).uppcirc)
                    this.status =1;

                else {
                    oBook.addOrder(new order(this.user,this.type,this.scrip,this.qty,this.rate));
                }
            }
        }
    }

    public int getStatus()
    {
        return this.status;
    }

    public String disStock()
    {
        return (this.scrip+":"+this.qty);
    }

    public static trader gettrader(ArrayList<trader> tr, String user)
    {
        int i;
        for( i=0; i<tr.size(); i++) {
           // System.out.println(i + " " + tr.get(i).name + " USER  "+user);
            if ((tr.get(i).name).equals(user)) {
                {
                   // System.out.println(i + " " + tr.get(i).name);
                    return tr.get(i);
                }
            }
        }
    return tr.get(0);
    }

}

class orderBook
{
    ArrayList<order> book = new ArrayList<>();

    public ArrayList<order> getBook() {
        return book;
    }

    public String getOrderName(int i){
        return book.get(i).scrip;
    }

    public void addOrder(order o1){
        book.add(o1);

    }
    public void printBook(BufferedWriter out) throws IOException {
        for(int i=0; i<book.size(); i++)
        {
            out.write(book.get(i).type +" Order "+ book.get(i).scrip+":"+book.get(i).qty+" at "+book.get(i).rate);
            out.write("\n");
        }
    }

    public void transaction(ArrayList<trader> tr,BufferedWriter out)throws IOException
    { //funds check

        for(int i=0; i<book.size();i++)
        {
           // System.out.println(book.get(i).scrip);
            for(int j=i+1; j<book.size();j++)
            {

                if(book.get(i).scrip.equals(book.get(j).scrip ))//&& book.get(i).type!=book.get(j).type)
                {
                    if(book.get(i).type.equals("sell") )
                    {

                        int k1=0, k2=0;
                        trader buyer = book.get(j).gettrader(tr,(book.get(j)).user);
                        trader seller = book.get(i).gettrader(tr,(book.get(i)).user);
                        // System.out.println("buyer: "+buyer.name +"stock: "+buyer.stock.size());
                        //  System.out.println("seller: "+seller.name);

                        for(k1=0; k1< buyer.stock.size(); k1++) {
                            if ((buyer.stock.get(k1).scrip).equals(book.get(j).scrip)) {

                                break;
                            }
                        }
                        for(k2=0; k2< seller.stock.size(); k2++)
                        {
                            //System.out.println(seller.stock.get(k2).scrip);
                            if((seller.stock.get(k2).scrip).equals(book.get(j).scrip))
                            {
                                break;
                            }
                        }
                        buyer.balance = buyer.balance - book.get(i).rate*book.get(i).qty;
                        seller.balance = seller.balance + book.get(i).rate*book.get(i).qty;
                        //  System.out.println("k1: "+k1 + " k2: "+k2);

                        (seller.stock.get(k2)).user=buyer.name;
                        buyer.stock.add(seller.stock.get(k2));
                        seller.stock.remove(k2);
                        // 25 qty of scrip:SBI sold for INR 195; Buyer: Jaydeep, Seller: Nusrat
                        out.write(book.get(i).qty+" qty of scrip:"+book.get(k1).scrip+" sold for INR "+book.get(i).rate+"; buyer: "+buyer.name+", Seller: "+seller.name);
                        out.write("\n");

                        book.remove(i);
                        //book.remove(j);
                    }
                    else if(book.get(i).type.equals("buy") && book.get(j).rate<=book.get(i).rate)
                    {

                        int k1=0, k2=0;
                       trader buyer = book.get(i).gettrader(tr,(book.get(i)).user);
                        trader seller = book.get(j).gettrader(tr,(book.get(j)).user);
                       // System.out.println("buyer: "+buyer.name +"stock: "+buyer.stock.size());
                      //  System.out.println("seller: "+seller.name);

                        for(k1=0; k1< buyer.stock.size(); k1++) {
                            if ((buyer.stock.get(k1).scrip).equals(book.get(i).scrip)) {

                                break;
                            }
                        }
                        for(k2=0; k2< seller.stock.size(); k2++)
                        {
                            //System.out.println(seller.stock.get(k2).scrip);
                            if((seller.stock.get(k2).scrip).equals(book.get(i).scrip))
                            {
                                break;
                            }
                        }
                        buyer.balance = buyer.balance - book.get(j).rate*book.get(j).qty;
                        seller.balance = seller.balance + book.get(j).rate*book.get(j).qty;
                      //  System.out.println("k1: "+k1 + " k2: "+k2);

                        (seller.stock.get(k2)).user=buyer.name;
                       buyer.stock.add(seller.stock.get(k2));
                       seller.stock.remove(k2);
                       // 25 qty of scrip:SBI sold for INR 195; Buyer: Jaydeep, Seller: Nusrat
                        out.write(book.get(j).qty+" qty of scrip:"+book.get(k1).scrip+" sold for INR "+book.get(j).rate+"; buyer: "+buyer.name+", Seller: "+seller.name);
                        out.write("\n");

                        book.remove(i);
                        //book.remove(j);
                    }
                }


            }
        }
            //    else
            //    {System.out.println("NO ");}

    }


}
