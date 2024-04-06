import React, { useEffect, useState } from "react";
import Rowtableoldtransaction from "./Rowtableoldtransaction";

export default function Tableoldtrade({ transactions, limit }) {
  const [transactionsFilter, setTransactionsFilter] = useState([]);

  useEffect(() => {
    const filter = Array.isArray(transactions)
      ? transactions.slice(0, limit)
      : [];
    setTransactionsFilter(filter);
  }, [transactions, limit]);

  return (
    <aside className="container-table-old-trades ">
      <table className="table-actives-trades">
        <thead>
          <tr className=" ">
            <th className="label-column-old-trades">Symbol</th>
            <th className="label-column-old-trades">Nome</th>
            <th className="label-column-old-trades">Order ID</th>
            <th className="label-column-old-trades">Data</th>
            <th className="label-column-old-trades">Side</th>
            <th className="label-column-old-trades">Quantidade</th>
            <th className="label-column-old-trades">Preço</th>
            <th className="label-column-old-trades">Profit</th>
            <th className="label-column-old-trades">Tipo</th>
            <th className="label-column-old-trades">Trans. Par</th>
            <th className="label-column-old-trades">Op. Ativa?</th>
            <th className="label-column-old-trades">Status</th>
          </tr>
        </thead>
        <tbody className="body-table-actives-trades">
          {transactionsFilter &&
            transactionsFilter.length > 0 &&
            transactionsFilter.map((item, index) => (
              <Rowtableoldtransaction
                key={index}
                symbol={
                  item.acronym
                    ? item.acronym.replace("USDT", "").toLowerCase()
                    : ""
                }
                acronym={item.asset.acronym}
                orderid={item.orderId}
                date={new Date(item.openDate * 1000).toLocaleString()}
                side={item.side}
                quantity={item.executedQty}
                price={item.price}
                profit={item.profit ? item.profit : "0.00"}
                typeTransaction={item.typeTransaction}
                pairTransaction={item.pairTransaction}
                active={item.openTrade === false ? "Não" : "Sim"}
                status={item.status}
              />
            ))}
        </tbody>
      </table>
    </aside>
  );
}
