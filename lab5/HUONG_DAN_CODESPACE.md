# HƯỚNG DẪN TRIỂN KHAI VÀ CHẠY LAB 5 TRÊN GITHUB CODESPACES

Dự án này đã được cấu hình và chuẩn hóa theo đúng mô hình MVC trong tài liệu hướng dẫn của thầy:
- **Model**: `src/main/java/model/Product.java`
- **DAO**: `src/main/java/dao/ProductDAO.java`
- **Context**: `src/main/java/context/DBContext.java` (hỗ trợ cả MySQL và SQL Server)
- **Controller**: `src/main/java/controller/ListProductServlet.java` (đường dẫn `/products`)
- **View**: `src/main/webapp/product.jsp` (sử dụng thẻ thư viện JSTL `<c:forEach>`)
- **Database Script**: `my_sql_northwind.sql` (đã bổ sung View bí danh tương thích)

---

## PHẦN 1: CÁCH ĐƯA SQL VÀ CODE LÊN GITHUB CODESPACES

### Cách 1: Kéo thả trực tiếp (Nhanh nhất nếu Codespaces đang mở)
1. Mở GitHub Codespaces của bạn trên trình duyệt (hoặc qua VS Code).
2. Mở cửa sổ Explorer (bên trái Codespaces).
3. Từ máy tính, kéo toàn bộ các file trong thư mục `D:\lab5` (bao gồm cả file `my_sql_northwind.sql` và `pom.xml`, thư mục `src`) thả trực tiếp vào cây thư mục của Codespaces.

### Cách 2: Đẩy lên GitHub qua giao diện Web
1. Truy cập vào kho chứa (Repository) của bạn trên GitHub (ví dụ: `codespaces-blank` hoặc repo môn học).
2. Bấm vào nút **Add file** -> chọn **Upload files**.
3. Kéo toàn bộ file và thư mục của dự án `lab5` vào đó, rồi bấm **Commit changes**.
4. Mở lại Codespaces (hoặc trong Codespaces gõ `git pull` để đồng bộ code về).

---

## PHẦN 2: CHẠY MYSQL VÀ IMPORT DỮ LIỆU TRÊN CODESPACES

Mở **Terminal** trong Codespaces (bấm `Ctrl + ~` hoặc chọn menu **Terminal -> New Terminal**):

### Bước 1: Khởi chạy MySQL Docker Container
Chạy lệnh sau trong Terminal Codespaces:
```bash
docker run -d \
  --name mysql-db \
  -e MYSQL_ROOT_PASSWORD=Vietnam@123 \
  -p 3306:3306 \
  mysql:8.0
```

> **Lưu ý**:
> - Đợi khoảng 10 - 15 giây để MySQL bên trong container khởi động hoàn tất.
> - Sau này nếu mở lại Codespace mà thấy MySQL bị dừng, chỉ cần gõ: `docker start mysql-db` (không cần cài lại).

### Bước 2: Import Cơ sở dữ liệu Northwind từ file `my_sql_northwind.sql`
Chạy đúng một dòng lệnh sau trong Terminal (đảm bảo file `my_sql_northwind.sql` nằm cùng thư mục hiện tại):
```bash
docker exec -i mysql-db mysql -uroot -pVietnam@123 < my_sql_northwind.sql
```
Lệnh này sẽ tự động tạo database `northwind`, toàn bộ các bảng và nạp đầy đủ dữ liệu demo sản phẩm.

### Bước 3: (Tùy chọn) Kiểm tra kết nối từ VS Code Extension
- Vào tab Extensions (biểu tượng 4 ô vuông bên trái), tìm và cài extension **MySQL** hoặc **Database Client**.
- Tạo kết nối:
  - Host: `127.0.0.1`
  - Port: `3306`
  - User: `root`
  - Password: `Vietnam@123`
  - Database: `northwind`

---

## PHẦN 3: BIÊN DỊCH VÀ CHẠY DỰ ÁN JAVA WEB

### Bước 1: Chạy Server Tomcat
Tại Terminal Codespaces, gõ:
```bash
mvn tomcat7:run
```

### Bước 2: Mở giao diện kiểm tra danh sách sản phẩm
1. Codespace sẽ hiển thị popup ở góc dưới phải: **"Your application running on port 8080 is available"**, bấm **Open in Browser**.
   (Hoặc chuyển sang tab **PORTS** cạnh Terminal, click biểu tượng quả địa cầu ở dòng `8080`).
2. Trình duyệt mở ra tab mới, hãy thêm `/products` vào sau đường link, ví dụ:
   ```
   https://<mã-codespace>-8080.app.github.dev/products
   ```
3. Bạn sẽ thấy bảng danh sách toàn bộ sản phẩm Northwind (ID, Name, Price, Stock) hiển thị đúng như trong bài lab!

---

## PHẦN 4: (TÙY CHỌN) CHUYỂN SANG DÙNG SQL SERVER THEO FILE WORD GỐC

Nếu thầy yêu cầu chạy bằng Microsoft SQL Server thay vì MySQL:
1. Chạy lệnh cài đặt SQL Server Docker trong Codespaces:
   ```bash
   docker run -e "ACCEPT_EULA=Y" \
     -e "SA_PASSWORD=Vietnam@123" \
     -p 1433:1433 \
     -d mcr.microsoft.com/mssql/server:2022-latest
   ```
2. Mở file `src/main/java/context/DBContext.java`, đổi dòng:
   ```java
   public static final boolean USE_MYSQL = true;
   ```
   thành:
   ```java
   public static final boolean USE_MYSQL = false;
   ```
3. Chạy lại: `mvn tomcat7:run` và truy cập `/products`.
