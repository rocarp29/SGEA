import React, { useState } from "react";

import Logo from "../img/logo.png";
import "../App.css";
export default function App() {
  //login
  const [showLogin, setShowLogin] = useState(false);
  const handleCloseLogin = () => setShowLogin(false);
  const handleShowLogin = () => setShowLogin(true);
  //register
  const [showRegister, setShowRegister] = useState(false);
  const handleCloseRegister = () => setShowRegister(false);
  const handleShowRegister = () => setShowRegister(true);

  const [userName, setUserName] = useState("");
  const [password, setPassword] = useState("");

  const handleSubmit = (e) => {
    e.preventDefault();

    alert(`Gracias por registrarte ${userName}`);
  };

  return (
    <div>
      <div class="todo">
        <div class="imagen">
          <img className="logo" src={Logo} alt="..." />
        </div>

        <div className="container">
          <h1>Sistema de Gestión de Eventos</h1>

          <p>
            Academic Events Manager es una Pyme dedicada a la organización y
            produccion de eventos académicos y sociales, <br /> conformada por
            profesionales y especialistas en diversas áreas del conocimiento.
          </p>
          <br />
          <p>
            Creamos con responsabilidad y dedicación eventos sociales y
            académicos, generando una experiencia memorable <br /> a través de
            nuestras aplicaciones web.
          </p>
        </div>
      </div>
    </div>
  );
}
