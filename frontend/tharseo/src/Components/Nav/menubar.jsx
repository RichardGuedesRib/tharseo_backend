import React from "react";
import { Link } from "react-router-dom";

export default function Menubar() {
  return (
    <>
      <aside className="container-dashboard-left">
        <section className="container-title-menubar">
          <span className="title-menubar">tharseo</span>
        </section>

        <aside className={`container-menubar-buttons }`}>
          <section className="container-menubar-buttons-top">
            <article className="button-menubar">
              <Link to="/home" className="link-button-menubar">
                <span className="icon-button-menubar">
                  <span
                    className="material-symbols-outlined"
                    style={{ fontSize: 30 }}
                  >
                    dashboard
                  </span>
                </span>
                <span className="text-button-menubar">
                  <span>Dashboard</span>
                </span>
              </Link>
            </article>

            <article className="button-menubar">
              <Link to="/trade" className="link-button-menubar">
                <span className="icon-button-menubar">
                  <span
                    className="material-symbols-outlined"
                    style={{ fontSize: 35 }}
                  >
                    bid_landscape
                  </span>
                </span>
                <span className="text-button-menubar">
                  <span>Trade</span>
                </span>
              </Link>
            </article>

            <article className="button-menubar">
              <Link to="/opentrades" className="link-button-menubar">
                <span className="icon-button-menubar">
                  <span
                    className="material-symbols-outlined"
                    style={{ fontSize: 35 }}
                  >
                    auto_graph
                  </span>
                </span>
                <span className="text-button-menubar">
                  <span>Em Andamento</span>
                </span>
              </Link>
            </article>

            <article className="button-menubar">
              <Link to="/oldtransactions" className="link-button-menubar">
                <span className="icon-button-menubar">
                  <span
                    className="material-symbols-outlined"
                    style={{ fontSize: 35 }}
                  >
                    history
                  </span>
                </span>
                <span className="text-button-menubar">
                  <span>Histórico</span>
                </span>
              </Link>
            </article>

            <article className="button-menubar">
              <Link to="/config" className="link-button-menubar">
                <span className="icon-button-menubar">
                  <span
                    className="material-symbols-outlined"
                    style={{ fontSize: 35 }}
                  >
                    tv_options_input_settings
                  </span>
                </span>
                <span className="text-button-menubar">
                  <span>Configurações</span>
                </span>
              </Link>
            </article>

            <article className="button-menubar">
              <Link to="/" className="link-button-menubar">
                <span className="icon-button-menubar">
                  <span
                    className="material-symbols-outlined"
                    style={{ fontSize: 35 }}
                  >
                    person
                  </span>
                </span>
                <span className="text-button-menubar">
                  <span>Perfil</span>
                </span>
              </Link>
            </article>
          </section>

          <section className="container-menubar-buttons-bottom">
            <article className="button-menubar">
              <Link to="/" className="link-button-menubar">
                <span className="icon-button-menubar">
                  <span
                    className="material-symbols-outlined"
                    style={{ fontSize: 35 }}
                  >
                    logout
                  </span>
                </span>
                <span className="text-button-menubar">
                  <span>Sair</span>
                </span>
              </Link>
            </article>
          </section>
        </aside>
      </aside>

      <section className="navbar-mobile">
        <article className="button-menubar-mobile">
          <Link to="/trade" className="link-button-menubar-mobile">
            <span
              className="material-symbols-outlined"
              style={{ fontSize: 30 }}
            >
              bid_landscape
            </span>
          </Link>
        </article>

        <article className="button-menubar-mobile">
          <Link to="/opentrades" className="link-button-menubar-mobile">
            <span className="material-symbols-outlined" style={{ fontSize: 30 }}>
              auto_graph
            </span>
          </Link>
        </article>

        <article className="button-menubar-mobile button-center">
          <Link to="/home" className="link-button-menubar-mobile">
            <span
              className="material-symbols-outlined"
              style={{ fontSize: 40 }}
            >
              dashboard
            </span>
          </Link>
        </article>

        <article className="button-menubar-mobile">
          <Link to="/oldtransactions" className="link-button-menubar-mobile">
            <span
              className="material-symbols-outlined"
              style={{ fontSize: 30 }}
            >
              history
            </span>
          </Link>
        </article>

        <article className="button-menubar-mobile">
          <Link to="/config" className="link-button-menubar-mobile">
            <span
              className="material-symbols-outlined"
              style={{ fontSize: 30 }}
            >
              tv_options_input_settings
            </span>
          </Link>
        </article>
      </section>
    </>
  );
}
