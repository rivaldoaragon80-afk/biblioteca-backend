-- ============================================
-- DATOS INICIALES PARA BIBLIOTECA
-- ============================================

-- Roles
INSERT IGNORE INTO roles (id, nombre) VALUES (1, 'ADMIN');
INSERT IGNORE INTO roles (id, nombre) VALUES (2, 'USUARIO');

-- Usuarios (passwords encriptadas con BCrypt)
-- Password para admin: admin123
INSERT IGNORE INTO usuarios (id, nombre, email, password, activo, rol_id, created_at, updated_at) 
VALUES (1, 'Administrador', 'admin@biblioteca.com', '$2a$10$8YvNZqXq3X0YQv9X9X0X9.X0X0X0X0X0X0X0X0X0X0X0X0X0X0X0', true, 1, NOW(), NOW());

-- Password para usuario: user123
INSERT IGNORE INTO usuarios (id, nombre, email, password, activo, rol_id, created_at, updated_at) 
VALUES (2, 'Usuario Demo', 'usuario@biblioteca.com', '$2a$10$8YvNZqXq3X0YQv9X9X0X9.X0X0X0X0X0X0X0X0X0X0X0X0X0X0X0', true, 2, NOW(), NOW());

-- Libros de ejemplo
INSERT IGNORE INTO libros (id, titulo, autor, anio, stock, isbn, descripcion, created_at, updated_at) 
VALUES 
(1, 'Cien años de soledad', 'Gabriel García Márquez', 1967, 10, '978-0307474728', 'Una obra maestra del realismo mágico', NOW(), NOW()),
(2, 'Don Quijote de la Mancha', 'Miguel de Cervantes', 1605, 8, '978-0060934347', 'La obra cumbre de la literatura española', NOW(), NOW()),
(3, '1984', 'George Orwell', 1949, 15, '978-0451524935', 'Una distopía sobre el totalitarismo', NOW(), NOW()),
(4, 'El principito', 'Antoine de Saint-Exupéry', 1943, 12, '978-0156012195', 'Un cuento filosófico y poético', NOW(), NOW()),
(5, 'Orgullo y prejuicio', 'Jane Austen', 1813, 7, '978-0141439518', 'Una historia romántica clásica', NOW(), NOW());

-- Nota: Para usar estos datos, asegúrate de tener la base de datos creada
-- Las passwords encriptadas aquí son ejemplos, genera las tuyas con BCrypt
