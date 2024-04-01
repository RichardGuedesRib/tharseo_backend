import React, { useState, useEffect } from "react";

const WebSocketComponent = () => {
  const [message, setMessage] = useState("");
  const [error, setError] = useState(null);

  useEffect(() => {
    const client = new WebSocket("ws://localhost:8080/websocket");

    client.onopen = () => {
      console.log("Conectado ao servidor WebSocket");
    };

    client.onmessage = (e) => {
      setMessage(e.data);
    };

    client.onerror = (error) => {
      setError("Erro ao conectar ao servidor WebSocket: " + error.message);
    };

    return () => {
      client.close();
    };
  }, []);

  if (error) {
    return <div>Error: {error}</div>;
  }

  return (
    <div>
      <h2>Mensagem recebida:</h2>
      <p>{message}</p>
    </div>
  );
};

export default WebSocketComponent;
