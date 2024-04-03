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
      <th className="label-cell-old-trades">$ {price}</th>
      <th className="label-cell-old-trades">$ {profit}</th>
      <th className="label-cell-old-trades">{typeTransaction}</th>
      <th className="label-cell-old-trades">{pairTransaction}</th>
      <th className="label-cell-old-trades">{active}</th>
      <th className="label-cell-old-trades">{status}</th>
    </tr>
  );
}
