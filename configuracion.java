//Esta variable va entre la variable "del tamaño" 

                if (nombre. equals("Configuracion")){
                boton.addActionListener(e -> abrirVentanaConfiguracion()); 
//Y entre el "El evento de los botones"
//Despues de la variable "Espacios en lo botones"                  
  private void abrirVentanaConfiguracion() {
        // 1. Limpiamos lo que haya en el panel principal
        panelPrincipal.removeAll();
        panelPrincipal.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20)); // Layout para los botones nuevos
        
        // 2. Cambiamos el título para que el usuario sepa dónde está
        titulo.setText("Configuración del Sistema");

        // 3. Creamos botones nuevos para la pantalla PRINCIPAL

        JButton btnOpcion1 = new JButton("Cambiar Contraseña");
        JButton btnOpcion2 = new JButton("Modo Oscuro");
        JButton btnOpcion3 = new JButton( "Eliminar Cuenta");
        // Aqui se cambian los colores para que no se vea tan plano y feo
        btnOpcion1.setBackground(COLOR_VERDE_PRIMARIO);
        btnOpcion1.setForeground(Color.WHITE);
        btnOpcion1.setFocusPainted(false );
        //Color Gris 
        btnOpcion2.setBackground(new Color(60, 60, 60));
        btnOpcion2.setForeground(Color.WHITE);
        btnOpcion2.setFocusPainted(false);
        //Color Rojo
        btnOpcion3.setBackground(new Color(211, 47, 47));
        btnOpcion3.setForeground(Color.WHITE);
        btnOpcion3.setFocusPainted(false);
        
        // Estilizamos un poco para que no se vean simples
        btnOpcion1.setPreferredSize(new Dimension(200, 100));
        btnOpcion2.setPreferredSize(new Dimension(200, 100));
        btnOpcion3.setPreferredSize(new Dimension(200, 100));


        // 4. Los añadimos al panel principal (no al lateral)
        panelPrincipal.add(btnOpcion1);
        panelPrincipal.add(btnOpcion2);
        panelPrincipal.add(btnOpcion3);

        panelPrincipal.revalidate();
        panelPrincipal.repaint();
        
        System.out.println("Cambiando a pestaña de configuración... perfectirijillo");
    }
