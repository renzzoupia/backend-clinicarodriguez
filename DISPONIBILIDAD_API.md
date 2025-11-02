# API de Disponibilidad - Documentación

## 📋 Descripción
API para consultar la disponibilidad de médicos y horarios filtrados por especialidad.

---

## 🔍 Endpoint Principal

### **GET** `/api/disponibilidad/especialidad/{especialidadId}`

Obtiene todos los médicos que tienen una especialidad específica junto con sus horarios disponibles.

---

## 📥 Parámetros

| Parámetro | Tipo | Ubicación | Requerido | Descripción |
|-----------|------|-----------|-----------|-------------|
| `especialidadId` | Long | Path | Sí | ID de la especialidad a consultar |

---

## 📤 Respuesta Exitosa

**Status Code:** `200 OK`

```json
{
  "success": true,
  "message": "Disponibilidad encontrada",
  "totalMedicos": 2,
  "data": {
    "especialidadId": 1,
    "especialidadNombre": "Cardiología",
    "especialidadDescripcion": "Especialidad en enfermedades del corazón",
    "medicosDisponibles": [
      {
        "medicoId": 5,
        "medicoNombre": "Juan",
        "medicoApellido": "Pérez",
        "medicoTelefono": "987654321",
        "medicoFotoUrl": "http://localhost:8080/api/files/medico-5.jpg",
        "medicoEstado": "ACTIVO",
        "horarios": [
          {
            "diaId": 1,
            "diaNombre": "LUNES",
            "horaInicio": "08:00:00",
            "horaFin": "12:00:00",
            "duracion": 30,
            "estado": 1
          },
          {
            "diaId": 3,
            "diaNombre": "MIERCOLES",
            "horaInicio": "14:00:00",
            "horaFin": "18:00:00",
            "duracion": 30,
            "estado": 1
          }
        ]
      },
      {
        "medicoId": 8,
        "medicoNombre": "María",
        "medicoApellido": "López",
        "medicoTelefono": "987123456",
        "medicoFotoUrl": "http://localhost:8080/api/files/medico-8.jpg",
        "medicoEstado": "ACTIVO",
        "horarios": [
          {
            "diaId": 2,
            "diaNombre": "MARTES",
            "horaInicio": "09:00:00",
            "horaFin": "13:00:00",
            "duracion": 45,
            "estado": 1
          }
        ]
      }
    ]
  }
}
```

---

## ❌ Respuestas de Error

### **Especialidad no encontrada**
**Status Code:** `404 NOT FOUND`

```json
{
  "success": false,
  "message": "Especialidad no encontrada con id: 999"
}
```

### **Sin médicos disponibles**
**Status Code:** `200 OK`

```json
{
  "success": false,
  "message": "No hay médicos disponibles para esta especialidad",
  "data": {
    "especialidadId": 5,
    "especialidadNombre": "Pediatría",
    "especialidadDescripcion": "Especialidad infantil",
    "medicosDisponibles": []
  }
}
```

### **Error interno**
**Status Code:** `500 INTERNAL SERVER ERROR`

```json
{
  "success": false,
  "message": "Error al obtener disponibilidad: [detalle del error]"
}
```

---

## 🔑 Campos de Respuesta

### DisponibilidadEspecialidadDTO

| Campo | Tipo | Descripción |
|-------|------|-------------|
| `especialidadId` | Long | ID de la especialidad |
| `especialidadNombre` | String | Nombre de la especialidad |
| `especialidadDescripcion` | String | Descripción de la especialidad |
| `medicosDisponibles` | Array | Lista de médicos con sus horarios |

### MedicoDisponibilidadDTO

| Campo | Tipo | Descripción |
|-------|------|-------------|
| `medicoId` | Long | ID del médico |
| `medicoNombre` | String | Nombre del médico |
| `medicoApellido` | String | Apellido del médico |
| `medicoTelefono` | String | Teléfono del médico |
| `medicoFotoUrl` | String | URL de la foto del médico |
| `medicoEstado` | String | Estado del médico (ACTIVO/INACTIVO) |
| `horarios` | Array | Lista de horarios disponibles |

### HorarioDTO

| Campo | Tipo | Descripción |
|-------|------|-------------|
| `diaId` | Long | ID del día de la semana |
| `diaNombre` | String | Nombre del día (LUNES, MARTES, etc.) |
| `horaInicio` | Time | Hora de inicio de atención |
| `horaFin` | Time | Hora de fin de atención |
| `duracion` | Integer | Duración de cada consulta en minutos |
| `estado` | Integer | Estado del horario (1=activo, 0=inactivo) |

