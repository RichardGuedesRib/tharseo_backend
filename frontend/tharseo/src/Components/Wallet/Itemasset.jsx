import React from "react";
import Icon from "react-crypto-icons";

export default function Itemasset({ symbol, name, quantity, value, percent }) {
  return (
    <aside className="item-asset-wallet">
      <section className="item-asset-wallet-top">
        <section className="item-asset-wallet-top-left">
          <span className="icon-asset-item">
            <Icon name={symbol} size={35} />
          </span>
          <span className="title-asset-item">{name}</span>
        </section>
        <span className="quantity-asset-item">{quantity}</span>
      </section>
      <section className="item-asset-wallet-bottom">
        <span className="value-asset-item">$ {value === "" ? "0.00" : value}</span>
        <span className="percent-asset-item">{!isNaN(percent) ? percent : "0"}%</span>
      </section>
    </aside>
  );
}
