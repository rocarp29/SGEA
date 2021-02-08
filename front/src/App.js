import React, { useState } from "react";
import { Navbar, Nav, Form, Button, NavDropdown } from "react-bootstrap";
import { useLocation } from "wouter";
import { NavLink } from "react-router-dom";
import Modal from "react-bootstrap/Modal";
import Routes from "./Routes";
import "./App.css";
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

  const [, navigate] = useLocation();

  const handleSubmit = (e) => {
    e.preventDefault();
    navigate(`/login`);
    alert(`Gracias por registrarte ${userName}`);
    setShowLogin(false);
  };

  return (
    <div>
      <Navbar expand="lg" bg="dark" variant="dark">
        <Navbar.Toggle aria-controls="basic-navbar-nav" />
        <Navbar.Collapse id="basic-navbar-nav">
          <Nav className="mr-auto">
            <Nav.Link href="/">Home</Nav.Link>

            <NavDropdown title="Usuarios" id="basic-nav-dropdown">
              <NavDropdown.Item href="/CrearUsuario">
                Crear Usuario
              </NavDropdown.Item>
              <NavDropdown.Item href="#ModificarUsuario">
                Modificar Usuario
              </NavDropdown.Item>
            </NavDropdown>

            <NavDropdown title="Eventos" id="basic-nav-dropdown">
              <NavDropdown.Item href="#CrearEvento">
                Crear Evento
              </NavDropdown.Item>
              <NavDropdown.Item href="#ModificarEvento">
                Modificar Evento
              </NavDropdown.Item>
              <NavDropdown.Item href="#CalendariodeEventos">
                Calendario de Eventos
              </NavDropdown.Item>
            </NavDropdown>
            <NavDropdown title="Presentaciones" id="basic-nav-dropdown">
              <NavDropdown.Item href="#CrearPresentación">
                Crear Presentación
              </NavDropdown.Item>
              <NavDropdown.Item href="#ModificarPresentación">
                Modificar Presentación
              </NavDropdown.Item>
            </NavDropdown>
          </Nav>
          <Form inline>
            <Button variant="outline-primary" onClick={handleShowLogin}>
              Ingresar
            </Button>
            <Button variant="outline-success" onClick={handleShowRegister}>
              Registrarse
            </Button>
          </Form>
        </Navbar.Collapse>
      </Navbar>

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
          <Button variant="primary" href="/login" onClick={handleSubmit}>
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
      <Routes />
    </div>
  );
}
