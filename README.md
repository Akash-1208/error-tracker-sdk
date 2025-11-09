# 🐞 Error Tracker SDK (Java)

A lightweight Java SDK to capture exceptions and send them to an Error Tracker backend.

[![Release](https://img.shields.io/github/v/release/yourname/error-tracker-sdk)](https://github.com/yourname/error-tracker-sdk/releases)
[![License: MIT](https://img.shields.io/badge/License-MIT-green.svg)](LICENSE)

## ✨ What it does
- Intercepts exceptions (via AOP/annotation)
- Serializes error details (message, stacktrace, timestamp, context)
- Sends to your backend endpoint (Spring Boot in v1, cloud in v2)


## 🚀 Quick Start

1. **Download the JAR** from the [Releases page](https://github.com/yourname/error-tracker-sdk/releases).
2. **Add to your project**:

**Gradle**
```groovy
// Place JAR in your app's /libs folder
implementation files('libs/error-tracker-sdk-1.0.0.jar')
```
**Maven**
```groovy
<dependency>
    <groupId>com.techsavvy</groupId>
	<artifactId>Track-Error</artifactId>
     <version>1.0.0</version>
</dependency>
```

3. **Annotate Methods**

```groovy
public class Demo {
@TrackError
public void risky() {
int x = 1 / 0; // captured
}
}
```