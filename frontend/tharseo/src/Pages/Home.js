import "../assets/css/style.css";
import React, { useState, useEffect } from "react";
import Menubar from "../Components/menubar.jsx";
import Chart from "../Components/Chart.jsx";
import Tableactivetrade from "../Components/Tableactivetrade.jsx";
import Menuwallet from "../Components/Menuwallet.jsx";

function Home({ user, addressServer }) {
  const [chartInfo, setChartInfo] = useState([]);

  useEffect(() => {
    const intervalId = setInterval(async () => {
      try {
        const res = await fetch(addressServer + "/chart");

        if (!res.ok) {
          throw new Error("Error when get chart info");
        }
        const data = await res.json();
        let chartCandles = [];
        for (let i = 0; i < data.length; i++) {
          const candleRequest = data[i];
          const startTimeInMillis = parseInt(candleRequest.startTime);
          const newCandle = {
            x: new Date(startTimeInMillis),
            y: [
              parseFloat(candleRequest.openPrice),
              parseFloat(candleRequest.highPrice),
              parseFloat(candleRequest.lowPrice),
              parseFloat(candleRequest.closePrice),
            ],
          };
          chartCandles.push(newCandle);
        }
        setChartInfo(chartCandles);
      } catch (error) {
        console.error("Error Chart Resquest", error);
      }
    }, 5000);
    return () => clearInterval(intervalId);
  }, []);

  const wallet = user.wallet;
  const testetable = [
    {
      symbol: "btc",
      name: "Bitcoin",
      value: "1000",
      balance: "1.56",
      value: "34345",
      performance: "+54",
      trade: "Trade",
    },
    {
      symbol: "ada",
      name: "Cardano",
      value: "1000",
      balance: "1.56",
      value: "34345",
      performance: "+54",
      trade: "Trade",
    },
    {
      symbol: "sol",
      name: "Solana",
      value: "1000",
      balance: "1.56",
      value: "34345",
      performance: "+54",
      trade: "Trade",
    },
  ];
  const [limitAsset, setLimitAsset] = useState(5);
  const btnIsVisible = document.getElementById("icon-visible");

  let visibleBalance = false;

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

  return (
    <main className="app-dashboard">
      <section className="container-dashboard">
        <Menubar />

        <aside className="container-dashboard-right">
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

          <aside className="container-dashboard-right-middle">
            <aside className="menu-wallet">
              <section className="container-title-menu-wallet">
                <span className="title-menu-wallet">Ativos em Carteira</span>
              </section>
              <Menuwallet wallet={wallet} limit={limitAsset} />

              <section className="container-button">
                <span
                  className="btn-view-all"
                  id="btnviewall"
                  onClick={showMoreAssets}
                >
                  <span className="text-btn-view-all" id="btn-view-all">
                    Ver Todos
                  </span>
                </span>
              </section>
            </aside>
            <Chart data={chartInfo} wallet={wallet} />
          </aside>

          <aside className="container-dashboard-right-bottom">
            <section className="container-dashboard-right-bottom-top">
              <span className="title-active-trades">Ativar Trade</span>
            </section>
            <section className="container-dashboard-right-bottom-middle">
              <Tableactivetrade table={testetable} />
            </section>
         
          </aside>
        </aside>
      </section>
    </main>
  );

  function showMoreAssets() {
    setLimitAsset((prevLimit) => prevLimit + 5);
    document
      .getElementsByClassName("container-assets-wallet")[0]
      ?.classList.add("show-more");
  }
}

export default Home;
