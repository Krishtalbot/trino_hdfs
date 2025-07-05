docker exec -it spark-master spark-shell \
  --master spark://spark-master:7077 \
  --packages org.apache.iceberg:iceberg-spark-runtime-3.4_2.12:1.4.2 \
  --conf spark.sql.extensions=org.apache.iceberg.spark.extensions.IcebergSparkSessionExtensions \
  --conf spark.sql.catalog.spark_catalog=org.apache.iceberg.spark.SparkSessionCatalog \
  --conf spark.sql.catalog.spark_catalog.type=rest \
  --conf spark.sql.catalog.spark_catalog.uri=http://iceberg-rest:8181 \
  --conf spark.serializer=org.apache.spark.serializer.KryoSerializer