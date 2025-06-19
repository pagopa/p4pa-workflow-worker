# p4pa-workflow-worker

This application belong to the **batch** tier of the **Piattaforma Unitaria** product.

See [PU Microservice Architecture](https://pagopa.atlassian.net/wiki/spaces/SPAC/pages/1405845916/Architettura+microservizi) for more details.

## 🧱 Role

* To execute workflow activities through [Temporal.io](https://temporal.io/);
  * See [Temporal.io Confluence page](https://pagopa.atlassian.net/wiki/spaces/SPAC/pages/1776189802/Temporal.io) for details on its usage;
  * See [Workflow Confluence page](https://pagopa.atlassian.net/wiki/spaces/SPAC/pages/1287356459/Workflow) for implemented Workflows;
  * It will register the activities implemented on [p4pa-payhub-activities](https://github.com/pagopa/p4pa-payhub-activities) to Temporal.io task queue:
    * See `spring.temporal.workers.*` properties on [application.yml](src/main/resources/application.yml).

## 🔎 Monitoring
See available actuator endpoints through the following path:
* `/actuator`

### 📌 Relevant endpoints
* Health (provide an accessToken to see details): `/actuator/health`
  * Liveness: `/actuator/health/liveness`
  * Readiness: `/actuator/health/readiness`
* Metrics: `/actuator/metrics`
  * Prometheus: `/actuator/prometheus`

Further endpoints are exposed through the JMX console.

## ✏️ Logging
See [log configured pattern](/src/main/resources/logback-spring.xml).

## 🔗 Dependencies

### 🗄️ Resources
* Temporal.io
* See Resources declared on [p4pa-payhub-activities](https://github.com/pagopa/p4pa-payhub-activities)

## 🔧 Configuration

* See [application.yml](src/main/resources/application.yml) for each configurable property;
* See [pa4pa-payhub-activities application.yml](https://github.com/pagopa/p4pa-payhub-activities/blob/main/src/main/resources/config/application.yml) for additional configurable properties.

### 📌 Relevant configurations
See also relevant configurations documented on [pa4pa-payhub-activities](https://github.com/pagopa/p4pa-payhub-activities).

#### 🌐 Application Server
| ENV         | DESCRIPTION                       | DEFAULT |
|-------------|-----------------------------------|---------|
| SERVER_PORT | Application server listening port | 8080    |

#### ✏️ Logging
| ENV                                   | DESCRIPTION                                                                                                                                                                     | DEFAULT |
|---------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|---------|
| LOG_LEVEL_ROOT                        | Base level                                                                                                                                                                      | INFO    |
| LOG_LEVEL_PAGOPA                      | Base level of custom classes                                                                                                                                                    | INFO    |
| LOG_LEVEL_SPRING                      | Level applied to Spring framework                                                                                                                                               | INFO    |
| LOG_LEVEL_SPRING_BOOT_AVAILABILITY    | To print availability events                                                                                                                                                    | DEBUG   |
| LOGGING_LEVEL_API_REQUEST_EXCEPTION   | Level applied to APIs exception                                                                                                                                                 | INFO    |
| LOG_LEVEL_PERFORMANCE_LOG             | Level applied to [PerformanceLog](https://pagopa.atlassian.net/wiki/spaces/SPAC/pages/1540096383/Logging#2.2.-Log-di-performance)                                               | INFO    |
| LOG_LEVEL_PERFORMANCE_LOG_API_REQUEST | Level applied to [API Performance Log](https://pagopa.atlassian.net/wiki/spaces/SPAC/pages/1540096383/Logging#2.2.2.1.-Log-di-perfomance-per-le-API)                            | INFO    |
| LOG_LEVEL_PERFORMANCE_LOG_REST_INVOKE | Level applied to [REST invoke Performance Log](https://pagopa.atlassian.net/wiki/spaces/SPAC/pages/1540096383/Logging#2.2.2.2.-Log-di-performance-per-i-servizi-REST-integrati) | INFO    |

#### 🔁 Integrations

##### 🕒 Temporal.io
| ENV                                                       | DESCRIPTION                                                            | DEFAULT   |
|-----------------------------------------------------------|------------------------------------------------------------------------|-----------|
| TEMPORAL_SERVER_HOST                                      | Temporal hostname                                                      | localhost |
| TEMPORAL_SERVER_PORT                                      | Temporal port                                                          | 7233      |
| TEMPORAL_SERVER_ENABLE_HTTPS                              | To use HTTPS when invoking Temporal                                    | false     |
| TEMPORAL_SERVER_NAMESPACE                                 | Temporal namespace                                                     | pu        |

###### 📥 TaskQueue poller sizes
| ENV                                           | DESCRIPTION                                                                 | DEFAULT |
|-----------------------------------------------|-----------------------------------------------------------------------------|---------|
| WF_LOW_PRIORITY_POLLER_SIZE                   | Poller size configured for Temporal task queue `LowPriorityWF`              | 3       |
| WF_DP_LOW_PRIORITY_POLLER_SIZE                | Poller size configured for Temporal task queue `DebtPositionWF`             | 3       |
| WF_DP_RESERVED_SYNC_POLLER_SIZE               | Poller size configured for Temporal task queue `DebtPositionSyncWF`         | 10      |
| WF_DP_RESERVED_CUSTOM_SYNC_POLLER_SIZE        | Poller size configured for Temporal task queue `DebtPositionCustomSyncWF`   | 5       |
| WF_IMPORT_MEDIUM_PRIORITY_POLLER_SIZE         | Poller size configured for Temporal task queue `IngestionFlowFileWF`        | 3       |
| WF_EXPORT_MEDIUM_PRIORITY_POLLER_SIZE         | Poller size configured for Temporal task queue `ExportFileWF`               | 3       |
| WF_CLASSIFICATION_MEDIUM_PRIORITY_POLLER_SIZE | Poller size configured for Temporal task queue `ClassificationWF`           | 3       |
| WF_SEND_RESERVED_NOTIFICATION_POLLER_SIZE     | Poller size configured for Temporal task queue `SendNotificationProcessWF`  | 3       |
| WF_SEND_LOW_PRIORITY_POLLER_SIZE              | Poller size configured for Temporal task queue `SendWF`                     | 3       |
| WF_ASSESSMENTS_RESERVED_CREATION_POLLER_SIZE  | Poller size configured for Temporal task queue `AssessmentCreationWF`       | 5       |
| WF_ASSESSMENTS_POLLER_SIZE                    | Poller size configured for Temporal task queue `AssessmentsWF`              | 5       |

#### 🔑 keys
| ENV                                  | DESCRIPTION                                                                              | DEFAULT |
|--------------------------------------|------------------------------------------------------------------------------------------|---------|
| JWT_TOKEN_PUBLIC_KEY                 | p4pa-auth JWT public key                                                                 |         |

## 🛠️ Getting Started

### 📝 Prerequisites

Ensure the following tools are installed on your machine:

1. **Java 21+**
2. **Gradle** (or use the Gradle wrapper included in the repository)
3. **Docker** (to build and run on an isolated environment, optional)
4. **GITHUB_TOKEN environment variable**

### 🔐 Write Locks

```sh
./gradlew dependencies --write-locks
```

### ⚙️ Build

```sh
./gradlew clean build
```

### 🧪 Test

#### 📌 JUnit
```sh
./gradlew test
```

### 🚀 Run local

```sh
./gradlew bootRun
```

### 🐳 Build & run through Docker
```sh
docker build -t <APP_NAME> .
docker run --env-file <ENV_FILE> <APP_NAME>
```
