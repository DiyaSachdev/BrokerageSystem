# BrokerageSystem
A mini brokerage system
In this problem, the objective is to create a toy brokerage firm software system while implementing OOP techniques. We will need to know some pre-requisites as below.

• A publicly listed company is a company whose ownership is distributed among general shareholders through a stock exchange. For example, Tata Consultancy Services, Google Inc, State Bank of India etc.
• A stock exchange or securities exchange is a facility where traders can buy/sell shares of publicly listed companies and other securities(i.e., bonds, rights, debentures etc.) mostly electronically. Forexample New York Stock Exchange, Bombay Stock Exchange etc,
• A person or entity who buys/sells securities is called a trader or investor depending on the context.It could be an individual or a society or an organisation.
• A brokerage firm is a liaison between the trader/investor and the stock exchange. For example,Interactive Borkers, Zerodha etc.
• Each listed company in the stock exchanges has a symbol referred to as ticker. For example, the ticker for Infosys is Infy, the ticker for Mahindra & Mahindra Limited is M&M.
Business takes place in Stock exchanges generally on weekdays for a period of 7 to 8 hours. For example,in India there are two major stock exchanges namely NSE and BSE where trading hours is from 9:15 AM to 3:30 PM. During the market hours, the prices of the stocks(i.e., TCS, Hindustan Uniliver, TVS Motors,SBI, DLF etc) keep changing based on the market conditions.
• LTP is the latest trading price of a stock at any given time.
• On a given trading day, the price at which the first transaction takes place is called the open price and the price at which the last transaction takes place is called the close price.
• On a given trading day, for a given stock, the highest price at which a transaction takes place is called the high price and the lowest price at which a transaction takes place is called the low price
• The lowest price at which a seller is willing to sell a particular stock is called the ask price.
• The highest price at which a buyer is willing to buy a particular stock is called the bid price.
• When a trader places an order with the brokerage firm the order is said to be open. Then the order(say, buy order) gets matched with another order of opposite type(say, sell order), the order gets executed and is said to be filled.
• The orderbook is the recordbook of all open buy and sell orders at any given time.
• It is possible to sell the shares of a stock even before owning it. Say you don’t own a share of TCS but you think that the price of TCS will fall in the next few hours. In that case you may sell a share of TCS and buy it later during the day. Such type of selling is called short selling.

• You should implement two stock exchanges namely NSE and BSE. A given stock may be registeredto either or both of these exchanges.
• Add new stocks into the system for trading. Suppose a new company is listed in the stock exchange, then in your program, you have to implement that by adding a new instance of your relevant class. The company should have a unique identifier or ticker. It should be possible to query Open, High,Low, Close values for a given stock.
• Delete an existing stock from the system if the company is delisted from the stock exchange. Such things happen in the stock market.
• Add new trader into the system. When a new trader registers himself with the broker you have to add her into the system by instantiating appropriate classes in your program. The trader should have a unique customer ID. It should be possible to query the list of shares of stocks she owns. It should
be possible to query the set of open orders she has placed.
• Enable placing Buy/Sell orders. Also ensure that the buyer has sufficient funds when she places a order. You should implement appropriate methods in your program.
• Execute transactions when Ask and Bid prices agree. You should implement appropriate methods in your program.
