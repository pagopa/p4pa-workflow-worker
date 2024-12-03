locals {
  # Common Tags:
  common_tags = {
    CreatedBy   = "Terraform"
    Environment = var.env
    Owner       = upper(var.prefix)
    Source      = "https://github.com/pagopa/p4pa-workflow-worker" # Repository URL
    CostCenter  = "TS310 - PAGAMENTI & SERVIZI"
  }

  # Repo
  github = {
    org        = "pagopa"
    repository = "p4pa-workflow-worker" # Repository Name
  }

  env_secrets   = {}
  env_variables = {}

  repo_secrets = var.env_short == "p" ? {
    SONAR_TOKEN       = data.azurerm_key_vault_secret.sonar_token[0].value
    SLACK_WEBHOOK_URL = data.azurerm_key_vault_secret.slack_webhook[0].value
  } : {}

  repo_env = var.env_short == "p" ? {
    SONARCLOUD_PROJECT_NAME = "p4pa-workflow-worker"
    SONARCLOUD_PROJECT_KEY  = "pagopa_p4pa-workflow-worker"
    SONARCLOUD_ORG          = "pagopa"
  } : {}

  map_repo = {
    "dev" : "*",
    "uat" : "uat"
    "prod" : "main"
  }
}
