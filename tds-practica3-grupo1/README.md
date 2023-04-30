Tiempo empleado:
-Darío: 8h 30m
-Álvaro: 7h 40m

Clases que forman la solución: Order, Gestor, Dimension3D, , Producto, OrderState, IDataBaseManager

Ratio code to test:
-Clase OrderTest: 1:3
-Clase GestorTest: 1:15
-Clase ProductoTest: 1:2
-Clase Dimension3DTest: 1:1 

Refactorizacion:
-Replace static variable with parameter: En la clase Order creamos dos varaibles estaticas para los mensaje de error que se repetian.
-Rename Field: Renombramos la clase pedidoTest a OrderTest, para que tenga consistencia con la clase a la que testea, Order.
-Inline Variable: En la clase Gestor, en la funcion getFechaAntigua, en vez de crear una variable para almacenar la lista cronologica, lo hacemos directamente en el return de la funcion.
-Rename variable: Renombramos las variables id con nombres más especificos según la clase a la que pertenezcan, ejemplo: idProducto, idOrder.
