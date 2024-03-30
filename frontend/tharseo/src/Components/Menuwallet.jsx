import { useEffect, useState } from "react";
import React from "react";
import Itemasset from "./Itemasset";

export default function Menuwallet({ wallet, limit, assetsPrice }) {
  const [updatedWallet, setUpdatedWallet] = useState([]);
  console.log(">>>>>>>>>>>>>ASSETPRICE");
  console.log(assetsPrice);
  console.log(">>>>>>>>>>>>>wallet");
  console.log(wallet);

  useEffect(() => {
    if (wallet && assetsPrice) {
      const updatedWallet = updateWallet(wallet, assetsPrice);
      setUpdatedWallet(updatedWallet);
    }
  }, [wallet, assetsPrice]);

  const updateWallet = (wallet, prices) => {
    return wallet.map((item) => {
      const priceItem = prices.find((price) => price.symbol === item.symbol);
      return {
        ...item,
        price: priceItem ? priceItem.price : null,
      };
    });
  };

  const walletFilter = Array.isArray(updatedWallet)
    ? updatedWallet.slice(0, limit)
    : [];

  console.log(walletFilter);
  return (
    <section className="container-assets-wallet">
      {walletFilter.map((item, index) => (
        <Itemasset
          key={index}
          symbol={item.acronym.toLowerCase()}
          name={item.name}
          quantity={item.quantity}
          value={item.price}
          percent="55%"
        />
      ))}
    </section>
  );
}
