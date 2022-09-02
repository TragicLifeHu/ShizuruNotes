# 靜流筆記
這是一個為「超異域公主連結 Re:Dive」開發的**非**官方開源免費 Android 應用程式.  
目前已支援日版、台版、陸版資料. 額外功能尚在開發中

## 系統需求
* Android 8+ (由於系統需求，不支援低於 Android 8 的模擬器)

## 組建
最好使用 Android Studio Chipmunk (2021.2.1) 或更高版本.  
本專案已無法輕易使用 Eclipse 進行後續開發作業.  

在組建之前，你可能需要在專案根目錄下新增一个名為 `local.properties` 的檔案 (如未找到).

```sh
$ touch local.properties
```

### Debug 版本 App
可直接執行以下命令以組建 **Debug 版本** app:

```sh
$ ./gradlew :app:assembleDebug
```

### Release 版本 App
由於專案結構對比原版進行修改，請使用 Android Studio `Build` -> `Generate Signed Bundle / APK` 功能完成本作業，   
因本人能力問題造成的不便，敬請諒解.  

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

## 與原版相比的改變
* 100% Kotlin 編寫  
* 改進的角色搜尋功能  
* 可以儲存角色卡面圖像至裝置內部儲存空間  
* 可以切換顯示角色聲優名和本名  
* 可以切換至跟隨系統語言模式  
* 支援台版伺服器資料  

## 在地化
完整支持中文、日文、英文. 部分支援韓文.  
韓文字串由 [applemintia](https://twitter.com/_applemintia) 提供.  
英文字串由 [southrop](https://github.com/southrop) 和 [MightyZanark](https://github.com/MightyZanark) 提供，喵幻對最終文字進行了一些修改.  
繁體中文字串及改進的多語言支援由喵幻製作.  
歡迎志願者完成和改善英文翻譯.

## 引用與參考
靜流筆記的開發受到了以下專案的重要啟發，感謝這些偉大的先驅者:
* [PrincessGuide](https://github.com/superk589/PrincessGuide)
* [redive_master_db_diff](https://github.com/esterTion/redive_master_db_diff)
* [DereHelper](https://github.com/Lazyeraser/DereHelper)
* 來自 StackOverFlow 的零散程式碼片段之作者們

靜流筆記還使用了[這些](OPENSOURCE.md)開源工具.

## 許可證
使用、改造、分發靜流筆記時請遵循 Apache License 2.0 開源許可證.

## 安裝套件包
[下載頁面](https://github.com/TragicLifeHu/ShizuruNotes/releases)  

## 支援開發
本專案因作為二次開發作品，暫不接受捐贈.  