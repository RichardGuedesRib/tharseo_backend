import React, { useState, useContext } from "react";
import Icon from "react-crypto-icons";
import { useNavigate } from "react-router-dom";
import { UserContext } from "../../Services/UserDataProvider.js";
import serverConfig from "../../Services/ServerConfig.js";

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
}) {
  const { userProfile, updateUserData } = useContext(UserContext);
  const navigate = useNavigate();
  const cancelOrder = async () => {
    const urlRequest = `${serverConfig.addressServerTharseo}/tharseo/cancelopenorder?user=${userProfile.id}&orderid=${orderid}`;

    try {
      const request = await fetch(urlRequest, { method: "DELETE" });
      if (!request.ok) {
        throw new Error("Error when cancel order");
      }
      await updateUserData();
      navigate("/opentrades");
    } catch (error) {
      console.error(`Error Requesting Order:`, error);
    }
  };

  return (
    <tr className="row-opentrade-mobile">
      <th className="row-media-icon">
        {" "}
        <Icon name={symbol} size={25} />
      </th>
      <th className="label-cell-old-trades row-media-acronym">{acronym}</th>
      <th className="label-cell-old-trades row-media-id">{orderid}</th>
      <th className="label-cell-old-trades row-media-open-date">{date}</th>
      <th className="label-cell-old-trades row-media-open-side">{side}</th>
      <th className="label-cell-old-trades row-media-open-quantity">
        {quantity}
      </th>
      <th className="label-cell-old-trades open-trade-media-response row-media-price">
        $ {price}
      </th>
      <th className="label-cell-old-trades open-trade-media-response row-media-open-profit">
        $ {profit}
      </th>
      <th className="label-cell-old-trades row-media-type">
        {typeTransaction}
      </th>
      <th className="label-cell-old-trades row-media-pairtransaction">
        {pairTransaction}
      </th>
      <th className="label-cell-old-trades row-media-active">{active}</th>
      <th className="label-cell-old-trades row-media-status">{status}</th>
      <th className="label-cell-old-trades row-media-delete">
        <span className="btn-cancel-order" onClick={cancelOrder}>
          <span class="material-symbols-outlined">delete_forever</span>
        </span>
      </th>
      <th className="row-media-open-more">
        <span class="material-symbols-outlined">more_vert</span>
      </th>
    </tr>
  );
}
