# Endpoint: Registrar Usuario con Persona

## Descripción
Este endpoint permite registrar un nuevo usuario con sus datos completos de persona en una sola petición. Sigue el patrón de normalización donde primero se crea el registro en la tabla `personas` y luego se crea el usuario vinculado a esa persona.

## Información del Endpoint

- **URL**: `/api/usuarios/registrar`
- **Método**: `POST`
- **Content-Type**: `application/json`

## Request Body (DTO)

```json
{
  "nombrecompleto": "Juan Carlos Pérez García",
  "tipoDoc": "DNI",
  "nroDoc": "12345678",
  "sexo": "MASCULINO",
  "fecNacimiento": "1990-05-15",
  "estadoCivil": "SOLTERO",
  "telefono": "987654321",
  "email": "juan.perez@example.com",
  "direccion": "Av. Principal 123, Lima",
  "fotoUrl": null,
  "username": "jperez",
  "password": "miPassword123"
}
```

### Campos Requeridos

- **nombrecompleto**: String - Nombre completo de la persona
- **tipoDoc**: Enum - Tipo de documento (`DNI`, `CARNET_EXTRANJERIA`, `PASAPORTE`, `RUC`)
- **nroDoc**: String - Número de documento
- **username**: String - Nombre de usuario único para el sistema
- **password**: String - Contraseña (se encriptará automáticamente con BCrypt)

### Campos Opcionales

- **sexo**: Enum - Sexo (`MASCULINO`, `FEMENINO`, `OTRO`)
- **fecNacimiento**: Date - Fecha de nacimiento (formato: YYYY-MM-DD)
- **estadoCivil**: Enum - Estado civil (`SOLTERO`, `CASADO`, `DIVORCIADO`, `VIUDO`, `UNION_LIBRE`)
- **telefono**: String - Número de teléfono
- **email**: String - Correo electrónico
- **direccion**: String - Dirección completa
- **fotoUrl**: String - URL de la foto (puede ser null)

## Response

### Respuesta Exitosa (201 CREATED)

```json
{
  "success": true,
  "message": "Usuario registrado exitosamente",
  "data": {
    "usuaId": 1,
    "username": "jperez",
    "ultimaSesion": null,
    "estado": true,
    "persId": 1,
    "nombrecompleto": "Juan Carlos Pérez García",
    "tipoDoc": "DNI",
    "nroDoc": "12345678",
    "sexo": "MASCULINO",
    "fecNacimiento": "1990-05-15",
    "estadoCivil": "SOLTERO",
    "telefono": "987654321",
    "email": "juan.perez@example.com",
    "direccion": "Av. Principal 123, Lima",
    "fotoUrl": null
  }
}
```

### Respuesta de Error (400 BAD REQUEST)

```json
{
  "success": false,
  "message": "Ya existe una persona con este documento"
}
```

```json
{
  "success": false,
  "message": "El username ya está en uso"
}
```

### Respuesta de Error de Validación (400 BAD REQUEST)

```json
{
  "success": false,
  "message": "El nombre completo es requerido"
}
```

## Proceso Interno

1. **Validación de campos requeridos** (nombre, tipoDoc, nroDoc, username, password)
2. **Creación de entidad Persona** con los datos proporcionados
3. **Validación de documento único** - verifica que no exista otra persona con el mismo tipo y número de documento
4. **Validación de username único** - verifica que no exista otro usuario con el mismo username
5. **Encriptación de contraseña** usando BCrypt
6. **Creación del usuario** vinculado a la persona
7. **Retorno de DTO** con toda la información del usuario y persona creados

## Ejemplos de Uso

### Ejemplo con Postman

1. Crear una nueva petición POST
2. URL: `http://localhost:8080/api/usuarios/registrar`
3. Headers: `Content-Type: application/json`
4. Body (raw - JSON):

```json
{
  "nombrecompleto": "María Elena Torres",
  "tipoDoc": "DNI",
  "nroDoc": "98765432",
  "sexo": "FEMENINO",
  "fecNacimiento": "1985-03-20",
  "estadoCivil": "CASADO",
  "telefono": "912345678",
  "email": "maria.torres@example.com",
  "direccion": "Jr. Los Olivos 456, Lima",
  "fotoUrl": null,
  "username": "mtorres",
  "password": "Password123!"
}
```

### Ejemplo con cURL

```bash
curl -X POST http://localhost:8080/api/usuarios/registrar \
  -H "Content-Type: application/json" \
  -d '{
    "nombrecompleto": "Carlos Mendoza",
    "tipoDoc": "DNI",
    "nroDoc": "11223344",
    "username": "cmendoza",
    "password": "SecurePass456"
  }'
```

## Notas Importantes

- La contraseña se encripta automáticamente usando BCrypt antes de guardarse
- El campo `usuaEstado` se establece en `true` por defecto
- El campo `persEsActivo` se establece en `true` por defecto
- Si el documento ya existe en la base de datos, se retorna un error
- Si el username ya existe, se retorna un error
- La foto puede agregarse posteriormente usando otros endpoints

## Relación con Otros Endpoints

Este endpoint es similar a:
- `/api/pacientes/registrar` - Para registrar pacientes con persona
- `/api/medicos/registrar` - Para registrar médicos con persona y usuario

Todos siguen el mismo patrón de normalización con la tabla `personas` como entidad central.
