import "../assets/css/style.css";
import React, { useState, useEffect } from "react";
import Menubar from "../Components/menubar.jsx";
import Tableopentrade from "../Components/Tableopentrade.jsx";

function OpenTrades({ user, addressServer, getUser }) {
  const [limitAsset, setLimitAsset] = useState(14);
  const [openTransactions, setOpenTransactions] = useState([]);
  const [userTransactions, setUserTransactions] = useState(user.transactions);
  const wallet = user.wallet;

  const btnIsVisible = document.getElementById("icon-visible");
  let visibleBalance = false;

  useEffect(() => {
    if (userTransactions && userTransactions.length > 0) {
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
      <section className="container-dashboard">
        <Menubar />

        <aside className="container-dashboard-trades">
          <aside className="container-dashboard-right-top">
            <section className="container-dashboard-right-top-left">
              <span className="text-header-welcome">Bem Vindo, João</span>
            </section>
            <section className="container-dashboard-right-top-right">
              <section className="container-balance-visible">
                <span className="icon-visible-balance">
                  <span
                    class="material-symbols-outlined"
                    id="icon-visible"
                    style={{ fontSize: 20 }}
                  >
                    visibility_off
                  </span>
                </span>
                <span className="text-balance" id="balance-text">
                  $ -----
                </span>
              </section>

              <span className="icon-notification-header">
                <span
                  class="material-symbols-outlined"
                  style={{ fontSize: 30 }}
                >
                  notifications_unread
                </span>
              </span>
              <span className="text-name-user">Joao</span>
              <span className="avatar-header-user">
                <span
                  class="material-symbols-outlined"
                  style={{ fontSize: 50 }}
                >
                  face
                </span>
              </span>
            </section>
          </aside>

          <aside className="container-trades">
            <section className="container-dashboard-right-bottom-top">
              <span className="title-active-trades">Trades Pendentes</span>
            </section>

            <section className="container-dashboard-right-bottom-middle container-table-assets-trade">
              <Tableopentrade
                transactions={openTransactions}
                limit={limitAsset}
                getUser={getUser}
                user={user}
                addressServer={addressServer}
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
