import "../assets/css/style.css";
import React, { useState, useEffect } from "react";
import Menubar from "../Components/menubar.jsx";
import Tabletrade from "../Components/Tabletrade.jsx";
import Containergrid from "../Components/Containergrid.jsx";

function Trade({ user, addressServer }) {
  const [walletFilter, setWalletFilter] = useState([]);
  const [limitAsset, setLimitAsset] = useState(14);
  const [containerInputGrid, setContainerInputGrid] = useState(false);
  const [gridData, setGridData] = useState(null);
  const [menuhidden, setMenuhidden] = useState(false);
  const wallet = user.wallet;
  const btnIsVisible = document.getElementById("icon-visible");
  let visibleBalance = false;

  useEffect(() => {
    if (wallet) {
      const walletFilter = Array.isArray(wallet)
        ? wallet.slice(0, limitAsset)
        : [];
      setWalletFilter(walletFilter);
    }
  }, [limitAsset, gridData]);

  const getGridData = (data) => {
    setGridData(data);
  };

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

  function show() {
    setContainerInputGrid(true);
    console.log("Chamou Visible");
    console.log(containerInputGrid);
  }

  return (
    <main className="app-dashboard">
       <section className="menu-hidden" onClick={() => {setMenuhidden(!menuhidden)}}>
        <span class="material-symbols-outlined" style={{fontSize:30}}>menu</span>
      </section>
      <section className="container-dashboard">
        <Menubar menuhidden={menuhidden}/>
        <Containergrid
          containerInputGrid={containerInputGrid}
          setContainerInputGrid={setContainerInputGrid}
          gridData={gridData}
          addressServer={addressServer}
          user={user}
        />

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
              <span className="title-active-trades">
                Configurar Automatização
              </span>
              <span className="title-active-trades" onClick={() => show()}>
                Separado para Buttons Filter
              </span>
            </section>

            <section className="container-dashboard-right-bottom-middle container-table-assets-trade">
              <aside className="container-table-trade-assets">
                <Tabletrade
                  table={walletFilter}
                  setContainerInputGrid={setContainerInputGrid}
                  getGridData={getGridData}
                  setGridConfig={user.grids}
                  addressServer={addressServer}
                  className="show-more"
                  user={user}
                />
              </aside>

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

export default Trade;
