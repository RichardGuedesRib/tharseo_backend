import { useEffect, useState, useContext } from "react";
import React from "react";
import Itemasset from "./Itemasset";
import { UserContext } from "../../Services/UserDataProvider";

function Menuwallet({limit}) {
  const [walletFilter, setWalletFilter] = useState([]);
  const [sumWallet, setSumWallet] = useState(0);
  const {userProfile, wallet, updateUserData} = useContext(UserContext);

  useEffect(() => {
    if (wallet) {
      const sum = wallet.reduce((sumValue, item) => sumValue + item.price, 0);
      setSumWallet(sum);

      const activesAssets = wallet.filter((item) => (item.isActive ===1));
     
      const updatedWallet = activesAssets.map((item) => ({
        ...item,
        percent: ((item.price / sum) * 100).toFixed(2),
      }));
      updatedWallet.sort((a, b) => b.percent - a.percent);

      setWalletFilter(updatedWallet);

      const walletFilter = Array.isArray(updatedWallet)
        ? updatedWallet.slice(0, limit)
        : [];
        
      setWalletFilter(walletFilter);
    }

   
  }, [wallet, limit, userProfile]);

  return (
    <section className="container-assets-wallet">
      {walletFilter.map((item, index) => (
        <Itemasset
          key={index}
          symbol={item.acronym.replace("USDT", "").toLowerCase()}
          name={item.name}
          quantity={item.quantity ? item.quantity.toFixed(3) : ""}
          value={item.price ? item.price.toFixed(2) : ""}
          percent={item.percent}
        />
      ))}
    </section>
  );
}

export default Menuwallet;
