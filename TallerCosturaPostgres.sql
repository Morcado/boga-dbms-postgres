CREATE DATABASE "TallerCostura"
    WITH OWNER = postgres
        ENCODING = 'UTF8'
        TABLESPACE = pg_default
        LC_COLLATE = 'English_United States.1252'  -- Ubicaciones
        LC_CTYPE = 'English_United States.1252'
        CONNECTION LIMIT = -1; -- Limitar las conexiones

CREATE SCHEMA Taller
 
CREATE TABLE Taller.TipoTrabajo (
    IdTipoTrabajo BIGSERIAL NOT NULL,
    Costo NUMERIC(1000, 2) NOT NULL,
    Nombre VARCHAR( 100 ) NOT NULL,
    CONSTRAINT PK_TIPOTRABAJO PRIMARY KEY( IdTipoTrabajo )
)

DROP TABLE Taller.TipoTrabajo cascade

 
CREATE TABLE Taller.Cliente (
    IdCliente BIGSERIAL NOT NULL,
    Nombre VARCHAR( 80 ) NOT NULL,
    ApellidoPaterno VARCHAR( 80 ) NOT NULL,
    ApellidoMaterno VARCHAR( 80 ) NOT NULL,
    Telefono VARCHAR( 50 ) NOT NULL,
    CONSTRAINT PK_CLIENTE PRIMARY KEY( IdCliente )
)

DROP  TABLE  Taller.Cliente cascade;
 
CREATE TABLE Taller.Empleado (
    IdEmpleado BIGSERIAL NOT NULL,
    Nombre VARCHAR( 80 ) NOT NULL,
    ApellidoMaterno VARCHAR( 80 ) NOT NULL,
    ApellidoPaterno VARCHAR( 80 ) NOT NULL,
    Telefono VARCHAR( 10 ) NOT NULL,
    Direccion VARCHAR( 200 ) NOT NULL,
    CONSTRAINT PK_EMPLEADO PRIMARY KEY( IdEmpleado )
)
 
CREATE TABLE Taller.Confeccion (
    IdConfeccion BIGSERIAL NOT NULL,
    CostoTotal FLOAT NULL,
    FechaPedido DATE NOT NULL,
    FechaEntrega DATE NULL,
    Anticipo FLOAT NOT NULL,
    IdCliente BIGINT NOT NULL,
    CONSTRAINT PK_CONFECCION PRIMARY KEY( IdConfeccion ),
    CONSTRAINT FK_CONFECCION_CLIENTE FOREIGN KEY( IdCliente ) REFERENCES Taller.Cliente( IdCliente )
)
 
CREATE TABLE Taller.Prenda (
    IdPrenda BIGSERIAL NOT NULL,
    IdConfeccion BIGINT NOT NULL,
    CostoTrabajo FLOAT NULL,
    CostoMaterial FLOAT NULL,
    Finalizado BOOLEAN NOT NULL,
 
    CONSTRAINT PK_PRENDA PRIMARY KEY( IdPrenda ),
    CONSTRAINT FK_PRENDA_CONFECCION FOREIGN KEY( IdConfeccion ) REFERENCES Taller.Confeccion( IdConfeccion )
)
 
CREATE TABLE Taller.Trabajo (
    IdTrabajo BIGSERIAL NOT NULL,
    IdTipoTrabajo BIGINT NOT NULL,
    IdPrenda BIGINT NOT NULL,
    IdEmpleado BIGINT NOT NULL,
 
    CONSTRAINT PK_TRABAJO PRIMARY KEY( IdTrabajo ),
    CONSTRAINT FK_TRABAJO_TIPO_TRABAJO FOREIGN KEY( IdTipoTrabajo ) REFERENCES Taller.TipoTrabajo( IdTipoTrabajo ),
    CONSTRAINT FK_TRABAJO_PRENDA FOREIGN KEY( IdPrenda ) REFERENCES Taller.Prenda( IdPrenda ),
    CONSTRAINT FK_TRABAJO_EMPLEADO FOREIGN KEY( IdEmpleado ) REFERENCES Taller.Empleado( IdEmpleado )
)
 
 
CREATE TABLE Taller.Material (
    IdMaterial BIGSERIAL NOT NULL,
    PrecioCliente FLOAT NOT NULL,
    Descripcion VARCHAR( 300 ) NOT NULL,
 
    CONSTRAINT PK_MATERIAL PRIMARY KEY( IdMaterial )
)

