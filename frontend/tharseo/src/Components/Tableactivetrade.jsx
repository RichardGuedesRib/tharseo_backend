import React from "react";
import Rowtableactivetrades from "./Rowtableactivestrade";

export default function Tableactivetrade({ table }) {
  return (
    <table className="table-actives-trades">
      <thead>
        <tr className="row-table-trades">
          <th className="label-column-active-trades "></th>
          <th className="label-column-active-trades  label-name-active-trades">
            Nome
          </th>
          <th className="label-column-active-trades ">Saldo</th>
          <th className="label-column-active-trades ">Valor</th>
          <th className="label-column-active-trades ">Performance</th>
        </tr>
      </thead>
      <tbody className="body-table-actives-trades">
        {table.map((item, index) => (
          <Rowtableactivetrades
            key={index}
            symbol={item.symbol}
            name={item.name}
            value={item.balance}
            balance={item.value}
            performance={item.performance}
            trade={item.trade}
          />
        ))}
      </tbody>
    </table>
  );
}
