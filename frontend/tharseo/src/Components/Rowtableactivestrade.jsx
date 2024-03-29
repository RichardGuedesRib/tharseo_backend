import React from "react";
import Icon from "react-crypto-icons";

export default function Rowtableactivetrades({
  symbol,
  name,
  balance,
  value,
  performance,
  trade,
}) {
  return (
    <tr className="row-table-active-trades">
      <td className="label-active-trades">
        <span className="icon-active-trades">
          {" "}
          <Icon name={symbol} size={25} />
        </span>
      </td>

      <td className="label-active-trades label-row-name">{name}</td>
      <td className="label-active-trades">{balance}</td>
      <td className="label-active-trades">$ {value}</td>
      <td className="label-active-trades">{performance}%</td>
      <td className="label-active-trades container-btn-active-trade"><span className="btn-active-trades">{trade}</span></td>
    </tr>
  );
}
