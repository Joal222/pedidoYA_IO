-- Tabla: productos
CREATE TABLE productos (
    id_producto SERIAL PRIMARY KEY,
    nombre_producto varchar (255) not null,
    tipo_producto VARCHAR(255) NOT NULL,
    precio NUMERIC(10,2) not null,
    cantidad_disponible integer,
    url varchar(510);
    creado_por VARCHAR(255) NOT NULL,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    modificado_por VARCHAR(255) NOT NULL
);

create table proveedores(
id_proveedor SERIAL primary key,
nombre varchar (255),
creado_por VARCHAR(255) NOT NULL,
fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
modificado_por VARCHAR(255) NOT NULL
);

-- Tabla: puesto
CREATE TABLE puesto (
    id_puesto SERIAL PRIMARY KEY,
    descripcion TEXT,
    creado_por VARCHAR(255) NOT NULL,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    modificado_por VARCHAR(255) NOT NULL
);

-- Tabla: vehiculos
CREATE TABLE vehiculos (
    id_vehiculo SERIAL PRIMARY KEY,
    modelo VARCHAR(255),
    marca VARCHAR(255),
    costo_activacion numeric (10,2),
    limite_carga NUMERIC(10,2),
    creado_por VARCHAR(255) NOT NULL,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    modificado_por VARCHAR(255) NOT NULL
);

-- Tabla: rol
CREATE TABLE rol (
    id_rol SERIAL PRIMARY KEY,
    description TEXT,
    nombre_rol VARCHAR(255),
    creado_por VARCHAR(255) NOT NULL,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    modificado_por VARCHAR(255) NOT NULL
);

create table proveedor_producto(
id_proveedor integer references proveedores(id_proveedor),
id_producto integer references productos(id_producto),
creado_por VARCHAR(255) NOT NULL,
fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
modificado_por VARCHAR(255) NOT null,
PRIMARY key(id_proveedor, id_producto)
);


-- Tabla: usuario
CREATE TABLE usuario (
    id_usuario SERIAL PRIMARY KEY,
    rol INTEGER REFERENCES rol(id_rol),
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    creado_por VARCHAR(255) NOT NULL,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    modificado_por VARCHAR(255) NOT NULL
);

-- Tabla: empleado
CREATE TABLE empleado (
    id_empleado SERIAL PRIMARY KEY,
    id_puesto INTEGER REFERENCES puesto(id_puesto),
    id_usuario INTEGER REFERENCES usuario(id_usuario),
    nombres VARCHAR(255),
    direccion VARCHAR(255),
    telefono VARCHAR(15),
    creado_por VARCHAR(255) NOT NULL,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    modificado_por VARCHAR(255) NOT NULL
);

-- Tabla: ruta_entrega
CREATE TABLE ruta_entrega (
    id_ruta_entrega SERIAL PRIMARY KEY,
    id_vehiculo INTEGER REFERENCES vehiculos(id_vehiculo),
    costo_activacion NUMERIC(10,2),
    creado_por VARCHAR(255) NOT NULL,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    modificado_por VARCHAR(255) NOT NULL
);

-- Tabla: ruta_entrega_detalle
CREATE TABLE ruta_entrega_detalle (
    id_ruta_entrega_detalle SERIAL PRIMARY KEY,
    id_rutaEntrega INTEGER REFERENCES ruta_entrega(id_ruta_entrega),
    horarios_entrega VARCHAR(255),
    creado_por VARCHAR(255) NOT NULL,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    modificado_por VARCHAR(255) NOT NULL
);


-- Tabla: clientes
CREATE TABLE clientes (
    id_cliente SERIAL PRIMARY KEY,
    id_ruta_entrega_detalle INTEGER REFERENCES ruta_entrega_detalle(id_ruta_entrega_detalle),
    id_usuario INTEGER REFERENCES usuario(id_usuario),
    creado_por VARCHAR(255) NOT NULL,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    modificado_por VARCHAR(255) NOT NULL
);


-- Tabla: pedidos
CREATE TABLE pedidos (
    id_pedido SERIAL PRIMARY KEY,
    id_cliente INTEGER REFERENCES clientes(id_cliente),
    direccion_entrega VARCHAR(255),
    direccion_recepcion VARCHAR(255),
    creado_por VARCHAR(255) NOT NULL,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    modificado_por VARCHAR(255) NOT NULL
);

-- Tabla: tripulacion
CREATE TABLE tripulacion (
    id_empleado INTEGER REFERENCES empleado(id_empleado),
    id_ruta_entrega INTEGER REFERENCES ruta_entrega(id_ruta_entrega),
    creado_por VARCHAR(255) NOT NULL,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    modificado_por VARCHAR(255) NOT NULL,
    PRIMARY KEY (id_empleado, id_ruta_entrega)
);

-- Tabla: pedido_producto (tabla intermedia)
CREATE TABLE pedido_producto (
    id_pedido INTEGER REFERENCES pedidos(id_pedido),
    id_producto INTEGER REFERENCES productos(id_producto),
    creado_por VARCHAR(255) NOT NULL,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    modificado_por VARCHAR(255) NOT NULL,
    PRIMARY KEY (id_pedido, id_producto)
);


