CREATE EXTENSION IF NOT EXISTS pgcrypto;

INSERT INTO usuarios (id_usuario, dtype, correo_electronico, contrasena, nombres, apellidos) VALUES (1, 'Administrador', 'admin@pawsity.org', encode(digest('Admin2026', 'sha256'), 'hex'), 'Carlos', 'Administrador'), (2, 'Veterinario', 'vet@pawsity.org', encode(digest('Vet2026', 'sha256'), 'hex'), 'Dra. Ana', 'Medina'), (3, 'Adoptante', 'adoptante@pawsity.org', encode(digest('Adopt2026', 'sha256'), 'hex'), 'Mateo', 'Perez') ON CONFLICT (id_usuario) DO NOTHING;

INSERT INTO administradores (id_usuario, puesto) VALUES (1, 'Gerente General') ON CONFLICT (id_usuario) DO NOTHING;

INSERT INTO veterinarios (id_usuario, especialidad, numero_licencia) VALUES (2, 'Cirugia y Vacunacion', 'VET-9988') ON CONFLICT (id_usuario) DO NOTHING;

INSERT INTO adoptantes (id_usuario, telefono, direccion, ocupacion) VALUES (3, '0993334455', 'Loja, Catamayo', 'Estudiante Universitario') ON CONFLICT (id_usuario) DO NOTHING;

INSERT INTO mascotas (nombre, especie, edad, tamano, sexo, color, estado, fecha_ingreso, imagen_url) VALUES ('Firulais', 'Canino', 2.5, 'Mediano', 'Macho', 'Cafe y Blanco', 'DISPONIBLE', '2026-05-10', 'https://images.unsplash.com/photo-1543466835-00a7907e9de1?q=80&w=600&auto=format&fit=crop'), ('Luna', 'Felino', 1.0, 'Pequeno', 'Hembra', 'Gris', 'DISPONIBLE', '2026-06-01', 'https://images.unsplash.com/photo-1514888286974-6c03e2ca1dba?q=80&w=600&auto=format&fit=crop'), ('Max', 'Canino', 4.0, 'Grande', 'Macho', 'Negro', 'DISPONIBLE', '2026-04-15', 'https://images.unsplash.com/photo-1583511655857-d19b40a7a54e?q=80&w=600&auto=format&fit=crop');