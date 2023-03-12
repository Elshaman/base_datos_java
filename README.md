# Conexion por operacion

Singleton es util para tener una sola instancia que controle la conexion, para varias transacciones a realizar: Elimina la necesidad de tener que crear multiples objetos de conexion.

### Usos

En un escenario web, cuando se debe hacer una operacion de bd por cada request, y por tanto, es requerido, por cada una , abrir una conexion , y al finalizar la operacion, cerrar la misma.

### Modificaciones hechas:

- Cada operacion abre una conexion en un try(lo que quiere decir que al terminar automaticamente se cierra)
- El singleton se modifica para que ahora no se pregunte por la instancia primero, sino que devuelva objeto de conexion siempre:

  
        public static Connection getInstance() throws SQLException {

                    connection = DriverManager.getConnection(url, username, password);

                return connection;
        }

- no hay necesidad de try catch en el invocador, ya que cada metodo tiene su propio bloque de excepciones

### Desventajas 

- Si se tiene demasiada concurrencia(donde sea abren y cierran conexion varias veces por segundo ), esto puede causar cuello de bottella, y seria mejor utilizar un pool de conexiones.
