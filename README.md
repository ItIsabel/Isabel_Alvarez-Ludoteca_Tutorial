# Ludoteca SPA - MVP

Este proyecto es una Aplicación de Página Única (SPA) para gestionar una ludoteca que maneja préstamos de juegos para clientes.

## Objetivos No Conseguidos

- **Filtrado:** Que el filtro encuentre préstamos donde FechaBusqueda = FechaInicioPrestamo
- **Validación de fechas:** La fecha de fin no puede ser anterior a la fecha de inicio
- **Alertas:** Mostrar alerta si se intenta guardar un periodo mayor de 14 días
- **Mensajes de error:** Mostrar mensajes claros al usuario cuando ocurren errores de validación o violaciones de integridad

- **## tests** 
- No funcionan correctamente los siguientes :
   - public void saveWithoutIdShouldCreateNewLoan() {
   - public void getExistsAuthorIdShouldReturnAuthor() {
   - public void modifyWithExistIdShouldModifyGame() {
   - public void saveWithoutIdShouldCreateNewGame() {

… aunque la funcionalidad del frontend funciona según lo esperado.


## Estado del Proyecto

### Características Conseguidas

#### Sección de Filtrado
- Título del Juego: Filtro desplegable para mostrar préstamos por juego seleccionado
- Cliente: Filtro desplegable para mostrar préstamos por cliente seleccionado  
- Fecha de Búsqueda: Selector de fecha que filtra préstamos donde la fecha de búsqueda cae dentro del periodo activo del préstamo

#### Sección de Listado (Paginado)
- ID del Préstamo: Campo de solo visualización que muestra el identificador único del préstamo
- Nombre del Juego: Campo de solo visualización que muestra el nombre del juego prestado
- Nombre del Cliente: Campo de solo visualización que muestra el nombre del cliente
- Fecha de Inicio del Préstamo: Campo de solo visualización que muestra cuándo comenzó el préstamo
- Fecha de Fin del Préstamo: Campo de solo visualización que muestra cuándo termina el préstamo
- Eliminar Préstamo: Botón que elimina el préstamo correspondiente

#### Pantalla de Nuevo Préstamo
- Identificador: Campo de solo lectura autogenerado (se genera al guardar)
- Nombre del Cliente: Selección desplegable obligatoria
- Nombre del Juego: Selección desplegable obligatoria

#### Lógica de Validación de Préstamos
- Ambas fechas son obligatorias (Validación en Frontend)
- El periodo máximo de préstamo es de 14 días (Validación en Frontend)
- El mismo juego no puede ser prestado a diferentes clientes en periodos que se solapen (Validación en Backend)
- Los clientes no pueden tener más de 2 préstamos activos en un día determinado (Validación en Backend)
- Cuando se elimina un cliente, todos los préstamos asociados son eliminados (Cascade OneToMany)
- Solo la categoría "Ameritrash" puede ser eliminada (porque no tiene juegos asociados)


### CONSIDERACIONES

- Si un cliente se borra. se borran los préstamos asociados a él (
OnetoMany on delete cascade).

- La única categoría que se puede borrar es Ameritrash porque no tiene
juegos asociados.

#### ANGULAR:

- Como se repetían mucho los estilos. He creado un container-list para los
listados (salvo el de games que va por cards) y container-edit (para los
edit-dialogs) y aplicado los estilos en el styles.scss

#### IMPLEMENTACIONES FUTURAS:

- Seria guay terminar todas las validaciones y mostrar mensajes al
usuario, porque cuando da fallos de violación de integridad y demás no
le salta nada...



-----------------------------------------------------------------------------------------------------------------------------------------------------------------
## Notas de Implementación Técnica - Esto ya es para mi =):

### Backend
- Framework Spring Boot
- Java 17 o superior
- Base de datos H2
- Arquitectura API RESTful

### Frontend
- Framework Angular
- Estilos compartidos para contenedores de listas y diálogos de edición

## Configuración del Entorno de Desarrollo

### Requisitos Backend
- IDE IntelliJ
- Java 17 o superior
- Postman para pruebas de API

### Configuración de Spring
- Tipo de proyecto: Maven
- Lenguaje: Java
- Versión de Spring Boot: 3.2.4 (o similar versión 3.x no-SNAPSHOT)
- Group: com.ccsw
- ArtifactId: tutorial
- Versión de Java: 17 o superior

### Dependencias Backend
- Spring Web
- Spring Data JPA
- Base de datos H2
- Dependencias adicionales:
  ```xml
  <dependency>
      <groupId>org.springdoc</groupId>
      <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
      <version>2.0.3</version>
  </dependency>
  <dependency>
      <groupId>org.hibernate</groupId>
      <artifactId>hibernate-validator</artifactId>
      <version>8.0.0.Final</version>
  </dependency>
  <dependency>
      <groupId>org.modelmapper</groupId>
      <artifactId>modelmapper</artifactId>
      <version>3.1.1</version>
  </dependency>
  ```

### Requisitos Frontend
- Visual Studio Code
- Node.js (instalado vía Scoop)

### Configuración del Gestor de Paquetes Scoop
```powershell
Set-ExecutionPolicy -ExecutionPolicy RemoteSigned -Scope CurrentUser
Invoke-RestMethod -Uri https://get.scoop.sh | Invoke-Expression
scoop config use_lessmsi true
scoop install main/nodejs
```

### Configuración de Angular
1. Instalar Angular CLI: `npm install -g @angular/cli`
2. Crear nuevo proyecto: `ng new tutorial --strict=false`
   - ¿Desea añadir enrutamiento Angular? Preferiblemente: y
   - ¿Qué formato de hoja de estilos le gustaría usar? Preferiblemente: SCSS
   - ¿Desea habilitar la renderización del lado del servidor (SSR)? Preferiblemente: N
3. Añadir Angular Material: `ng add @angular/material`
4. Iniciar el proyecto: `ng serve`

