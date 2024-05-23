import React, { useEffect, useState } from "react";
import Rowtableopentransaction from "./Rowtableopentransaction";

export default function Tableopentrade({
  transactions,
  limit,
  getUser,
  user,
  addressServer,
}) {
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
          <tr className="">
            <th className="label-column-old-trades">Symbol</th>
            <th className=" label-column-old-trades label-media-name">Nome</th>
            <th className="label-column-old-trades label-name-id">Order ID</th>
            <th className="label-column-old-trades">Data</th>
            <th className="label-column-old-trades">Side</th>
            <th className="label-column-old-trades">Quantidade</th>
            <th className="label-column-old-trades open-trade-media-response">
              Preço
            </th>
            <th className="label-column-old-trades label-media-profit">
              Profit
            </th>
            <th className="label-column-old-trades label-media-type">Tipo</th>
            <th className="label-column-old-trades label-media-pair">
              Trans. Par
            </th>
            <th className="label-column-old-trades label-media-active">
              Op. Ativa?
            </th>
            <th className="label-column-old-trades label-media-status">
              Status
            </th>
            <th className="label-column-old-trades">Cancel</th>
          </tr>
        </thead>
        <tbody className="body-table-actives-trades">
          {transactionsFilter &&
            transactionsFilter.length > 0 &&
            transactionsFilter.map((item, index) => {
              return (
                <Rowtableopentransaction
                  key={index}
                  symbol={
                    item.acronym
                      ? item.acronym.replace("USDT", "").toLowerCase()
                      : ""
                  }
                  acronym={item.asset.acronym}
                  orderid={item.id}
                  date={new Date(item.openDate).toLocaleString()}
                  side={item.side}
                  quantity={item.executedQty}
                  price={item.price}
                  profit={item.profit ? item.profit : "0.00"}
                  typeTransaction={item.typeTransaction}
                  pairTransaction={item.orderPairTrade}
                  active={item.openTrade === false ? "Não" : "Sim"}
                  status={item.status}
                  user={user}
                  getUser={getUser}
                  addressServer={addressServer}
                />
              );
            })}
        </tbody>
      </table>
    </aside>
  );
}
