import org.apache.spark.sql.{SparkSession, Row}
import org.apache.spark.sql.types._
import scala.util.Random
import java.time.LocalDate
import java.time.format.DateTimeFormatter

val spark = SparkSession.builder()
  .appName("Transaction DataFrame")
  .master("local[*]")
  .getOrCreate()

import spark.implicits._

val schema = StructType(Seq(
  StructField("txn_id", StringType, nullable = false),
  StructField("date", StringType, nullable = false),
  StructField("customer_id", IntegerType, nullable = false),
  StructField("product", StringType, nullable = false),
  StructField("amount", DoubleType, nullable = false)
))

val products = Seq("Book", "Laptop", "Phone", "Headphones", "Pen", "Keyboard", "Mouse")

val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
val startDate = LocalDate.parse("2023-01-01", formatter)

val rows = (1 to 50).map { i =>
  val txnId = f"TXN_$i%04d"
  val date = startDate.plusDays(Random.nextInt(365)).format(formatter)
  val customerId = Random.nextInt(7) + 1 // 1 to 7
  val product = products(Random.nextInt(products.length))
  val amount = BigDecimal(10 + Random.nextDouble() * 990).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble

  Row(txnId, date, customerId, product, amount)
}

val df = spark.createDataFrame(spark.sparkContext.parallelize(rows), schema)

df.show(50, truncate = false)