DROP TABLE Taller.Material cascade

CREATE TABLE Taller.MaterialParaTrabajo (
    IdTrabajo BIGINT NOT NULL,
    IdMaterial BIGINT NOT NULL,
    Cantidad BIGINT NOT NULL,
 
    CONSTRAINT FK_MPT_TIPO_TRABAJO FOREIGN KEY( IdTrabajo ) REFERENCES Taller.Trabajo( IdTrabajo ),
    CONSTRAINT FK_MPT_MATERIAL FOREIGN KEY( IdMaterial ) REFERENCES Taller.Material( IdMaterial )
)

DROP TABLE TALLER.MATERIALPARATRABAJO
 
CREATE TABLE Taller.Proveedor (
    IdProveedor BIGSERIAL NOT NULL,
    Nombre VARCHAR( 80 ) NOT NULL,
    Telefono VARCHAR( 10 ) NOT NULL,
    Direccion VARCHAR( 100 ) NOT NULL,
    
    CONSTRAINT PK_PROVEEDOR PRIMARY KEY (IdProveedor)
)
 
 
CREATE TABLE Taller.Compra (
    IdCompra BIGSERIAL NOT NULL,
    IdProveedor BIGINT NOT NULL,
    FechaCompra DATE NOT NULL,
    Total FLOAT NULL,
    
    CONSTRAINT PK_COMPRA PRIMARY KEY (IdCompra),
    CONSTRAINT FK_PROVEEDOR FOREIGN KEY( IdProveedor ) REFERENCES Taller.Proveedor( IdProveedor )
)

DROP TABLE TALLER.COMPRA CASCADE
 
CREATE TABLE Taller.DetalleCompra (
    IdDetalleCompra BIGSERIAL NOT NULL,
    IdCompra BIGINT NOT NULL,
    IdMaterial BIGINT NOT NULL,
    CostoUnitario FLOAT NOT NULL,
    Cantidad BIGINT NOT NULL,
    Subtotal FLOAT NOT NULL,
 
    CONSTRAINT FK_DT_COMPRA FOREIGN KEY( IdCompra ) REFERENCES Taller.Compra( IdCompra ),
    CONSTRAINT FK_DT_MATERIAL FOREIGN KEY( IdMaterial ) REFERENCES Taller.Material( IdMaterial )
)

DROP TABLE taller.detallecompra
--Triggers
--TRIGGER 1---
--1. Al insertar en la tabla "Trabajo" actualiza el "CostoTrabajo" de 
--la tabla "Prenda" con el valor de "CostoTrabajo" de la tabla "TipoTrabajo".
 
CREATE OR REPLACE FUNCTION costo_prenda_insert() RETURNS TRIGGER AS $$
BEGIN
    EXECUTE '
    UPDATE Taller.Prenda AS tp
    SET CostoTrabajo = ttt.Costo 
    FROM (Taller.Prenda AS tpp
    INNER JOIN Taller.Trabajo AS tt
        ON tpp.IdPrenda = tt.IdPrenda
    INNER JOIN Taller.TipoTrabajo AS ttt
        ON tt.IdTipoTrabajo = ttt.IdTipoTrabajo)
    WHERE tp.IdPrenda = tpp.IdPrenda
        'USING NEW;
        
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;
 
CREATE TRIGGER costo_prenda_trigg BEFORE INSERT ON Taller.Trabajo FOR EACH ROW EXECUTE PROCEDURE costo_prenda_insert();
DROP TRIGGER costo_prenda_trigg ON Taller.Trabajo
 
--TRIGGER 2---
--2. El "CostoMaterial" en la tabla "Prenda" 
--es actualizado por la "Cantidad" x "Costo" en la tabla "Trabajo".
 
