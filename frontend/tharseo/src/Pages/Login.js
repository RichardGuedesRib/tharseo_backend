import "../assets/css/style.css";
import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";

function Login({ addressServerTharseo, getUserByLogin }) {
  const [userLogin, setUserLogin] = useState("");
  const [userPassword, setUserPassword] = useState("");
  const [firstContainer, setFirstContainer] = useState("");
  const [secondContainer, setSecondContainer] = useState("close");
  const navigate = useNavigate();

  const checkUser = async () => {
    const urlRequest = `${addressServerTharseo}/authenticate/checkuser?login=${userLogin}`;
    try {
      const request = await fetch(urlRequest, { method: "GET" });
      if (!request.ok) {
        alert("Login ou senha incorreto");
      } else {
        setFirstContainer("close");
        setSecondContainer("");
      }
    } catch (error) {}
  };
  const backLogin = () => {
    setSecondContainer("close");
    setFirstContainer("");
  };
  const authenticate = async () => {
    const urlRequest = `${addressServerTharseo}/authenticate/checkpassword`;
    try {
      const request = await fetch(urlRequest, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          login: userLogin,
          password: userPassword,
        }),
      });
      if (!request.ok) {
        alert("Usuário ou Senha Inválidos");
      } else {
         const userData = await request.json(); 
         getUserByLogin(userData);
        // console.log(user);
        navigate("/home");
      }
    } catch (error) {
      console.error(error);
    }
  };
  useEffect(() => {}, []);

  return (
    <main className="app-dashboard">
      <section className="container-dashboard container-login">
        <section className="section-login-elements">
          <section className="login-title">THARSEO</section>
        
          <section className={`section-login-elements-one ${firstContainer}`}>
       
            <section className="login-description">E-mail ou Celular*</section>
            
            <section className="login-user">
              <span className="login-user-icon">
                <span class="material-symbols-outlined">person</span>
              </span>
              <input
                type="text"
                placeholder="Digite aqui..."
                className="login-user-text"
                onChange={(e) => setUserLogin(e.target.value)}
              />
            </section>
            <section className="login-select-account">
              <span className="login-select-account-icon">
                {" "}
                <span class="material-symbols-outlined">g_translate</span>
              </span>

              <span className="login-select-account-description">
                <span className="login-select-account-description-text">
                  {" "}
                  Login com o Google
                </span>
              </span>
            </section>
            <section className="login-select-account">
              <span className="login-select-account-icon">
                {" "}
                <span class="material-symbols-outlined">nutrition</span>
              </span>

              <span className="login-select-account-description">
                <span className="login-select-account-text">
                  Login com a Apple
                </span>
              </span>
            </section>
            
            <section className="login-btn">
              <a href="/register" className="login-btn-createacc">
                Criar Conta
              </a>

              <span className="login-btn-acess" onClick={checkUser}>
                <span>Acessar</span>{" "}
              </span>
            </section>
          </section>
         

          <section className={`section-login-elements-two ${secondContainer}`}>
            <section className="login-btn " onClick={backLogin}>
              <span className="login-btn-createacc btn-back-login">Voltar</span>
            </section>
            <section className="login-description text-welcome-login">
              Bem vindo de volta, {userLogin}
            </section>

            <section className="description-password">
              <section className="login-description description-title-password ">
                Senha*
              </section>
              <section className="login-password">
                <span className="login-password-icon">
                  <span class="material-symbols-outlined">key</span>
                </span>
                <input
                  type="password"
                  placeholder="Digite aqui sua senha..."
                  className="login-user-text"
                  onChange={(e) => setUserPassword(e.target.value)}
                />
              </section>
            </section>

            <section className="login-btn login-btn-section-two">
              <a href="/" className="login-btn-createacc">
                Esqueceu sua senha
              </a>

              <span className="login-btn-acess" onClick={authenticate}>
                <span>Acessar</span>{" "}
              </span>
            </section>
          </section>
        </section>
    
      </section>
      
    </main>
  );
}

export default Login;
