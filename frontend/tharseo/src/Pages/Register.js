import "../assets/css/style.css";
import React, { useState, useEffect } from "react";

function Register({}) {
  useEffect(() => {}, []);

  return (
    <main className="app-dashboard">
      <section className="container-dashboard">
        <section className="container-register-elements">
          <section className="section-sections-register">
            <aside className="section-sections">
              <span className="section-sections-circle">
                <span
                  class="material-symbols-outlined"
                  style={{ fontSize: 20 }}
                >
                  badge
                </span>
              </span>
              <span className="section-sections-text">
                <span className="section-sections-text-top">
                  Sobre sua Conta
                </span>
                <span className="section-sections-text-bottom">
                  Crie seu acesso
                </span>
              </span>
            </aside>

            <aside className="divisor-sections"></aside>

            <aside className="section-sections">
              <span className="section-sections-circle">
                <span
                  class="material-symbols-outlined"
                  style={{ fontSize: 20 }}
                >
                  description
                </span>
              </span>
              <span className="section-sections-text">
                <span className="section-sections-text-top">
                  Informações Pessoais
                </span>
                <span className="section-sections-text-bottom">
                  Informações sobre sua Conta
                </span>
              </span>
            </aside>

            <aside className="divisor-sections"></aside>

            <aside className="section-sections">
              <span className="section-sections-circle">
                <span
                  class="material-symbols-outlined"
                  style={{ fontSize: 20 }}
                >
                  done_all
                </span>
              </span>
              <span className="section-sections-text">
                <span className="section-sections-text-top">
                  Revisão e Configuração
                </span>
                <span className="section-sections-text-bottom">
                  Revise suas Informações
                </span>
              </span>
            </aside>
          </section>

          <section className="section-register-elements">
            <section className="register-title">THARSEO</section>

            <section className="section-elements-one close">
              <section className="register-description">Cadastro:</section>

              <section className="register-description">
                Email ou Celular*:
              </section>
              <section className="register-user">
                <span className="register-user-icon">
                  <span class="material-symbols-outlined">person</span>
                </span>
                <input
                  type="text"
                  placeholder="Digite aqui..."
                  className="register-user-text"
                />
              </section>

              <section className="register-description password">
                Senha:
              </section>
              <section className="register-user">
                <span className="register-user-icon">
                  <span class="material-symbols-outlined">key</span>
                </span>
                <input
                  type="password"
                  placeholder="Digite aqui..."
                  className="register-user-text"
                />
              </section>

              <section className="register-description confirm-password">
                Confirmar Senha:
              </section>
              <section className="register-user">
                <span className="register-user-icon">
                  <span class="material-symbols-outlined">key</span>
                </span>
                <input
                  type="password"
                  placeholder="Digite aqui..."
                  className="register-user-text"
                />
              </section>

              <section className="register-description description-bottom">
                Nós nunca iremos compartilhar seus dados.
              </section>
              <section className="register-description ">
                Confira nossa <b>Política de Privacidade.</b>{" "}
              </section>

              <section className="register-btn">
                <a href="/login" className="login-btn-createacc">
                  Ja tenho uma conta
                </a>

                <span className="register-btn-acess">
                  <span>Próximo</span>{" "}
                </span>
              </section>
            </section>

            <section className="section-elements-two close">
              <form className="form-register-two">
                <aside className="container-input-register">
                  <section className="register-description">Nome:</section>
                  <section className="input-register">
                    <span className="input-register-icon">
                      <span class="material-symbols-outlined">id_card</span>
                    </span>
                    <input
                      type="text"
                      placeholder="Digite Aqui"
                      className="input-register-text"
                    />
                  </section>
                </aside>
                <aside className="container-input-register">
                  <section className="register-description">Sobrenome:</section>
                  <section className="input-register">
                    <span className="input-register-icon">
                      <span class="material-symbols-outlined">dns</span>
                    </span>
                    <input
                      type="text"
                      placeholder="Digite Aqui"
                      className="input-register-text"
                    />
                  </section>
                </aside>
                <aside className="container-input-register">
                  <section className="register-description">Telefone:</section>
                  <section className="input-register">
                    <span className="input-register-icon">
                      <span class="material-symbols-outlined">call</span>
                    </span>
                    <input
                      type="text"
                      placeholder="Digite Aqui"
                      className="input-register-text"
                    />
                  </section>
                </aside>
                <aside className="container-input-register">
                  <section className="register-description">Email:</section>
                  <section className="input-register">
                    <span className="input-register-icon">
                      <span class="material-symbols-outlined">mail</span>
                    </span>
                    <input
                      type="email"
                      placeholder="Digite Aqui"
                      className="input-register-text"
                    />
                  </section>
                </aside>

                <section className="register-description description-bottom">
                  Nós nunca iremos compartilhar seus dados.
                </section>
                <section className="register-description ">
                  Confira nossa <b>Política de Privacidade.</b>{" "}
                </section>

                <section className="register-btn btn-section-two">
                  <span className="register-btn-acess">
                    <span>Próximo</span>{" "}
                  </span>
                </section>
              </form>
            </section>

            <section className="section-elements-third close">

            <section className="register-description text-acc-create">Sua Conta foi Criada com Sucesso!</section>

            <section className="register-description description-title-register-third">Agora vamos configurar a API da Binance:</section>
             
                <aside className="container-input-register">
                  <section className="register-description">APIKEY:</section>
                  <section className="input-register">
                    <span className="input-register-icon">
                      <span class="material-symbols-outlined">key</span>
                    </span>
                    <input
                      type="text"
                      placeholder="Digite Aqui"
                      className="input-register-text"
                    />
                  </section>
                </aside>
                <aside className="container-input-register">
                  <section className="register-description">SECRETKEY:</section>
                  <section className="input-register">
                    <span className="input-register-icon">
                      <span class="material-symbols-outlined">key</span>
                    </span>
                    <input
                      type="text"
                      placeholder="Digite Aqui"
                      className="input-register-text"
                    />
                  </section>
                </aside>
               
                 <section className="register-description description-bottom">
                 Suas Keys serão criptografadas ao serem salvas
                </section>
                <section className="register-description ">
                 
                </section>

                <section className="register-btn btn-section-two">
                  <span className="register-btn-acess">
                    <span>Finalizar</span>{" "}
                  </span>
                </section>
            
            </section>


          </section>
        </section>
      </section>
    </main>
  );
}

export default Register;
