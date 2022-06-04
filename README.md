[繁體中文說明請點選此處](README_TW.md)

# ShizuruNotes
An **unofficial** Android tool application for "Princess Connect Re:Dive".  
It supports the server of Japan and Mainland China at present.

## Requirement
* Android 8+ (Doesn't support Android Emulator lower than Android 8)

## Build
Requires Android Studio Arctic Fox or later.  

Before executing build commands, you need to create a `local.properties` in project's root directory if it does not exist.

```sh
$ touch local.properties
```

#### Debug Version App
You can build app with **debug variant** by excuting the following command directly. 

```sh
$ ./gradlew :app:assembleDebug
```

#### Release Version App
If you want to build a release version app, you must add the following lines into `local.properties` first:

```sh
signing.storeFile=${PATH_TO_YOUR_KEY_STORE_FILE}
signing.storePassword=${YOUR_KEY_STORE_PASSWORD}
signing.keyAlias=${YOUR_KEY_ALIAS}
signing.keyPassword=${YOUR_KEY_PASSWORD}
```

Then you can excute build command with **release variant**:

```sh
$ ./gradlew :app:assembleRelease
```

The above instruction is set for OG app, may not work somehow for this repo

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

## Differences between the [Original ShizuruNotes](https://github.com/MalitsPlus/ShizuruNotes)
* Better internationalization
* Kotlin migration (Working)
* Dependencies cleanup (Almost done)
* TBD

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

ShizuruNotes also uses [these](OPENSOURCE.md) open source software.

## Related projects
* [KasumiNotes](https://github.com/HerDataSam/KasumiNotes) The KR server version of ShizuruNotes, developed by [HerDataSam](https://github.com/HerDataSam).  
* [ShizuruNotes Original](https://github.com/MalitsPlus/ShizuruNotes) The base code of this repo.
 
## License 
ShizuruNotes is licensed under the Apache License 2.0. 
