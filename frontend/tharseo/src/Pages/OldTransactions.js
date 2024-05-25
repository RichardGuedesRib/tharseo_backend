import "../assets/css/style.css";
import React, { useState, useEffect, useContext } from "react";
import Menubar from "../Components/Nav/menubar.jsx";
import Tableoldtrade from "../Components/OldTransactions/Tableoldtrade.jsx";
import { UserContext } from "../Services/UserDataProvider.js";
import Header from "../Components/Nav/Header.jsx";

function OldTransactions() {
  const { userProfile,  transactions, updateUserData, setIDUser } =
  useContext(UserContext);
  const [limitAsset, setLimitAsset] = useState(14);
  const [oldTransactions, setOldTransactions] = useState([]);
  const [userTransactions, setUserTransactions] = useState(userProfile.transactions);
    const [wallet, setWallet] = useState(userProfile.wallet);




  useEffect(() => {
    if (userTransactions && userTransactions.length > 0) {
      const closedTransactions = userTransactions.filter(
        (item) => item.status === "Closed" || item.status === "Canceled"
      );
      setOldTransactions(closedTransactions);
    }

    updateUserData();
    }, [userTransactions]);


 
  function showMoreAssets() {
    setLimitAsset((prevLimit) => prevLimit + 5);
    document
      .getElementsByClassName("table-trades")[0]
      ?.classList.add("show-more");
  }

  return (
    <main className="app-dashboard">
    
      <section className="container-dashboard">
        <Menubar />

        <aside className="container-dashboard-trades">
          
         <Header/>

          <aside className="container-trades container-open-trades">
            <section className="container-dashboard-right-bottom-top">
              <span className="title-active-trades">
                Histórico de Transações
              </span>
            </section>

            <section className="container-dashboard-right-bottom-middle container-table-assets-trade table-open-responsive">
              <Tableoldtrade
                transactions={oldTransactions}
                limit={limitAsset}
              />

              <aside className="container-btn-showmore-trade">
                <span
                  className="btn-showmore-trade"
                  id="btn-showmore-trade"
                  onClick={showMoreAssets}
                >
                  Ver Mais
                </span>
              </aside>
            </section>
          </aside>
        </aside>
      </section>
    </main>
  );
}

export default OldTransactions;
