# jwt-token-oauth2
Spring Oauth2 JWT Token Project

mvn clean install

liquibase:dropAll liquibase:update -Dliquibase.contexts=sql,local,data,test-data,prod-data
