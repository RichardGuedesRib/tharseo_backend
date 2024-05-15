import React, {useContext, useState, useEffect} from "react";
import Rowtabletrades from "./Rowtabletrade";
import { UserContext } from "../../Services/UserDataProvider";
import serverConfig from "../../Services/ServerConfig";

export default function Tabletrade({
  table,
  setContainerInputGrid,
  getGridData,
  setGridConfig,
  addressServer,
  user,
  limitActiveTrade
}) {

  const { userProfile, wallet, updateUserData } =
  useContext(UserContext);
  const [walletFilter, setWalletFilter] = useState([]);

  useEffect(() => {
    
    if (wallet) {
    
      const activesAssets = wallet.filter((item) => (item.isActive ===1));

    
      const itensWallet = Array.isArray(activesAssets)
        ? activesAssets.slice(0, limitActiveTrade)
        : [];

      setWalletFilter(itensWallet);

        }
 
    return () => {
     
    }
  }, [])
  

  return (
    <table className="table-trades config-media-trade">
      <thead>
        <tr className="row-table-trades">
          <th className="label-column-active-trades "></th>
          <th className="label-column-active-trades  label-name-active-trades">
            Nome
          </th>
          <th className="label-column-active-trades ">Saldo</th>
          <th className="label-column-active-trades ">Profit</th>
          <th className="label-column-active-trades ">Performance</th>
          <th className="label-column-active-trades "></th>
          <th className="label-column-active-trades "></th>
        </tr>
      </thead>
      <tbody className="body-table-trades">
        {walletFilter && walletFilter.map((item, index) => {
          const itemTrade = setGridConfig
            ? setGridConfig.find(
                (configItem) => configItem.acronym === item.acronym
              )
            : null;

         
          return (
            <Rowtabletrades
              key={index}
              id={item.id}
              symbol={item.acronym.replace("USDT", "").toLowerCase()}
              name={item.name}
              balance={item.price ? item.price.toFixed(2) : ""}
              profit={
                itemTrade && itemTrade.profit
                  ? itemTrade.profit.toFixes(2)
                  : "0.00"
              }
              performance={
                itemTrade && itemTrade.performance
                  ? itemTrade.performance.toFixed(2)
                  : "0.00"
              }
              trade={"Trade"}
              isActive={
                itemTrade && itemTrade.isActive ? itemTrade.isActive : null
              }
              setContainerInputGrid={setContainerInputGrid}
              getGridData={getGridData}
              setGridConfig={setGridConfig}
              addressServer={addressServer}
              user={user}
              className="row-trade"
            />
          );
        })}
      </tbody>
    </table>
  );
}
