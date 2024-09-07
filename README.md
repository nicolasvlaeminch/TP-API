# TP-API -> Gestión de Empleados y Jornadas Laborales.

## Resumen
Una API REST dedicada a la administración de empleados y sus horarios laborales. Ofrece herramientas para crear, buscar, actualizar y eliminar empleados, además de gestionar sus jornadas laborales. Permite asignar distintos tipos de jornadas, consultar estas jornadas y obtener los detalles relacionados con cada tipo.
## Descripción
El sistema propuesto cuenta con una serie de características clave que facilitan la gestión de empleados y su jornada laboral. Permite realizar operaciones CRUD sobre la información de los empleados, así como asignarles jornadas laborales específicas. Las jornadas laborales se organizan en tres categorías distintas: Turno Normal, Turno Extra y Día Libre. Además, se implementan validaciones personalizadas para asegurar la precisión de cada acción dentro del sistema.

En cuanto a la arquitectura y las tecnologías empleadas, el sistema está basado en el framework Spring. Su diseño se basa en una arquitectura en capas, promoviendo una separación clara de responsabilidades. Para la persistencia de datos, se utiliza JPA (Java Persistence API) junto con una base de datos H2 en memoria, que facilita el desarrollo y la prueba del sistema sin necesidad de una base de datos externa. Los DTOs se emplean para la transferencia eficiente de datos entre las distintas capas del sistema. La gestión de excepciones se maneja de manera global y específica para asegurar que cualquier error sea tratado de manera adecuada y consistente.

## ¿Cómo usar la API REST con IntelliJ?

Primero, clona el repositorio del proyecto a tu máquina local. Una vez que hayas hecho esto, abre IntelliJ IDEA y carga el proyecto recién clonado. Dentro del proyecto, busca la clase TurnosRotativosApplication. Esta clase es el punto de entrada de la aplicación, así que simplemente ejecútala y la aplicación estará lista para funcionar.

**Gestión de Empleados**:
- `POST /empleado`: Registra un nuevo empleado.
- `GET /empleado/{empleadoId}`: Consultar los detalles de un empleado.
- `PUT /empleado/{empleadoId}`: Modificar la información de un empleado.
- `DELETE /empleado/{empleadoId}`: Eliminar un empleado.

**Conceptos Laborales**:
- `GET /concepto`: Obtener la lista de conceptos laborales.

**Jornadas Laborales**:
- `POST /jornada`: Asignar una jornada laboral a un empleado.
- `GET /jornada`: Consultar las jornadas disponibles.
