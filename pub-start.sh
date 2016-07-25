DATE=`date "+%Y-%m-%d"`
mvn spring-boot:run &> log.${DATE}.term
#java -jar target/currency-fair-api-0.0.1-SNAPSHOT.jar &> log.term
