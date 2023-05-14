# MajanCalculator

## アプリ概要
翻、符による点数の計算。  
手牌から待ちや点数の計算を行うAndroidアプリ。  

## 機能

### 翻、符から点数計算

- 翻と符を選択
- 親番の有無
- ツモ、ロンの選択
を行い、「=」ボタンを押すと計算された点数が表示される。

![fannfuimage](https://github.com/17043003/MajanCalculator/assets/35760051/506ad67a-957a-47c6-aadd-b43515eaa4ac)

### 待ち牌表示

画面下の麻雀牌から選択した画像で手牌を作り、その待ち牌を表示する。

![waitimage](https://github.com/17043003/MajanCalculator/assets/35760051/ffba784d-37cf-4da2-97b1-9ff62e8cca74)

麻雀牌を長押しすると鳴きの選択ができる。

![openimage](https://github.com/17043003/MajanCalculator/assets/35760051/aa75c218-e908-459b-9132-88dc5aedb33c)

### 手牌から点数計算と役詳細表示

画面下の麻雀牌から手牌を作成。
鳴き牌はトグルボタンが押された状態で麻雀牌をタップ。
場風やドラ、各種役の設定にチェックを入れ、「点数表示」ボタンを押すと別画面に遷移し、点数と役、符の詳細を表示する。

![pointimage](https://github.com/17043003/MajanCalculator/assets/35760051/d0d5b08c-10f4-4d38-b8bc-7aa59d8506ce)

![resultimage](https://github.com/17043003/MajanCalculator/assets/35760051/27c40c0e-80c2-403a-862e-0f0ba92c4fce)

## Development environment
- Android Studio Flamingo 2022.2.1 Patch1
- Gradle 7.4
- Android Gradle Plugin 7.3.1
- JDK 17.0.6
- Kotlin 1.7.10
