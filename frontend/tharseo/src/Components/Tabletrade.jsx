import React from "react";
import Rowtabletrades from "./Rowtabletrade";

export default function Tabletrade({ table }) {
  return (
    <table className="table-trades">
      <thead>
        <tr className="row-table-trades">
          <th className="label-column-active-trades "></th>
          <th className="label-column-active-trades  label-name-active-trades">
            Nome
          </th>
          <th className="label-column-active-trades ">Saldo</th>
          <th className="label-column-active-trades ">Profit</th>
          <th className="label-column-active-trades ">Performance</th>
          <th className="label-column-active-trades "></th>
          <th className="label-column-active-trades "></th>
        </tr>
      </thead>
      <tbody className="body-table-trades">
        {table.map((item, index) => (
          <Rowtabletrades
            key={index}
            symbol={item.acronym.replace("USDT", "").toLowerCase()}
            name={item.name}
            balance={item.price ? item.price.toFixed(2) : ""}
            profit={item.profit ? item.profit.toFixes(2) : "0.00"}
            performance={item.performance ? item.performance.toFixed(2) : "0.00"}
            trade={"Trade"}
          />
        ))}
      </tbody>
    </table>
  );
}
