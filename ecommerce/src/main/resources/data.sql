-- Productos
INSERT INTO producto (nombre, descripcion, precio, categoria, imagen_url, stock)
VALUES
('Mouse Logitech', 'Mouse inalámbrico ergonómico', 4500.00, 'Periféricos', 'https://res.cloudinary.com/dd8s5hozt/image/upload/v1752973130/mouse-ergonomico_hqrh4n.webp', 20),
('Teclado Redragon', 'Teclado mecánico RGB', 9000.00, 'Periféricos', 'https://res.cloudinary.com/dd8s5hozt/image/upload/v1752973131/teclado-mecanico-rgb_hbfnzu.webp', 10),
('Monitor Samsung', 'Monitor 24 pulgadas Full HD', 58000.00, 'Pantallas', 'https://res.cloudinary.com/dd8s5hozt/image/upload/v1752973130/Monitor_24_pulgadas_Full_HD_t0e1ai.webp', 5),
('Auriculares Sony', 'Auriculares con cancelación de ruido', 12000.00, 'Audio', 'https://res.cloudinary.com/dd8s5hozt/image/upload/v1752973130/Auriculares_con_cancelaci%C3%B3n_de_ruido_jvkb0n.webp', 15),
('Webcam Logitech', 'Webcam Full HD 1080p', 7000.00, 'Periféricos', 'https://res.cloudinary.com/dd8s5hozt/image/upload/v1752973130/Webcam_Full_HD_1080p_q2djdu.webp', 12),
('Notebook Lenovo', 'Notebook 15" i5 8GB RAM', 250000.00, 'Computadoras', 'https://res.cloudinary.com/dd8s5hozt/image/upload/v1752973130/Notebook_15_pulgadas_i5_8GB_RAM_ijyhmb.webp', 8),
('Impresora HP', 'Impresora multifunción WiFi', 30000.00, 'Impresoras', 'https://res.cloudinary.com/dd8s5hozt/image/upload/v1752973130/Impresora_multifunci%C3%B3n_WiFi_dnpjq3.webp', 6),
('Router TP-Link', 'Router inalámbrico AC1200', 8500.00, 'Redes', 'https://res.cloudinary.com/dd8s5hozt/image/upload/v1752973131/Router_inal%C3%A1mbrico_AC1200_tdcqlq.webp', 9);
-- Clientes
INSERT INTO cliente (nombre, apellido, dni, activo) VALUES
('Juan', 'Pérez', 12345678, true),
('Ana', 'González', 87654321, true),
('Luis', 'Martínez', 11223344, false),
('María', 'López', 99887766, true);
-- Pedidos
INSERT INTO pedido (cliente_id, fecha) VALUES
(1, '2025-07-01'),
(2, '2025-07-02'),
(3, '2025-07-03');
-- Líneas de pedido
INSERT INTO linea_pedido (producto_id, pedido_id, cantidad, precio_unitario, sub_total) VALUES
(1, 1, 2, 4500.00, 9000.00),
(2, 1, 1, 9000.00, 9000.00);

INSERT INTO linea_pedido (producto_id, pedido_id, cantidad, precio_unitario, sub_total) VALUES
(3, 2, 1, 58000.00, 58000.00);

INSERT INTO linea_pedido (producto_id, pedido_id, cantidad, precio_unitario, sub_total) VALUES
(2, 3, 3, 9000.00, 27000.00),
(1, 3, 1, 4500.00, 4500.00);

