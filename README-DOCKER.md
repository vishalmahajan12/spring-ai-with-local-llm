# Docker Setup Guide

This guide explains how to run the Spring AI with Local LLM application using Docker Compose.

## Prerequisites

- Docker Desktop installed and running
- At least 4GB of RAM available for Docker
- Internet connection (for pulling Ollama model)

## Quick Start

### 1. Build and Start Services

```bash
docker-compose up -d
```

This command will:
- Pull the Ollama image
- Build the Spring Boot application
- Start both services in detached mode

### 2. Pull the Ollama Model

After the services are running, you need to pull the required model:

```bash
docker exec -it ollama ollama pull orieg/gemma3-tools:1b-it-qat
```

Or if you want to use a different model:

```bash
docker exec -it ollama ollama pull gemma3:1b
```

### 3. Verify Services

Check if services are running:

```bash
docker-compose ps
```

View logs:

```bash
# All services
docker-compose logs -f

# Specific service
docker-compose logs -f spring-app
docker-compose logs -f ollama
```

## Access the Application

- **Spring Application**: http://localhost:8085
- **Swagger UI**: http://localhost:8085/swagger-ui.html
- **API Docs**: http://localhost:8085/api-docs
- **Ollama API**: http://localhost:11434

## Testing the API

### Using curl:

```bash
# Simple chat
curl -X POST http://localhost:8085/api/chat \
  -H "Content-Type: application/json" \
  -d '{"message":"Hello, how are you?"}'

# Chat with functions
curl -X POST http://localhost:8085/api/chat-model/with-functions \
  -H "Content-Type: application/json" \
  -d '{"message":"What is the current date and time?"}'
```

### Using Swagger UI:

Navigate to http://localhost:8085/swagger-ui.html and test the endpoints interactively.

## Managing the Application

### Stop Services

```bash
docker-compose down
```

### Stop and Remove Volumes (Complete Cleanup)

```bash
docker-compose down -v
```

### Rebuild the Application

```bash
docker-compose up -d --build
```

### Restart a Specific Service

```bash
docker-compose restart spring-app
```

## Troubleshooting

### Service Not Starting

1. Check logs:
   ```bash
   docker-compose logs spring-app
   docker-compose logs ollama
   ```

2. Verify Ollama health:
   ```bash
   curl http://localhost:11434/
   ```

### Model Not Found Error

If you get a "model not found" error, ensure you've pulled the model:

```bash
docker exec -it ollama ollama pull orieg/gemma3-tools:1b-it-qat
```

### Port Already in Use

If ports 8085 or 11434 are already in use, modify the ports in `docker-compose.yml`:

```yaml
ports:
  - "8086:8085"  # Change host port
```

### Out of Memory

Increase Docker's memory allocation in Docker Desktop settings (recommended: at least 4GB).

## Advanced Configuration

### Using a Different Model

1. Update the model in `docker-compose.yml`:
   ```yaml
   environment:
     - SPRING_AI_OLLAMA_CHAT_OPTIONS_MODEL=your-model-name
   ```

2. Pull the new model:
   ```bash
   docker exec -it ollama ollama pull your-model-name
   ```

3. Restart the Spring application:
   ```bash
   docker-compose restart spring-app
   ```

### Persistent Data

Ollama models are stored in the `ollama_data` volume, so you don't need to re-download them when restarting containers.

### GPU Support (Optional)

If you have an NVIDIA GPU and want to use it with Ollama:

1. Install nvidia-docker2
2. Update the ollama service in docker-compose.yml:
   ```yaml
   ollama:
     image: ollama/ollama:latest
     runtime: nvidia
     environment:
       - NVIDIA_VISIBLE_DEVICES=all
   ```

## Cleanup

To completely remove all containers, networks, and volumes:

```bash
docker-compose down -v
docker rmi spring-ai-with-local-llm-spring-app
docker rmi ollama/ollama:latest
```

