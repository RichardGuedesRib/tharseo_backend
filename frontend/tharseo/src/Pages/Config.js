import "../assets/css/style.css";
import React, { useState, useEffect } from "react";
import Menubar from "../Components/Nav/menubar.jsx";

function Config({ user, addressServer, getUser }) {
  const [menuhidden, setMenuhidden] = useState(false);
  const wallet = user.wallet;
  const btnIsVisible = document.getElementById("icon-visible");
  let visibleBalance = false;

  // const updateAssetsExchange = async () => {
  //   try {
  //     const res = await fetch(
  //       addressServer + "/tharseo/updatedatauser/1"
  //     );
  //     if (!res.ok) {
  //       throw new Error("Error when get user");
  //     }
  //     const userData = await res.json();
  //     setUser(userData);
  //     console.log("Return by Variable: ", userData);
  //   } catch (error) {
  //     console.error("Error User Resquest", error);
  //   }
  // };

  useEffect(() => {}, []);

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
              <span className="text-name-user">Joao.</span>
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

          <aside className="container-trades container-config">
            <section className="container-dashboard-right-bottom-top">
              <span className="title-active-trades">Configurações</span>
            </section>

            <section className="container-dashboard-right-bottom-middle container-table-assets-trade container-item-config">
              <aside className="conteiner-line-config">
                <span className="config-line">
                  Sincronizar ativos com corretora
                </span>
                <span className="btn-config update">Sincronizar Agora</span>
              </aside>
            </section>
          </aside>
        </aside>
      </section>
    </main>
  );
}

export default Config;
