import React, { useState } from "react";
import { Navbar, Nav, Form, Button, NavDropdown } from "react-bootstrap";
import Modal from "react-bootstrap/Modal";
import Carousel from "react-bootstrap/Carousel";
import Slide1 from "../img/slide1.png";
import Slide2 from "../img/slide2.png";
import Slide3 from "../img/slide3.png";
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
      <Carousel>
        <Carousel.Item>
          <img className="d-block w-100" src={Slide1} alt="..." />
        </Carousel.Item>
        <Carousel.Item>
          <img className="d-block w-100" src={Slide2} alt="..." />
        </Carousel.Item>
        <Carousel.Item>
          <img className="d-block w-100" src={Slide3} alt="..." />
        </Carousel.Item>
      </Carousel>

      <Modal show={showLogin} onHide={handleCloseLogin}>
        <Modal.Header closeButton>
          <Modal.Title>Iniciar Sesión</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Form>
            <Form.Group controlId="formBasicEmail">
              <Form.Label>EMAIL</Form.Label>
              <Form.Control
                type="email"
                placeholder="Ingrese su email"
                onChange={(e) => setUserName(e.target.value)}
              />
            </Form.Group>

            <Form.Group controlId="formBasicPassword">
              <Form.Label>Contraseña</Form.Label>
              <Form.Control
                type="password"
                placeholder="Contraseña"
                onChange={(e) => setPassword(e.target.value)}
              />
            </Form.Group>
          </Form>
        </Modal.Body>
        <Modal.Footer>
          <Button variant="primary" onClick={handleSubmit}>
            Iniciar sesión
          </Button>
          <Button variant="secondary" onClick={handleCloseLogin}>
            Cerrar
          </Button>
        </Modal.Footer>
      </Modal>

      <Modal show={showRegister} onHide={handleCloseRegister}>
        <Modal.Header closeButton>
          <Modal.Title>Registrarte</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Form>
            <Form.Group controlId="formBasicEmail">
              <Form.Label>EMAIL</Form.Label>
              <Form.Control
                type="email"
                placeholder="Ingrese su email"
                onChange={(e) => setUserName(e.target.value)}
              />
            </Form.Group>

            <Form.Group controlId="formBasicPassword">
              <Form.Label>Contraseña</Form.Label>
              <Form.Control
                type="password"
                placeholder="Contraseña"
                onChange={(e) => setPassword(e.target.value)}
              />
            </Form.Group>
            <Form.Group controlId="formBasicPassword">
              <Form.Label>Repita su ontraseña</Form.Label>
              <Form.Control type="password" placeholder="Contraseña" />
            </Form.Group>
          </Form>
        </Modal.Body>
        <Modal.Footer>
          <Button variant="success" onClick={handleSubmit}>
            Registrarte
          </Button>
          <Button variant="secondary" onClick={handleCloseRegister}>
            Cerrar
          </Button>
        </Modal.Footer>
      </Modal>

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
