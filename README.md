# AutoEscuela Santa Fe

## Configuración inicial

1. Se recomienda utilizar Apache NetBeans como IDE de desarrollo. Puede descargar la última versión desde [aquí](https://netbeans.apache.org/front/main/download/). O en su defecto, IntelliJ IDEA. Puede descargar la última versión desde [aquí](https://www.jetbrains.com/es-es/idea/download/?section=windows).
2. Instalar cualquier versión de Java JDK que sea superior a v17, la cual puede descargarse [aquí]([https://nodejs.org/dist/v18.16.0/node-v18.16.0-x64.msi](https://www.oracle.com/ar/java/technologies/downloads/#java21)).

## Aplicaciones

<details>
  <summary>Referencia sobre ejecución de aplicaciones, librerías y scripts del proyecto</summary>

### Librerías

- `/Resources/Libraries` - Todas las librerías deben importarse en el proyecto.

## Referencia de directorios

	-`Controller`: Contiene clases responsables de manejar la entrada del usuario y gestionar el flujo de datos hacia y desde el DAO y la interfaz de usuario.

	-`DAO`: Abreviatura de Data Access Object. Contiene clases responsables de interactuar con la base de datos, encapsulando la lógica de acceso a la base de datos.

	-`Files`: Reservado para almacenar varios archivos relacionados con el proyecto.

	-`Frame`: Contiene clases relacionadas con los frames o ventanas de la interfaz gráfica.

	-`Images`: Almacena imágenes utilizadas en el proyecto. Subcarpetas como background, icon_original, icon_resize, logo_original, logo_resize y PDF categorizan diferentes tipos de imágenes.

	-`Objects`: Contiene clases que representan varios objetos en el proyecto. La subcarpeta Unifiers podría contener clases relacionadas con la unificación o estandarización de ciertas funcionalidades.

	-`Panels`: Alberga clases relacionadas con diferentes paneles de la interfaz de usuario, incluyendo subcarpetas para funcionalidades específicas como Agenda, Calendar, Config, Finance, Legal, Logo, Payment_History, People y Price_List.

	-`PDF`: Reservado para almacenar archivos relacionados con PDF.

	-`Resources`: Contiene recursos generales utilizados en el proyecto, y subcarpetas como Others pueden contener archivos de recursos adicionales.

	-`Service`: Contiene clases que proporcionan servicios o lógica empresarial a otras partes de la aplicación.

	-`Utils`: Clases de utilidad para diferentes propósitos, incluyendo subcarpetas como Cronos para manejar tareas relacionadas con el tiempo, Migration para migración de bases de datos, y Security para utilidades relacionadas con la seguridad.

	Windows: Contiene clases relacionadas con diferentes ventanas de la aplicación.

## Proceso de Desarrollo

<details>

<summary>Nociones sobre el proceso de desarrollo y cómo realizar las contribuciones de código y documentación del proyecto</summary>
### Consejos para generación de commits

- Hacer commits pequeños y frecuentes, para facilitar la revisión de los cambios.
  - Ejemplo: Si se está trabajando en una funcionalidad que requiere de 3 commits, se deberán realizar 3 commits, en lugar de realizar un commit con los 3 cambios.
  - Esto facilita la revisión de los cambios, ya que se puede revisar cada commit por separado.
  - Además, permite que si se encuentra un error en un commit, se pueda revertir ese commit sin afectar los demás.
- Escribir los mensajes de commits de forma breve y concisa, en tiempo presente.
  - 👌 Ejemplo: `[FE-#19] - Agrega componente de login`
  - ⛔ No: `[FE-#19] - Agregué componente de login`

#### Mensajes de commits

Para una mejor referencia de cuál es el área a la que contribuye un commit, se deben utilizar los siguientes prefijos en los mensajes de commits, acompañados por el ID correspondiente al issue al que pertenecen.

- **[AR]** - Arquitectura
- **[BE]** - Backend
- **[DM]** - Modelos de dominio
- **[DOCS]** - Documentación
- **[FE]** - Frontend
- **[TOOL]** - Herramientas de desarrollo

#### Ejemplos de mensaje de commits

- `[FE-#47] - Agrega componente de login`
- `[BE-#93] - Agrega endpoint de login`
- `[TOOL-#7] - Agrega librería @ngrx/store`

### Ramas de desarrollo (Branches)

- **main** - Rama principal del proyecto. Contiene la última versión estable del proyecto.
- **release/**_[numero_de_version]_ - Rama de lanzamiento de una versión. Contiene la última versión de la aplicación en producción.
  - Por ejemplo: `release/0.0.1` es la rama correspondiente a la versión 1.0.0 de la aplicación. A esta rama se deberán realizar los merges de las ramas de desarrollo de las nuevas funcionalidades previo al lanzamiento de esta versión.
- **feature/**_[id_issue][nombre_de_la_funcionalidad]_ - Rama de desarrollo de una funcionalidad, correspondiente a un issue determinado.
  - Por ejemplo: `feature/57-login` es la rama correspondiente al desarrollo de la funcionalidad de login, correspondiente al issue #57 en el repositorio.
- **bugfix/**_[id_issue][nombre_del_bug]_ - Rama de desarrollo de un bug, correspondiente a un issue determinado.
  - Por ejemplo: `bugfix/49-bug-flicker-en-header` es la rama correspondiente al desarrollo de la corrección de un bug de flicker en el header de la aplicación, correspondiente al issue #49 en el repositorio.

#### Manejo de ramas de desarrollo

Para más facilidad en el manejo de las ramas de desarrollo, se recomienda utilizar el módulo incorporado de GitHub Desktop (descargable [aquí](https://central.github.com/deployments/desktop/desktop/latest/win32)), al cual puede accederse haciendo click en la opción `Git` en la barra de menú superior y en el botón de la esquina inferior derecha del IDE, donde se visualiza la rama activa actual.

- Para crear una nueva rama de desarrollo, se debe hacer checkout de la rama `release/[numero_de_version]` vigente y crear (hacer checkout de) una nueva rama a partir de esta.
  - `git checkout release/[numero_de_version]
  - `git checkout -b feature/57-login`
- Para actualizar una rama de desarrollo, se debe hacer checkout de la rama `release/[numero_de_version]` vigente y realizar un merge hacia la rama de desarrollo correspondiente.
  - `git checkout release/[numero_de_version]`
  - `git pull'`
  - `git checkout feature/57-login`
  - `git merge release/[numero_de_version]`
  - `git push`
- Para actualizar una rama de desarrollo, se debe crear una nueva Pull Request desde la rama `release/[numero_de_version]` hacia la rama de desarrollo correspondiente.
- Para actualizar la rama `main`, se debe hacer checkout de la rama `main` y realizar un pull desde la rama `release/[numero_de_version]`.
  - `git checkout main`
  - `git pull release/[numero_de_version]`

#### Pull Requests

Para realizar un Pull Request, se debe crear una nueva Pull Request desde la rama `release/[numero_de_version]` hacia la rama de desarrollo correspondiente, utilizando el dashboard de pull requests

Los mensajes de cada Pull Request deben incluir el ID del issue al que pertenece, el nombre de la funcionalidad que se está desarrollando y el prefijo de las áreas del proyecto que modifican.

- Ejemplo: `[#57-FE] - Agrega componente de login`
- Ejemplo: `[#93-BE] - Agrega endpoint de login`
- Ejemplo: `[#7-TOOL] - Agrega librería @ngrx/store`
- Ejemplo: `[#49-FE] - Corrige bug de flicker en header`
- Ejemplo: `[#73-DM] - Agrega modelo User`

En el caso de que una PR modifique varias áreas del proyecto, se deberá incluir el prefijo de cada área modificada, separada por comas.

- Ejemplo: `[#93-BE,FE] - Agrega nuevo parámetro para request de login.`
- Ejemplo: `[#93-BE,FE,DM] - Agrega nuevo atributo al modelo Provider y lo utiliza en el request de login y su procesamiento.`
</details>

## Herramientas de desarrollo

<details>
<summary>Listado de las herramientas y tecnologías usadas para el desarrollo</summary>
#### Herramientas de desarrollo

- [IntelliJ 2023] - IDE para desarrollo de aplicaciones Java.
- [Visual Studio Code](https://code.visualstudio.com/) - Editor de codigo.
- [Git](https://git-scm.com/) - Sistema de control de versiones.
- [GitHub](https://github.com) - Plataforma de desarrollo colaborativo.
- [Github Projects](https://github.com) - Plataforma de gestión de proyectos.
- [DBeaver](https://dbeaver.io/) - Herramienta para administración de bases de datos.
- [MySQL](https://www.mysql.com/) - Base de datos relacional.

#### Despliegue de versiones de desarrollo

- [Vercel](https://vercel.com/) - Plataforma de hosting de aplicaciones web.
  - Para aplicaciones de frontend.
- [Render](https://render.com/) - Plataforma de hosting de aplicaciones web.
  - Para aplicaciones de backend.

#### Lenguajes de programación

- [Java](https://www.java.com/) - Lenguaje de programación versátil utilizado para el desarrollo de aplicaciones en una variedad de dominios, desde aplicaciones de escritorio 	hasta sistemas embebidos.
	- Versión: 17
- [SQL](https://www.mysql.com/) - Lenguaje de programación para desarrollo de procedimientos almacenados en MySQL DB.