---

## 🎯 Lógica Implementada

El endpoint realiza las siguientes operaciones:

1. **Validar especialidad**: Busca la especialidad por ID
2. **Buscar médicos**: Obtiene médicos asociados a esa especialidad (tabla `medicos_especialidades`)
3. **Filtrar por estado**: Solo incluye médicos con estado "ACTIVO"
4. **Obtener horarios**: Por cada médico, busca en tabla `dias_medico`
5. **Filtrar horarios activos**: Solo incluye horarios con `dime_estado = 1`
6. **Excluir médicos sin horarios**: No incluye médicos que no tienen horarios configurados
7. **Retornar estructura**: Devuelve JSON con toda la información organizada

---

## 💡 Ejemplo de Uso en Frontend

### JavaScript (Fetch API)

```javascript
async function obtenerDisponibilidadEspecialidad(especialidadId) {
  try {
    const response = await fetch(
      `http://localhost:8080/api/disponibilidad/especialidad/${especialidadId}`
    );
    const result = await response.json();
    
    if (result.success) {
      const { medicosDisponibles } = result.data;
      
      // Mostrar médicos y horarios
      medicosDisponibles.forEach(medico => {
        console.log(`Dr. ${medico.medicoNombre} ${medico.medicoApellido}`);
        
        medico.horarios.forEach(horario => {
          console.log(`  ${horario.diaNombre}: ${horario.horaInicio} - ${horario.horaFin}`);
        });
      });
    }
  } catch (error) {
    console.error('Error:', error);
  }
}

// Uso
obtenerDisponibilidadEspecialidad(1);
```

### Axios

```javascript
import axios from 'axios';

const obtenerDisponibilidad = async (especialidadId) => {
  try {
    const { data } = await axios.get(
      `http://localhost:8080/api/disponibilidad/especialidad/${especialidadId}`
    );
    
    return data.data; // DisponibilidadEspecialidadDTO
  } catch (error) {
    console.error('Error al obtener disponibilidad:', error);
    throw error;
  }
};
```

---

## 🧪 Pruebas con Postman

1. **Crear nueva petición GET**
2. **URL**: `http://localhost:8080/api/disponibilidad/especialidad/1`
3. **Headers**: No requiere headers especiales (a menos que uses JWT)
4. **Send**

### Variaciones de prueba:

- ID válido con médicos: `/especialidad/1`
- ID válido sin médicos: `/especialidad/999`
- ID inválido: `/especialidad/abc` (debería dar error 400)

---

## 📊 Diagrama de Flujo

```
Usuario Frontend
    ↓
Selecciona Especialidad
    ↓
GET /api/disponibilidad/especialidad/{id}
    ↓
DisponibilidadController
    ↓
DisponibilidadService
    ↓
1. EspecialidadesRepository → Buscar especialidad
2. MedicosEspecialidadesRepository → Buscar médicos
3. DiasMedicoRepository → Buscar horarios
    ↓
Construir DTO
    ↓
Retornar JSON
    ↓
Frontend muestra médicos y horarios
```

---

## ✅ Ventajas de esta implementación

1. **Una sola petición**: El frontend obtiene toda la información necesaria
2. **Filtrado automático**: Solo médicos activos y horarios disponibles
3. **Estructura clara**: JSON bien organizado y fácil de consumir
4. **Optimizado**: No incluye médicos sin horarios
5. **Flexible**: Fácil de extender con más filtros (fecha, estado, etc.)

---

## 🔄 Posibles Extensiones Futuras

1. **Filtro por fecha**: `/especialidad/{id}?fecha=2025-01-15`
2. **Filtro por rango**: `/especialidad/{id}?fechaInicio=2025-01-15&fechaFin=2025-01-20`
3. **Incluir citas existentes**: Calcular slots realmente disponibles
4. **Ordenamiento**: Por nombre de médico, día, hora, etc.
5. **Paginación**: Si hay muchos médicos

---

## 📝 Notas Importantes

- El endpoint **NO requiere autenticación** (puede agregarse si es necesario)
- Solo retorna médicos con estado "ACTIVO"
- Solo retorna horarios con estado = 1
- Los días se retornan con su nombre en MAYÚSCULAS (LUNES, MARTES, etc.)
- Las horas están en formato HH:mm:ss