CREATE OR REPLACE FUNCTION costo_material_insert() RETURNS TRIGGER AS $$
BEGIN
    EXECUTE '
        UPDATE Taller.Prenda
        SET CostoTrabajo = b.Cantidad * c.PrecioCliente 
        FROM (Taller.Prenda AS d
	INNER JOIN Taller.Trabajo AS a
	    ON d.IdPrenda = a.IdPrenda
        INNER JOIN Taller.MaterialParaTrabajo AS b
            ON a.IdTrabajo = b.IdTrabajo
        INNER JOIN Taller.Material AS c
            ON b.IdMaterial = c.IdMaterial)
        WHERE d.IdPrenda = a.IdPrenda
    '
    USING NEW;
    RETURN NEW;
    --EXECUTE 'INSERT INTO Prenda' || date_part('quarter', NEW.day) || ' VALUES ($1.*)' USING NEW;
END;
$$ LANGUAGE plpgsql;

 DROP TRIGGER costo_material_insert ON Taller.Trabajo ;
CREATE TRIGGER costo_material_insert BEFORE INSERT ON Taller.Trabajo FOR EACH ROW EXECUTE PROCEDURE costo_material_insert();
 

--TRIGGER 3---
--3.El valor costo total de la tabla “Confección” se actualiza con
--la suma de “Costo trabajo” y “Costo material” de la tabla “Prenda”
--donde coincida IdConfeccion. Cada vez que se actualiza prenda, se
--actualiza el valor de costo total.
CREATE OR REPLACE FUNCTION Taller.costo_total_confeccion() RETURNS TRIGGER AS
$$
BEGIN

UPDATE Taller.Confeccion AS c SET CostoTotal =  p.CostoTrabajo + p.CostoMaterial
    FROM( Taller.Confeccion AS co
    INNER JOIN Taller.Prenda AS p ON co.IdConfeccion = p.IdConfeccion )
WHERE c.IdConfeccion = p.IdConfeccion ;

RETURN NEW ;
END
$$
LANGUAGE plpgsql ;

DROP TRIGGER TR_costo_total_confeccion ON Taller.Prenda ;
CREATE TRIGGER TR_costo_total_confeccion AFTER UPDATE ON Taller.Prenda EXECUTE PROCEDURE Taller.costo_total_confeccion();

--TRIGGER 4---
--4. El total en "Compra" es actualizado con la suma de "Subtotal" de la tabla detalle compra,
--donde coincida IdCompra

--Updates
INSERT INTO Taller.DetalleCompra( IdCompra, IdMaterial, CostoUnitario, Cantidad, Subtotal ) VALUES( 3, 2, 40, 10, 400 )
SELECT * FROM Taller.DetalleCompra
SELECT * FROM Taller.Compra

--Function
CREATE OR REPLACE FUNCTION Taller.costo_total_compra() RETURNS TRIGGER AS
$$
BEGIN

UPDATE Taller.Compra AS c SET Total = dt.total
    FROM (
        SELECT Taller.DetalleCompra.IdCompra, SUM( Taller.DetalleCompra.Subtotal) AS total
        FROM Taller.DetalleCompra
        GROUP BY Taller.DetalleCompra.IdCompra
    ) AS dt
    WHERE dt.IdCompra = c.IdCompra ;

RETURN NEW ;
END
$$
LANGUAGE plpgsql ;

--Drop para eliminar el/los anterior/es.
DROP TRIGGER TR_costo_total_compra ON Taller.DetalleCompra ;
--Bind
CREATE TRIGGER TR_costo_total_compra AFTER INSERT ON Taller.DetalleCompra EXECUTE PROCEDURE Taller.costo_total_compra();


--TRIGGER 5---
--5. El valor "Anticipo" de la tabla "Confeccion" se calcula con el 50% del valor de "CostoTotal" de la misma tabla.
 
