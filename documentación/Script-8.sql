-- Crear tabla tipo_vehiculo
CREATE TABLE tipo_vehiculo (
    id_tipo_vehiculo SERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    creado_por VARCHAR(255) NOT NULL,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    modificado_por VARCHAR(255) NOT NULL
);

-- Crear tabla tipo_producto
CREATE TABLE tipo_producto (
    id_tipo_producto SERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    creado_por VARCHAR(255) NOT NULL,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    modificado_por VARCHAR(255) NOT NULL
);

-- Crear tabla rol
CREATE TABLE rol (
    id_rol SERIAL PRIMARY KEY,
    nombre_rol VARCHAR(255) NOT NULL,
    descripcion TEXT,
    creado_por VARCHAR(255) NOT NULL,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    modificado_por VARCHAR(255) NOT NULL
);

-- Crear tabla productos
CREATE TABLE productos (
    id_producto SERIAL PRIMARY KEY,
    descripcion TEXT,
    nombre VARCHAR(255) NOT NULL,
    dimension_m3 DECIMAL(10, 2),
    peso_kg DECIMAL(10, 2),
    precio DECIMAL(10, 2),
    id_tipo_producto INT REFERENCES tipo_producto(id_tipo_producto),
    creado_por VARCHAR(255) NOT NULL,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    modificado_por VARCHAR(255) NOT NULL
);

-- Crear tabla estados
CREATE TABLE estados (
    id_estado SERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    creado_por VARCHAR(255) NOT NULL,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    modificado_por VARCHAR(255) NOT NULL
);

-- Crear tabla usuario
CREATE TABLE usuario (
    id_usuario SERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    id_rol INT REFERENCES rol(id_rol),
    creado_por VARCHAR(255) NOT NULL,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    modificado_por VARCHAR(255) NOT NULL
);

-- Crear tabla puesto
CREATE TABLE puesto (
    id_puesto SERIAL PRIMARY KEY,
    descripcion TEXT,
    creado_por VARCHAR(255) NOT NULL,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    modificado_por VARCHAR(255) NOT NULL
);

-- Crear tabla proveedor
CREATE TABLE proveedor (
    id_proveedor SERIAL PRIMARY KEY,
    ubicacion VARCHAR(255),
    direccion VARCHAR(255),
    horario_atencion VARCHAR(255),
    creado_por VARCHAR(255) NOT NULL,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    modificado_por VARCHAR(255) NOT NULL
);

-- Crear tabla vehiculos
CREATE TABLE vehiculos (
    id_vehiculo SERIAL PRIMARY KEY,
    modelo VARCHAR(255),
    marca VARCHAR(255),
    limite_capacidad_m3 DECIMAL(10, 2),
    ubicacion VARCHAR(255),
    id_tipo_vehiculo INT REFERENCES tipo_vehiculo(id_tipo_vehiculo),
    creado_por VARCHAR(255) NOT NULL,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    modificado_por VARCHAR(255) NOT NULL
);

-- Crear tabla ruta_recoleccion
CREATE TABLE ruta_recoleccion (
    id_recoleccion SERIAL PRIMARY KEY,
    id_vehiculo INT REFERENCES vehiculos(id_vehiculo),
    costo_activacion DECIMAL(10, 2),
    creado_por VARCHAR(255) NOT NULL,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    modificado_por VARCHAR(255) NOT NULL
);

-- Crear tabla empleado
CREATE TABLE empleado (
    id_empleado SERIAL PRIMARY KEY,
    nombres VARCHAR(255),
    direccion VARCHAR(255),
    telefono VARCHAR(255),
    id_puesto INT REFERENCES puesto(id_puesto),
    id_usuario INT REFERENCES usuario(id_usuario),
    id_ruta_recolecion INT REFERENCES ruta_recoleccion(id_recoleccion),
    creado_por VARCHAR(255) NOT NULL,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    modificado_por VARCHAR(255) NOT NULL
);

-- Crear tabla ruta_entrega
CREATE TABLE ruta_entrega (
    id_ruta_entrega SERIAL PRIMARY KEY,
    id_vehiculo INT REFERENCES vehiculos(id_vehiculo),
    costo_activacion DECIMAL(10, 2),
    horario_atencion VARCHAR(255),
    creado_por VARCHAR(255) NOT NULL,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    modificado_por VARCHAR(255) NOT NULL
);

-- Crear tabla clientes
CREATE TABLE clientes (
    id_cliente SERIAL PRIMARY KEY,
    id_ruta_entrega_detalle INT REFERENCES ruta_entrega(id_ruta_entrega),
    id_usuario INT REFERENCES usuario(id_usuario),
    ubicacion VARCHAR(255),
    direccion VARCHAR(255),
    creado_por VARCHAR(255) NOT NULL,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    modificado_por VARCHAR(255) NOT NULL
);

-- Crear tabla pedidos
CREATE TABLE pedidos (
    id_pedido SERIAL PRIMARY KEY,
    id_cliente INT REFERENCES clientes(id_cliente),
    id_ruta_recoleccion INT REFERENCES ruta_recoleccion(id_recoleccion),
    ruta_entrega INT REFERENCES ruta_entrega(id_ruta_entrega),
    id_estado INT REFERENCES estados(id_estado),
    direccion_entrega VARCHAR(255),
    direccion_recepcion VARCHAR(255),
    ubicacion_entrega VARCHAR(255),
    ubicacion_recepcion VARCHAR(255),
    creado_por VARCHAR(255) NOT NULL,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    modificado_por VARCHAR(255) NOT NULL
);

-- Crear tabla pedido_producto
CREATE TABLE pedido_producto (
    id_pedido INT REFERENCES pedidos(id_pedido),
    id_producto INT REFERENCES productos(id_producto),
    cantidad INT,
    creado_por VARCHAR(255) NOT NULL,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    modificado_por VARCHAR(255) NOT NULL,
    PRIMARY KEY (id_pedido, id_producto)
);

-- Crear tabla proveedor_producto
CREATE TABLE proveedor_producto (
    id_proveedor INT REFERENCES proveedor(id_proveedor),
    id_producto INT REFERENCES productos(id_producto),
    disponibilidad BOOLEAN,
    creado_por VARCHAR(255) NOT NULL,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    modificado_por VARCHAR(255) NOT NULL,
    PRIMARY KEY (id_proveedor, id_producto)
);

-- Crear tabla cliente_producto
CREATE TABLE cliente_producto (
    id_cliente INT REFERENCES clientes(id_cliente),
    id_producto INT REFERENCES productos(id_producto),
    disponibilidad BOOLEAN,
    creado_por VARCHAR(255) NOT NULL,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    modificado_por VARCHAR(255) NOT NULL,
    PRIMARY KEY (id_cliente, id_producto)
);


