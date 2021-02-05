## devcontainer.json
- extensions
    |拡張機能名|概要|公式URL|
    |---|---|---|
    |johnpapa.angular-essentials|Angular開発向け拡張機能全部入り|https://marketplace.visualstudio.com/items?itemName=johnpapa.angular-essentials|
    |vscjava.vscode-java-pack|インテリセンス等、VSCode向けJava拡張|https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-pack|
    |adashen.vscode-tomcat|Tomcat拡張機能|https://github.com/adashen/vscode-tomcat|
    |shengchen.vscode-checkstyle|VSCodeでCheckStyleを適用する|https://marketplace.visualstudio.com/items?itemName=shengchen.vscode-checkstyle|
    |pivotal.vscode-spring-boot|SpringBoot用拡張機能|https://marketplace.visualstudio.com/items?itemName=Pivotal.vscode-spring-boot|
    |pivotal.vscode-boot-dev-pack|SpringBoot開発パッケージ|https://marketplace.visualstudio.com/items?itemName=Pivotal.vscode-boot-dev-pack|
    |richardwillis.vscode-gradle|Gradleタスク実行|https://marketplace.visualstudio.com/items?itemName=richardwillis.vscode-gradle|
    |naco-siren.gradle-language|gradle入力サポート|https://marketplace.visualstudio.com/items?itemName=naco-siren.gradle-language|
    |donjayamanne.git-extension-pack|Git拡張セット|https://marketplace.visualstudio.com/items?itemName=donjayamanne.git-extension-pack|
- Dockerfile
    - 基本イメージ：alpine + AdoptOpenJDK11
    - 共通設定：日本語、日本時間ローカライズ
    - インストール
        - npm
