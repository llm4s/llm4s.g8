# LLM4S Giter8 Template

Official [Giter8](http://www.foundweekends.org/giter8/) template for creating [LLM4S](https://github.com/llm4s/llm4s) projects.

## Usage

### Create a new LLM4S project

To create a new project using the latest template version:

```bash
sbt new llm4s/llm4s.g8
```

You will be prompted for various parameters:

- `name`: Project name (default: `llm4s-template`)
- `package`: Package name (default: `org.llm4s.template`)
- `version`: Initial project version (default: `0.1.0-SNAPSHOT`)
- `llm4s_version`: LLM4S library version (default: `0.1.1`)
- `scala_version`: Scala version (default: `2.13.16`)
- `java_version`: Java version (default: `21`)

### Using a specific template version

To use a specific version of the template:

```bash
sbt new llm4s/llm4s.g8.git#v1.0.0
```

### Non-interactive mode

You can also provide parameters directly:

```bash
sbt new llm4s/llm4s.g8 \
  --name=my-llm-project \
  --package=com.example.llm \
  --llm4s_version=0.1.1 \
  --scala_version=2.13.16
```

## Template Features

This template creates a complete LLM4S project with:

- ✅ Pre-configured `build.sbt` with LLM4S dependencies
- ✅ Example application demonstrating LLM4S usage
- ✅ Test setup with Munit
- ✅ Scalafmt configuration
- ✅ GitHub Actions CI/CD workflows
- ✅ Environment configuration support
- ✅ Pre-commit hooks for code quality

## Project Structure

```
my-llm-project/
├── .github/
│   └── workflows/
│       └── ci.yml           # CI/CD configuration
├── src/
│   ├── main/
│   │   └── scala/
│   │       └── com/example/
│   │           └── Main.scala    # Example application
│   └── test/
│       └── scala/
│           └── com/example/
│               └── MainSuite.scala # Example tests
├── .env.example              # Environment variables template
├── .gitignore
├── .scalafmt.conf           # Code formatting rules
├── build.sbt                # SBT build configuration
├── project/
│   └── build.properties
└── README.md                # Project documentation
```

## Version Compatibility

| Template Version | LLM4S Versions | Scala Versions |
|-----------------|----------------|----------------|
| 1.0.x           | 0.1.0 - 0.1.x  | 2.13.16, 3.7.1 |

## Development

### Running Tests

To test the template locally:

```bash
sbt test
```

This will:
1. Generate a project from the template
2. Compile the generated project
3. Run tests in the generated project

### Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

This template is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Support

For issues, questions, or contributions, please visit:
- [Template Repository](https://github.com/llm4s/llm4s.g8)
- [LLM4S Framework](https://github.com/llm4s/llm4s)
- [Documentation](https://llm4s.org)