CREATE OR REPLACE FUNCTION anticipo_confeccion_insert() RETURNS TRIGGER AS $$
BEGIN
    EXECUTE '
        UPDATE Taller.Confeccion AS tc
        SET Anticipo = ttc.CostoTotal / 2 
        FROM Taller.Confeccion AS ttc
        WHERE tc.IdConfeccion = ttc.IdConfeccion
    '
    USING NEW;
    RETURN NEW;
   
 
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER anticipo_confeccion_insert ON Taller.Confeccion
CREATE TRIGGER anticipo_confeccion_insert BEFORE INSERT ON Taller.Confeccion FOR EACH ROW EXECUTE PROCEDURE anticipo_confeccion_insert();

--TRIGGER 6---
--6. El subtotal de "DetalleCompra" es calculado con "Cantidad" x "CostoUnitario"
 
CREATE OR REPLACE FUNCTION subtotal_detallecompra_insert() RETURNS TRIGGER AS $$
BEGIN
    EXECUTE '
        UPDATE Taller.DetalleCompra 
        SET Subtotal = Cantidad * CostoUnitario 
    '
    USING NEW;
    RETURN NEW;
   
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER subtotal_detallecompra_insert ON Taller.DetalleCompra
CREATE TRIGGER subtotal_detallecompra_insert AFTER INSERT ON Taller.DetalleCompra FOR EACH ROW EXECUTE PROCEDURE subtotal_detallecompra_insert();
 
--Usuarios y permisos.
ALTER USER admin WITH PASSWORD 'admin' ;
ALTER USER consultor WITH PASSWORD 'consultor' ;
GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA Taller TO consultor ;

REVOKE ALL PRIVILEGES ON SCHEMA Taller FROM consultor ;
GRANT USAGE ON SCHEMA Taller TO consultor ;

REVOKE ALL ON Taller.Cliente FROM consultor ;
GRANT SELECT ON Taller.Cliente TO consultor ;

REVOKE ALL ON Taller.Compra FROM consultor ;
GRANT SELECT ON Taller.Compra TO consultor ;

REVOKE ALL ON Taller.Confeccion FROM consultor ;
GRANT SELECT ON Taller.Confeccion TO consultor ;

REVOKE ALL ON Taller.DetalleCompra FROM consultor ;
GRANT SELECT ON Taller.DetalleCompra TO consultor ;

REVOKE ALL ON Taller.Empleado FROM consultor ;
GRANT SELECT ON Taller.Empleado TO consultor ;

REVOKE ALL ON Taller.Material FROM consultor ;
GRANT SELECT ON Taller.Material TO consultor ;

REVOKE ALL ON Taller.MaterialParaTrabajo FROM consultor ;
GRANT SELECT ON Taller.MaterialParaTrabajo TO consultor ;

REVOKE ALL ON Taller.Prenda FROM consultor ;
GRANT SELECT ON Taller.Prenda TO consultor ;

REVOKE ALL ON Taller.Proveedor FROM consultor ;
GRANT SELECT ON Taller.Proveedor TO consultor ;

REVOKE ALL ON Taller.TipoTrabajo FROM consultor ;
GRANT SELECT ON Taller.TipoTrabajo TO consultor ;

REVOKE ALL ON Taller.Trabajo FROM consultor ;
GRANT SELECT ON Taller.Trabajo TO consultor ;


REVOKE ALL PRIVILEGES ON SCHEMA Taller FROM admin ;
GRANT USAGE ON SCHEMA Taller TO admin ;
GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA Taller TO admin ;

REVOKE ALL ON Taller.Cliente FROM admin ;
GRANT SELECT ON Taller.Cliente TO admin ;
GRANT INSERT ON Taller.Cliente TO admin ;
GRANT UPDATE ON Taller.Cliente TO admin ;

REVOKE ALL ON Taller.Compra FROM admin ;
GRANT SELECT ON Taller.Compra TO admin ;
GRANT INSERT ON Taller.Compra TO admin ;
GRANT UPDATE ON Taller.Compra TO admin ;

REVOKE ALL ON Taller.Confeccion FROM admin ;
GRANT SELECT ON Taller.Confeccion TO admin ;
GRANT INSERT ON Taller.Confeccion TO admin ;
GRANT UPDATE ON Taller.Confeccion TO admin ;

