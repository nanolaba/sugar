<!--@nrg.languages=en,ru-->
<!--@nrg.defaultLanguage=en-->

<!--@groupId=com.nanolaba-->
<!--@artifactId=sugar-->
<!--@version=1.0-->
<!--@repoUrl=https://github.com/nanolaba/sugar-->

${widget:languages}

# Sugar

A tiny, zero-dependency Java library of static syntactic-sugar helpers — one class, one purpose: cut the boilerplate out of everyday Java.<!--en-->
Крошечная Java-библиотека без зависимостей со статическими синтаксическими хелперами — один класс, одна цель: убрать шаблонный код из повседневной Java.<!--ru-->

The whole library is a single class, `com.nanolaba.sugar.Code`, with helpers for the most common friction points: running lambdas that throw checked exceptions, multi-value equality checks, and lazy memoization.<!--en-->
Вся библиотека — это единственный класс `com.nanolaba.sugar.Code` с хелперами для самых частых неудобств: запуск лямбд с проверяемыми исключениями, проверка равенства сразу с несколькими значениями и ленивая мемоизация.<!--ru-->

${widget:tableOfContents(title = "${en:'Table of contents', ru:'Содержание'}", ordered = "true")}

## ${en:'Requirements', ru:'Требования'}

- Java 8+
- Maven 3.x ${en:'(to build from source)', ru:'(для сборки из исходников)'}

## ${en:'Installation', ru:'Установка'}

Released artifacts are published to **Maven Central** — no extra repository configuration is required.<!--en-->
Релизные артефакты публикуются в **Maven Central** — никаких дополнительных репозиториев подключать не нужно.<!--ru-->

**Maven**

```xml

<dependency>
    <groupId>${groupId}</groupId>
    <artifactId>${artifactId}</artifactId>
    <version>${version}</version>
</dependency>
```

**Gradle**

```groovy
dependencies {
    implementation 'com.nanolaba:sugar:${version}'
}
```

### ${en:'Snapshot versions', ru:'SNAPSHOT-версии'}

To use a development snapshot, add the Sonatype Central snapshot repository:<!--en-->
Чтобы использовать разрабатываемую snapshot-версию, подключите snapshot-репозиторий Sonatype Central:<!--ru-->

```xml

<repositories>
    <repository>
        <id>central.sonatype.com-snapshot</id>
        <url>https://central.sonatype.com/repository/maven-snapshots</url>
        <snapshots>
            <enabled>true</enabled>
            <updatePolicy>always</updatePolicy>
        </snapshots>
    </repository>
</repositories>
```

## ${en:'Usage', ru:'Использование'}

All helpers are `static` methods on `Code`. A static import keeps call sites tidy:<!--en-->
Все хелперы — статические методы класса `Code`. Статический импорт делает код на стороне вызова лаконичным:<!--ru-->

```java
import static com.nanolaba.sugar.Code.*;
```

### `run` — ${en:'checked exceptions out of the way', ru:'убираем проверяемые исключения'}

Wraps a lambda that may throw a checked `Exception`. Any exception is rethrown as a `RuntimeException`; unchecked exceptions and `Error`s pass through unchanged.<!--en-->
Оборачивает лямбду, которая может бросить проверяемое `Exception`. Любое исключение перебрасывается как `RuntimeException`; непроверяемые исключения и `Error`-ы проходят без изменений.<!--ru-->

```java
// With a return value
byte[] content = run(() -> Files.readAllBytes(path));

// Side effect only
run(() ->Thread.

sleep(100));
```

### `runQuietly` — ${en:'swallow, or fall back to a default', ru:'поглощаем исключение или возвращаем значение по умолчанию'}

Swallows any exception from the lambda. The overload with a default `Supplier` returns that default when an exception occurs — the supplier is only evaluated on failure.<!--en-->
Поглощает любое исключение из лямбды. Перегрузка с дефолтным `Supplier`-ом возвращает значение по умолчанию при исключении — поставщик вычисляется только при сбое.<!--ru-->

```java
// Fall back to a default
int port = runQuietly(() -> Integer.parseInt(System.getenv("PORT")), () -> 8080);

// Fire-and-forget
runQuietly(() ->socket.

close());
```

### `equalsAny` — ${en:'null-safe multi-value equality', ru:'null-безопасное равенство с любым из значений'}

Returns `true` if the first argument equals any of the following ones, using `Objects.equals` (so `null` is compared safely).<!--en-->
Возвращает `true`, если первый аргумент равен любому из последующих, по `Objects.equals` (т.е. `null` сравнивается безопасно).<!--ru-->

```java
if(equalsAny(status, "OK","READY","IDLE")){
        // ...
        }
```

### `memoize` — ${en:'lazy, thread-safe, compute-at-most-once', ru:'ленивая, потокобезопасная мемоизация'}

Wraps a `Supplier<T>` so its value is computed at most once. The first `get()` is `synchronized`; afterwards the delegate replaces itself and every subsequent call is a plain field read with no locking.<!--en-->
Оборачивает `Supplier<T>` так, чтобы значение вычислялось не более одного раза. Первый вызов `get()` синхронизирован; затем делегат заменяет сам себя, и все последующие обращения — обычное чтение поля без блокировок.<!--ru-->

```java
Supplier<Config> config = memoize(() -> loadConfigFromDisk());
config.

get(); // reads from disk
config.

get(); // cached, no lock
```

## ${en:'Building from source', ru:'Сборка из исходников'}

```bash
mvn clean install
```

The build runs PIT mutation testing with a 100% threshold — any surviving mutant fails the build. When adding code, expect to cover every branch and boundary with assertions.<!--en-->
При сборке запускается PIT mutation testing со 100%-м порогом — любой выживший мутант ломает сборку. При добавлении кода потребуется покрыть все ветви и граничные случаи ассертами.<!--ru-->

## ${en:'Links', ru:'Ссылки'}

- ${en:'Source code', ru:'Исходный код'}: [${repoUrl}](${repoUrl})
- ${en:'Issue tracker', ru:'Багтрекер'}: [${repoUrl}/issues](${repoUrl}/issues)
- ${en:'Maven Central', ru:'Maven Central'}: [central.sonatype.com/artifact/${groupId}/${artifactId}](https://central.sonatype.com/artifact/${groupId}/${artifactId})

## ${en:'License', ru:'Лицензия'}

Released under the [Apache License 2.0](LICENSE).<!--en-->
Распространяется под лицензией [Apache License 2.0](LICENSE).<!--ru-->

---
*${en:'Generated with', ru:'Сгенерировано с помощью'} [nanolaba/readme-generator](https://github.com/nanolaba/readme-generator) — ${widget:date(pattern = 'dd.MM.yyyy')}*
