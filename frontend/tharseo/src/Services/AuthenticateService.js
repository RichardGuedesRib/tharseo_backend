import serverConfig from "./ServerConfig";



export async function checkUserLogin(
  userLogin,
  setFirstContainer,
  setSecondContainer
) {
  const urlRequest = `${serverConfig.addressServerTharseo}/authenticate/checkuser?login=${userLogin}`;
  try {
    const request = await fetch(urlRequest, { method: "GET" });
    if (!request.ok) {
      alert("Login ou senha incorreto");
    } else {
      setFirstContainer("close");
      setSecondContainer("");
    }
  } catch (error) {}
}

export async function authenticateLogin(userLogin, userPassword, navigate, setUser) {

  const urlRequest = `${serverConfig.addressServerTharseo}/authenticate/checkpassword`;
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
           setUser(userData);
      navigate("/home");
    }
  } catch (error) {
    console.error(error);
  }
}
