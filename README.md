[中文說明請點選此處](README_TW.md)

# ShizuruNotes
An **unofficial** Android tool application for "Princess Connect Re:Dive".  
It supports the server of Japan, Taiwan and Mainland China at present.

## Requirement
* Android 8+ (Doesn't support Android Emulator lower than Android 8)

## Build
Better to use Android Studio Chipmunk (2021.2.1) or later.  

Before executing build commands, you need to create a `local.properties` in project's root directory if it does not exist.

```sh
$ touch local.properties
```

### Debug Version App
You can build app with **debug variant** by executing the following command directly. 

```sh
$ ./gradlew :app:assembleDebug
```

### Release Version App
Use Android Studio's "Generate Signed Bundle / APK" function to complete this step,  
due to different structure between this repo and the original one.

## Features
* Character 
* Character Derivative Status & calculation 
* Clan Battle Boss 
* Dungeon 
* Equipment 
* Equipment Drop Search 
* Event Boss 
* Event Calendar 
* Event Notification 
* Rank Comparison   

## Differences between the [Original Repo](https://github.com/MalitsPlus/ShizuruNotes)
* Better internationalization
* Fully wrote in Kotlin (Although there maybe a lot of workaround because of the Database structure)
* Dependencies all cleaned up, now no more third-party code exists
* Save Character Card Image to Device Local Storage
* Switch display between Character's Voice Actor Name & Actual Name
* Improved Character Search result
* Support Taiwan server database
* Use Jetpack Compose to build new UI (Working in my another repository, TBD)

## Localization  
Japanese, English and Chinese fully supported, Korean partially supported.  
Korean strings are provided by [applemintia](https://twitter.com/_applemintia).  
English strings are provided by [southrop](https://github.com/southrop) & [MightyZanark](https://github.com/MightyZanark) (With some change made by Nyan Fantasia).  
Chinese Traditional strings and improved localization functions are provided by Nyan Fantasia.  
Any contribution is welcomed, I could handle it.

## References
ShizuruNotes is highly inspired by the following illustrious repos, thanks them:
* [PrincessGuide](https://github.com/superk589/PrincessGuide)
* [redive_master_db_diff](https://github.com/esterTion/redive_master_db_diff)
* [DereHelper](https://github.com/Lazyeraser/DereHelper)
* Authors of some snippets from StackOverFlow

ShizuruNotes also uses [these](OPENSOURCE.md) open source software.

## Related projects
* [KasumiNotes](https://github.com/HerDataSam/KasumiNotes) The KR server version of ShizuruNotes, developed by [HerDataSam](https://github.com/HerDataSam).  
* [ShizuruNotes Original](https://github.com/MalitsPlus/ShizuruNotes) The base code of this repo.
 
## License 
ShizuruNotes is licensed under the Apache License 2.0. 
