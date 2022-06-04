# 靜流筆記
這是一個為「超異域公主連結 Re:Dive」開發的**非**官方開源免費 Android 應用程式.  
目前已支援日版、陸版資料. 台版資料以及額外功能尚在開發中

## 系統需求
* Android 8+ (由於系統需求，不支援低於 Android 8 的模擬器)

## 組建
需要 Android Studio Arctic Fox 或更高版本.

在組建之前，你可能需要在專案根目錄下新增一个名為 `local.properties` 的檔案 (如未找到).

```sh
$ touch local.properties
```

#### Debug 版本 App
可直接執行以下命令以組建 **Debug 版本** app:

```sh
$ ./gradlew :app:assembleDebug
```

#### Release 版本 App
如果你想組建 Release 版本 App，必須先向 `local.properties` 中新增以下程式碼：

```sh
signing.storeFile=${PATH_TO_YOUR_KEY_STORE_FILE}
signing.storePassword=${YOUR_KEY_STORE_PASSWORD}
signing.keyAlias=${YOUR_KEY_ALIAS}
signing.keyPassword=${YOUR_KEY_PASSWORD}
```

之後即可執行以下命令組建 **Release 版本** App：

```sh
$ ./gradlew :app:assembleRelease
```

## 功能
* 角色資料
    * 角色衍生資料計算
    * Rank 數值對比
* Boss 資料
    * 公會戰 Boss 資料
    * 地下城 Boss 資料
    * 劇情活動 Boss 資料
    * 伺服全體活動 Boss 資料
* 裝備資訊
    * 裝備掉落檢索
* 活動行事曆
    * 活動通知

## 在地化
完整支持中文、日文、英文. 部分支援韓文.  
韓文字串由 [applemintia](https://twitter.com/_applemintia) 提供.  
英文字串由 [southrop](https://github.com/southrop) 和 [MightyZanark](https://github.com/MightyZanark) 提供，喵幻對最終文字進行了一些修改.  
繁體中文字串及改進的多語言支援由喵幻製作.  
歡迎志願者完成英文翻譯.

## 引用與參考
靜流筆記的開發受到了以下專案的重要啟發，感謝這些偉大的先驅者:
* [PrincessGuide](https://github.com/superk589/PrincessGuide)
* [redive_master_db_diff](https://github.com/esterTion/redive_master_db_diff)
* [DereHelper](https://github.com/Lazyeraser/DereHelper)

靜流筆記還使用了[這些](OPENSOURCE.md)開源工具.

## 許可證
使用、改造、分發靜流筆記時請遵循 Apache License 2.0 開源許可證.

## 安裝套件包
[下載頁面](https://github.com/TragicLifeHu/ShizuruNotes/releases)  
