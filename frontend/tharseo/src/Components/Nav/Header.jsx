import { useEffect, useState, useContext } from "react";
import React from "react";
import { UserContext } from "../../Services/UserDataProvider";
import perfilImage from "../../assets/img/perfil.jpg";

function Header() {
  const { userProfile, wallet, transactions, updateUserData, setIDUser } =
    useContext(UserContext);
  const [visibleBalance, setVisibleBalance] = useState(false);


  return (
    <>
      <aside className="container-dashboard-right-top">
        <section className="container-dashboard-right-top-left">
          <span className="text-header-welcome">
            {" "}
            Bem Vindo,{" "}
            {userProfile && userProfile.name ? userProfile.name : "Unknown"}
          </span>
        </section>
        <section className="container-dashboard-right-top-right">
          <section className="container-balance-visible">
            <span
              className="icon-visible-balance"
              onClick={() => setVisibleBalance(!visibleBalance)}
            >
              <span
                className="material-symbols-outlined"
                id="icon-visible"
                style={{ fontSize: 20 }}
              >
                {visibleBalance ? "visibility" : "visibility_off"}
              </span>
            </span>
            <span className="text-balance" id="balance-text">
              {visibleBalance
                ? `$ ${wallet
                    .find((item) => item.acronym === "USDTUSDT")
                    .quantity.toFixed(0)}`
                : "$ -----"}
            </span>
          </section>

          <span className="icon-notification-header">
            <span
              className="material-symbols-outlined"
              style={{ fontSize: 30 }}
            >
              notifications_unread
            </span>
          </span>
          <span className="text-name-user">
            {userProfile && userProfile.name ? userProfile.name : "Unknown"}
          </span>
          <span
            className="avatar-header-user"
            style={{ backgroundImage: `url(${perfilImage || "none"})` }}
          >
            {!perfilImage && (
              <span
                className="material-symbols-outlined"
                style={{ fontSize: 50 }}
              >
                face
              </span>
            )}
          </span>
        </section>
      </aside>
    </>
  );
}

export default Header;
