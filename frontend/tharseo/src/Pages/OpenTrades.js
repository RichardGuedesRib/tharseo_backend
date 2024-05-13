import "../assets/css/style.css";
import React, { useState, useEffect, useContext } from "react";
import Menubar from "../Components/Nav/menubar.jsx";
import Tableopentrade from "../Components/OpenTransactions/Tableopentrade.jsx";
import serverConfig from "../Services/ServerConfig.js";
import { UserContext } from "../Services/UserDataProvider.js";
import Header from "../Components/Nav/Header.jsx";

function OpenTrades({  }) {
  const { userProfile,  transactions, updateUserData, setIDUser } =
  useContext(UserContext);
  const [limitAsset, setLimitAsset] = useState(14);
  const [openTransactions, setOpenTransactions] = useState([]);
  const [userTransactions, setUserTransactions] = useState(userProfile.transactions);
  const [menuhidden, setMenuhidden] = useState(false);
 
    const [wallet, setWallet] = useState(userProfile.wallet);

 

  const btnIsVisible = document.getElementById("icon-visible");
  let visibleBalance = false;

  useEffect(() => {
    if (userTransactions && userTransactions.length > 0) {
      const openTransactions = userTransactions.filter(
        (item) => item.status === "Open" || item.status === "Await"
      );
      setOpenTransactions(openTransactions);
    }
  }, [userTransactions]);

  useEffect(() => {
    if (userTransactions.length > 0) {
      const open = userTransactions.filter(
        (item) => item.status === "Open" || item.status === "Await"
      );
      setOpenTransactions(open);
    }
  }, [userTransactions]);

  const showBalance = () => {
    const balance = document.getElementById("balance-text");
    const iconEye = document.getElementById("icon-visible");
    const usdt = wallet.find((item) => item.acronym === "USDTUSDT");
    const balanceUsdt = usdt.quantity.toFixed(0);
    if (visibleBalance === true) {
      balance.innerText = "$ -----";
      iconEye.innerText = "visibility_off";
      visibleBalance = false;
    } else {
      balance.innerText = `$ ${balanceUsdt}`;
      visibleBalance = true;
      iconEye.innerText = "visibility";
    }
  };
  if (btnIsVisible) {
    btnIsVisible.addEventListener("click", showBalance);
  }

  function showMoreAssets() {
    setLimitAsset((prevLimit) => prevLimit + 5);
    document
      .getElementsByClassName("table-trades")[0]
      ?.classList.add("show-more");
  }

  return (
    <main className="app-dashboard">
      <section
        className="menu-hidden"
        onClick={() => {
          setMenuhidden(!menuhidden);
        }}
      >
        <span class="material-symbols-outlined" style={{ fontSize: 30 }}>
          menu
        </span>
      </section>
      <section className="container-dashboard">
        <Menubar menuhidden={menuhidden} />

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
