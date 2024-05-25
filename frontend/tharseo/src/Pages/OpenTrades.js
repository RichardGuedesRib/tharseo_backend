import "../assets/css/style.css";
import React, { useState, useEffect, useContext } from "react";
import Menubar from "../Components/Nav/menubar.jsx";
import Tableopentrade from "../Components/OpenTransactions/Tableopentrade.jsx";
import serverConfig from "../Services/ServerConfig.js";
import { UserContext } from "../Services/UserDataProvider.js";
import Header from "../Components/Nav/Header.jsx";

function OpenTrades() {
  const { userProfile, transactions, updateUserData, setIDUser } =
    useContext(UserContext);
  const [limitAsset, setLimitAsset] = useState(14);
  const [openTransactions, setOpenTransactions] = useState([]);
  const [userTransactions, setUserTransactions] = useState(
    userProfile.transactions
  );
  
  const [wallet, setWallet] = useState(userProfile.wallet);

  useEffect(() => {
    if (userTransactions && userTransactions.length > 0) {
      const openTransactions = userTransactions.filter(
        (item) => item.status === "Open" || item.status === "Await"
      );
      setOpenTransactions(openTransactions);
    }
    updateUserData();
  }, [userTransactions]);

  useEffect(() => {
    if (userTransactions.length > 0) {
      const open = userTransactions.filter(
        (item) => item.status === "Open" || item.status === "Await"
      );
      setOpenTransactions(open);
    }
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
        <Menubar  />

        <aside className="container-dashboard-trades">
          <Header />

          <aside className="container-trades container-open-trades">
            <section className="container-dashboard-right-bottom-top">
              <span className="title-active-trades">Trades Pendentes</span>
            </section>

            <section className="container-dashboard-right-bottom-middle container-table-assets-trade table-open-responsive">
              <Tableopentrade
                transactions={openTransactions}
                limit={limitAsset}
                getUser={updateUserData}
                user={userProfile}
                addressServer={serverConfig.addressServerTharseo}
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

export default OpenTrades;
