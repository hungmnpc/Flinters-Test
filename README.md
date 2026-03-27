# Flinters Test

Đọc file CSV chứa dữ liệu quảng cáo, tổng hợp thống kê theo campaign và xuất kết quả ra thư mục `results/`.

## Yêu cầu hệ thống

- **Java 17** trở lên
- **Maven 3.8+** (nếu build từ source)

Kiểm tra phiên bản Java:

```bash
java -version
```

## Build từ source

```bash
git clone https://github.com/hungmnpc/Flinters-Test.git
cd Flinters-Test
mvn clean package -DskipTests
```

File JAR sẽ được tạo tại:

```
target/Flinters_Test-1.0-SNAPSHOT.jar
```

## Chạy ứng dụng

```bash
java -jar target/Flinters_Test-1.0-SNAPSHOT.jar --input <đường-dẫn-tới-file-csv>
```

**Ví dụ:**

```bash
java -jar target/Flinters_Test-1.0-SNAPSHOT.jar --input ad_data.csv
```
