package com.itson.juatsapp.ui.controller;

import com.itson.juatsapp.domain.Usuario;

public interface InformacionContactoController {

    void guardarCambios(Usuario usuarioOriginal, Usuario datosNuevos);
}
