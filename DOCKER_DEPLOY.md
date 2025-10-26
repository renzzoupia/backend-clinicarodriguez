# 🐳 Despliegue con Docker

## 📋 Requisitos previos
- Docker instalado
- Cuenta en plataforma de hosting (Railway, Render, Fly.io, etc.)
- Base de datos MySQL accesible (ya configurada en Railway)

## 🏗️ Construcción de la imagen

### Local (para pruebas)
```bash
docker build -t clinicarodriguez-backend .
```

### Para plataformas de demo (Render, Railway, etc.)
La mayoría de plataformas construyen automáticamente desde el Dockerfile.

## 🚀 Ejecución local

### Usando el Dockerfile
```bash
docker run -p 8080:8080 \
  -e DATABASE_URL="jdbc:mysql://hopper.proxy.rlwy.net:54881/railway" \
  -e DATABASE_USERNAME="root" \
  -e DATABASE_PASSWORD="bqzullTNaujzuCmFibynLCceaDtWibuq" \
  -e JWT_SECRET="ClaveSecretaClinicaRodriguezGrupoPerfilDeEgreso5toanio123456789" \
  clinicarodriguez-backend
```

### Usando Docker Compose (opcional)
```bash
docker-compose up
```

## 🌐 Variables de entorno necesarias

Estas variables deben configurarse en tu plataforma de hosting:

| Variable | Descripción | Ejemplo |
|----------|-------------|---------|
| `DATABASE_URL` | URL de conexión MySQL | `jdbc:mysql://host:port/database` |
| `DATABASE_USERNAME` | Usuario de la BD | `root` |
| `DATABASE_PASSWORD` | Contraseña de la BD | `tu_password_seguro` |
| `JWT_SECRET` | Clave secreta para JWT | `ClaveSecreta123...` |
| `JWT_EXPIRATION` | Tiempo de expiración (ms) | `86400000` (24h) |
| `PORT` | Puerto del servidor | `8080` (default) |
| `UPLOAD_DIR` | Directorio de uploads | `./uploads` (default) |

## 📦 Despliegue en plataformas populares

### 🚂 Railway
1. Conecta tu repositorio GitHub
2. Railway detecta automáticamente el Dockerfile
3. Configura las variables de entorno en el panel
4. Deploy automático

### 🎨 Render
1. Conecta tu repositorio GitHub
2. Selecciona "Docker" como tipo de servicio
3. Configura las variables de entorno
4. Deploy automático

### ✈️ Fly.io
```bash
# Instalar flyctl
flyctl launch

# Configurar variables
flyctl secrets set DATABASE_URL="jdbc:mysql://..."
flyctl secrets set DATABASE_USERNAME="root"
flyctl secrets set DATABASE_PASSWORD="..."

# Deploy
flyctl deploy
```

### 🐙 Azure Container Instances
```bash
az container create \
  --resource-group myResourceGroup \
  --name clinicarodriguez-backend \
  --image tu-registry/clinicarodriguez-backend:latest \
  --dns-name-label clinicarodriguez \
  --ports 8080 \
  --environment-variables \
    DATABASE_URL='jdbc:mysql://...' \
    DATABASE_USERNAME='root' \
    DATABASE_PASSWORD='...'
```

## 🔍 Verificación

Después del despliegue, verifica que la API esté funcionando:

```bash
# Health check
curl https://tu-dominio.com/api/auth/login

# Swagger UI (si está habilitado)
https://tu-dominio.com/swagger-ui.html
```

## 📊 Logs y debugging

```bash
# Ver logs del contenedor
docker logs -f container_id

# En Railway/Render
# Los logs están disponibles en el dashboard
```

## 🔐 Seguridad

**IMPORTANTE:** 
- ❌ NO subas credenciales al repositorio
- ✅ Usa variables de entorno
- ✅ Cambia el `JWT_SECRET` en producción
- ✅ Usa contraseñas fuertes para la BD
- ✅ Habilita HTTPS en producción

## 🐛 Troubleshooting

### La aplicación no se conecta a la BD
- Verifica que la BD sea accesible desde internet
- Confirma las credenciales
- Revisa los logs: `docker logs container_id`

### Error de puerto
- Asegúrate que la variable `PORT` esté configurada
- Algunas plataformas (Heroku, Railway) asignan el puerto automáticamente

### Archivos no se guardan
- Verifica que el directorio `uploads/` tenga permisos
- En producción, considera usar almacenamiento en la nube (S3, Cloudinary)

## 📝 Notas adicionales

### Optimizaciones de producción
El Dockerfile ya incluye:
- ✅ Multi-stage build (imagen ligera)
- ✅ Usuario no-root (seguridad)
- ✅ Cache de dependencias Maven
- ✅ Compresión de respuestas
- ✅ Límites de memoria configurables

### Tamaño de imagen
- Imagen final: ~250-300 MB
- Tiempo de build: 3-5 minutos (primera vez)
- Builds subsecuentes: 30-60 segundos (con cache)

### Monitoreo
Considera agregar:
- Spring Boot Actuator para métricas
- Logging centralizado (ELK, Splunk)
- Alertas de errores (Sentry)