REVOKE ALL ON Taller.DetalleCompra FROM admin ;
GRANT SELECT ON Taller.DetalleCompra TO admin ;
GRANT INSERT ON Taller.DetalleCompra TO admin ;
GRANT UPDATE ON Taller.DetalleCompra TO admin ;

REVOKE ALL ON Taller.Empleado FROM admin ;
GRANT SELECT ON Taller.Empleado TO admin ;
GRANT INSERT ON Taller.Empleado TO admin ;
GRANT UPDATE ON Taller.Empleado TO admin ;

REVOKE ALL ON Taller.Material FROM admin ;
GRANT SELECT ON Taller.Material TO admin ;
GRANT INSERT ON Taller.Material TO admin ;
GRANT UPDATE ON Taller.Material TO admin ;

REVOKE ALL ON Taller.MaterialParaTrabajo FROM admin ;
GRANT SELECT ON Taller.MaterialParaTrabajo TO admin ;
GRANT INSERT ON Taller.MaterialParaTrabajo TO admin ;
GRANT UPDATE ON Taller.MaterialParaTrabajo TO admin ;

REVOKE ALL ON Taller.Prenda FROM admin ;
GRANT SELECT ON Taller.Prenda TO admin ;
GRANT INSERT ON Taller.Prenda TO admin ;
GRANT UPDATE ON Taller.Prenda TO admin ;

REVOKE ALL ON Taller.Proveedor FROM admin ;
GRANT SELECT ON Taller.Proveedor TO admin ;
GRANT INSERT ON Taller.Proveedor TO admin ;
GRANT UPDATE ON Taller.Proveedor TO admin ;

REVOKE ALL ON Taller.TipoTrabajo FROM admin ;
GRANT SELECT ON Taller.TipoTrabajo TO admin ;
GRANT INSERT ON Taller.TipoTrabajo TO admin ;
GRANT UPDATE ON Taller.TipoTrabajo TO admin ;

REVOKE ALL ON Taller.Trabajo FROM admin ;
GRANT SELECT ON Taller.Trabajo TO admin ;
GRANT INSERT ON Taller.Trabajo TO admin ;
GRANT UPDATE ON Taller.Trabajo TO admin ; 
 
--Pruebas y valores de prueba.--
--Selects
SELECT * FROM Taller.Confeccion
SELECT * FROM Taller.Proveedor
SELECT * FROM Taller.Compra
SELECT * FROM Taller.Material
SELECT * FROM Taller.DetalleCompra
SELECT * FROM Taller.Confeccion
SELECT * FROM Taller.Prenda
SELECT * FROM Taller.Trabajo
SELECT * FROM Taller.TipoTrabajo
SELECT * FROM Taller.MaterialParaTrabajo
SELECT * FROM Taller.Cliente

--Inserción de valores de prueba.
--Cliente y empleado
INSERT INTO Taller.Cliente( Nombre, ApellidoPaterno, ApellidoMaterno, Telefono ) VALUES( 'Juan', 'Wagner', 'Gutenberg', '5554895623' )
INSERT INTO Taller.Empleado( Nombre, ApellidoPaterno, ApellidoMaterno, Telefono, Direccion )
    VALUES( 'Hans', 'Valdez', 'Sanchez', '5556895623', 'Via Agia, San Luis Potosí' )
 
--Trabajos
INSERT INTO Taller.TipoTrabajo( Costo, Nombre ) VALUES( 10, 'Boton' )
INSERT INTO Taller.Confeccion( FechaPedido, Anticipo, IdCliente ) VALUES ( NOW(), 50, 4 )
INSERT INTO Taller.Prenda( Finalizado, IdConfeccion ) VALUES( FALSE, 23 )
INSERT INTO Taller.Trabajo( IdTipoTrabajo, IdPrenda, IdEmpleado ) VALUES(.2 , 11, 1 )
 
