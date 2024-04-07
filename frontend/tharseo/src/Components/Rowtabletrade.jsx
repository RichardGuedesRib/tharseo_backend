import React, { useState } from "react";
import Icon from "react-crypto-icons";

export default function Rowtabletrades({
  symbol,
  name,
  balance,
  profit,
  performance,
  trade,
  setContainerInputGrid

  
}) {
  const [toggle, setToggle] = useState(false);
  const [toggleColor, setToggleColor] = useState("#EAECEF");
  const [performanceColor, setPerformanceColor] = useState("#EAECEF");


  

  function toggled() {
    setToggle(!toggle);
    setToggleColor(toggle ? "#EAECEF" : "#006BFA");
  }

  return (
    <tr className="row-table-trades">
      <td className="label-active-trades">
        <span className="icon-active-trades">
          {" "}
          <Icon name={symbol} size={25} />
        </span>
      </td>

      <td className="label-active-trades label-row-name">{name}</td>
      <td className="label-active-trades">$ {balance}</td>
      <td className="label-active-trades">$ {profit}</td>
      <td className="label-active-trades">{performance}%</td>
      <td className="label-active-trades container-btn-active-trade">
        <span className="btn-trades" onClick={() => setContainerInputGrid(true)}>{trade}</span>
      </td>
      <td className="label-active-trades label-toogle-trade">
        <span className="btn-toogle-trade" onClick={toggled}>
          <span
            class="material-symbols-outlined"
            id="btn-toogle"
            style={{ fontSize: 35, color: toggleColor }}
          >
            {toggle ? "toggle_on" : "toggle_off"}
          </span>
        </span>
      </td>
    </tr>
  );
}
