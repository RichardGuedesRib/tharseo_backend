import serverConfig from "../Services/ServerConfig";
import "../assets/css/style.css";
import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";


function Register() {
  const [logform, setLogForm] = useState("");
  const [userLogin, setUserLogin] = useState("");
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [name, setName] = useState("");
  const [lastName, setLastName] = useState("");
  const [phoneNumber, setPhoneNumber] = useState("");
  const [email, setEmail] = useState("");
  const [apiKey, setApiKey] = useState("");
  const [secretKey, setSecretKey] = useState("");
  const [firstSectionOk, setFirstSectionOk] = useState(false);
  const [secondSectionOk, setSecondSectionOk] = useState(false);
  const [thirdSectionOk, setThirdSectionOk] = useState(false);
  const [firstContainerRegister, setFirstContainerRegister] = useState("");
  const [secondContainerRegister, setSecondContainerRegister] =
    useState("close");
  const [thirdContainerRegister, setThirdContainerRegister] = useState("close");
  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  const phoneRegex = /^\d{2}\s?\d{9}$/;
  const navigate = useNavigate();

  const activeFormTwo = async () => {
    console.log("ADDRESS FORM TWO", serverConfig.addressServerTharseo);
    if (userLogin === "") {
      setLogForm("Digite um usuário válido");
      return;
    }

    if (password === "") {
      setLogForm("Digite uma senha");
      return;
    }

    if (password !== confirmPassword) {
      setLogForm("Senhas não coincidem");
      return;
    }

    if (emailRegex.test(userLogin) || phoneRegex.test(userLogin)) {
      const urlRequest = `${serverConfig.addressServerTharseo}/authenticate/checkuser?login=${userLogin}`;

      try {
        const request = await fetch(urlRequest, { method: "GET" });
        if (request.ok) {
          setLogForm("Email ou Celular ja Cadastrado");
          return;
        } else {
          if (emailRegex.test(userLogin)) {
            setEmail(userLogin);
          }
          if (phoneRegex.test(userLogin)) {
            setPhoneNumber(userLogin);
          }
          setFirstContainerRegister("close");
          setSecondContainerRegister("");
          setFirstSectionOk(true);
          setLogForm("");
        }
      } catch (error) {}
    } else {
      setLogForm("Digite um email ou celular válido");
      return;
    }

    const urlRequest = `${serverConfig.addressServerTharseo}/authenticate/checkuser?login=${userLogin}`;

    if (emailRegex.test(email) || phoneRegex.test(phoneNumber)) {
      try {
        const request = await fetch(urlRequest, { method: "GET" });
        if (request.ok) {
          setLogForm("Email ou Celular ja Cadastrado");
          return;
        }
      } catch (error) {}
    }
  };

  const backToFormOne = () => {
    setFirstContainerRegister("");
    setSecondContainerRegister("close");
    setFirstSectionOk(false);
    setLogForm("");
  };

  const activeFormThree = async (event) => {
    event.preventDefault();

    if (!emailRegex.test(email)) {
      setLogForm("Email Inválido");
      return;
    }
    if (!phoneRegex.test(phoneNumber)) {
      setLogForm("Celular Inválido");
      return;
    }
    if (name === "") {
      setLogForm("Digite um Nome");
      return;
    }
    if (lastName === "") {
      setLogForm("Digite um Sobrenome");
      return;
    }

    if (emailRegex.test(email)) {
      const urlCheckEmail = `${serverConfig.addressServerTharseo}/authenticate/checkuser?login=${email}`;
      try {
        const request = await fetch(urlCheckEmail, { method: "GET" });
        if (request.ok) {
          setLogForm("Email ja Cadastrado");
          return;
        }
      } catch (error) {}
    }

    if (phoneRegex.test(phoneNumber)) {
      const urlCheckPhone = `${serverConfig.addressServerTharseo}/authenticate/checkuser?login=${phoneNumber}`;
      try {
        const request = await fetch(urlCheckPhone, { method: "GET" });
        if (request.ok) {
          setLogForm("Celular ja Cadastrado");
          return;
        }
      } catch (error) {}
    }
    setSecondContainerRegister("close");
    setThirdContainerRegister("");
    setSecondSectionOk(true);
    setLogForm("");
  };

  const getData = {
    name: name,
    lastname: lastName,
    phoneNumber: phoneNumber,
    email: email,
    password: password,
    apiKey: apiKey,
    secretKey: secretKey,
  };

  const sendRegister = async () => {
    const urlRequest = `${serverConfig.addressServerTharseo}/users`;

    console.log(getData);
    try {
      const request = await fetch(urlRequest, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(getData),
      });

      if (!request.ok) {
        alert("Error Register");
        alert(request.body);
      }

      setThirdContainerRegister("");
      setThirdSectionOk(true);
      alert("Ok!");
      navigate("/");
    } catch (error) {
      console.error(error);
    }
  };

  useEffect(() => {
    console.log("ADDRESS", serverConfig.addressServerTharseo);
  }, []);

  return (
    <main className="app-dashboard">
      <section className="container-dashboard">
        <section className="container-register-elements">
          <section className="section-sections-register">
            <aside className="section-sections">
              <span
                className="section-sections-circle"
                style={{
                  backgroundColor: firstSectionOk ? "#006BFA" : "#374151",
                }}
              >
                <span
                  class="material-symbols-outlined"
                  style={{ fontSize: 20 }}
                >
                  {firstSectionOk ? "done" : "badge"}
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
              <span
                className="section-sections-circle"
                style={{
                  backgroundColor: secondSectionOk ? "#006BFA" : "#374151",
                }}
              >
                <span
                  class="material-symbols-outlined"
                  style={{ fontSize: 20 }}
                >
                  {secondSectionOk ? "done" : "description"}
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
              <span
                className="section-sections-circle"
                style={{
                  backgroundColor: thirdSectionOk ? "#006BFA" : "#374151",
                }}
              >
                <span
                  class="material-symbols-outlined"
                  style={{ fontSize: 20 }}
                >
                  {thirdSectionOk ? "done" : "done_all"}
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

            <section
              className={`section-elements-one ${firstContainerRegister}`}
            >
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
                  className="register-user-text no-border"
                  onChange={(e) => {
                    setUserLogin(e.target.value);
                  }}
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
                  className="register-user-text no-border"
                  onChange={(e) => {
                    setPassword(e.target.value);
                  }}
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
                  className="register-user-text no-border"
                  onChange={(e) => {
                    setConfirmPassword(e.target.value);
                  }}
                />
              </section>

              <section className="register-description description-bottom">
                Nós nunca iremos compartilhar seus dados.
              </section>
              <section className="register-description ">
                Confira nossa <b>Política de Privacidade.</b>{" "}
              </section>
              <p className="alert-log-register">{logform}</p>

              <section className="register-btn">
                <a href="/" className="login-btn-createacc">
                  Ja tenho uma conta
                </a>

                <span className="register-btn-acess" onClick={activeFormTwo}>
                  <span>Próximo</span>{" "}
                </span>
              </section>
            </section>

            <section
              className={`section-elements-two ${secondContainerRegister}`}
            >
              <form className="form-register-two" onSubmit={activeFormThree}>
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
                      onChange={(e) => {
                        setName(e.target.value);
                      }}
                      required
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
                      onChange={(e) => {
                        setLastName(e.target.value);
                      }}
                      required
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
                      onChange={(e) => {
                        setPhoneNumber(e.target.value);
                      }}
                      value={phoneNumber ? phoneNumber : ""}
                      required
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
                      onChange={(e) => {
                        setEmail(e.target.value);
                      }}
                      value={email ? email : ""}
                      required
                    />
                  </section>
                </aside>

                <section className="register-description description-bottom">
                  Nós nunca iremos compartilhar seus dados.
                </section>
                <section className="register-description ">
                  Confira nossa <b>Política de Privacidade.</b>{" "}
                </section>
                <p className="alert-log-register">{logform}</p>

                <section className="register-btn btn-section-two">
                  <a
                    href=""
                    className="login-btn-createacc"
                    onClick={backToFormOne}
                  >
                    Voltar
                  </a>
                  <button
                    className="register-btn-acess register-btn-acess-form-two"
                    type="submit"
                    onClick={activeFormThree}
                  >
                    <span>Próximo</span>{" "}
                  </button>
                </section>
              </form>
            </section>

            <section
              className={`section-elements-third ${thirdContainerRegister}`}
            >
              <section className="register-description text-acc-create">
                Sua Conta foi Criada com Sucesso!
              </section>

              <section className="register-description description-title-register-third">
                Agora vamos configurar a API da Binance:
              </section>

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
                    onChange={(e) => {
                      setApiKey(e.target.value);
                    }}
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
                    onChange={(e) => {
                      setSecretKey(e.target.value);
                    }}
                  />
                </section>
              </aside>

              <section className="register-description description-bottom">
                Suas Keys serão criptografadas ao serem salvas
              </section>
              <section className="register-description "></section>

              <section className="register-btn ">
                <span
                  className="login-btn-createacc btn-section-three"
                  onClick={sendRegister}
                >
                  Pular essa etapa
                </span>
                <span className="register-btn-acess" onClick={sendRegister}>
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
