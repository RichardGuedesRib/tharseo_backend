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
    <tr className="row-opentrade-mobile">
      <th className="row-media-icon">
        {" "}
        <Icon name={symbol} size={25} />
      </th>
      <th className="label-cell-old-trades row-media-acronym">{acronym}</th>
      <th className="label-cell-old-trades row-media-id">{orderid}</th>
      <th className="label-cell-old-trades row-media-old-date">{date}</th>
      <th className="label-cell-old-trades row-media-open-side">{side}</th>
      <th className="label-cell-old-trades row-media-old-quantity">{quantity}</th>
      <th className="label-cell-old-trades row-media-price">$ {price}</th>
      <th className={`label-cell-old-trades row-media-old-profit ${profit > 0 ? "profit-green" : ""} ${profit < 0 ? "profit-red" : ""}`}>$ {profit}</th>
      <th className="label-cell-old-trades row-media-type">{typeTransaction}</th>
      <th className="label-cell-old-trades row-media-pairtransaction">{pairTransaction}</th>
      <th className="label-cell-old-trades row-media-active">{active}</th>
      <th className="label-cell-old-trades row-media-status">{status}</th>
      <th className="row-media-open-more">
        <span class="material-symbols-outlined">more_vert</span>
      </th>
    </tr>
  );
}
