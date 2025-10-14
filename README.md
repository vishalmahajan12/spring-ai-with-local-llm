# Spring AI with Local LLM

A Spring Boot application demonstrating integration with local Large Language Models (LLM) using Ollama and Spring AI. This project showcases how to build AI-powered chat applications with function calling capabilities, all running locally without requiring external API keys.

## 🚀 Features

- **Local LLM Integration**: Uses Ollama for running LLMs locally
- **Multiple Chat APIs**: Different endpoints for various use cases
  - Standard chat with ChatClient
  - Chat with ChatModel and detailed metadata
  - Streaming responses
  - Function calling with date/time tools
- **OpenAPI/Swagger Documentation**: Interactive API documentation
- **Docker Support**: Easy deployment with Docker Compose
- **Function Calling**: AI can access custom tools (e.g., getting current date/time)

## 📋 Prerequisites

### For Local Development
- Java 21 or higher
- Maven 3.6+
- [Ollama](https://ollama.ai/) installed and running
- At least 4GB RAM

### For Docker
- Docker Desktop
- Docker Compose
- At least 4GB RAM available for Docker

## 🛠️ Technology Stack

- **Spring Boot 3.5.6** - Application framework
- **Spring AI 1.0.3** - AI integration layer
- **Ollama** - Local LLM runtime
- **SpringDoc OpenAPI** - API documentation
- **Maven** - Build tool

## 📦 Installation & Setup

### Option 1: Local Development

#### 1. Install and Start Ollama

**Windows/Mac:**
```bash
# Download from https://ollama.ai/download
# After installation, Ollama runs automatically
```

**Linux:**
```bash
curl -fsSL https://ollama.ai/install.sh | sh
```

#### 2. Pull the Required Model

```bash
ollama pull orieg/gemma3-tools:1b-it-qat
# Or use a different model:
# ollama pull gemma3:1b
```

#### 3. Clone and Build the Project

```bash
git clone <your-repo-url>
cd spring-ai-with-local-llm
mvn clean install
```

#### 4. Configure Application (Optional)

Edit `src/main/resources/application.properties` if needed:

```properties
server.port=8085
spring.ai.ollama.base-url=http://localhost:11434
spring.ai.ollama.chat.options.model=orieg/gemma3-tools:1b-it-qat
spring.ai.ollama.chat.options.temperature=0.7
spring.ai.ollama.chat.options.tools-enabled=true
```

#### 5. Run the Application

```bash
mvn spring-boot:run
```

Or run the JAR file:

```bash
java -jar target/spring-ai-with-local-llm-0.0.1-SNAPSHOT.jar
```

### Option 2: Docker (Recommended for Quick Start)

#### 1. Start Services

```bash
docker-compose up -d
```

#### 2. Pull the Ollama Model

```bash
docker exec -it ollama ollama pull orieg/gemma3-tools:1b-it-qat
```

#### 3. Verify Services

```bash
docker-compose ps
docker-compose logs -f
```

For detailed Docker instructions, see [README-DOCKER.md](README-DOCKER.md)

## 🌐 Accessing the Application

Once running, access:

- **Application**: http://localhost:8085
- **Swagger UI**: http://localhost:8085/swagger-ui.html
- **OpenAPI Docs**: http://localhost:8085/api-docs
- **Ollama API**: http://localhost:11434

## 📖 API Endpoints

### Chat Endpoints (ChatClient)

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/chat` | Simple chat using ChatClient |
| GET | `/api/chat` | Chat via GET with query parameter |
| POST | `/api/chat/stream` | Streaming chat response |

### ChatModel Endpoints (with metadata)

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/chat-model` | Chat with detailed response metadata |
| GET | `/api/chat-model` | Chat via GET with metadata |
| POST | `/api/chat-model/stream` | Streaming response |
| POST | `/api/chat-model/with-functions` | Chat with function calling enabled |
| GET | `/api/chat-model/with-functions` | Function calling via GET |

## 💡 Usage Examples

### Using cURL

#### Simple Chat

```bash
curl -X POST http://localhost:8085/api/chat \
  -H "Content-Type: application/json" \
  -d '{"message":"What is Spring Boot?"}'
```

#### Chat with Functions (Date/Time)

```bash
curl -X POST http://localhost:8085/api/chat-model/with-functions \
  -H "Content-Type: application/json" \
  -d '{"message":"What is the current date and time?"}'
```

#### GET Request

```bash
curl "http://localhost:8085/api/chat?message=Hello"
```

### Using Swagger UI

1. Navigate to http://localhost:8085/swagger-ui.html
2. Select an endpoint
3. Click "Try it out"
4. Enter your message
5. Click "Execute"

### Response Examples

**Simple Chat Response:**
```json
{
  "response": "Spring Boot is a framework that makes it easy to create production-ready applications..."
}
```

**ChatModel Response (with metadata):**
```json
{
  "response": "The current date and time is January 15, 2024 at 10:30 AM EST.",
  "metadata": {
    "model": "orieg/gemma3-tools:1b-it-qat",
    "finishReason": "STOP",
    "promptTokens": 45,
    "completionTokens": 23,
    "totalTokens": 68
  }
}
```

## 🔧 Configuration Options

### Model Selection

Change the model in `application.properties`:

```properties
spring.ai.ollama.chat.options.model=gemma3:1b
# Other options:
# - llama2
# - mistral
# - codellama
# - neural-chat
```

Don't forget to pull the model:
```bash
ollama pull <model-name>
```

### Temperature

Adjust creativity (0.0 = deterministic, 1.0 = creative):

```properties
spring.ai.ollama.chat.options.temperature=0.7
```

### Server Port

```properties
server.port=8085
```

## 🧪 Testing

Run tests:

```bash
mvn test
```

## 📊 Available Models

Common Ollama models you can use:

- `gemma3:1b` - Lightweight, fast
- `gemma3-tools:1b-it-qat` - Optimized with function calling
- `llama2` - Meta's Llama 2
- `mistral` - Mistral AI's model
- `codellama` - Specialized for code
- `neural-chat` - Intel's model

List all available models:
```bash
ollama list
```

## 🐛 Troubleshooting

### Ollama Connection Error

**Problem**: `Connection refused to localhost:11434`

**Solution**:
```bash
# Check if Ollama is running
ollama list

# Restart Ollama (Mac/Windows)
# Close and reopen Ollama application

# Linux
sudo systemctl restart ollama
```

### Model Not Found

**Problem**: `model "xyz" not found`

**Solution**:
```bash
ollama pull <model-name>
```

### Out of Memory

**Problem**: Application crashes or model doesn't load

**Solution**:
- Use a smaller model (e.g., gemma3:1b instead of larger models)
- Increase available RAM
- For Docker: Increase Docker's memory limit in Docker Desktop settings

### Port Already in Use

**Problem**: Port 8085 is already in use

**Solution**: Change the port in `application.properties`:
```properties
server.port=8086
```

## 📁 Project Structure

```
spring-ai-with-local-llm/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/spring_ai_with_local_llm/
│   │   │       ├── config/          # Configuration classes
│   │   │       ├── controller/      # REST controllers
│   │   │       ├── dto/             # Data Transfer Objects
│   │   │       ├── function/        # Custom function implementations
│   │   │       ├── service/         # Business logic
│   │   │       └── tools/           # Tool definitions for AI
│   │   └── resources/
│   │       └── application.properties
│   └── test/                        # Test classes
├── docker-compose.yml               # Docker Compose configuration
├── Dockerfile                       # Docker build instructions
├── pom.xml                          # Maven dependencies
├── README.md                        # This file
└── README-DOCKER.md                 # Docker-specific documentation
```

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## 📝 License

This project is open source and available under the [MIT License](LICENSE).

## 🔗 Resources

- [Spring AI Documentation](https://docs.spring.io/spring-ai/reference/)
- [Ollama Documentation](https://ollama.ai/docs)
- [Spring Boot Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [SpringDoc OpenAPI](https://springdoc.org/)

## 📧 Support

For issues and questions:
- Open an issue in this repository
- Check the troubleshooting section above
- Review Ollama and Spring AI documentation

## 🎯 Future Enhancements

- [ ] Add more custom functions (weather, search, etc.)
- [ ] Implement conversation history/context
- [ ] Add authentication and rate limiting
- [ ] Support for image generation models
- [ ] Vector database integration for RAG
- [ ] Multiple model support simultaneously
- [ ] WebSocket support for real-time streaming
- [ ] Frontend UI for interactive chat

---

**Happy Coding! 🚀**

