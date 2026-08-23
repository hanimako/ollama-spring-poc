# Ollama Java PoC

ローカルLLMをJavaのWebアプリケーションから利用するPoCです。

ユーザーが入力した問い合わせをローカルLLMで解析し、
「カテゴリー」と「優先度」に分類します。

## 主な機能

-   Web画面から問い合わせを入力
-   Ollama APIをJavaから呼び出し
-   Qwen3 4Bによる問い合わせ分類
-   JSON Schemaによる構造化出力
-   問い合わせを以下のカテゴリーに分類
    -   質問
    -   障害
    -   要望
-   優先度を以下の3段階で判定
    -   低
    -   中
    -   高
-   入力値チェック
-   Ollama APIエラー時のエラー表示

## 使用技術

-   Java 21
-   Spring Boot
-   Thymeleaf
-   Jackson
-   Ollama
-   Qwen3 4B
-   Maven

## 構成

``` text
ブラウザ
   ↓
Spring Boot
   ↓
HomeController
   ↓
OllamaService
   ↓
Ollama API
   ↓
Qwen3 4B
   ↓
JSON形式の分類結果
   ↓
Thymeleafで画面表示
```

## 実行方法

Ollamaを起動します。

``` bash
ollama serve
```

別のターミナルでSpring Bootを起動します。

``` bash
./mvnw spring-boot:run
```

ブラウザから以下にアクセスします。

``` text
http://localhost:8080
```

## 判定例

入力：

``` text
一部の社員だけシステムにログインできません。
ログインできない社員は別のPCを使えば業務できます。
```

出力：

``` text
カテゴリー：障害
優先度：中
```

## 目的

ローカルLLMを業務システムから利用する方法を学習するため、
問い合わせ分類を題材として作成しました。

LLM APIの呼び出しだけでなく、プロンプトによる判定基準の指定、
構造化出力、複数のテストケースによる判定結果の確認まで実施しています。
