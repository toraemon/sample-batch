
# CSVからMySQLへのバッチ処理

このJavaアプリケーションは、CSVファイルからデータを読み込み、MySQLデータベースに挿入します。DockerでMySQLをコンテナとして動作させ、Gradleでプロジェクト管理を行います。

## 機能
- OpenCSVを使用してCSVファイルを読み込みます。
- MySQLデータベースにレコードを挿入します。
- DockerでMySQLコンテナを管理します。
- Cronジョブを設定して、自動実行を行えます。

## 前提条件
- Java 17
- Gradle
- Docker & Docker Compose
- Eclipse（オプション）

## プロジェクトセットアップ手順

### 1. リポジトリのクローン
```bash
git clone https://github.com/your/repository.git
cd your-repository
```

### 2. プロジェクトのビルド
Gradleを使用してプロジェクトをビルドします。プロジェクトのルートディレクトリで以下を実行します：
```bash
./gradlew build
```

### 3. DockerでMySQLを実行

1. プロジェクトのルートディレクトリに`docker-compose.yml`ファイルを作成し、以下の内容を追加します：

    ```yaml
    version: '3.8'
    services:
      db:
        image: mysql:8.0
        container_name: mysql_container
        environment:
          MYSQL_ROOT_PASSWORD: rootpassword
          MYSQL_DATABASE: testdb
          MYSQL_USER: user
          MYSQL_PASSWORD: password
        ports:
          - "3306:3306"
        volumes:
          - ./mysql_data:/var/lib/mysql
        command: --default-authentication-plugin=mysql_native_password
    ```

2. MySQLコンテナを起動します：
    ```bash
    docker-compose up -d
    ```

3. コンテナが起動したら、MySQL CLIにアクセスしてテーブルを作成します：
    ```bash
    docker exec -it mysql_container mysql -u root -p
    ```

4. `testdb`データベースに以下のテーブルを作成します：

    ```sql
    USE testdb;
    CREATE TABLE sample_data (
        id INT AUTO_INCREMENT PRIMARY KEY,
        name VARCHAR(100),
        age INT,
        address VARCHAR(255)
    );
    ```

### 4. Javaアプリケーションの設定

`DatabaseInserter.java`ファイルで、Dockerコンテナ上のMySQLに接続できるように接続情報を設定します：

```java
public class DatabaseInserter {
    private static final String URL = "jdbc:mysql://localhost:3306/testdb";
    private static final String USER = "user";
    private static final String PASSWORD = "password";
}
```

### 5. アプリケーションの実行
プロジェクトのルートディレクトリで以下のコマンドを実行して、アプリケーションを起動します：
```bash
./gradlew run
```

### 6. データ挿入の確認
アプリケーション実行後、MySQLコンテナに再度接続して、データが正しく挿入されているか確認します：

```sql
SELECT * FROM sample_data;
```

## Cronジョブの設定

1. バッチ処理を実行するためのスクリプト`run-batch.sh`を作成します：

    ```bash
    #!/bin/bash
    java -jar /path/to/your/project/build/libs/your-project.jar
    ```

2. スクリプトに実行権限を付与します：
    ```bash
    chmod +x run-batch.sh
    ```

3. `crontab`を編集して、バッチジョブをスケジュールします（例: 毎日午前1時に実行）：
    ```bash
    crontab -e
    ```

4. 以下のcronエントリを追加します：
    ```bash
    0 1 * * * /path/to/your/script/run-batch.sh
    ```

## 依存関係

- **OpenCSV**: CSVファイルを読み込むために使用。
- **MySQL JDBC Driver**: MySQLデータベースに接続するために必要。
- **JUnit**: ユニットテストに使用（オプション）。

依存関係は`build.gradle`ファイルで管理されています：

```groovy
dependencies {
    implementation 'mysql:mysql-connector-java:8.0.30'
    implementation 'com.opencsv:opencsv:5.5.2'
    testImplementation 'org.junit.jupiter:junit-jupiter-api:5.7.0'
    testRuntimeOnly 'org.junit.jupiter:junit-jupiter-engine:5.7.0'
}
```

## トラブルシューティング

1. **MySQL接続の問題**:
   Dockerが正常に動作しているか、MySQLコンテナが起動しているか確認してください。

2. **Gradleのビルド問題**:
   Javaのバージョン（Java 17）が正しくインストールされ、Gradleが正しく設定されているか確認してください。

3. **データがMySQLに挿入されない**:
   `testdb`データベースにテーブルが存在するか、CSVファイルの構造が正しいか確認してください。

