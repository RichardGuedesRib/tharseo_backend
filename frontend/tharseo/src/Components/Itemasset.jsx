import React from "react";
import Icon from "react-crypto-icons";

export default function Itemasset({ symbol, name, quantity, percent }) {
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
        <span>------------------------</span>
        <span className="percent-asset-item">{percent}</span>
      </section>
    </aside>
  );
}
