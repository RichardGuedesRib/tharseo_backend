import React from "react";
import Icon from "react-crypto-icons";

export default function Rowtableoldtransaction({
  symbol,
  acronym,
  orderid,
  date,
  side,
  quantity,
  price,
  profit,
  typeTransaction,
  pairTransaction,
  active,
  status,
}) {
  return (
    <tr>
      <th>
        {" "}
        <Icon name={symbol} size={25} />
      </th>
      <th className="label-cell-old-trades">{acronym}</th>
      <th className="label-cell-old-trades">{orderid}</th>
      <th className="label-cell-old-trades">{date}</th>
      <th className="label-cell-old-trades">{side}</th>
      <th className="label-cell-old-trades">{quantity}</th>
      <th className="label-cell-old-trades open-trade-media-response">$ {price}</th>
      <th className={`label-cell-old-trades open-trade-media-response ${profit > 0 ? "profit-green" : ""} ${profit < 0 ? "profit-red" : ""}`}>$ {profit}</th>
      <th className="label-cell-old-trades open-trade-media-response">{typeTransaction}</th>
      <th className="label-cell-old-trades open-trade-media-response">{pairTransaction}</th>
      <th className="label-cell-old-trades open-trade-media-response">{active}</th>
      <th className="label-cell-old-trades open-trade-media-response">{status}</th>
    </tr>
  );
}
