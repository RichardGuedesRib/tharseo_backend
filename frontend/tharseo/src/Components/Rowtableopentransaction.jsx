import React, { useState } from "react";
import Icon from "react-crypto-icons";
import { useNavigate } from "react-router-dom";

export default function Rowtableopentransaction({
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
  user,
  getUser,
  addressServer,
}) {
  const navigate = useNavigate();
  const cancelOrder = async () => {
    const urlRequest = `${addressServer}/tharseo/cancelopenorder?user=1&orderid=${orderid}`;

    try {
      const request = await fetch(urlRequest, { method: "DELETE" });
      if (!request.ok) {
        throw new Error("Error when cancel order");
      }
      await getUser();
      alert("OK!");
      navigate("/");
    } catch (error) {
      console.error(`Error Requesting Order:`, error);
    }
  };

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
      <th className="label-cell-old-trades open-trade-media-response">$ {profit}</th>
      <th className="label-cell-old-trades open-trade-media-response">{typeTransaction}</th>
      <th className="label-cell-old-trades open-trade-media-response">{pairTransaction}</th>
      <th className="label-cell-old-trades open-trade-media-response">{active}</th>
      <th className="label-cell-old-trades open-trade-media-response">{status}</th>
      <th className="label-cell-old-trades">
        <span className="btn-cancel-order" onClick={cancelOrder}>
          <span class="material-symbols-outlined">delete_forever</span>
        </span>
      </th>
    </tr>
  );
}