--Provedores y materiales para trabajo.
INSERT INTO Taller.Material( Descripcion, PrecioCliente ) VALUES( 'Seda', 50 )
INSERT INTO Taller.Material( Descripcion, PrecioCliente ) VALUES( 'Cierre', 40 )
INSERT INTO Taller.Proveedor( Nombre, Telefono, Direccion ) VALUES( 'Luis Nieto', '5554677889', 'Av. Manuel Nava, San Luis Potosí' )
INSERT INTO Taller.Compra( IdProveedor, FechaCompra, Total ) VALUES( 1, NOW(), 500 )
INSERT INTO Taller.Compra( IdProveedor, FechaCompra, Total ) VALUES( 1, NOW(), 600 )
INSERT INTO Taller.DetalleCompra( IdCompra, IdMaterial, CostoUnitario, Cantidad, Subtotal ) VALUES( 1, 1, 50, 10, 500 )
INSERT INTO Taller.DetalleCompra( IdCompra, IdMaterial, CostoUnitario, Cantidad, Subtotal ) VALUES( 1, 2, 20, 6, 500 )
INSERT INTO Taller.DetalleCompra( IdCompra, IdMaterial, CostoUnitario, Cantidad, Subtotal ) VALUES( 1, 3, 40, 5, 500 )
INSERT INTO Taller.DetalleCompra( IdCompra, IdMaterial, CostoUnitario, Cantidad, Subtotal ) VALUES( 3, 2, 40, 10, 400 )
INSERT INTO Taller.MaterialParaTrabajo( IdMaterial, IdTrabajo, Cantidad) VALUES( 1, 28, 3 )
INSERT INTO Taller.MaterialParaTrabajo( IdMaterial, IdTrabajo, Cantidad) VALUES( 2, 30, 4 )

--Los update e insert sirven para probar los triggers, cada vez que se modifique algo, se actualiza la tabla correspondiente.
UPDATE Taller.Prenda AS p SET CostoTrabajo = 150 WHERE p.IdPrenda = 2 ;
UPDATE Taller.Prenda AS p SET CostoMaterial = 150 WHERE p.IdPrenda = 2 ;
SELECT * FROM Taller.Confeccion AS c ORDER BY c.IdConfeccion

UPDATE Taller.Prenda AS p SET CostoTrabajo = 120 WHERE p.IdPrenda = 3 ;
UPDATE Taller.Prenda AS p SET CostoMaterial = 120 WHERE p.IdPrenda = 3 ;
SELECT * FROM Taller.Confeccion AS c ORDER BY c.IdConfeccion

SELECT * FROM TALLER.COMPRA
SELECT IdDetalleCompra, d.IdCompra, c.FechaCompra, c.IdProveedor, d.IdMaterial, m.Descripcion, CostoUnitario, Cantidad, Subtotal FROM Taller.DetalleCompra AS d INNER JOIN Taller.Material AS m ON d.IdMaterial = m.IdMaterial INNER JOIN Taller.Compra AS c ON d.IdCompra = c.IdCompra

SELECT d.IdCompra, c.IdProveedor, m.Descripcion, c.FechaCompra, c.Total FROM Taller.DetalleCompra AS d
        INNER JOIN Taller.Material AS m ON d.IdMaterial = m.IdMaterial
        INNER JOIN Taller.Compra AS c ON d.IdCompra = c.IdCompra

        SELECT * FROM Taller.compra

SELECT d.IdDetalleCompra, d.IdCompra, c.FechaCompra, d.IdMaterial, m.Descripcion, CostoUnitario, Cantidad, Subtotal FROM Taller.DetalleCompra AS d INNER JOIN Taller.Material AS m ON d.IdMaterial = m.IdMaterial INNER JOIN Taller.Compra AS c ON d.IdCompra = c.IdCompra  WHERE d.IdDetalleCompra = 1

SELECT p.IdPrenda, p.IdConfeccion, CONCAT( cl.Nombre,' ',cl.ApellidoPaterno,' ',cl.ApellidoMaterno,' ',TO_CHAR( c.FechaPedido,'HH12:MI:SS') ) AS InfoConfeccion, CostoTrabajo, CostoMaterial, Finalizado FROM Taller.Prenda AS p INNER JOIN Taller.Confeccion AS c ON p.IdConfeccion = c.IdConfeccion INNER JOIN Taller.Cliente AS cl ON c.IdCliente = cl.IdCliente


