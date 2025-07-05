docker exec -it spark-master spark-shell \
  --master spark://spark-master:7077 \
  --packages org.apache.iceberg:iceberg-spark-runtime-3.4_2.12:1.4.2 \
  --conf spark.sql.extensions=org.apache.iceberg.spark.extensions.IcebergSparkSessionExtensions \
  --conf spark.sql.catalog.spark_catalog=org.apache.iceberg.spark.SparkCatalog \
  --conf spark.sql.catalog.spark_catalog.type=rest \
  --conf spark.sql.catalog.spark_catalog.uri=http://iceberg-rest:8181 \
  --conf spark.sql.catalogImplementation=in-memory \
  --conf spark.serializer=org.apache.spark.serializer.KryoSerializer

# Step-by-step Iceberg workflow:
# 1. Check current namespaces (will be empty initially)
# spark.sql("SHOW NAMESPACES").show()

# 2. Create a default namespace 
# spark.sql("CREATE NAMESPACE IF NOT EXISTS default")

# 3. Create other namespaces as needed
# spark.sql("CREATE NAMESPACE IF NOT EXISTS analytics")
# spark.sql("CREATE NAMESPACE IF NOT EXISTS test")

# 4. Verify namespaces were created
# spark.sql("SHOW NAMESPACES").show()

# 5. Use a namespace and create tables
# spark.sql("USE default")
# spark.sql("CREATE TABLE sample_data (id INT, name STRING, value DOUBLE) USING ICEBERG")
# spark.sql("INSERT INTO sample_data VALUES (1, 'Alice', 100.5), (2, 'Bob', 200.7)")
# spark.sql("SELECT * FROM sample_data").show()

# 6. Show tables in current namespace
# spark.sql("SHOW TABLES").show()
